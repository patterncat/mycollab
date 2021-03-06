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

import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.project.events.ProjectMemberEvent;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.UserAvatarControlFactory;
import com.vaadin.ui.Button;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class ProjectUserLink extends Button {
	private static final long serialVersionUID = 1L;

	public ProjectUserLink(final String username, String userAvatarId,
			final String displayName) {
		this(username, userAvatarId, displayName, true, false);
	}

	public ProjectUserLink(final String username, String userAvatarId,
			final String displayName, boolean useWordWrap) {
		this(username, userAvatarId, displayName, useWordWrap, false);
	}

	public ProjectUserLink(final String username, String userAvatarId,
			final String displayName, boolean useWordWrap,
			boolean isDisplayAvatar) {
		super(displayName, new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				EventBus.getInstance().fireEvent(
						new ProjectMemberEvent.GotoRead(this, username));
			}
		});

		if (isDisplayAvatar && username != null && !username.equals("")) {
			this.setIcon(UserAvatarControlFactory.createAvatarResource(
					userAvatarId, 16));
		}

		this.setStyleName("link");

		if (useWordWrap) {
			this.addStyleName(UIConstants.WORD_WRAP);
		}
	}
}
