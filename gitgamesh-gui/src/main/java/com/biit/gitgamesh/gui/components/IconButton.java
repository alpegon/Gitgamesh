package com.biit.gitgamesh.gui.components;

import com.biit.gitgamesh.gui.localization.ILanguageCode;
import com.biit.gitgamesh.gui.theme.IThemeResource;
import com.vaadin.ui.Button;

public class IconButton extends Button {
	private static final long serialVersionUID = -8287465276670542699L;
	private final static IconSize defaultIconSize = IconSize.SMALL;

	public IconButton(IThemeResource icon, IconSize size, ILanguageCode tooltip) {
		super("");
		createButton(icon, size, tooltip);
		addStyleName("link");
	}

	public IconButton(IThemeResource icon, IconSize size, String tooltip) {
		super("");
		createButton(icon, size, tooltip);
		addStyleName("link");
	}

	public IconButton(IThemeResource icon, ILanguageCode tooltip, IconSize size, ClickListener clickListener) {
		super("", clickListener);
		createButton(icon, size, tooltip);
		addStyleName("link");
	}

	public IconButton(IThemeResource icon, ILanguageCode tooltip, ClickListener clickListener) {
		super("", clickListener);
		createButton(icon, defaultIconSize, tooltip);
		addStyleName("link");
	}

	public IconButton(ILanguageCode caption, IThemeResource icon, ILanguageCode tooltip, IconSize size,
			ClickListener clickListener) {
		super(caption.translation(), clickListener);
		createButton(icon, size, tooltip);
	}

	public IconButton(ILanguageCode caption, IThemeResource icon, ILanguageCode tooltip, ClickListener clickListener) {
		super(caption.translation(), clickListener);
		createButton(icon, defaultIconSize, tooltip);
	}

	public IconButton(ILanguageCode caption, IThemeResource icon, ILanguageCode tooltip, IconSize size) {
		super(caption.translation());
		createButton(icon, size, tooltip);
	}

	public IconButton(ILanguageCode caption, IThemeResource icon, ILanguageCode tooltip) {
		super(caption.translation());
		createButton(icon, defaultIconSize, tooltip);
	}

	public IconButton(ILanguageCode caption, IThemeResource icon, String tooltip) {
		super(caption.translation());
		createButton(icon, defaultIconSize, tooltip);
	}

	public void setIcon(IThemeResource icon) {
		setIcon(icon, defaultIconSize);
	}

	public void setIcon(IThemeResource icon, IconSize size) {
		if (icon != null && (!size.equals(IconSize.NULL))) {
			addStyleName(size.getSyle());
			setIcon(icon.getThemeResource());
		}
	}

	private void createButton(IThemeResource icon, IconSize size, ILanguageCode tooltip) {
		setIcon(icon, size);
		setDescription(tooltip);
		setImmediate(true);
	}

	private void createButton(IThemeResource icon, IconSize size, String tooltip) {
		setIcon(icon, size);
		setDescription(tooltip);
		setImmediate(true);
	}

	public void setDescription(ILanguageCode tooltip) {
		setDescription(tooltip.translation());
	}
}
