package com.biit.gitgamesh.gui.components;

import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Custom horizontal button group. This class will have a debug id bassed on
 * it's classname.
 *
 */
public class HorizontalButtonGroup extends CustomComponent {

	private static final long serialVersionUID = 4862986305501412362L;
	protected static final String BUTTON_IN_MENU_CLASS = "v-button-in-menu";
	private static String CLASSNAME = "v-horizontal-button-group";
	protected HorizontalLayout rootLayout;
	private String size;
	private boolean contractIcons;

	public HorizontalButtonGroup() {
		super();
		setId(this.getClass().getName());
		initHorizontalButtonGroup();
		setIconSizeWithAttachListener();
	}

	protected void initHorizontalButtonGroup() {
		setStyleName(CLASSNAME);

		rootLayout = new HorizontalLayout();
		rootLayout.setSizeFull();
		rootLayout.setSpacing(false);
		setCompositionRoot(rootLayout);
		setSizeFull();

		contractIcons = false;
	}

	protected void setIconSizeWithAttachListener() {
		addAttachListener(new AttachListener() {
			private static final long serialVersionUID = -2513076537414804598L;

			@Override
			public void attach(AttachEvent event) {
				setIconSize();
			}
		});
	}

	public void addIconButton(IconButton button) {
		rootLayout.addComponent(button);
		button.setSizeFull();
		button.addStyleName(BUTTON_IN_MENU_CLASS);
	}
	
	public void addButton(Button button) {
		rootLayout.addComponent(button);
		button.setSizeFull();
		button.addStyleName(BUTTON_IN_MENU_CLASS);
	}

	public void setContractIcons(boolean contractIcons) {
		this.contractIcons = contractIcons;
		this.size = null;
		rootLayout.setWidth(null);
	}

	public void setContractIcons(boolean contractIcons, String size) {
		this.contractIcons = contractIcons;
		this.size = size;
		rootLayout.setWidth(null);
	}

	private void setIconSize() {
		Iterator<Component> itr = rootLayout.iterator();
		if (contractIcons) {
			rootLayout.setWidth(null);
		}

		while (itr.hasNext()) {
			Component component = itr.next();
			rootLayout.setExpandRatio(component, 0.0f);
			if (contractIcons) {
				component.setWidth(size);
			} else {
				component.setWidth("100%");
			}
		}

		markAsDirtyRecursive();
	}

	protected String getIconSize() {
		return size;
	}

}