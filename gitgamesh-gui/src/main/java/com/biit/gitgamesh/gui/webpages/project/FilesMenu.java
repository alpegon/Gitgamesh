package com.biit.gitgamesh.gui.webpages.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;

import com.biit.gitgamesh.gui.components.HorizontalButtonGroup;
import com.biit.gitgamesh.gui.components.IconButton;
import com.biit.gitgamesh.gui.localization.LanguageCodes;
import com.biit.gitgamesh.gui.theme.ThemeIcon;
import com.biit.gitgamesh.logger.GitgameshLogger;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class FilesMenu extends HorizontalButtonGroup {
	private static final long serialVersionUID = 170784848610533314L;
	private final IconButton downloadFile, deleteFile;
	private final Plupload uploadFile, updateFile;
	private static final String MAX_FILE_SIZE = "50mb";
	private FileDownloader downloader;
	private StreamResource streamResource;
	private byte[] dataSource;
	private static final String DEFAULT_FILENAME = "filename";

	public FilesMenu() {
		downloadFile = new IconButton(LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON, ThemeIcon.DOWNLOAD_FILE,
				LanguageCodes.PROJECT_DOWNLOAD_FILE_BUTTON_TOOLTIP);

		uploadFile = new Plupload(LanguageCodes.PROJECT_NEW_FILE_BUTTON.translation(),
				ThemeIcon.UPLOAD_FILE.getThemeResource());
		uploadFile.setMaxFileSize(MAX_FILE_SIZE);

		updateFile = new Plupload(LanguageCodes.PROJECT_UPDATE_BUTTON.translation(),
				ThemeIcon.UPDATE_FILE.getThemeResource());
		updateFile.setMaxFileSize(MAX_FILE_SIZE);

		deleteFile = new IconButton(LanguageCodes.PROJECT_DELETE_BUTTON, ThemeIcon.DELETE_FILE,
				LanguageCodes.PROJECT_DELETE_BUTTON_TOOLTIP);

		uploadFile.addFilesAddedListener(new Plupload.FilesAddedListener() {
			private static final long serialVersionUID = 996588434372802197L;

			@Override
			public void onFilesAdded(PluploadFile[] files) {
				uploadFile.start();
			}
		});

		// handle errors
		uploadFile.addErrorListener(new Plupload.ErrorListener() {
			private static final long serialVersionUID = -5287634756244623514L;

			@Override
			public void onError(PluploadError error) {
				GitgameshLogger.errorMessage(this.getClass().getName(), "There was an error: " + error.getMessage());
			}
		});

		updateFile.addFilesAddedListener(new Plupload.FilesAddedListener() {
			private static final long serialVersionUID = 635078828748293032L;
			@Override
			public void onFilesAdded(PluploadFile[] files) {
				updateFile.start();
			}
		});

		// handle errors
		updateFile.addErrorListener(new Plupload.ErrorListener() {
			private static final long serialVersionUID = -5287634756244623514L;
			@Override
			public void onError(PluploadError error) {
				GitgameshLogger.errorMessage(this.getClass().getName(), "There was an error: " + error.getMessage());
			}
		});
		
		streamResource = new StreamResource(new StreamSource() {
			private static final long serialVersionUID = 1025447777607569514L;
			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(dataSource);
			}
		}, DEFAULT_FILENAME);
		downloader = new FileDownloader(streamResource);
		downloader.extend(downloadFile);
		addIconButton(downloadFile);
		addButton(uploadFile);
		addButton(updateFile);
		addIconButton(deleteFile);
	}
	
	/**
	 * Set the resource stream in the downloader that extends the download button
	 */
	public synchronized void setDownloaderDataSource(byte[] dataStreamSource) {
		this.dataSource = dataStreamSource;
	}
	
	public void setFileName(String filename) {
		streamResource.setFilename(filename);
	}

	public IconButton getDownloadFileButton() {
		return downloadFile;
	}

	public Plupload getUploadFileButton() {
		return uploadFile;
	}

	public Plupload getUpdateFileButton() {
		return updateFile;
	}

	public IconButton getDeleteFileButton() {
		return deleteFile;
	}
}
