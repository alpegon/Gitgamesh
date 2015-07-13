package com.biit.gitgamesh.persistence.dao.jpa;

import org.springframework.stereotype.Repository;

import com.biit.gitgamesh.persistence.dao.IPrinterProjectDao;
import com.biit.gitgamesh.persistence.entity.PrinterProject;

@Repository
public class PrinterProjectDao extends AnnotatedGenericDao<PrinterProject, Long> implements IPrinterProjectDao {

	public PrinterProjectDao() {
		super(PrinterProject.class);
	}

}
