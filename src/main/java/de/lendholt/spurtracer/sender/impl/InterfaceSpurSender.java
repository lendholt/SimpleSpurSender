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

import de.lendholt.spurtracer.sender.IInterfaceSpurSender;
import de.lendholt.spurtracer.sender.SpurFailureException;

/**
 * Client for Spurtracer with fixed values for context announcement for a
 * certain interface
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public class InterfaceSpurSender extends ComponentSpurSender implements IInterfaceSpurSender {

	/** The newcomponent value to be used */
	String newComponent;

	/**
	 * Creates a new Spurtrace client to the given Spurtrace server with the
	 * given host, component and newcomponent values
	 * 
	 * @param myHost
	 * @param myComponent
	 * @param spurTracer
	 */
	public InterfaceSpurSender(String myHost, String myComponent, String newComponent, URL spurTracer) {
		super(myHost, myComponent, spurTracer);
		this.newComponent = newComponent;
	}

	@Override
	public void announceContext(String context, String newContext) throws SpurFailureException {
		super.announceContext(context, newComponent, newContext);
	}

}
