package League;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.CubicCurve;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static ArrayList<League> leagues = new ArrayList<League>();
    public JFXTextField teamsNum;
    public JFXDatePicker startDate;
    public JFXButton newLeague;
    public JFXButton closeButton;
    public Tooltip tooltip;
    public JFXTextField nameOfLeague;
    public JFXButton goOn;
    public AnchorPane newLeaguePage;
    public Label hintForStart;
    public CubicCurve forStartLine;
    public Label topWelcomeHint;

    public JFXButton previous;
    public JFXButton playPause;
    public JFXButton fileBrowser;
    public JFXButton next;
    public HBox horizonPlayBox;
    public AnchorPane mainBack;
    public MaterialDesignIconView browser;
    public JFXSlider seekSlider;
    public Slider volumeSlider;
    public Label durationLabel;
    public Label Path;
    public Label playTime;
    public JFXToggleButton playPauseButton;
    public ImageView playPauseImage;
    public JFXButton reports;
    public JFXButton current1;
    public JFXButton current2;
    public JFXButton current3;
    public JFXButton current4;
    public JFXButton current5;
    public JFXButton current6;
    public Label songName;
    public JFXButton folderBrowser;
    //    private int slideshowCount;
    private int count = 0;


    public void Close(MouseEvent event) {
//        Thread.sleep(600);
        if (event.getSource() == closeButton) {
            System.exit(0);
        }
    }

    public void setNewLeagueVisible(ActionEvent event) {
        event.getSource();
        hintForStart.setVisible(false);
        topWelcomeHint.setVisible(false);
        fadeOut();
        forStartLine.setVisible(false);
        fadeOut();
        fadeIn();
        newLeaguePage.setVisible(true);
    }

    public void setNewLeague() throws MalformedURLException, ParseException {
        String name = nameOfLeague.getText();
        String date = String.valueOf(startDate.getValue());
        int numberOfTeams = Integer.parseInt(teamsNum.getText());
        if (count < 6) {
            leagues.add(new League(name, numberOfTeams, date));
        }
        count++;
        current1.setText(leagues.get(count).getName());
    }

    //    Make a fade transition
    private void fadeIn() {
        FadeTransition fade = new FadeTransition(Duration.millis(800), newLeaguePage);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void fadeOut() {
        FadeTransition fade = new FadeTransition(Duration.millis(800), newLeaguePage);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    //    music player
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private File musicFile = null;
    private File imageFilePausePlay;
    private String localUrl;
    private Double totalTimeOfMusic;
    private final List<MediaPlayer> players = new ArrayList<>();
    private ChangeListener<Duration> progressChangeListener;

    @FXML
    public void browseButton() {
        String path = getFiles();
        seekSlider.setValue(0);
        playPause.setText("pause");
        Path.setVisible(true);
        Path.setText(path);
        playTime.setVisible(true);
        playTime.setText(new DecimalFormat("#0.00").format(mediaPlayer.getCurrentTime().toMinutes()) + "  /");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaView = new MediaView(mediaPlayer);
    }

    @FXML
    public void playPause() {
        if (playPause.getText().equals("pause")) {
            playPause.setText("play");
            if (musicFile != null) {
                totalTimeOfMusic = mediaPlayer.getTotalDuration().toSeconds();
                durationLabel.setVisible(true);
                durationLabel.setText(String.valueOf(new DecimalFormat("#.00")
                        .format(mediaPlayer.getTotalDuration().toMinutes())));// duration label
                System.out.println(totalTimeOfMusic);
                // for volume set value for initial
                volumeSlider.setValue(40);
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                mediaPlayer.play();
                volumeSlider.valueProperty().addListener((Observable) -> mediaPlayer.setVolume(volumeSlider.getValue() / 100));
                // seek slider
                mediaPlayer.currentTimeProperty().addListener((Observable) -> {
                    if (seekSlider.isValueChanging()) {
                        mediaPlayer.seek(Duration.seconds((seekSlider.getValue() * (totalTimeOfMusic) / 100)));
                    }
                    if (seekSlider.isPressed()) {
                        mediaPlayer.seek(Duration.seconds((seekSlider.getValue() * (totalTimeOfMusic) / 100)));
                    }
                    seekSlider.setValue((mediaPlayer.getCurrentTime().toSeconds() * 100) / totalTimeOfMusic);
                    playTime.setVisible(true);
                    playTime.setText(new DecimalFormat("#0.00").format(mediaPlayer.getCurrentTime().toMinutes()) + "  /");
                });
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);// for run
            }
        } else if (playPause.getText().equals("play")) {
            if (musicFile != null) {
                playPause.setText("pause");
                durationLabel.setText(String.valueOf(new DecimalFormat("#.00")
                        .format(mediaPlayer.getTotalDuration().toMinutes())));
                mediaPlayer.pause();
            }
        }
    }

    @FXML
    public void next() {
        System.out.println("next");
        final MediaPlayer curPlayer = mediaView.getMediaPlayer();
        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
        mediaView.setMediaPlayer(nextPlayer);
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.stop();
        nextPlayer.play();
    }

    @FXML
    public void folderOpen() {
        System.out.println("folder");
        String folderPath = getFolder();
        final File dir = new File(folderPath);
        if (!dir.exists() && dir.isDirectory()) {
            System.out.println("Cannot find audio source directory: " + dir);
        }
        // create some media players.
        final List<MediaPlayer> players = new ArrayList<>();
        for (String file : dir.list((dir1, name) -> name.endsWith(".mp3")))
            players.add(createPlayer("file:///" + (dir + "\\" + file)
                    .replace("\\", "/").replaceAll(" ", "%20")));
        if (players.isEmpty()) {
            System.out.println("No audio found in " + dir);
        }
        // play each audio file in turn.
        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(() -> {
                player.currentTimeProperty().removeListener(progressChangeListener);
                mediaView.setMediaPlayer(nextPlayer);
                nextPlayer.play();
            });
        }
    }

    private String getFiles() {
        try {
            FileChooser fc = new FileChooser();
            musicFile = fc.showOpenDialog(null);
            if (musicFile != null) {
                String path = musicFile.getAbsolutePath();
                path = path.replace("\\", "/");
                Media media = new Media(new File(path).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFolder() {
        String path;
        try {
            FileChooser fc = new FileChooser();
            musicFile = fc.showOpenDialog(null);
            if (musicFile != null) {
                path = musicFile.getParent();
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void previous() {
        System.out.println("previous");
    }

    @FXML
    public void dragDone() {
        System.out.println("drag done");
    }

    @FXML
    public void dragEntered() {
        System.out.println("drag entered");
    }

    @FXML
    public void dragDetected() {
        System.out.println("drag Detected");
    }

    @FXML
    public void dragDropped() {
        System.out.println("dropped");
    }

    private MediaPlayer createPlayer(String aMediaSrc) {
        final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
        player.setOnError(() -> System.out.println("Media error occurred: " + player.getError()));
        return player;
    }
//    Thread th = new Thread(task);
//            th.setDaemon(true)
//            th.start();
//    ArrayList<Object> imageArrayList = new ArrayList<>();
//    //then the working logic in my eventHandler
//    Task task = new Task<Void>() {
//        @Override
//        public Void call() throws Exception {
//            for (int i = 0; i < imageArrayList.size(); i++) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        mainBack.setImage(imageArrayList.get(slideshowCount));
//                        slideshowCount++;
//                        if (slideshowCount >= imageArrayList.size()) {
//                            slideshowCount = 0;
//                        }
//                    }
//                });
//                Thread.sleep(1000);
//            }
//            return null;
//        }
//    };
}

