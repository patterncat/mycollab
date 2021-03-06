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
package com.esofthead.mycollab.module.project.view.settings.component;

import java.util.List;

import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchRequest;
import com.esofthead.mycollab.core.arguments.StringSearchField;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectMemberStatusConstants;
import com.esofthead.mycollab.module.project.domain.SimpleProjectMember;
import com.esofthead.mycollab.module.project.domain.criteria.ProjectMemberSearchCriteria;
import com.esofthead.mycollab.module.project.service.ProjectMemberService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.ui.UserAvatarControlFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class ProjectMemberSelectionField extends CustomField<String> {

	private static final long serialVersionUID = 1L;

	private ComboBox userSelectionBox;

	public ProjectMemberSelectionField() {
		super();

		userSelectionBox = new ComboBox();
		userSelectionBox.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		userSelectionBox.setNullSelectionAllowed(false);

		ProjectMemberSearchCriteria criteria = new ProjectMemberSearchCriteria();
		criteria.setProjectId(new NumberSearchField(CurrentProjectVariables
				.getProjectId()));
		criteria.setStatus(new StringSearchField(
				ProjectMemberStatusConstants.ACTIVE));

		ProjectMemberService userService = ApplicationContextUtil
				.getSpringBean(ProjectMemberService.class);
		List<SimpleProjectMember> memberList = userService
				.findPagableListByCriteria(new SearchRequest<ProjectMemberSearchCriteria>(
						criteria, 0, Integer.MAX_VALUE));
		loadUserList(memberList);
	}

	private void loadUserList(List<SimpleProjectMember> memberList) {

		for (SimpleProjectMember member : memberList) {
			userSelectionBox.addItem(member.getUsername());
			userSelectionBox.setItemCaption(member.getUsername(),
					member.getDisplayName());
			userSelectionBox.setItemIcon(
					member.getUsername(),
					UserAvatarControlFactory.createAvatarResource(
							member.getMemberAvatarId(), 16));
		}
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {
		Object value = newDataSource.getValue();
		if (value instanceof String) {
			userSelectionBox.setValue(value);
		}
		super.setPropertyDataSource(newDataSource);
	}

	@Override
	public void commit() throws SourceException, InvalidValueException {
		this.setInternalValue((String) userSelectionBox.getValue());
		super.commit();
	}

	@Override
	protected Component initContent() {
		return userSelectionBox;
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

}
