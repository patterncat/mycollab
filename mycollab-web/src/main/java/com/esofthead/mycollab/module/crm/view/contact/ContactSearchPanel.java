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
package com.esofthead.mycollab.module.crm.view.contact;

import com.esofthead.mycollab.common.localization.GenericI18Enum;
import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchCriteria;
import com.esofthead.mycollab.core.arguments.SearchField;
import com.esofthead.mycollab.core.arguments.SetSearchField;
import com.esofthead.mycollab.core.arguments.StringSearchField;
import com.esofthead.mycollab.core.db.query.Param;
import com.esofthead.mycollab.core.utils.LocalizationHelper;
import com.esofthead.mycollab.core.utils.StringUtils;
import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.criteria.ContactSearchCriteria;
import com.esofthead.mycollab.module.crm.events.ContactEvent;
import com.esofthead.mycollab.module.user.ui.components.ActiveUserListSelect;
import com.esofthead.mycollab.security.RolePermissionCollections;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.DefaultGenericSearchPanel;
import com.esofthead.mycollab.vaadin.ui.DynamicQueryParamLayout;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.Separator;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.UiUtils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@SuppressWarnings("serial")
public class ContactSearchPanel extends
		DefaultGenericSearchPanel<ContactSearchCriteria> {

	private static Param[] paramFields = new Param[] {
			ContactSearchCriteria.p_firstname,
			ContactSearchCriteria.p_lastname,
			ContactSearchCriteria.p_leadsource,
			ContactSearchCriteria.p_billingCountry,
			ContactSearchCriteria.p_shippingCountry,
			ContactSearchCriteria.p_anyPhone, ContactSearchCriteria.p_anyEmail,
			ContactSearchCriteria.p_anyCity, ContactSearchCriteria.p_assignee };

	private HorizontalLayout createSearchTopPanel() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setSpacing(true);
		layout.setMargin(true);

		final Image titleIcon = new Image(null,
				MyCollabResource.newResource("icons/22/crm/contact.png"));
		layout.addComponent(titleIcon);
		layout.setComponentAlignment(titleIcon, Alignment.MIDDLE_LEFT);

		final Label searchtitle = new Label("Contacts");
		searchtitle.setStyleName(Reindeer.LABEL_H2);
		layout.addComponent(searchtitle);
		layout.setExpandRatio(searchtitle, 1.0f);
		layout.setComponentAlignment(searchtitle, Alignment.MIDDLE_LEFT);

		final Button createAccountBtn = new Button("Create",
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(final ClickEvent event) {
						EventBus.getInstance().fireEvent(
								new ContactEvent.GotoAdd(this, null));
					}
				});
		createAccountBtn.setIcon(MyCollabResource
				.newResource("icons/16/addRecord.png"));
		createAccountBtn.setStyleName(UIConstants.THEME_BLUE_LINK);
		createAccountBtn.setEnabled(AppContext
				.canWrite(RolePermissionCollections.CRM_CONTACT));

		UiUtils.addComponent(layout, createAccountBtn, Alignment.MIDDLE_RIGHT);

		return layout;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BasicSearchLayout<ContactSearchCriteria> createBasicSearchLayout() {
		return new ContactBasicSearchLayout();
	}

	@Override
	protected SearchLayout<ContactSearchCriteria> createAdvancedSearchLayout() {
		return new ContactAdvancedSearchLayout();
	}

	@SuppressWarnings("rawtypes")
	private class ContactBasicSearchLayout extends BasicSearchLayout {

		private static final long serialVersionUID = 1L;
		private TextField nameField;
		private CheckBox myItemCheckbox;

		@SuppressWarnings("unchecked")
		public ContactBasicSearchLayout() {
			super(ContactSearchPanel.this);
		}

		@Override
		public ComponentContainer constructHeader() {
			return ContactSearchPanel.this.createSearchTopPanel();
		}

		@Override
		public ComponentContainer constructBody() {
			final HorizontalLayout layout = new HorizontalLayout();
			layout.setSpacing(false);
			layout.setMargin(true);
			// layout.addComponent(new Label("Name"));
			this.nameField = this.createSeachSupportTextField(new TextField(),
					"NameFieldOfBasicSearch");
			this.nameField.setWidth(UIConstants.DEFAULT_CONTROL_WIDTH);
			UiUtils.addComponent(layout, this.nameField,
					Alignment.MIDDLE_CENTER);

			final Button searchBtn = new Button();
			searchBtn.setStyleName("search-icon-button");
			searchBtn.setIcon(MyCollabResource
					.newResource("icons/16/search_white.png"));

			searchBtn.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					ContactBasicSearchLayout.this.callSearchAction();
				}
			});

			UiUtils.addComponent(layout, searchBtn, Alignment.MIDDLE_LEFT);

			this.myItemCheckbox = new CheckBox(
					LocalizationHelper
							.getMessage(GenericI18Enum.SEARCH_MYITEMS_CHECKBOX));
			this.myItemCheckbox.setWidth("75px");
			UiUtils.addComponent(layout, this.myItemCheckbox,
					Alignment.MIDDLE_CENTER);

			final Separator separator1 = new Separator();

			UiUtils.addComponent(layout, separator1, Alignment.MIDDLE_LEFT);

			final Button cancelBtn = new Button(
					LocalizationHelper.getMessage(GenericI18Enum.BUTTON_CLEAR));
			cancelBtn.setStyleName(UIConstants.THEME_LINK);
			cancelBtn.addStyleName("cancel-button");
			cancelBtn.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					ContactBasicSearchLayout.this.nameField.setValue("");
				}
			});
			UiUtils.addComponent(layout, cancelBtn, Alignment.MIDDLE_CENTER);

			final Separator separator2 = new Separator();

			UiUtils.addComponent(layout, separator2, Alignment.MIDDLE_LEFT);

			final Button advancedSearchBtn = new Button(
					LocalizationHelper
							.getMessage(GenericI18Enum.BUTTON_ADVANCED_SEARCH),
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							ContactSearchPanel.this
									.moveToAdvancedSearchLayout();
						}
					});
			advancedSearchBtn.setStyleName("link");
			UiUtils.addComponent(layout, advancedSearchBtn,
					Alignment.MIDDLE_CENTER);
			return layout;
		}

		@Override
		protected SearchCriteria fillupSearchCriteria() {
			final ContactSearchCriteria searchCriteria = new ContactSearchCriteria();
			searchCriteria.setSaccountid(new NumberSearchField(SearchField.AND,
					AppContext.getAccountId()));
			if (StringUtils.isNotNullOrEmpty(this.nameField.getValue()
					.toString().trim())) {
				searchCriteria.setContactName(new StringSearchField(
						SearchField.AND, this.nameField.getValue().toString()
								.trim()));
			}

			if (this.myItemCheckbox.getValue()) {
				searchCriteria.setAssignUsers(new SetSearchField<String>(
						SearchField.AND, new String[] { AppContext
								.getUsername() }));
			} else {
				searchCriteria.setAssignUsers(null);
			}
			return searchCriteria;
		}
	}

	private class ContactAdvancedSearchLayout extends
			DynamicQueryParamLayout<ContactSearchCriteria> {

		public ContactAdvancedSearchLayout() {
			super(ContactSearchPanel.this, CrmTypeConstants.CONTACT);
		}

		@Override
		public ComponentContainer constructHeader() {
			return ContactSearchPanel.this.createSearchTopPanel();
		}

		@Override
		public Param[] getParamFields() {
			return paramFields;
		}

		@Override
		protected Class<ContactSearchCriteria> getType() {
			return ContactSearchCriteria.class;
		}

		protected Component buildSelectionComp(String fieldId) {
			if ("contact-assignuser".equals(fieldId)) {
				return new ActiveUserListSelect();
			}
			return null;
		}
	}
}
