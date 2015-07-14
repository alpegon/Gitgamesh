package com.biit.gitgamesh.gui.components;

public enum IconSize {
	NULL(""), SMALL("iconbutton-size-small"), MEDIUM("iconbutton-size-medium"), BIG("iconbutton-size-big");

	private String style;

	private IconSize(String style) {
		this.style = style;
	}

	public String getSyle() {
		return style;
	}
}
