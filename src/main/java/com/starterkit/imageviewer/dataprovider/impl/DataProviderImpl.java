package com.starterkit.imageviewer.dataprovider.impl;

import java.io.File;
import java.util.List;

import com.starterkit.imageviewer.dataprovider.DataProvider;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class DataProviderImpl implements DataProvider {

	private List<File> fileList;
	
	@Override
	public List<File> getFiles(Window stage) {
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		fileChooser.setTitle("Select images");
		fileList = fileChooser.showOpenMultipleDialog(stage);
		return fileList;
	}

	
	
}
