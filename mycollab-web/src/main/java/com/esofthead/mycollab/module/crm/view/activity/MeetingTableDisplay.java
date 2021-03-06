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

package com.esofthead.mycollab.module.crm.view.activity;

import java.util.GregorianCalendar;
import java.util.List;

import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.crm.domain.SimpleMeeting;
import com.esofthead.mycollab.module.crm.domain.criteria.MeetingSearchCriteria;
import com.esofthead.mycollab.module.crm.events.ActivityEvent;
import com.esofthead.mycollab.module.crm.service.MeetingService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.ButtonLink;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.table.DefaultPagedBeanTable;
import com.esofthead.mycollab.vaadin.ui.table.TableViewField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * 
 * @author MyCollab Ltd.
 */
public class MeetingTableDisplay extends
		DefaultPagedBeanTable<MeetingService, MeetingSearchCriteria, SimpleMeeting> {

	private static final long serialVersionUID = 1L;

	public MeetingTableDisplay(List<TableViewField> displaycolumns) {
		super(ApplicationContextUtil.getSpringBean(MeetingService.class),
				SimpleMeeting.class, displaycolumns);

		this.addGeneratedColumn("subject", new Table.ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public com.vaadin.ui.Component generateCell(Table source,
					final Object itemId, Object columnId) {
				final SimpleMeeting meeting = MeetingTableDisplay.this
						.getBeanByIndex(itemId);
				ButtonLink b = new ButtonLink(meeting.getSubject(),
						new Button.ClickListener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(Button.ClickEvent event) {
								EventBus.getInstance().fireEvent(
										new ActivityEvent.CallRead(this,
												meeting.getId()));
							}
						});
				b.addStyleName(UIConstants.LINK_COMPLETED);

				if ("Held".equals(meeting.getStatus())) {
					b.addStyleName(UIConstants.LINK_COMPLETED);
				} else {
					if (meeting.getEnddate() != null
							&& (meeting.getEnddate()
									.before(new GregorianCalendar().getTime()))) {
						b.addStyleName(UIConstants.LINK_OVERDUE);
					}
				}
				return b;

			}
		});

		this.addGeneratedColumn("startdate", new Table.ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public com.vaadin.ui.Component generateCell(Table source,
					final Object itemId, Object columnId) {
				final SimpleMeeting meeting = MeetingTableDisplay.this
						.getBeanByIndex(itemId);
				return new Label(AppContext.formatDateTime(meeting
						.getStartdate()));

			}
		});
	}
}
