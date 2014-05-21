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
package de.lendholt.spurtracer.sender.impl;

import java.net.URL;

import de.lendholt.spurtracer.sender.IComponentSpurSender;
import de.lendholt.spurtracer.sender.NotificationStatus;
import de.lendholt.spurtracer.sender.SpurFailureException;

/**
 * Client for Spurtracer with fixed host and component values
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public class ComponentSpurSender extends HostSpurSender implements IComponentSpurSender {

	/** The component value to be used */
	String component;

	/**
	 * Creates a new Spurtrace client to the given Spurtrace server with the
	 * given host and component values
	 * 
	 * @param myHost
	 * @param myComponent
	 * @param spurTracer
	 */
	public ComponentSpurSender(String myHost, String myComponent, URL spurTracer) {
		super(myHost, spurTracer);
		this.component = myComponent;
	}

	@Override
	public void announceContext(String context, String newComponent, String newContext) throws SpurFailureException {
		super.announceContext(component, context, newComponent, newContext);
	}

	@Override
	public void notify(String context, NotificationStatus status, String description) throws SpurFailureException {
		super.notify(component, context, status, description);
	}

	@Override
	public void notify(String context, NotificationStatus status) throws SpurFailureException {
		super.notify(component, context, status);
	}

}
