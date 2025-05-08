package Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class MusicApp {
    private static MediaPlayer mediaPlayer;

    public static void playBackgroundMusic(String fileName) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            URL resource = MusicApp.class.getResource("/View/Audio/nhacnen.mp3");
            Media media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}