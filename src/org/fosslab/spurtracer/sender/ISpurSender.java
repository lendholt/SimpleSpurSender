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
package org.fosslab.spurtracer.sender;

/**
 * Client for Spurtracer {@link http://spurtracer.sourceforge.net/}
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public interface ISpurSender {

	/**
	 * Sends a spurtrace notification
	 * 
	 * @param host
	 * @param component
	 * @param context
	 * @param status
	 * @param description
	 * @throws SpurFailureException
	 */
	public void notify(String host, String component, String context, NotificationStatus status, String description)
			throws SpurFailureException;

	/**
	 * Sends a spurtrace notification without notification
	 * 
	 * @param host
	 * @param component
	 * @param context
	 * @param status
	 * @throws SpurFailureException
	 */
	public void notify(String host, String component, String context, NotificationStatus status)
			throws SpurFailureException;

	/**
	 * Sends a spurtrace context announcement
	 * 
	 * @param host
	 * @param component
	 * @param context
	 * @param newComponent
	 * @param newContext
	 * @throws SpurFailureException
	 */
	public void announceContext(String host, String component, String context, String newComponent, String newContext)
			throws SpurFailureException;
}
