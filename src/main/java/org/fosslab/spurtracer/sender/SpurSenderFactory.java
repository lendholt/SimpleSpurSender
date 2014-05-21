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

import java.net.URL;

import org.fosslab.spurtracer.sender.impl.ComponentSpurSender;
import org.fosslab.spurtracer.sender.impl.HostSpurSender;
import org.fosslab.spurtracer.sender.impl.InterfaceSpurSender;
import org.fosslab.spurtracer.sender.impl.SpurSender;

/**
 * Factory for creating {@link ISpurSender}, {@link IHostSpurSender},
 * {@link IComponentSpurSender} and {@link IInterfaceSpurSender}
 * 
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public class SpurSenderFactory {

	/**
	 * Creates a new {@link ISpurSender}
	 * 
	 * @param spurTracer
	 * @return
	 */
	public static ISpurSender createSpurSender(URL spurTracer) {
		return new SpurSender(spurTracer);
	}

	/**
	 * Creates a new {@link IHostSpurSender}
	 * 
	 * @param myHost
	 * @param spurTracer
	 */
	public static IHostSpurSender createHostSpurSender(String myHost, URL spurTracer) {
		return new HostSpurSender(myHost, spurTracer);
	}

	/**
	 * Creates a new {@link IComponentSpurSender}
	 * 
	 * @param myHost
	 * @param myComponent
	 * @param spurTracer
	 * @return
	 */
	public static IComponentSpurSender createComponentSpurSender(String myHost, String myComponent, URL spurTracer) {
		return new ComponentSpurSender(myHost, myComponent, spurTracer);
	}

	/**
	 * Creates a new {@link IInterfaceSpurSender}
	 * 
	 * @param myHost
	 * @param myComponent
	 * @param newComponent
	 * @param spurTracer
	 * @return
	 */
	public static IInterfaceSpurSender createInterfaceSpurSender(String myHost, String myComponent,
			String newComponent, URL spurTracer) {
		return new InterfaceSpurSender(myHost, myComponent, newComponent, spurTracer);
	}
}
