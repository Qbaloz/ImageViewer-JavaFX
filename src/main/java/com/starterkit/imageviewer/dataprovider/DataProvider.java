package com.starterkit.imageviewer.dataprovider;

import java.io.File;
import java.util.List;

import com.starterkit.imageviewer.dataprovider.impl.DataProviderImpl;

import javafx.stage.Window;

public interface DataProvider {

	DataProvider INSTANCE = new DataProviderImpl();
	
	List<File> getFiles(Window stage);
	
}
