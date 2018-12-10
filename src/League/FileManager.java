package League;

import javafx.beans.value.ChangeListener;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    //    for a Bigger and Better music player:
//    private File imageFilePausePlay;
//    private String localUrl;
    private ChangeListener<Duration> progressChangeListener;
    private File file;

    public String getFilesPath() {
        try {
            FileChooser fc = new FileChooser();
            file = fc.showOpenDialog(null);
            if (file != null) {
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFolderPath() {
        String path;
        try {
            FileChooser fc = new FileChooser();
            file = fc.showOpenDialog(null);
            if (file != null) {
                path = file.getParent();
                path = path.replace("\\", "/");
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChangeListener<Duration> getProgressChangeListener() {
        return progressChangeListener;
    }
}





