/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.common;

import org.junit.Test;

import com.esofthead.mycollab.module.common.view.InvalidSomeView;
import com.esofthead.mycollab.vaadin.mvp.ViewManager;

public class ViewManagerTest {

	@Test
	public void testGetViewClassSuccess() {
		// SomeView view = ViewManager.getView(SomeView.class);
		// Assert.assertEquals(SomeViewImpl.class, view.getClass());
	}

	@Test(expected = RuntimeException.class)
	public void testNotFindView() {
		ViewManager.getView(InvalidSomeView.class);
	}
}
