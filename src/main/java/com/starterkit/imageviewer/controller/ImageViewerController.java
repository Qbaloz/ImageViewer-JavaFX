package com.starterkit.imageviewer.controller;

import java.io.File;
import java.util.List;

import com.starterkit.imageviewer.dataprovider.DataProvider;
import com.starterkit.imageviewer.model.ImageViewerModel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;

public class ImageViewerController {

	@FXML
	Button selectButton;

	@FXML
	Button previousButton;

	@FXML
	Button startButton;
	
	@FXML
	Button stopButton;

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

	final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
	
	private static final long CALL_DELAY = 3000;
	
	private List<File> fileList;
	
	private Timeline timeline;

	@FXML
	public void initialize() {
		imageFileList.itemsProperty().bind(model.imageListProperty());
		enableZoom();
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
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(50);
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
	
	private void enableZoom(){
        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
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

	private void startSlideShow(){
		if(fileList != null){
			int amountOfImages = fileList.size() - imageFileList.getSelectionModel().getSelectedIndex();
			timeline = new Timeline(new KeyFrame(
			        Duration.millis(CALL_DELAY),
			        ae -> imageFileList.getSelectionModel().selectNext()));
			timeline.setCycleCount(amountOfImages);
			timeline.play();
		}
	}
	
	private void stopSlideShow(){
		if(timeline != null){
			timeline.stop();
		}
	}
	
	@FXML
	public void previousButtonAction(ActionEvent event) {
		imageFileList.getSelectionModel().selectPrevious();
	}

	@FXML
	public void stopButtonAction(ActionEvent event) {
		stopSlideShow();
	}
	
	@FXML
	public void startButtonAction(ActionEvent event) {
		startSlideShow();
	}

	@FXML
	public void nextButtonAction(ActionEvent event) {
		imageFileList.getSelectionModel().selectNext();
	}
}
