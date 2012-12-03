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
 * Exception indicating serious problems on sending notifications to a
 * spurtracer
 * 
 * @author <a href="mailto:lendholt@gmail.com">Matthias Lendholt</a>
 * 
 */
public class SpurFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	String spurl;

	public SpurFailureException(String arg0, Throwable arg1, String spurl) {
		super(arg0, arg1);
		this.spurl = spurl;
	}

	public SpurFailureException(String arg0, String spurl) {
		super(arg0);
		this.spurl = spurl;
	}

	public SpurFailureException(Throwable arg0, String spurl) {
		super(arg0);
		this.spurl = spurl;
	}

	String getSpurl() {
		return spurl;
	}
}
