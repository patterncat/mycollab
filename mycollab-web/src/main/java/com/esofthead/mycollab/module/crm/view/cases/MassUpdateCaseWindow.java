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
package com.esofthead.mycollab.module.crm.view.cases;

import com.esofthead.mycollab.common.localization.GenericI18Enum;
import com.esofthead.mycollab.core.utils.LocalizationHelper;
import com.esofthead.mycollab.module.crm.domain.CaseWithBLOBs;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.esofthead.mycollab.vaadin.ui.GridFormLayoutHelper;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.MassUpdateWindow;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 2.0
 * 
 */
public class MassUpdateCaseWindow extends MassUpdateWindow<CaseWithBLOBs> {
	private static final long serialVersionUID = 1L;

	public MassUpdateCaseWindow(final String title,
			final CaseListPresenter presenter) {
		super(title, MyCollabResource.newResource("icons/18/crm/case.png"),
				new CaseWithBLOBs(), presenter);
	}

	@Override
	protected IFormLayoutFactory buildFormLayoutFactory() {
		return new MassUpdateContactFormLayoutFactory();
	}

	@Override
	protected AbstractBeanFieldGroupEditFieldFactory<CaseWithBLOBs> initBeanFormFieldFactory() {
		return new CaseEditFormFieldFactory<CaseWithBLOBs>(updateForm);
	}

	private class MassUpdateContactFormLayoutFactory implements
			IFormLayoutFactory {
		private static final long serialVersionUID = 1L;

		private GridFormLayoutHelper informationLayout;

		@Override
		public Layout getLayout() {
			final VerticalLayout formLayout = new VerticalLayout();
			formLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

			final Label organizationHeader = new Label("Case Information");
			organizationHeader.setStyleName(UIConstants.H2_STYLE2);
			formLayout.addComponent(organizationHeader);

			this.informationLayout = new GridFormLayoutHelper(2, 6, "100%",
					"167px", Alignment.MIDDLE_LEFT);

			this.informationLayout.getLayout().setWidth("100%");
			this.informationLayout.getLayout().setMargin(false);
			this.informationLayout.getLayout().setSpacing(false);
			this.informationLayout.getLayout().addStyleName(
					"colored-gridlayout");
			formLayout.addComponent(this.informationLayout.getLayout());

			formLayout.addComponent(buildButtonControls());

			return formLayout;
		}

		// priority, status, account name, origin, type, reason, assignuser
		@Override
		public boolean attachField(final Object propertyId, final Field<?> field) {
			if (propertyId.equals("priority")) {
				this.informationLayout.addComponent(field, "Priority", 0, 0);
			} else if (propertyId.equals("status")) {
				this.informationLayout.addComponent(field, "Status", 1, 0);
			} else if (propertyId.equals("accountid")) {
				this.informationLayout
						.addComponent(field, "Account Name", 0, 1);
			} else if (propertyId.equals("origin")) {
				this.informationLayout.addComponent(field, "Origin", 1, 1);
			} else if (propertyId.equals("type")) {
				this.informationLayout.addComponent(field, "Type", 0, 2);
			} else if (propertyId.equals("reason")) {
				this.informationLayout.addComponent(field, "Reason", 1, 2);
			} else if (propertyId.equals("assignuser")) {
				this.informationLayout.addComponent(field, LocalizationHelper
						.getMessage(GenericI18Enum.FORM_ASSIGNEE_FIELD), 0, 3,
						2, "297px", Alignment.TOP_LEFT);
			} else {
				return false;
			}

			return true;
		}
	}
}
