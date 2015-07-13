package com.biit.gitgamesh.gui.webpages.common;

import com.biit.gitgamesh.gui.GitgameshUi;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.mvp.IMVPPresenter;
import com.biit.gitgamesh.gui.mvp.IMVPView;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.gui.webpages.Gallery;
import com.biit.gitgamesh.gui.webpages.Profile;
import com.biit.gitgamesh.gui.webpages.Upload;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;

public abstract class GitgameshCommonView<IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>> extends WebPageComponent<CssLayout,IV,IP> {
	private static final long serialVersionUID = 4313698910834851991L;
	private static final String CSS_LEFT_MENU_LAYOUT = "menu-fixed-left";
	private static final String CSS_CONTENT_AREA = "content-area";
	private static final String CSS_ROOT_GITGAMESH_PAGE = "root-layout-gitgamesh";
	private static final String CSS_BUTTON_LAYOUT = "gitgamesh-button-layout";
	private static final String CSS_BUTTON_SELECTED = "menu-button-selected";
	
	private final CssLayout menuLayout;
	private final CssLayout contentLayout;
	
	private CssLayout buttonLayout;
	private Button userProfile;
	private Button gallery;
	private Button upload;
	private Button selectedButton;
	
	public GitgameshCommonView() {
		super(CssLayout.class);
		setFullScreen(true);
		
		menuLayout = new CssLayout();
		menuLayout.setStyleName(CSS_LEFT_MENU_LAYOUT);
		menuLayout.setHeight(FULL);
		
		initializeMenuElements();
		menuLayout.addComponent(buttonLayout);
		
		contentLayout = new CssLayout();
		contentLayout.setStyleName(CSS_CONTENT_AREA);
		contentLayout.setWidth(FULL);
		contentLayout.setHeightUndefined();
		
		Responsive.makeResponsive(getRootLayout());
		getRootLayout().setStyleName(CSS_ROOT_GITGAMESH_PAGE);
		getRootLayout().addComponent(menuLayout);
		getRootLayout().addComponent(contentLayout);
	}

	private Button createButton(ThemeIcon icon, LanguageCodes caption, LanguageCodes description,ClickListener clickListener){
		Button button = new Button(icon.getThemeResource());
		button.setCaption(caption.translation());
		button.setDescription(description.translation());
		button.addClickListener(clickListener);
		
		return button;
	}
	
	private void initializeMenuElements() {
		buttonLayout = new CssLayout();
		buttonLayout.setWidth(FULL);
		buttonLayout.setStyleName(CSS_BUTTON_LAYOUT);
		
		userProfile = createButton(ThemeIcon.USER_PROFILE,LanguageCodes.USER_PROFILE_CAPTION,LanguageCodes.USER_PROFILE_TOOLTIP,new ClickListener() {
			private static final long serialVersionUID = 7333928077624084354L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectButton(userProfile);
				GitgameshUi.navigateTo(Profile.NAME);
			}
		});
		gallery = createButton(ThemeIcon.GALLERY,LanguageCodes.GALLERY_CAPTION,LanguageCodes.GALLERY_TOOLTIP,new ClickListener() {
			private static final long serialVersionUID = -1909704965160360694L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectButton(gallery);
				GitgameshUi.navigateTo(Gallery.NAME);				
			}
		});
		upload = createButton(ThemeIcon.UPLOAD,LanguageCodes.UPLOAD_CAPTION,LanguageCodes.UPLOAD_TOOLTIP,new ClickListener() {
			private static final long serialVersionUID = -8701022950995423972L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectButton(upload);
				GitgameshUi.navigateTo(Upload.NAME);
			}
		});
		
		buttonLayout.addComponent(userProfile);
		buttonLayout.addComponent(gallery);
		buttonLayout.addComponent(upload);
	}
	
	public void selectButton(Button button){
		if(buttonLayout.getComponentIndex(button)!=-1){
			if(selectedButton!=null){
				selectedButton.removeStyleName(CSS_BUTTON_SELECTED);
			}
			selectedButton = button;
			selectedButton.addStyleName(CSS_BUTTON_SELECTED);
		}
	}

	public void setFullScreen(boolean value) {
		if (value) {
			getCompositionRoot().setSizeFull();
		} else {
			getCompositionRoot().setHeightUndefined();
		}
	}
}
