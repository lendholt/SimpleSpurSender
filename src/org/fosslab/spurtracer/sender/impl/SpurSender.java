/* 
 * Copyright (C) 2012  GFZ German Research Centre for Geosciences 
 * (http://www.gfz-potsdam.de)
 * 
 * This program was developed within the context of the following 
 * publicly funded projects:
 * - DEWS, EU 6th Framework Programme, Grant Agreement 045453
 *   (http://www.dews-online.org)
 * - TRIDEC, EU 7th Framework Programme, Grant Agreement 258723
 *   (http://www.tridec-online.eu)
 * 
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.fosslab.spurtracer.sender.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fosslab.spurtracer.sender.ISpurSender;
import org.fosslab.spurtracer.sender.NotificationStatus;
import org.fosslab.spurtracer.sender.SpurFailureException;

/**
 * Client for Spurtracer {@link http://spurtracer.sourceforge.net/}
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public class SpurSender implements ISpurSender {

	/**
	 * The HTTP Request type. Currently only GET supported. (PUT not yet
	 * implemented by Spurtracer)
	 */
	private static String HTTP_REQUEST_TYPE = "GET"; //$NON-NLS-1$

	/**
	 * The Spurtracer command for putting new notifications. Necessary until
	 * HTTP PUT is supported by Spurtracer
	 */
	private static String SPURTRACER_NOTIFICATION_COMMAND = "/set"; //$NON-NLS-1$

	/** Default timeout for sending spurtraces via http. */
	private static int DEFAULT_CONNECTION_TIMEOUT = 2000; //$NON-NLS-1$

	/**
	 * Static (!) executor service to limit maximum number of parallel Threads
	 * used for sending spurtraces
	 */
	private static ExecutorService SPUR_SENDER = Executors.newFixedThreadPool(8);

	public static String DEFAULT_CHARACTER_ENCODING = "UTF-8"; //$NON-NLS-1$

	/** Basic URL of a Spurtrace server */
	URL spurTracer;

	/**
	 * Creates a new Spurtrace client to the given Spurtrace server
	 * 
	 * @param spurTracer
	 */
	public SpurSender(URL spurTracer) {
		this.spurTracer = spurTracer;
	}

	@Override
	public void notify(String host, String component, String context, NotificationStatus status, String description)
			throws SpurFailureException {
		// type=n (notification)
		Map<String, String> parameters = createBasicParameters(host, component, context, "n");//$NON-NLS-1$
		parameters.put("status", status.name());//$NON-NLS-1$
		if (description != null && !description.isEmpty()) {//$NON-NLS-1$
			parameters.put("desc", description);//$NON-NLS-1$
		}
		send(parameters);
	}

	@Override
	public void notify(String host, String component, String context, NotificationStatus status)
			throws SpurFailureException {
		this.notify(host, component, context, status, null);
	}

	@Override
	public void announceContext(String host, String component, String context, String newComponent, String newContext)
			throws SpurFailureException {
		// type=c (contextAnnouncement)
		Map<String, String> parameters = createBasicParameters(host, component, context, "c");//$NON-NLS-1$
		parameters.put("newcomponent", newComponent);//$NON-NLS-1$
		parameters.put("newctxt", newContext);//$NON-NLS-1$
		send(parameters);
	}

	/**
	 * Creates the basic parameter map. This includes the timestamp.
	 * 
	 * @param host
	 * @param component
	 * @param context
	 * @param type
	 * @return
	 * @throws SpurFailureException
	 */
	private Map<String, String> createBasicParameters(String host, String component, String context, String type)
			throws SpurFailureException {
		if (host == null || host.isEmpty()) {
			throw new SpurFailureException("host must not be null", new NullPointerException(), null);//$NON-NLS-1$
		}
		if (component == null || component.isEmpty()) {
			throw new SpurFailureException("component must not be null", new NullPointerException(), null);//$NON-NLS-1$
		}
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("host", host);//$NON-NLS-1$
		parameters.put("component", component);//$NON-NLS-1$
		parameters.put("ctxt", context);//$NON-NLS-1$
		parameters.put("type", type);//$NON-NLS-1$
		parameters.put("time", Long.toString(System.currentTimeMillis()));//$NON-NLS-1$
		return parameters;
	}

	/**
	 * Sends a spur to the server with the given parameters
	 * 
	 * @param parameters
	 * @throws SpurFailureException
	 */
	private void send(Map<String, String> parameters) throws SpurFailureException {
		StringBuilder parameterList = new StringBuilder();
		try {
			for (String parameter : parameters.keySet()) {
				parameterList.append(parameter + "="
						+ URLEncoder.encode(parameters.get(parameter), DEFAULT_CHARACTER_ENCODING) + "&"); //$NON-NLS-1$ $NON-NLS-2$
			}
		} catch (UnsupportedEncodingException e) {
			throw new SpurFailureException(e, parameters.toString());
		}
		parameterList.deleteCharAt(parameterList.length() - 1);
		String requestParameters = parameterList.toString();
		if (requestParameters != null && requestParameters.length() > 0) {
			String spurl = spurTracer.toString() + SPURTRACER_NOTIFICATION_COMMAND + "?" + requestParameters; //$NON-NLS-1$
			URL url = null;
			try {
				url = new URL(spurl);
			} catch (MalformedURLException e) {
				throw new SpurFailureException(e, spurl);
			}
			SPUR_SENDER.execute(new UrlTrigger(url));
		}
	}

	/**
	 * Runnable triggering the given URL via {@link HttpURLConnection}
	 * 
	 * @author Matthias Lendholt
	 * 
	 */
	static class UrlTrigger implements Runnable {
		private URL url;

		public UrlTrigger(URL url) {
			this.url = url;
		}

		@Override
		public void run() {
			HttpURLConnection httpCon;
			try {
				httpCon = (HttpURLConnection) url.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod(HTTP_REQUEST_TYPE);
				httpCon.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
				httpCon.connect();
				httpCon.getResponseMessage();
				httpCon.getResponseCode();
				httpCon.disconnect();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
