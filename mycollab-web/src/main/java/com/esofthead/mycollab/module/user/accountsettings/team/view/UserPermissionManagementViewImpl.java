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

package com.esofthead.mycollab.module.user.accountsettings.team.view;

import com.esofthead.mycollab.vaadin.mvp.AbstractPageView;
import com.esofthead.mycollab.vaadin.mvp.PageView;
import com.esofthead.mycollab.vaadin.mvp.PresenterResolver;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.ui.TabsheetDecor;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;

/**
 * 
 * @author MyCollab Ltd.
 * @since 2.0
 */
@ViewComponent
public class UserPermissionManagementViewImpl extends AbstractPageView
		implements UserPermissionManagementView {
	private static final long serialVersionUID = 1L;
	private TabsheetDecor groupTab;
	private UserPresenter userPresenter;
	private RolePresenter rolePresenter;

	public UserPermissionManagementViewImpl() {
		groupTab = new TabsheetDecor();
		groupTab.setStyleName("tab-style3");

		this.addComponent(groupTab);

		buildComponents();
	}

	private void buildComponents() {
		userPresenter = PresenterResolver.getPresenter(UserPresenter.class);
		groupTab.addTab(userPresenter.initView(), "Users");

		rolePresenter = PresenterResolver.getPresenter(RolePresenter.class);
		groupTab.addTab(rolePresenter.initView(), "Roles");

		groupTab.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				Tab tab = (Tab) ((TabsheetDecor) event.getTabSheet())
						.getSelectedTabInfo();
				String caption = tab.getCaption();
				if ("Users".equals(caption)) {
					userPresenter.go(UserPermissionManagementViewImpl.this,
							null);
				} else if ("Roles".equals(caption)) {
					rolePresenter.go(UserPermissionManagementViewImpl.this,
							null);
				}
			}
		});

		userPresenter.go(this, null);

	}

	@Override
	public Component gotoSubView(String name) {
		PageView component = (PageView) ((Tab) groupTab.selectTab(name))
				.getComponent();
		return component;
	}

}
