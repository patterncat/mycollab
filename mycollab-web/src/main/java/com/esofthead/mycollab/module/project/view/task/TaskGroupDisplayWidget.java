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
package com.esofthead.mycollab.module.project.view.task;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.hene.popupbutton.PopupButton;

import com.esofthead.mycollab.common.localization.GenericI18Enum;
import com.esofthead.mycollab.configuration.SiteConfiguration;
import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchField;
import com.esofthead.mycollab.core.arguments.SetSearchField;
import com.esofthead.mycollab.core.utils.LocalizationHelper;
import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.domain.SimpleTaskList;
import com.esofthead.mycollab.module.project.domain.criteria.TaskListSearchCriteria;
import com.esofthead.mycollab.module.project.domain.criteria.TaskSearchCriteria;
import com.esofthead.mycollab.module.project.events.TaskListEvent;
import com.esofthead.mycollab.module.project.service.ProjectTaskListService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.BeanList;
import com.esofthead.mycollab.vaadin.ui.ConfirmDialogExt;
import com.esofthead.mycollab.vaadin.ui.Depot;
import com.esofthead.mycollab.vaadin.ui.Separator;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class TaskGroupDisplayWidget
		extends
		BeanList<ProjectTaskListService, TaskListSearchCriteria, SimpleTaskList> {
	private static final long serialVersionUID = 1L;

	public TaskGroupDisplayWidget() {
		super(null, ApplicationContextUtil
				.getSpringBean(ProjectTaskListService.class),
				TaskListRowDisplayHandler.class);
		this.setDisplayEmptyListText(false);
	}

	public static class TaskListRowDisplayHandler implements
			BeanList.RowDisplayHandler<SimpleTaskList> {
		private static final long serialVersionUID = 1L;

		@Override
		public Component generateRow(final SimpleTaskList taskList,
				final int rowIndex) {
			return new TaskListDepot(taskList);
		}
	}

	static class TaskListDepot extends Depot {
		private static final long serialVersionUID = 1L;
		private final SimpleTaskList taskList;
		private PopupButton taskListFilterControl;
		private PopupButton taskListActionControl;
		private TaskDisplayComponent taskDisplayComponent;

		public TaskListDepot(final SimpleTaskList taskListParam) {
			super(taskListParam.getName(), null, new TaskDisplayComponent(
					taskListParam, true));
			if ("Closed".equals(taskListParam.getStatus())) {
				this.headerLbl.addStyleName(UIConstants.LINK_COMPLETED);
			}
			this.taskList = taskListParam;
			this.addStyleName("task-list");
			this.initHeader();
			this.setHeaderColor(true);
			this.taskDisplayComponent = (TaskDisplayComponent) this.bodyContent;
		}

		private void initHeader() {

			this.taskListFilterControl = new PopupButton("Active Tasks");
			this.taskListFilterControl.addStyleName("link");

			final VerticalLayout filterBtnLayout = new VerticalLayout();
			filterBtnLayout.setMargin(true);
			filterBtnLayout.setSpacing(true);
			filterBtnLayout.setWidth("200px");

			final Button allTasksFilterBtn = new Button("All Tasks",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListFilterControl
									.setPopupVisible(false);
							TaskListDepot.this.taskListFilterControl
									.setCaption("All Tasks");
							TaskListDepot.this.displayAllTasks();
						}
					});
			allTasksFilterBtn.setStyleName("link");
			filterBtnLayout.addComponent(allTasksFilterBtn);

			final Button activeTasksFilterBtn = new Button("Active Tasks Only",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListFilterControl
									.setPopupVisible(false);
							TaskListDepot.this.taskListFilterControl
									.setCaption("Active Tasks");
							TaskListDepot.this.displayActiveTasksOnly();
						}
					});
			activeTasksFilterBtn.setStyleName("link");
			filterBtnLayout.addComponent(activeTasksFilterBtn);

			final Button pendingTasksFilterBtn = new Button(
					"Pending Tasks Only", new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListFilterControl
									.setPopupVisible(false);
							TaskListDepot.this.taskListFilterControl
									.setCaption("Pending Tasks");
							TaskListDepot.this.displayPendingTasksOnly();
						}
					});
			pendingTasksFilterBtn.setStyleName("link");
			filterBtnLayout.addComponent(pendingTasksFilterBtn);

			final Button archievedTasksFilterBtn = new Button(
					"Archieved Tasks Only", new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListFilterControl
									.setCaption("Archieved Tasks");
							TaskListDepot.this.taskListFilterControl
									.setPopupVisible(false);
							TaskListDepot.this.displayInActiveTasks();
						}
					});
			archievedTasksFilterBtn.setStyleName("link");
			filterBtnLayout.addComponent(archievedTasksFilterBtn);
			this.taskListFilterControl.setContent(filterBtnLayout);
			this.addHeaderElement(this.taskListFilterControl);

			final Separator divider = new Separator();
			this.addHeaderElement(divider);

			this.taskListActionControl = new PopupButton("Action");
			this.taskListActionControl.addStyleName("link");
			this.addHeaderElement(this.taskListActionControl);

			final VerticalLayout actionBtnLayout = new VerticalLayout();
			actionBtnLayout.setMargin(true);
			actionBtnLayout.setSpacing(true);
			actionBtnLayout.setWidth("200px");
			this.taskListActionControl.setContent(actionBtnLayout);

			final Button readBtn = new Button("View",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListActionControl
									.setPopupVisible(false);
							EventBus.getInstance()
									.fireEvent(
											new TaskListEvent.GotoRead(event,
													TaskListDepot.this.taskList
															.getId()));
						}
					});
			readBtn.setEnabled(CurrentProjectVariables
					.canRead(ProjectRolePermissionCollections.TASKS));
			readBtn.setStyleName("link");
			actionBtnLayout.addComponent(readBtn);

			final Button editBtn = new Button("Edit",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListActionControl
									.setPopupVisible(false);
							EventBus.getInstance().fireEvent(
									new TaskListEvent.GotoEdit(event,
											TaskListDepot.this.taskList));
						}
					});
			editBtn.setEnabled(CurrentProjectVariables
					.canWrite(ProjectRolePermissionCollections.TASKS));
			editBtn.setStyleName("link");
			actionBtnLayout.addComponent(editBtn);

			final Button closeBtn = new Button("Close",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListActionControl
									.setPopupVisible(false);
							TaskListDepot.this.taskList.setStatus("Closed");
							final ProjectTaskListService taskListService = ApplicationContextUtil
									.getSpringBean(ProjectTaskListService.class);
							taskListService.updateWithSession(
									TaskListDepot.this.taskList,
									AppContext.getUsername());
							final Component parentComp = TaskListDepot.this
									.getParent();
							((TaskGroupDisplayWidget) parentComp.getParent())
									.removeRow(parentComp);
						}
					});
			closeBtn.setEnabled(CurrentProjectVariables
					.canWrite(ProjectRolePermissionCollections.TASKS));
			closeBtn.setStyleName("link");
			actionBtnLayout.addComponent(closeBtn);

			final Button deleteBtn = new Button("Delete",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							TaskListDepot.this.taskListActionControl
									.setPopupVisible(false);
							ConfirmDialogExt.show(
									UI.getCurrent(),
									LocalizationHelper.getMessage(
											GenericI18Enum.DELETE_DIALOG_TITLE,
											SiteConfiguration.getSiteName()),
									LocalizationHelper
											.getMessage(GenericI18Enum.CONFIRM_DELETE_RECORD_DIALOG_MESSAGE),
									LocalizationHelper
											.getMessage(GenericI18Enum.BUTTON_YES_LABEL),
									LocalizationHelper
											.getMessage(GenericI18Enum.BUTTON_NO_LABEL),
									new ConfirmDialog.Listener() {
										private static final long serialVersionUID = 1L;

										@Override
										public void onClose(
												final ConfirmDialog dialog) {
											if (dialog.isConfirmed()) {
												final ProjectTaskListService taskListService = ApplicationContextUtil
														.getSpringBean(ProjectTaskListService.class);
												taskListService
														.removeWithSession(
																TaskListDepot.this.taskList
																		.getId(),
																AppContext
																		.getUsername(),
																AppContext
																		.getAccountId());
												final Component parentComp = TaskListDepot.this
														.getParent();
												if (parentComp instanceof CssLayout) {
													((CssLayout) parentComp)
															.removeComponent(TaskListDepot.this);
												} else {
													((TaskGroupDisplayWidget) parentComp)
															.removeRow(TaskListDepot.this);
												}

											}
										}
									});
						}
					});
			deleteBtn.setEnabled(CurrentProjectVariables
					.canAccess(ProjectRolePermissionCollections.TASKS));
			deleteBtn.setStyleName("link");
			actionBtnLayout.addComponent(deleteBtn);
		}

		private TaskSearchCriteria createBaseSearchCriteria() {
			final TaskSearchCriteria criteria = new TaskSearchCriteria();
			criteria.setProjectid(new NumberSearchField(CurrentProjectVariables
					.getProjectId()));
			criteria.setTaskListId(new NumberSearchField(this.taskList.getId()));
			return criteria;
		}

		private void displayActiveTasksOnly() {
			final TaskSearchCriteria criteria = this.createBaseSearchCriteria();
			criteria.setStatuses(new SetSearchField<String>(SearchField.AND,
					new String[] { "Open" }));
			this.taskDisplayComponent.setSearchCriteria(criteria);
		}

		private void displayPendingTasksOnly() {
			final TaskSearchCriteria criteria = this.createBaseSearchCriteria();
			criteria.setStatuses(new SetSearchField<String>(SearchField.AND,
					new String[] { "Pending" }));
			this.taskDisplayComponent.setSearchCriteria(criteria);
		}

		private void displayAllTasks() {
			final TaskSearchCriteria criteria = this.createBaseSearchCriteria();
			this.taskDisplayComponent.setSearchCriteria(criteria);
		}

		private void displayInActiveTasks() {
			final TaskSearchCriteria criteria = this.createBaseSearchCriteria();
			criteria.setStatuses(new SetSearchField<String>(SearchField.AND,
					new String[] { "Closed" }));
			this.taskDisplayComponent.setSearchCriteria(criteria);
		}
	}

}
