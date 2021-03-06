package com.esofthead.mycollab.mobile.module.crm.ui;

import com.esofthead.mycollab.mobile.ui.AbstractBeanFieldGroupViewFieldFactory;
import com.esofthead.mycollab.mobile.ui.AdvancedPreviewBeanForm;
import com.esofthead.mycollab.mobile.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.mvp.AbstractMobilePageView;
import com.esofthead.vaadin.mobilecomponent.MobileViewToolbar;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;

/**
 * 
 * @author MyCollab Ltd.
 * @since 3.0
 * 
 * @param <B>
 */
public abstract class AbstractPreviewItemComp<B> extends AbstractMobilePageView {
	private static final long serialVersionUID = 1L;

	protected B beanItem;
	protected AdvancedPreviewBeanForm<B> previewForm;

	private Button editBtn;
	private Popover controlBtns;

	public AbstractPreviewItemComp() {

		previewForm = initPreviewForm();
		previewForm.setStyleName("readview-layout");
		this.setContent(previewForm);

		controlBtns = new Popover(createButtonControls());
		controlBtns.setClosable(true);
		controlBtns.setStyleName("controls-popover");
		editBtn = new Button("Edit", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent evt) {
				if (!controlBtns.isAttached())
					controlBtns.showRelativeTo(editBtn);
				else
					controlBtns.close();
			}
		});
		editBtn.setStyleName("edit-btn");
		this.setRightComponent(editBtn);
		ComponentContainer toolbarContent = createBottomPanel();
		toolbarContent.setStyleName("related-items");
		toolbarContent.setHeight("100%");
		toolbarContent.setWidth(Sizeable.SIZE_UNDEFINED, Unit.PIXELS);

		MobileViewToolbar toolbar = new MobileViewToolbar();
		toolbar.setComponent(toolbarContent);
		this.setToolbar(toolbar);
	}

	public void previewItem(final B item) {
		this.beanItem = item;

		previewForm.setFormLayoutFactory(initFormLayoutFactory());
		previewForm.setBeanFormFieldFactory(initBeanFormFieldFactory());
		previewForm.setBean(item);

		onPreviewItem();
	}

	public B getBeanItem() {
		return beanItem;
	}

	public AdvancedPreviewBeanForm<B> getPreviewForm() {
		return previewForm;
	}

	abstract protected void onPreviewItem();

	abstract protected String initFormTitle();

	abstract protected AdvancedPreviewBeanForm<B> initPreviewForm();

	// abstract protected void initRelatedComponents();

	abstract protected IFormLayoutFactory initFormLayoutFactory();

	abstract protected AbstractBeanFieldGroupViewFieldFactory<B> initBeanFormFieldFactory();

	abstract protected ComponentContainer createButtonControls();

	abstract protected ComponentContainer createBottomPanel();

}
