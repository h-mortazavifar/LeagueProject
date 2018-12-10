package League;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static ArrayList<League> leagues = new ArrayList<League>();
    public JFXTextField teamsNum;
    public JFXDatePicker startDate;
    public JFXButton continueToTeams;
    public JFXButton closeButton;
    public Tooltip tooltip;
    public JFXTextField nameOfLeague;
    public JFXButton goOn;
    public AnchorPane newLeaguePage;
    public Label hintForStart;
    public CubicCurve forStartLine;
    public Label topWelcomeHint;

    public JFXButton playPause;
    public JFXSlider seekSlider;
    public Slider volumeSlider;
    public Label durationLabel;
    public Label playTime;
    public JFXButton previous;
    public JFXButton fileBrowser;
    public JFXButton next;
    public HBox horizonPlayBox;
    public Label Path;
    public JFXButton reports;
    public JFXButton current1;
    public JFXButton current2;
    public JFXButton current3;
    public JFXButton current4;
    public JFXButton current5;
    public JFXButton current6;
    public Label songName;
    public JFXButton folderBrowser;
    public JFXScrollPane initializingTeams;
    public JFXButton teamsInitialized;
    public AnchorPane teamsDataImport;
    //    private int slideshowCount;
    private int count = 0;
    private int numberOfTeams;
    private FileManager fileManagerClass = new FileManager();
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private String path;
    private Double totalTimeOfMusic;
    private final List<MediaPlayer> players = new ArrayList<>();

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
        teamsDataImport.setVisible(false);
        fadeOut();
        fadeIn();
        newLeaguePage.setVisible(true);
    }

    public void setNewLeague() throws MalformedURLException {

        String name = nameOfLeague.getText();
        Date inputDate = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        numberOfTeams = Integer.parseInt(teamsNum.getText());
        if (count < 6) {
            leagues.add(new League(name, numberOfTeams, inputDate));
        }
        newLeaguePage.setVisible(false);
        fadeOut();
        VBox content = new VBox();
        initializingTeams.setContent(content);
        content.setSpacing(20);
        for (int i = 0; i < numberOfTeams; i++) {
            JFXTextField teamName = new JFXTextField();
            teamName.setPromptText("نام تیم "+ i);
            teamName.setId(teamName + String.valueOf(i));
            content.getChildren().add(teamName);
            JFXTextField howManyTeams = new JFXTextField();
            howManyTeams.setPromptText("تعداد بازیکن های تیم "+ i);
            howManyTeams.setId(howManyTeams + String.valueOf(i));
            content.getChildren().add(howManyTeams);
        }
        fadeIn();
        teamsDataImport.setVisible(true);
        count++;
//        should change to work for every button
        current1.setText(name);
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

    private void setTeams() {
        for (int i = 0; i < numberOfTeams; i++) {
//                leagues.get(count).setTeams();
        }
    }

    @FXML
    public void browseButton() {
        path = getFiles();
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
        if (playPause.getText().equals("pause") && path != null) {
            playPause.setText("play");
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
        } else if (path != null) {
            playPause.setText("pause");
            durationLabel.setText(String.valueOf(new DecimalFormat("#.00")
                    .format(mediaPlayer.getTotalDuration().toMinutes())));
            mediaPlayer.pause();
        }
    }

    @FXML
    public void openFolderButton() {
        System.out.println("folder");
        String folderPath = fileManagerClass.getFolderPath();
        final File dir = new File(folderPath);
        if (!dir.exists() && dir.isDirectory()) {
            System.out.println("Cannot find audio source directory: " + dir);
        }
        // create some media players.
        final List<MediaPlayer> players = new ArrayList<>();
        for (String file : dir.list((dir1, name) -> name.endsWith(".mp3")))
            players.add(createMediaPlayer("file:///" + (dir + "\\" + file)
                    .replace("\\", "/").replaceAll(" ", "%20")));
        if (players.isEmpty()) {
            System.out.println("No audio found in " + dir);
        }
        // play each audio file in turn.
        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(() -> {
                player.currentTimeProperty().removeListener(fileManagerClass.getProgressChangeListener());
                mediaView.setMediaPlayer(nextPlayer);
                nextPlayer.play();
            });
        }
    }

    private String getFiles() {
        String path = fileManagerClass.getFilesPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        return path;
    }

    @FXML
    public void previous() {
        System.out.println("previous");
    }

    public void next() {
        System.out.println("next");
        final MediaPlayer curPlayer = mediaView.getMediaPlayer();
        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
        mediaView.setMediaPlayer(nextPlayer);
        curPlayer.currentTimeProperty().removeListener(fileManagerClass.getProgressChangeListener());
        curPlayer.stop();
        nextPlayer.play();
    }

    private MediaPlayer createMediaPlayer(String aMediaSrc) {
        final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
        player.setOnError(() -> System.out.println("Media error occurred: " + player.getError()));
        return player;
    }
//    for a Bigger and Better music player:
    /*@FXML
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
    }*/

    /*Thread th = new Thread(task);
            th.setDaemon(true)
                    th.start();
    ArrayList<Object> imageArrayList = new ArrayList<>();
    //then the working logic in my eventHandler
    Task task = new Task<Void>() {
        @Override
        public Void call() throws Exception {
            for (int i = 0; i < imageArrayList.size(); i++) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mainBack.setImage(imageArrayList.get(slideshowCount));
                        slideshowCount++;
                        if (slideshowCount >= imageArrayList.size()) {
                            slideshowCount = 0;
                        }
                    }
                });
                Thread.sleep(1000);
            }
            return null;
        }
    };*/
}

