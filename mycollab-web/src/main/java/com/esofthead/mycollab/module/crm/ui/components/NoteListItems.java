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
package com.esofthead.mycollab.module.crm.ui.components;

import java.util.GregorianCalendar;
import java.util.List;

import org.vaadin.easyuploads.MultiFileUploadExt;

import com.esofthead.mycollab.common.CommentType;
import com.esofthead.mycollab.common.MonitorTypeConstants;
import com.esofthead.mycollab.common.domain.RelayEmailNotification;
import com.esofthead.mycollab.common.domain.SimpleComment;
import com.esofthead.mycollab.common.domain.criteria.CommentSearchCriteria;
import com.esofthead.mycollab.common.service.CommentService;
import com.esofthead.mycollab.common.service.RelayEmailNotificationService;
import com.esofthead.mycollab.common.ui.components.CommentRowDisplayHandler;
import com.esofthead.mycollab.common.ui.components.ReloadableComponent;
import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchField;
import com.esofthead.mycollab.core.arguments.StringSearchField;
import com.esofthead.mycollab.core.utils.DateTimeUtils;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.Note;
import com.esofthead.mycollab.module.crm.domain.SimpleNote;
import com.esofthead.mycollab.module.crm.domain.criteria.NoteSearchCriteria;
import com.esofthead.mycollab.module.crm.service.NoteService;
import com.esofthead.mycollab.module.ecm.domain.Content;
import com.esofthead.mycollab.module.file.AttachmentUtils;
import com.esofthead.mycollab.schedule.email.crm.AccountRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.CallRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.CampaignRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.CaseRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.ContactRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.LeadRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.MeetingRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.OpportunityRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.crm.TaskRelayEmailNotificationAction;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.AttachmentDisplayComponent;
import com.esofthead.mycollab.vaadin.ui.AttachmentPanel;
import com.esofthead.mycollab.vaadin.ui.BeanList;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.UrlDetectableLabel;
import com.esofthead.mycollab.vaadin.ui.UserAvatarControlFactory;
import com.esofthead.mycollab.vaadin.ui.BeanList.RowDisplayHandler;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class NoteListItems extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private String type;
	private Integer typeid;
	private VerticalLayout noteListContainer;
	private BeanList<NoteService, NoteSearchCriteria, SimpleNote> noteList;

	private final NoteService noteService;

	private Button createBtn;

	public NoteListItems(final String title) {
		this(title, "", 0);
	}

	public NoteListItems(final String title, final String type,
			final Integer typeid) {
		super();
		this.setWidth("100%");
		this.setMargin(false);
		addStyleName("note-list");

		noteService = ApplicationContextUtil.getSpringBean(NoteService.class);
		this.type = type;
		this.typeid = typeid;

		initUI();
	}

	public void setEnableCreateButton(boolean enabled) {
		createBtn.setEnabled(enabled);
	}

	private void addCreateBtn() {
		final Component component = this.getComponent(0);
		if (component instanceof NoteEditor) {
			this.replaceComponent(component, createBtn);
		}
	}

	private void displayNotes() {
		noteListContainer.removeAllComponents();
		noteListContainer.addComponent(noteList);

		final NoteSearchCriteria searchCriteria = new NoteSearchCriteria();
		searchCriteria.setType(new StringSearchField(SearchField.AND, type));
		searchCriteria.setTypeid(new NumberSearchField(typeid));
		noteList.setSearchCriteria(searchCriteria);
	}

	private void initUI() {
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, false, true));
		createBtn = new Button("New Note", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				NoteListItems.this
						.replaceComponent(createBtn, new NoteEditor());
			}
		});

		createBtn.setStyleName(UIConstants.THEME_BLUE_LINK);
		createBtn.setIcon(MyCollabResource
				.newResource("icons/16/addRecord.png"));
		this.addComponent(createBtn);

		noteList = new BeanList<NoteService, NoteSearchCriteria, SimpleNote>(
				noteService, NoteRowDisplayHandler.class);
		noteList.setDisplayEmptyListText(false);
		noteList.setStyleName("noteList");

		noteListContainer = new VerticalLayout();
		this.addComponent(noteListContainer);
		displayNotes();
	}

	public void showNotes(final String type, final int typeid) {
		this.type = type;
		this.typeid = typeid;
		displayNotes();
	}

	public static class NoteRowDisplayHandler implements
			RowDisplayHandler<SimpleNote>, ReloadableComponent {
		private static final long serialVersionUID = 1L;

		private VerticalLayout noteContentLayout;
		private BeanList<CommentService, CommentSearchCriteria, SimpleComment> commentList;
		private CommentInput commentInput;
		private SimpleNote note;
		private Button replyBtn;

		@Override
		public void cancel() {
			if (commentInput != null) {
				final int compIndex = noteContentLayout
						.getComponentIndex(commentInput);
				if (compIndex >= 0) {
					noteContentLayout.removeComponent(commentInput);
					commentInput = null;
					replyBtn.setVisible(true);
				}
			}
		}

		private Component constructNoteHeader(final SimpleNote note) {
			final HorizontalLayout layout = new HorizontalLayout();
			layout.setStyleName("message");
			layout.setSpacing(true);
			layout.setWidth("100%");
			layout.addComponent(UserAvatarControlFactory
					.createUserAvatarButtonLink(note.getCreatedUserAvatarId(),
							note.getCreateUserFullName()));

			final CssLayout rowLayout = new CssLayout();
			rowLayout.setStyleName("message-container");
			rowLayout.setWidth("100%");

			final HorizontalLayout messageHeader = new HorizontalLayout();
			messageHeader.setStyleName("message-header");
			final VerticalLayout leftHeader = new VerticalLayout();
			final Label username = new Label(note.getCreateUserFullName());
			username.setStyleName("user-name");
			leftHeader.addComponent(username);

			final VerticalLayout rightHeader = new VerticalLayout();
			final Label timePostLbl = new Label(
					DateTimeUtils.getStringDateFromNow(note.getCreatedtime()));
			timePostLbl.setSizeUndefined();
			timePostLbl.setStyleName("time-post");
			rightHeader.addComponent(timePostLbl);

			messageHeader.addComponent(leftHeader);
			messageHeader.setExpandRatio(leftHeader, 1.0f);
			messageHeader.addComponent(timePostLbl);
			messageHeader.setWidth("100%");

			rowLayout.addComponent(messageHeader);

			final Label messageContent = new UrlDetectableLabel(note.getNote());
			messageContent.setStyleName("message-body");
			rowLayout.addComponent(messageContent);

			final List<Content> attachments = note.getAttachments();
			if (attachments != null && !attachments.isEmpty()) {
				rowLayout.addComponent(new AttachmentDisplayComponent(
						attachments));
			}

			final VerticalLayout messageFooter = new VerticalLayout();
			replyBtn = new Button("Reply", new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(final ClickEvent event) {
					if (noteContentLayout.getComponentCount() > 0) {
						Component component = noteContentLayout
								.getComponent(noteContentLayout
										.getComponentCount() - 1);
						if (!(component instanceof CommentInput)) {
							commentInput = new CommentInput(
									NoteRowDisplayHandler.this,
									CommentType.CRM_NOTE, note.getId(), null,
									true, false);
							noteContentLayout.addComponent(commentInput,
									noteContentLayout.getComponentCount());
							replyBtn.setVisible(false);
						}
					}
				}
			});

			replyBtn.setStyleName("link");
			messageFooter.addComponent(replyBtn);
			messageFooter.setWidth("100%");
			messageFooter.setComponentAlignment(replyBtn, Alignment.TOP_RIGHT);
			rowLayout.addComponent(messageFooter);

			layout.addComponent(rowLayout);
			layout.setExpandRatio(rowLayout, 1.0f);
			return layout;
		}

		private void displayComments() {
			final CommentSearchCriteria searchCriteria = new CommentSearchCriteria();
			searchCriteria.setType(new StringSearchField(CommentType.CRM_NOTE
					.toString()));
			searchCriteria.setTypeid(new NumberSearchField(note.getId()));
			commentList.setSearchCriteria(searchCriteria);
		}

		@Override
		public Component generateRow(final SimpleNote note, final int rowIndex) {
			this.note = note;

			VerticalLayout commentListWrapper = new VerticalLayout();
			commentListWrapper.setWidth("100%");
			commentListWrapper.setMargin(new MarginInfo(false, false, false,
					true));
			commentListWrapper.addStyleName("comment-list-wrapper");

			noteContentLayout = new VerticalLayout();

			noteContentLayout.addComponent(constructNoteHeader(note));

			commentList = new BeanList<CommentService, CommentSearchCriteria, SimpleComment>(
					ApplicationContextUtil.getSpringBean(CommentService.class),
					CommentRowDisplayHandler.class);
			commentList.setDisplayEmptyListText(false);
			commentList.setWidth("100%");
			commentListWrapper.addComponent(commentList);
			noteContentLayout.addComponent(commentListWrapper);
			noteContentLayout.setComponentAlignment(commentListWrapper,
					Alignment.TOP_RIGHT);
			commentList.loadItems(note.getComments());

			return noteContentLayout;
		}

		@Override
		public void reload() {
			displayComments();
			cancel();
		}
	}

	private class NoteEditor extends VerticalLayout {

		private static final long serialVersionUID = 1L;
		private final RichTextArea noteArea;

		public NoteEditor() {
			super();
			setSpacing(true);
			this.setMargin(true);
			this.setWidth("600px");

			final AttachmentPanel attachments = new AttachmentPanel();

			noteArea = new RichTextArea();
			noteArea.setWidth("100%");
			noteArea.setHeight("200px");
			this.addComponent(noteArea);
			this.addComponent(attachments);

			final HorizontalLayout controls = new HorizontalLayout();
			controls.setSpacing(true);
			controls.setWidth("100%");

			final MultiFileUploadExt uploadExt = new MultiFileUploadExt(
					attachments);
			controls.addComponent(uploadExt);
			controls.setComponentAlignment(uploadExt, Alignment.MIDDLE_LEFT);

			final Label emptySpace = new Label();
			controls.addComponent(emptySpace);
			controls.setExpandRatio(emptySpace, 1.0f);

			final Button cancelBtn = new Button("Cancel",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							addCreateBtn();
						}
					});
			cancelBtn.setStyleName("link");
			controls.addComponent(cancelBtn);
			controls.setComponentAlignment(cancelBtn, Alignment.MIDDLE_RIGHT);

			final Button saveBtn = new Button("Post",
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(final ClickEvent event) {
							final Note note = new Note();
							note.setCreateduser(AppContext.getUsername());
							note.setNote((String) noteArea.getValue());
							note.setSaccountid(AppContext.getAccountId());
							note.setSubject("");
							note.setType(type);
							note.setTypeid(typeid);
							note.setCreatedtime(new GregorianCalendar()
									.getTime());
							note.setLastupdatedtime(new GregorianCalendar()
									.getTime());
							final int noteid = noteService.saveWithSession(
									note, AppContext.getUsername());

							// Save Relay Email -- having time must refact to
							// Aop
							// ------------------------------------------------------
							RelayEmailNotification relayNotification = new RelayEmailNotification();
							relayNotification.setChangeby(AppContext
									.getUsername());
							relayNotification
									.setChangecomment((String) noteArea
											.getValue());
							relayNotification.setSaccountid(AppContext
									.getAccountId());
							relayNotification.setType(type);
							relayNotification
									.setAction(MonitorTypeConstants.ADD_COMMENT_ACTION);
							relayNotification.setTypeid(typeid);
							if (type.equals(CrmTypeConstants.ACCOUNT)) {
								relayNotification
										.setEmailhandlerbean(AccountRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.CONTACT)) {
								relayNotification
										.setEmailhandlerbean(ContactRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.CAMPAIGN)) {
								relayNotification
										.setEmailhandlerbean(CampaignRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.LEAD)) {
								relayNotification
										.setEmailhandlerbean(LeadRelayEmailNotificationAction.class
												.getName());
							} else if (type
									.equals(CrmTypeConstants.OPPORTUNITY)) {
								relayNotification
										.setEmailhandlerbean(OpportunityRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.CASE)) {
								relayNotification
										.setEmailhandlerbean(CaseRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.TASK)) {
								relayNotification
										.setEmailhandlerbean(TaskRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.MEETING)) {
								relayNotification
										.setEmailhandlerbean(MeetingRelayEmailNotificationAction.class
												.getName());
							} else if (type.equals(CrmTypeConstants.CALL)) {
								relayNotification
										.setEmailhandlerbean(CallRelayEmailNotificationAction.class
												.getName());
							}
							RelayEmailNotificationService relayEmailNotificationService = ApplicationContextUtil
									.getSpringBean(RelayEmailNotificationService.class);
							relayEmailNotificationService.saveWithSession(
									relayNotification, AppContext.getUsername());
							// End save relay Email
							// --------------------------------------------------

							String attachmentPath = AttachmentUtils
									.getCrmNoteAttachmentPath(
											AppContext.getAccountId(), noteid);
							attachments.saveContentsToRepo(attachmentPath);
							displayNotes();
							addCreateBtn();
						}
					});
			saveBtn.setStyleName(UIConstants.THEME_BLUE_LINK);
			controls.addComponent(saveBtn);
			controls.setComponentAlignment(saveBtn, Alignment.MIDDLE_RIGHT);

			this.addComponent(controls);
		}
	}
}
