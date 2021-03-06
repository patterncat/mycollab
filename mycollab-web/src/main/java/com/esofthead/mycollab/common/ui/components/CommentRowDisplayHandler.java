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

package com.esofthead.mycollab.common.ui.components;

import java.util.List;

import com.esofthead.mycollab.common.domain.SimpleComment;
import com.esofthead.mycollab.core.utils.DateTimeUtils;
import com.esofthead.mycollab.module.ecm.domain.Content;
import com.esofthead.mycollab.vaadin.ui.AttachmentDisplayComponent;
import com.esofthead.mycollab.vaadin.ui.BeanList;
import com.esofthead.mycollab.vaadin.ui.UrlDetectableLabel;
import com.esofthead.mycollab.vaadin.ui.UserAvatarControlFactory;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class CommentRowDisplayHandler implements
BeanList.RowDisplayHandler<SimpleComment> {
	private static final long serialVersionUID = 1L;

	@Override
	public Component generateRow(SimpleComment comment, int rowIndex) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setStyleName("message");
		layout.setWidth("100%");
		layout.setSpacing(true);
		VerticalLayout userBlock = new VerticalLayout();
		userBlock.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		userBlock.setWidth("80px");
		userBlock.setSpacing(true);
		userBlock.addComponent(UserAvatarControlFactory
				.createUserAvatarButtonLink(
						comment.getOwnerAvatarId(),
						comment.getOwnerFullName()));
		Label userName = new Label(comment.getOwnerFullName());
		userName.setStyleName("user-name");
		userBlock.addComponent(userName);
		layout.addComponent(userBlock);

		CssLayout rowLayout = new CssLayout();
		rowLayout.setStyleName("message-container");
		rowLayout.setWidth("100%");

		HorizontalLayout messageHeader = new HorizontalLayout();
		messageHeader.setStyleName("message-header");
		VerticalLayout leftHeader = new VerticalLayout();

		VerticalLayout rightHeader = new VerticalLayout();
		Label timePostLbl = new Label(
				DateTimeUtils.getStringDateFromNow(comment.getCreatedtime()));
		timePostLbl.setSizeUndefined();
		timePostLbl.setStyleName("time-post");
		rightHeader.addComponent(timePostLbl);

		messageHeader.addComponent(leftHeader);
		messageHeader.setExpandRatio(leftHeader, 1.0f);
		messageHeader.addComponent(timePostLbl);
		messageHeader.setWidth("100%");
		messageHeader.setMargin(new MarginInfo(true, true, false, true));

		rowLayout.addComponent(messageHeader);

		Label messageContent = new UrlDetectableLabel(comment.getComment());
		messageContent.setStyleName("message-body");
		rowLayout.addComponent(messageContent);

		List<Content> attachments = comment.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			VerticalLayout messageFooter = new VerticalLayout();
			messageFooter.setWidth("100%");
			messageFooter.setStyleName("message-footer");
			AttachmentDisplayComponent attachmentDisplay = new AttachmentDisplayComponent(attachments);
			messageFooter.addComponent(attachmentDisplay);
			messageFooter.setMargin(true);
			messageFooter.setComponentAlignment(attachmentDisplay, Alignment.MIDDLE_RIGHT);
			rowLayout.addComponent(messageFooter);
		}

		layout.addComponent(rowLayout);
		layout.setExpandRatio(rowLayout, 1.0f);
		return layout;
	}
}
