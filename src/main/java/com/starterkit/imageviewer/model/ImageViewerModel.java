package com.starterkit.imageviewer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class ImageViewerModel {

	private final ListProperty<File> imageList = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<File>()));
	private final ObjectProperty<ImageView> imageView = new SimpleObjectProperty<>();
	private final ObjectProperty<ScrollPane> scrollPane = new SimpleObjectProperty<>();

	public final List<File> getImageList() {
		return imageList.get();
	}

	public final void setImageList(List<File> value) {
		imageList.setAll(value);
	}

	public ListProperty<File> imageListProperty() {
		return imageList;
	}
	
	public final ImageView getImageView() {
		return imageView.get();
	}

	public final void setImageView(ImageView value) {
		imageView.set(value);
	}

	public ObjectProperty<ImageView> imageViewProperty() {
		return imageView;
	}
	
	public final ScrollPane getScrollPane() {
		return scrollPane.get();
	}

	public final void setScrollPane(ScrollPane value) {
		scrollPane.set(value);
	}

	public ObjectProperty<ScrollPane> scrollPaneProperty() {
		return scrollPane;
	}
	
}
