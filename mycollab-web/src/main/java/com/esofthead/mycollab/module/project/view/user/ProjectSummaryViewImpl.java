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
package com.esofthead.mycollab.module.project.view.user;

import com.esofthead.mycollab.module.project.view.ProjectInformationComponent;
import com.esofthead.mycollab.vaadin.mvp.AbstractPageView;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@SuppressWarnings("serial")
@ViewComponent
public class ProjectSummaryViewImpl extends AbstractPageView implements
ProjectSummaryView {

	private final ProjectActivityStreamComponent activityPanel;
	private final ProjectInformationComponent prjView;
	private final ProjectMembersWidget membersWidget;
	private final ProjectTaskStatusComponent highlightWidget;
	private final ProjectMessageListComponent messageWidget;

	public ProjectSummaryViewImpl() {
		this.setMargin(true);
		this.setSpacing(true);

		this.prjView = new ProjectInformationComponent();
		this.addComponent(this.prjView);

		final HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setSpacing(true);
		this.addComponent(layout);

		final VerticalLayout leftPanel = new VerticalLayout();

		this.activityPanel = new ProjectActivityStreamComponent();
		leftPanel.addComponent(this.activityPanel);
		layout.addComponent(leftPanel);

		final VerticalLayout rightPanel = new VerticalLayout();
		rightPanel.setMargin(new MarginInfo(false, false, false, true));
		rightPanel.setSpacing(true);
		layout.addComponent(rightPanel);

		this.messageWidget = new ProjectMessageListComponent();
		rightPanel.addComponent(this.messageWidget);

		this.membersWidget = new ProjectMembersWidget();
		this.highlightWidget = new ProjectTaskStatusComponent();
		rightPanel.addComponent(this.membersWidget);
		rightPanel.addComponent(this.highlightWidget);
	}

	@Override
	public void displayDashboard() {
		this.activityPanel.showProjectFeeds();
		this.prjView.displayProjectInformation();
		this.membersWidget.showInformation();
		this.highlightWidget.showProjectTasksByStatus();
		this.messageWidget.showLatestMessages();
	}
}
