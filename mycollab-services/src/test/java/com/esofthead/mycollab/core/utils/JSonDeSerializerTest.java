/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.core.utils;

import org.junit.Assert;
import org.junit.Test;

import com.esofthead.mycollab.security.PermissionMap;

public class JSonDeSerializerTest {
	@Test
	public void testSerializeArray() {
		String[][] twoArr = { { "Nguyen", "Hai" }, { "eSoftHead", "MyCollab" } };
		String json = JsonDeSerializer.toJson(twoArr);

		String[][] newVal = JsonDeSerializer.fromJson(json, String[][].class);
		Assert.assertEquals(2, newVal.length);
		Assert.assertEquals("Nguyen", newVal[0][0]);

	}

	@Test
	public void testSerializePermissionMap() {
		PermissionMap map = new PermissionMap();
		map.addPath("a", 1);
		map.addPath("b", 2);

		String json = JsonDeSerializer.toJson(map);

		PermissionMap permissionMap = JsonDeSerializer.fromJson(json,
				PermissionMap.class);
		Assert.assertEquals(new Integer(1), permissionMap.get("a"));
	}
}
