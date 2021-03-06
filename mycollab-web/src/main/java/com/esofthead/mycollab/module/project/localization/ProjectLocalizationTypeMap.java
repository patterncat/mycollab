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
package com.esofthead.mycollab.module.project.localization;

import java.util.HashMap;
import java.util.Map;

import com.esofthead.mycollab.core.MyCollabException;

public class ProjectLocalizationTypeMap {
	private static Map<String, ProjectTypeI18nEnum> typeMap;

	static {
		typeMap = new HashMap<String, ProjectTypeI18nEnum>();
		typeMap.put("Project", ProjectTypeI18nEnum.PROJECT_ITEM);
		typeMap.put("Message", ProjectTypeI18nEnum.MESSAGE_ITEM);
		typeMap.put("Milestone", ProjectTypeI18nEnum.PHASE_ITEM);
		typeMap.put("Task", ProjectTypeI18nEnum.TASK_ITEM);
		typeMap.put("TaskList", ProjectTypeI18nEnum.TASKGROUP_ITEM);
		typeMap.put("Bug", ProjectTypeI18nEnum.BUG_ITEM);
		typeMap.put("Component", ProjectTypeI18nEnum.COMPONENT_ITEM);
		typeMap.put("Version", ProjectTypeI18nEnum.VERSION_ITEM);
		typeMap.put("StandUp", ProjectTypeI18nEnum.STANDUP_ITEM);
		typeMap.put("Problem", ProjectTypeI18nEnum.PROBLEM_ITEM);
		typeMap.put("Risk", ProjectTypeI18nEnum.RISK_ITEM);
	}

	public static ProjectTypeI18nEnum getType(String key) {
		ProjectTypeI18nEnum result = typeMap.get(key);
		if (result == null) {
			throw new MyCollabException("Can not get key: " + key);
		}

		return result;
	}
}
