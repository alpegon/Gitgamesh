package com.biit.gitgamesh.gui.webpages.upload;

import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.webpages.common.GitgameshCommonView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

@UIScope
@SpringComponent
public class UploadView extends GitgameshCommonView<IUploadView, IUploadPresenter> implements IUploadView {
	private static final long serialVersionUID = -3493109786988382122L;
	private static final String CSS_PAGE_TITLE = "page-title";
	
	public UploadView() {
		super();
	}
	
	@Override
	public void init() {
		Label title = new Label(LanguageCodes.UPLOAD_CAPTION.translation());
		title.setStyleName(CSS_PAGE_TITLE);
		
		getContentLayout().addComponent(title);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		selectButton(getUpload());
	}

}
