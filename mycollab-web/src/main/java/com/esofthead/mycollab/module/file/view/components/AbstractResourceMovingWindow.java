package com.esofthead.mycollab.module.file.view.components;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.esofthead.mycollab.module.ecm.domain.ExternalDrive;
import com.esofthead.mycollab.module.ecm.domain.ExternalFolder;
import com.esofthead.mycollab.module.ecm.domain.Folder;
import com.esofthead.mycollab.module.ecm.domain.Resource;
import com.esofthead.mycollab.module.ecm.service.ExternalDriveService;
import com.esofthead.mycollab.module.ecm.service.ExternalResourceService;
import com.esofthead.mycollab.module.ecm.service.ResourceMover;
import com.esofthead.mycollab.module.ecm.service.ResourceService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.UiUtils;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Tree.CollapseEvent;
import com.vaadin.ui.Tree.ExpandEvent;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.0
 * 
 */
public abstract class AbstractResourceMovingWindow extends Window {
	private static final long serialVersionUID = 1L;

	private final ResourceService resourceService;
	private final ExternalResourceService externalResourceService;
	private final ExternalDriveService externalDriveService;

	protected TreeTable folderTree;
	protected String rootPath;
	protected Folder baseFolder;
	private Resource resourceEditting;
	protected List<Resource> lstResEditting;
	private final ResourceMover resourceMover;
	private final boolean isNeedLoadExternalDrive;

	public AbstractResourceMovingWindow(Resource resource,
			boolean isNeedLoadExternalDrive) {
		super("Move File/Foler");
		center();
		this.setWidth("600px");
		this.resourceEditting = resource;
		this.resourceService = ApplicationContextUtil
				.getSpringBean(ResourceService.class);
		this.externalResourceService = ApplicationContextUtil
				.getSpringBean(ExternalResourceService.class);
		this.externalDriveService = ApplicationContextUtil
				.getSpringBean(ExternalDriveService.class);
		this.isNeedLoadExternalDrive = isNeedLoadExternalDrive;
		this.resourceMover = ApplicationContextUtil
				.getSpringBean(ResourceMover.class);
		constructBody();
	}

	public AbstractResourceMovingWindow(List<Resource> lstRes,
			boolean isNeedLoadExternalDrive) {
		super("Move File/Foler");
		center();
		this.setWidth("600px");
		this.lstResEditting = lstRes;
		this.resourceService = ApplicationContextUtil
				.getSpringBean(ResourceService.class);
		this.externalResourceService = ApplicationContextUtil
				.getSpringBean(ExternalResourceService.class);
		this.externalDriveService = ApplicationContextUtil
				.getSpringBean(ExternalDriveService.class);
		this.resourceMover = ApplicationContextUtil
				.getSpringBean(ResourceMover.class);
		this.isNeedLoadExternalDrive = isNeedLoadExternalDrive;

		constructBody();
	}

	private void constructBody() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setSpacing(true);
		contentLayout.setMargin(true);
		this.setContent(contentLayout);

		final HorizontalLayout resourceContainer = new HorizontalLayout();
		resourceContainer.setSizeFull();

		this.folderTree = new TreeTable();
		this.folderTree.setMultiSelect(false);
		this.folderTree.setSelectable(true);
		this.folderTree.setImmediate(true);
		this.folderTree.addContainerProperty("Name", String.class, "");
		this.folderTree.addContainerProperty("Date Modified", String.class, "");
		this.folderTree.setColumnWidth("Date Modified",
				UIConstants.TABLE_DATE_TIME_WIDTH);
		this.folderTree.setColumnExpandRatio("Name", 1.0f);
		this.folderTree.setWidth("100%");

		resourceContainer.addComponent(this.folderTree);

		this.folderTree.addExpandListener(new Tree.ExpandListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void nodeExpand(final ExpandEvent event) {
				final Folder expandFolder = (Folder) event.getItemId();
				// load externalResource if currentExpandFolder is
				// rootFolder
				if (rootPath.equals(expandFolder.getPath())
						&& AbstractResourceMovingWindow.this.isNeedLoadExternalDrive) {
					List<ExternalDrive> externalDrives = externalDriveService
							.getExternalDrivesOfUser(AppContext.getUsername());
					for (ExternalDrive externalDrive : externalDrives) {
						ExternalFolder externalMapFolder = new ExternalFolder();
						externalMapFolder.setStorageName(externalDrive
								.getStoragename());
						externalMapFolder.setExternalDrive(externalDrive);
						externalMapFolder.setPath("/");
						externalMapFolder.setName(externalDrive.getFoldername());

						Calendar cal = GregorianCalendar.getInstance();
						cal.setTime(externalDrive.getCreatedtime());

						externalMapFolder.setCreated(cal);
						expandFolder.addChild(externalMapFolder);

						AbstractResourceMovingWindow.this.folderTree.addItem(
								new Object[] {
										externalMapFolder.getName(),
										AppContext
												.formatDateTime(externalMapFolder
														.getCreated().getTime()) },
								externalMapFolder);

						AbstractResourceMovingWindow.this.folderTree
								.setItemIcon(
										externalMapFolder,
										MyCollabResource
												.newResource("icons/16/ecm/dropbox.png"));
						AbstractResourceMovingWindow.this.folderTree
								.setItemCaption(externalMapFolder,
										externalMapFolder.getName());
						AbstractResourceMovingWindow.this.folderTree.setParent(
								externalMapFolder, expandFolder);
					}
				}
				if (expandFolder instanceof ExternalFolder) {
					List<ExternalFolder> subFolders = externalResourceService.getSubFolders(
							((ExternalFolder) expandFolder).getExternalDrive(),
							expandFolder.getPath());
					for (final Folder subFolder : subFolders) {
						expandFolder.addChild(subFolder);
						Date dateTime = ((ExternalFolder) subFolder)
								.getExternalDrive().getCreatedtime();

						AbstractResourceMovingWindow.this.folderTree.addItem(
								new Object[] { subFolder.getName(),
										AppContext.formatDateTime(dateTime) },
								subFolder);

						AbstractResourceMovingWindow.this.folderTree.setItemIcon(
								subFolder,
								MyCollabResource
										.newResource("icons/16/ecm/dropbox_subfolder.png"));
						AbstractResourceMovingWindow.this.folderTree
								.setItemCaption(subFolder, subFolder.getName());
						AbstractResourceMovingWindow.this.folderTree.setParent(
								subFolder, expandFolder);
					}
				} else {
					final List<Folder> subFolders = AbstractResourceMovingWindow.this.resourceService
							.getSubFolders(expandFolder.getPath());

					AbstractResourceMovingWindow.this.folderTree.setItemIcon(
							expandFolder,
							MyCollabResource
									.newResource("icons/16/ecm/folder_open.png"));

					if (subFolders != null) {
						for (final Folder subFolder : subFolders) {
							expandFolder.addChild(subFolder);
							AbstractResourceMovingWindow.this.folderTree.addItem(
									new Object[] {
											subFolder.getName(),
											AppContext.formatDateTime(subFolder
													.getCreated().getTime()) },
									subFolder);

							AbstractResourceMovingWindow.this.folderTree.setItemIcon(
									subFolder,
									MyCollabResource
											.newResource("icons/16/ecm/folder_close.png"));
							AbstractResourceMovingWindow.this.folderTree
									.setItemCaption(subFolder,
											subFolder.getName());
							AbstractResourceMovingWindow.this.folderTree
									.setParent(subFolder, expandFolder);
						}
					}
				}
			}
		});

		this.folderTree.addCollapseListener(new Tree.CollapseListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void nodeCollapse(final CollapseEvent event) {
				final Folder collapseFolder = (Folder) event.getItemId();
				if (collapseFolder instanceof ExternalFolder) {
					if (collapseFolder.getPath().equals("/"))
						AbstractResourceMovingWindow.this.folderTree.setItemIcon(
								collapseFolder,
								MyCollabResource
										.newResource("icons/16/ecm/dropbox.png"));
					else
						AbstractResourceMovingWindow.this.folderTree.setItemIcon(
								collapseFolder,
								MyCollabResource
										.newResource("icons/16/ecm/dropbox_subfolder.png"));
				} else {
					AbstractResourceMovingWindow.this.folderTree.setItemIcon(
							collapseFolder,
							MyCollabResource
									.newResource("icons/16/ecm/folder_close.png"));
				}
				for (Folder folder : collapseFolder.getChilds()) {
					recursiveRemoveSubItem(folder);
				}
			}

			private void recursiveRemoveSubItem(Folder collapseFolder) {
				List<Folder> childs = collapseFolder.getChilds();
				if (childs.size() > 0) {
					for (final Folder subFolder : childs) {
						recursiveRemoveSubItem(subFolder);
					}
					AbstractResourceMovingWindow.this.folderTree
							.removeItem(collapseFolder);
				} else {
					AbstractResourceMovingWindow.this.folderTree
							.removeItem(collapseFolder);
				}
			}
		});

		this.folderTree
				.addItemClickListener(new ItemClickEvent.ItemClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void itemClick(final ItemClickEvent event) {
						AbstractResourceMovingWindow.this.baseFolder = (Folder) event
								.getItemId();
					}
				});

		contentLayout.addComponent(resourceContainer);
		displayFiles();

		HorizontalLayout controlGroupBtnLayout = new HorizontalLayout();
		controlGroupBtnLayout.setSpacing(true);

		Button moveBtn = new Button("Move", new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (resourceEditting != null) {
					try {
						resourceMover.moveResource(resourceEditting,
								baseFolder, AppContext.getUsername(),
								AppContext.getAccountId());

						displayAfterMoveSuccess(
								AbstractResourceMovingWindow.this.baseFolder,
								false);
					} finally {
						AbstractResourceMovingWindow.this.close();
					}
				} else if (lstResEditting != null && lstResEditting.size() > 0) {
					boolean checkingFail = false;
					for (Resource res : lstResEditting) {
						try {
							resourceMover.moveResource(res, baseFolder,
									AppContext.getUsername(),
									AppContext.getAccountId());
						} catch (Exception e) {
							checkingFail = true;
						}
					}
					AbstractResourceMovingWindow.this.close();
					displayAfterMoveSuccess(
							AbstractResourceMovingWindow.this.baseFolder,
							checkingFail);
				}
			}

		});
		moveBtn.addStyleName(UIConstants.THEME_BLUE_LINK);
		controlGroupBtnLayout.addComponent(moveBtn);
		Button cancelBtn = new Button("Cancel", new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AbstractResourceMovingWindow.this.close();
			}
		});
		cancelBtn.addStyleName(UIConstants.THEME_BLUE_LINK);
		controlGroupBtnLayout.addComponent(cancelBtn);

		UiUtils.addComponent(contentLayout, controlGroupBtnLayout,
				Alignment.MIDDLE_CENTER);
	}

	public abstract void displayAfterMoveSuccess(Folder folder, boolean checking);

	protected abstract void displayFiles();
}
