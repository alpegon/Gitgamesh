package com.biit.gitgamesh.gui.webpages.project;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.vaadin.server.StreamResource.StreamSource;

public class ViewerStreamSource implements StreamSource {
	private static final long serialVersionUID = 6449205146458173105L;
	private final ByteArrayInputStream stream;

	public ViewerStreamSource(String model) throws IOException {
		stream = new ByteArrayInputStream(model.getBytes(Charset.forName("UTF-8")));
	}

	@Override
	public InputStream getStream() {
		return stream;
	}

}
