package com.starterkit.imageviewer.controller;

import java.io.File;
import java.util.List;

import com.starterkit.imageviewer.dataprovider.DataProvider;
import com.starterkit.imageviewer.model.ImageViewerModel;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ImageViewerController {

	@FXML
	Button selectButton;

	@FXML
	Button previousButton;

	@FXML
	Button startButton;

	@FXML
	Button nextButton;

	@FXML
	ListView<File> imageFileList;

	@FXML
	ImageView imageView;
	
	@FXML
	ScrollPane scrollPane;

	@FXML
	AnchorPane anchorPane;

	private final ImageViewerModel model = new ImageViewerModel();
	
	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private List<File> fileList;

	@FXML
	public void initialize() {
		imageFileList.itemsProperty().bind(model.imageListProperty());
	}

	@FXML
	public void selectButtonAction(ActionEvent event) {
		fileList = dataProvider.getFiles(anchorPane.getScene().getWindow());
		initializeImageList();
		updateImageView();
	}

	private void initializeImageList() {
		if (fileList != null) {
			model.setImageList(fileList);
		}
		
        imageFileList.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> param) {
                ListCell<File> cell = new ListCell<File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                        	ImageView imageView = new ImageView();
                            Image image = new Image(item.toURI().toString());
                            imageView.setFitHeight(30);
                            imageView.setFitWidth(30);
                            imageView.setImage(image);
                            setGraphic(imageView);
                            setText(item.getName());
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
	}

	private void updateImageView() {
		imageFileList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<File>() {

			@Override
			public void changed(ObservableValue<? extends File> observable, File oldValue, final File newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						if(newValue != null){
							Image image = new Image(newValue.toURI().toString());
			                imageView.setFitWidth(image.getWidth());
			                imageView.setFitHeight(image.getHeight());
							imageView.setImage(image);
				        }
					}
				});
			}
		});
	}

	@FXML
	public void previousButtonAction(ActionEvent event) {
		imageFileList.getSelectionModel().selectPrevious();
	}

	@FXML
	public void startButtonAction(ActionEvent event) {

	}

	@FXML
	public void nextButtonAction(ActionEvent event) {
		imageFileList.getSelectionModel().selectNext();
	}
}
