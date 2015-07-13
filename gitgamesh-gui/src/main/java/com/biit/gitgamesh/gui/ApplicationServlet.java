package com.biit.gitgamesh.gui;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.server.SpringVaadinServlet;

//@WebServlet(value = "/*", asyncSupported = true)
public class ApplicationServlet extends SpringVaadinServlet {
	private static final long serialVersionUID = -5198329062506096945L;

	@WebListener
	public static class MyContextLoaderListener extends ContextLoaderListener {
	}

	@Configuration
	@EnableVaadin
	public static class MyConfiguration {
	}

}