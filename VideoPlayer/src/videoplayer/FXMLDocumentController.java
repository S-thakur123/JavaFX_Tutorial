/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoplayer;

//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author st718
 */
public class FXMLDocumentController //implements Initializable 
{
    
    
    @FXML private MediaView mediaView;
    @FXML private Slider volumeSlider;
    @FXML private Slider progressSlider;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private Button stopBtn;
    @FXML private Button openBtn;
    @FXML private Button fullscreenBtn;

    private MediaPlayer mediaPlayer;
    private List<File> playlist = new ArrayList<>();
    private int currentIndex = -1;
    
    
    public void initialize(/*URL url, ResourceBundle rb*/) {
        // TODO
        volumeSlider.setValue(50); // default
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        // progress slider listener (seeking)
        progressSlider.setOnMousePressed(this::seekVideo);
        progressSlider.setOnMouseDragged(this::seekVideo);
    }    
    private void seekVideo(MouseEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
        }
    }

    private void playFile(File file) {
        if (file == null) return;

        // stop old player if running
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        // bind progress slider to video
        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(media.getDuration().toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
                progressSlider.setValue(newTime.toSeconds());
            }
        });

        // keep volume in sync
        mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);

        mediaPlayer.play();
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @FXML
    private void handleStop() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.m4v", "*.flv", "*.mpg", "*.avi")
        );

        File file = fileChooser.showOpenDialog(mediaView.getScene().getWindow());
        if (file != null) {
            playlist.add(file);
            currentIndex = playlist.size() - 1;
            playFile(file);
        }
    }

    @FXML
    private void handleFullscreen() {
        Stage stage = (Stage) mediaView.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    // For next/previous in playlist
    @FXML
    private void handleNext() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex + 1) % playlist.size();
        playFile(playlist.get(currentIndex));
    }

    @FXML
    private void handlePrevious() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        playFile(playlist.get(currentIndex));
    }
}
