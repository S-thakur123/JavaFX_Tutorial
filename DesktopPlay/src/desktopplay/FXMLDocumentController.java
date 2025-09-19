/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopplay;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author st718
 */
public class FXMLDocumentController implements Initializable {

    MediaPlayer media;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider timeSlider;
    
    @FXML
    private Button playBtn;
    
    @FXML
    private Button preBtn;

    @FXML
    private Button preBtn1;
    
    @FXML
    private Button nextBtn1;


    @FXML
    private Button nextBtn;

    @FXML
    private Button speed;
    
    @FXML
    private Button rotate;

    @FXML
    void openFile(ActionEvent event) {
        System.out.println("Open File");
        try {
            FileChooser fileChoser = new FileChooser();
            File file = fileChoser.showOpenDialog(null);
            Media m = new Media(file.toURI().toURL().toString());
            
            if(media != null){
                media.dispose();
            }
            
            media = new MediaPlayer(m);

            mediaView.setMediaPlayer(media);
            media.setOnReady(() -> {
                timeSlider.setMin(0);
                timeSlider.setMax(media.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
            });
            //Listener on media (Time Slider)
            media.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    Duration d = media.getCurrentTime();
                    timeSlider.setValue(d.toSeconds());
                }
            });
            
            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if(timeSlider.isPressed()){
                        double val = timeSlider.getValue();
                        media.seek(new Duration(val*1000));
                    } 
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void play(ActionEvent event) {
        MediaPlayer.Status status = media.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            media.pause();
//        playBtn.setText("Play");
            try {
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            media.play();
//            playBtn.setText("Pause");
            try {
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pause.png"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void forwardSeek(ActionEvent event) {
        double d = media.getCurrentTime().toSeconds();
        d = d + 10;
        media.seek(new Duration(d*1000));
    }
    
    @FXML
    void backwardSeek(ActionEvent event) {
        double d = media.getCurrentTime().toSeconds();
        
        d = d - 10;
        media.seek(new Duration(d*1000));

    }
    
    
    
    @FXML
    void rotateVideo(ActionEvent event) {
        if(rotate.isPressed()){
            double val = (mediaView.getRotate() + 90)%360;
            mediaView.setRotate(val);
        }
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            preBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/previous.png"))));
            nextBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/nextbutton.png"))));
            rotate.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/rotateImage.png"))));
            preBtn1.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/rewind-button.png"))));
            nextBtn1.setGraphic((new ImageView(new Image(new FileInputStream("src/icons/forwardbutton.png")))));
            speed.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/dashboard.png"))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
