package League;

import javafx.beans.value.ChangeListener;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class FileManager {
    private ChangeListener<Duration> progressChangeListener;
    private File file;

    public String getFilesPath() {
        try {
            FileChooser fc = new FileChooser();
            file = fc.showOpenDialog(null);
            if (file != null) {
                String path = file.getAbsolutePath();
//                for populating icon path for table the path should be relative:
                String base = "C:/Users/hmort/IdeaProjects/LeagueProject";
                path = path.replace("\\", "/");
                return new File(base).toURI().relativize(new File(path).toURI()).getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFolderPath() {
        String path;
        try {
//            FileChooser fc = new FileChooser();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            file = directoryChooser.showDialog(null);
            if (file != null) {
                path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                return path;
            }
            System.out.println(directoryChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChangeListener<Duration> getProgressChangeListener() {
        return progressChangeListener;
    }
}





