package League;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    public JFXScrollPane initializingTeamsScroll;
    public JFXButton teamsInitialized;
    public AnchorPane teamsDataImport;
    public StackPane dialogue;
    public JFXTreeTableView<TeamsInnerClass> scoresTable;
    private int count = 0;
    private int numberOfTeams;
    private FileManager fileManagerClass = new FileManager();
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private String path;
    private Double totalTimeOfMusic;
    private final List<MediaPlayer> players = new ArrayList<>();
    private JFXDialog jfxDialog;
    private static ArrayList<League> leagues = new ArrayList<League>();
    private Label teamScrollBarTop = new Label("ایجاد تیم ها");
    private JFXTextField[] teamNames;
    private JFXTextField[] teamPlayerNumbers;
    private String[] iconsAddress;
    private String addressOfIcon;
    //    private int slideshowCount;

    class TeamsInnerClass extends RecursiveTreeObject<TeamsInnerClass> {
        StringProperty icon;
        StringProperty teamName;
        StringProperty howManyPlayers;
        StringProperty numOfMatches;
        StringProperty score;
        StringProperty goalsScored;
        StringProperty goalsAgainst;
        StringProperty won;
        StringProperty loss;
        StringProperty draw;

        public TeamsInnerClass(String  icon, String teamName, String howManyPlayers, String numOfMatches, String score,
                               String goalsScored, String goalsAgainst, String won, String loss, String draw) {
            this.icon = new SimpleStringProperty(icon);
            this.teamName = new SimpleStringProperty(teamName);
            this.howManyPlayers = new SimpleStringProperty(howManyPlayers);
            this.numOfMatches = new SimpleStringProperty(numOfMatches);
            this.score = new SimpleStringProperty(score);
            this.goalsScored = new SimpleStringProperty(goalsScored);
            this.goalsAgainst = new SimpleStringProperty(goalsAgainst);
            this.won = new SimpleStringProperty(won);
            this.loss = new SimpleStringProperty(loss);
            this.draw = new SimpleStringProperty(draw);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaView = new MediaView(mediaPlayer);
        JFXTreeTableColumn icon = new JFXTreeTableColumn<>("Icon");
        icon.setCellValueFactory(new PropertyValueFactory<>("image"));
        icon.setPrefWidth(50);
//        JFXTreeTableColumn<TeamsInnerClass, String> icon = new JFXTreeTableColumn<>("Icon");
//        icon.setPrefWidth(100);
//        icon.setCellValueFactory(param -> param.getValue().getValue().icon);
        JFXTreeTableColumn<TeamsInnerClass, String> teamName = new JFXTreeTableColumn<>("Team Names");
        teamName.setPrefWidth(100);
        teamName.setCellValueFactory(param -> param.getValue().getValue().teamName);
        JFXTreeTableColumn<TeamsInnerClass, String> howManyPlayers = new JFXTreeTableColumn<>("Players");
        howManyPlayers.setPrefWidth(50);
        howManyPlayers.setCellValueFactory(param -> param.getValue().getValue().howManyPlayers);
        JFXTreeTableColumn<TeamsInnerClass, String> numOfMatches = new JFXTreeTableColumn<>("Matches");
        numOfMatches.setPrefWidth(50);
        numOfMatches.setCellValueFactory(param -> param.getValue().getValue().numOfMatches);
        JFXTreeTableColumn<TeamsInnerClass, String> score = new JFXTreeTableColumn<>("Score");
        score.setPrefWidth(50);
        score.setCellValueFactory(param -> param.getValue().getValue().score);
        JFXTreeTableColumn<TeamsInnerClass, String> goalsScored = new JFXTreeTableColumn<>("GScored");
        goalsScored.setPrefWidth(50);
        goalsScored.setCellValueFactory(param -> param.getValue().getValue().goalsScored);
        JFXTreeTableColumn<TeamsInnerClass, String> goalsAgainst = new JFXTreeTableColumn<>("GAgainst");
        goalsAgainst.setPrefWidth(50);
        goalsAgainst.setCellValueFactory(param -> param.getValue().getValue().goalsAgainst);
        JFXTreeTableColumn<TeamsInnerClass, String> won = new JFXTreeTableColumn<>("Won");
        won.setPrefWidth(50);
        won.setCellValueFactory(param -> param.getValue().getValue().won);
        JFXTreeTableColumn<TeamsInnerClass, String> loss = new JFXTreeTableColumn<>("Loss");
        loss.setPrefWidth(50);
        loss.setCellValueFactory(param -> param.getValue().getValue().loss);
        JFXTreeTableColumn<TeamsInnerClass, String> draw = new JFXTreeTableColumn<>("Draw");
        draw.setPrefWidth(50);
        draw.setCellValueFactory(param -> param.getValue().getValue().draw);

        ObservableList<TeamsInnerClass> teamsInnerClass = FXCollections.observableArrayList();
        teamsInnerClass.add(new TeamsInnerClass("League/backs/1.jpg", "no", "16", "1", "15", "4", "3", "5", "10","5"));
        final TreeItem<TeamsInnerClass> root = new RecursiveTreeItem<>(teamsInnerClass, RecursiveTreeObject::getChildren);
        scoresTable.getColumns().setAll(icon, teamName, howManyPlayers, numOfMatches, score, goalsScored, goalsAgainst, won, loss, draw);
        scoresTable.setRoot(root);
        scoresTable.setShowRoot(false);
    }

    public void Close(MouseEvent event) {
//        for waiting before closing the app
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

    public void setNewLeague() throws NullPointerException {
        String name = null;
        Date inputDate = null;
        try {
            name = nameOfLeague.getText();
            inputDate = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            numberOfTeams = Integer.parseInt(teamsNum.getText());
        } catch (NullPointerException e) {
            getJfxDialog("اشتباه در ورود اطلاعات", "لطفا مقدار صحیح وارد کنید");
            e.printStackTrace();
        }
        teamNames = new JFXTextField[numberOfTeams];
        teamPlayerNumbers = new JFXTextField[numberOfTeams];
        iconsAddress = new String[numberOfTeams];
        if (count < 6) {
            leagues.add(new League(name, numberOfTeams, inputDate));
        }
        newLeaguePage.setVisible(false);
        fadeOut();
        VBox content = new VBox();
        content.setPrefWidth(200);
        content.setSpacing(10);
        teamScrollBarTop.setTextFill(Color.WHITE);
        teamScrollBarTop.setStyle("-fx-text-fill:WHITE; -fx-font-size: 30;");
        initializingTeamsScroll.setContent(content);
        initializingTeamsScroll.getBottomBar().getChildren().add(teamScrollBarTop);
        JFXScrollPane.smoothScrolling((ScrollPane) initializingTeamsScroll.getChildren().get(0));
        for (int i = 0; i < numberOfTeams; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setPadding(new Insets(10));
            hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            content.getChildren().add(hBox);
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.FILE);
            JFXButton getIconButton = new JFXButton("آیکن");
            getIconButton.setGraphic(iconView);
            getIconButton.setId("team" + i);
            getIconButton.setStyle("-fx-background-color:WHITE;");
            getIconButton.setPrefWidth(100);
            int finalI = i;
            getIconButton.setOnAction(event -> {
                addressOfIcon = fileManagerClass.getFilesPath();
                if (addressOfIcon != null) {
                    getIconButton.setText("اضافه شد");
                    getIconButton.setDisable(true);
                    getIconButton.setGraphic(null);
                    iconsAddress[finalI] = addressOfIcon;
                    System.out.println(iconsAddress[finalI]);
//                    برای تغییر آیکن بعد از دادن آدرس اقدام شود
//                    getIconButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("League/backs/2.jpg"))));
                }
            });
            hBox.getChildren().add(getIconButton);
            JFXTextField teamName = new JFXTextField();
            teamName.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            teamName.setPromptText("نام تیم " + (i + 1));
            teamName.setId(teamName + String.valueOf(i));
            teamNames[i] = teamName;
            hBox.getChildren().add(teamName);
            JFXTextField howManyPlayers = new JFXTextField();
            howManyPlayers.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            howManyPlayers.setPromptText("تعداد بازیکن های تیم " + (i + 1));
            howManyPlayers.setId(howManyPlayers + String.valueOf(i));
            teamPlayerNumbers[i] = howManyPlayers;
            hBox.getChildren().add(howManyPlayers);
        }
        fadeIn();
        teamsDataImport.setVisible(true);
        switch (count) {
            case 0:
                fadeIn();
                current1.setVisible(true);
                current1.setText(name);
                break;
            case 1:
                fadeIn();
                current2.setVisible(true);
                current2.setText(name);
                break;
            case 2:
                fadeIn();
                current3.setVisible(true);
                current3.setText(name);
                break;
            case 3:
                fadeIn();
                current4.setVisible(true);
                current4.setText(name);
                break;
            case 4:
                fadeIn();
                current5.setVisible(true);
                current5.setText(name);
                break;
            case 5:
                fadeIn();
                current6.setVisible(true);
                current6.setText(name);
                break;
            default:
                getJfxDialog("بیش از حد مجاز", "شما امکان ایجاد تنها 6 لیگ را دارید.");
        }
        count++;
    }

    //    get Teams Data And Put It Int teams of leagues! You Know!
    public void setTeamsData() {
        for (int i = 0; i < numberOfTeams; i++) {
            leagues.get(count).setTeams(iconsAddress[i], teamNames[i].getText(), Integer.parseInt(teamPlayerNumbers[i].getText()));
        }

    }

    @FXML
    private void getJfxDialog(String heading, String body) {
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        dialogContent.setHeading(new Text(heading));
        dialogContent.setBody(new Text(body));
        dialogContent.setMaxSize(300, 150);
        jfxDialog = new JFXDialog(dialogue, dialogContent, JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = new JFXButton("باشه");
        closeButton.setOnAction(event -> jfxDialog.close());
        dialogContent.setActions(closeButton);
        jfxDialog.show();
    }

    //    Make a fade in transition
    private void fadeIn() {
        FadeTransition fade = new FadeTransition(Duration.millis(800), newLeaguePage);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    //    Make a fade out transition
    private void fadeOut() {
        FadeTransition fade = new FadeTransition(Duration.millis(800), newLeaguePage);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    public void browseButton() {
        path = getFiles();
        seekSlider.setValue(0);
        playPause.setText("pause");
        Path.setVisible(true);
        Path.setText(path);
        playTime.setVisible(true);
        playTime.setText(new DecimalFormat("#0.00").format(mediaPlayer.getCurrentTime().toMinutes()) + "  /");
    }

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

