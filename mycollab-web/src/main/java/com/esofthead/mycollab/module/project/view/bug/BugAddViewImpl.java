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
package com.esofthead.mycollab.module.project.view.bug;

import java.util.List;

import com.esofthead.mycollab.module.file.AttachmentType;
import com.esofthead.mycollab.module.project.ui.components.AbstractEditItemComp;
import com.esofthead.mycollab.module.project.ui.components.DefaultProjectFormViewFieldFactory.ProjectFormAttachmentUploadField;
import com.esofthead.mycollab.module.project.view.milestone.MilestoneComboBox;
import com.esofthead.mycollab.module.project.view.settings.component.ProjectMemberSelectionField;
import com.esofthead.mycollab.module.tracker.domain.Component;
import com.esofthead.mycollab.module.tracker.domain.SimpleBug;
import com.esofthead.mycollab.module.tracker.domain.Version;
import com.esofthead.mycollab.vaadin.events.HasEditFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.esofthead.mycollab.vaadin.ui.AdvancedEditBeanForm;
import com.esofthead.mycollab.vaadin.ui.EditFormControlsGenerator;
import com.esofthead.mycollab.vaadin.ui.GenericBeanForm;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.NumberField;
import com.vaadin.data.Property;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@ViewComponent
public class BugAddViewImpl extends AbstractEditItemComp<SimpleBug> implements
		BugAddView {

	private static final long serialVersionUID = 1L;

	private ProjectFormAttachmentUploadField attachmentUploadField;

	private ComponentMultiSelectField componentSelect;
	private VersionMultiSelectField affectedVersionSelect;
	private VersionMultiSelectField fixedVersionSelect;

	public BugAddViewImpl() {
		this.setMargin(new MarginInfo(true, false, false, false));
	}
	
	@Override
	public ProjectFormAttachmentUploadField getAttachUploadField() {
		return this.attachmentUploadField;
	}

	@Override
	public List<Component> getComponents() {
		return this.componentSelect.getSelectedItems();
	}

	@Override
	public List<Version> getAffectedVersions() {
		return this.affectedVersionSelect.getSelectedItems();
	}

	@Override
	public List<Version> getFixedVersion() {
		return this.fixedVersionSelect.getSelectedItems();
	}

	private class EditFormFieldFactory extends
			AbstractBeanFieldGroupEditFieldFactory<SimpleBug> {

		private static final long serialVersionUID = 1L;

		public EditFormFieldFactory(GenericBeanForm<SimpleBug> form) {
			super(form);
		}

		@Override
		protected Field<?> onCreateField(final Object propertyId) {

			if (propertyId.equals("environment")) {
				final RichTextArea field = new RichTextArea("", "");
				field.setNullRepresentation("");
				return field;
			} else if (propertyId.equals("description")) {
				final RichTextArea field = new RichTextArea("", "");
				field.setNullRepresentation("");
				return field;
			} else if (propertyId.equals("priority")) {
				if (beanItem.getPriority() == null) {
					beanItem.setPriority(BugPriorityStatusConstants.PRIORITY_MAJOR);
				}
				return new BugPriorityComboBox();
			} else if (propertyId.equals("assignuser")) {
				return new ProjectMemberSelectionField();
			} else if (propertyId.equals("id")) {
				BugAddViewImpl.this.attachmentUploadField = new ProjectFormAttachmentUploadField();
				if (beanItem.getId() != null) {
					BugAddViewImpl.this.attachmentUploadField.getAttachments(
							beanItem.getProjectid(),
							AttachmentType.PROJECT_BUG_TYPE, beanItem.getId());
				}
				return BugAddViewImpl.this.attachmentUploadField;
			} else if (propertyId.equals("severity")) {
				if (beanItem.getSeverity() == null) {
					beanItem.setSeverity(BugSeverityConstants.MAJOR);
				}
				return new BugSeverityComboBox();
			} else if (propertyId.equals("components")) {
				componentSelect = new ComponentMultiSelectField();
				return componentSelect;
			} else if (propertyId.equals("affectedVersions")) {
				affectedVersionSelect = new VersionMultiSelectField();
				return affectedVersionSelect;
			} else if (propertyId.equals("fixedVersions")) {
				fixedVersionSelect = new VersionMultiSelectField();
				return fixedVersionSelect;
			} else if (propertyId.equals("summary")) {
				final TextField tf = new TextField();
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter summary");
				return tf;
			} else if (propertyId.equals("milestoneid")) {
				final MilestoneComboBox milestoneBox = new MilestoneComboBox();
				milestoneBox
						.addValueChangeListener(new Property.ValueChangeListener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void valueChange(
									Property.ValueChangeEvent event) {
								String milestoneName = milestoneBox
										.getItemCaption(milestoneBox.getValue());
								beanItem.setMilestoneName(milestoneName);
							}
						});
				return milestoneBox;
			} else if (propertyId.equals("estimatetime")
					|| (propertyId.equals("estimateremaintime"))) {
				TextField field = new TextField();
				NumberField.extend(field);
				return field;
			}

			return null;
		}
	}

	@Override
	public HasEditFormHandlers<SimpleBug> getEditFormHandlers() {
		return this.editForm;
	}

	@Override
	protected String initFormTitle() {
		return (beanItem.getId() == null) ? "Create Bug" : beanItem
				.getSummary();
	}

	@Override
	protected Resource initFormIconResource() {
		return MyCollabResource.newResource("icons/22/project/menu_bug.png");
	}

	@Override
	protected ComponentContainer createButtonControls() {
		final HorizontalLayout controlPanel = new HorizontalLayout();
		controlPanel.setMargin(true);
		controlPanel.addStyleName("control-buttons");
		final Layout controlButtons = (new EditFormControlsGenerator<SimpleBug>(
				editForm)).createButtonControls();
		controlButtons.setSizeUndefined();
		controlPanel.addComponent(controlButtons);
		controlPanel.setWidth("100%");
		controlPanel.setComponentAlignment(controlButtons,
				Alignment.MIDDLE_CENTER);
		return controlPanel;
	}

	@Override
	protected AdvancedEditBeanForm<SimpleBug> initPreviewForm() {
		return new AdvancedEditBeanForm<SimpleBug>();
	}

	@Override
	protected IFormLayoutFactory initFormLayoutFactory() {
		return new BugAddFormLayoutFactory();
	}

	@Override
	protected AbstractBeanFieldGroupEditFieldFactory<SimpleBug> initBeanFormFieldFactory() {
		return new EditFormFieldFactory(editForm);
	}
}
