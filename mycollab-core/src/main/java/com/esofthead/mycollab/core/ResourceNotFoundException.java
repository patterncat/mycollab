/**
 * This file is part of mycollab-core.
 *
 * mycollab-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.core;

/**
 * This exception occur when MyCollab can not find any resource (Document, User,
 * etc)
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class ResourceNotFoundException extends MyCollabException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException() {
		super("");
	}

	public ResourceNotFoundException(final Throwable e) {
		super(e);
	}

	public ResourceNotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
