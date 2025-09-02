/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoplayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 *
 * @author st718
 */
public class VideoPlayer extends Application {
    
    private MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer embeddedMediaPlayer;
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        BorderPane root = new BorderPane();

        // AWT Canvas for video
        Canvas videoCanvas = new Canvas();

        // Swing panel to hold the Canvas
        JPanel videoPanel = new JPanel(new BorderLayout());
        videoPanel.add(videoCanvas, BorderLayout.CENTER);

        // SwingNode for embedding Swing in JavaFX
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(videoPanel);

        root.setCenter(swingNode);

        // Init VLCJ
        mediaPlayerFactory = new MediaPlayerFactory();
        embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

        // Set video surface (Canvas is valid here in vlcj-3.x)
        embeddedMediaPlayer.setVideoSurface(
                mediaPlayerFactory.newVideoSurface(videoCanvas)
        );
        /*
        // JavaFX ImageView for video
        javafx.scene.image.ImageView videoSurface = new javafx.scene.image.ImageView();
        root.setCenter(videoSurface);

        // Init VLCJ
        mediaPlayerFactory = new MediaPlayerFactory();
        embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
        embeddedMediaPlayer.videoSurface().set(new ImageViewVideoSurface(videoSurface));
        */
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        String mediaPath = "C:/Users/st718/Videos/TimeCut_Video_2025-03-02_09_21_41.mp4";
        embeddedMediaPlayer.playMedia(mediaPath);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
