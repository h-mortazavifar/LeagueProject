package League;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
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
import org.jetbrains.annotations.NotNull;

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
    public JFXButton goOnToSecondPage;
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
    public JFXTreeTableView scoresTable;
    public AnchorPane ScorePageOfALeague;
    public JFXButton matchMaker;
    public Label leagueName;
    public Label leagueStartDate;
    public AnchorPane playersDataImport;
    public JFXScrollPane initializingPlayersScroll;
    public JFXButton playersInitialized;
    public VBox reportAccordionPane;
    public AnchorPane makingAMatchPane;
    public JFXButton matchHappend;
    private int count = 0;
    private int numberOfTeams;
    private FileManager fileManagerClass = new FileManager();
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private String path;
    private Double totalTimeOfMusic;
    private final List<MediaPlayer> players = new ArrayList<>();
    private JFXDialog jfxDialog;
    static ArrayList<League> leagues = new ArrayList<League>();
    private Label teamScrollBarTop = new Label("ایجاد تیم ها");
    private Label playersScrollBarTop = new Label("ایجاد بازیکن ها");
    private JFXTextField[] teamNames;
    private JFXTextField[][] playerNames;
    private JFXTextField[] teamPlayerNumbers;
    private JFXTextField[][] playerLastNames;
    private JFXComboBox[][] post;
    private String[] iconsAddress;
    private String addressOfIcon;
    private int whichIsLastClicked = 0;
    private String name = null;
    private Date inputDate = null;
    private int newCount;
    Player.Post[][] postToImport;
    String[][] playerNamesToImport;
    String[][] PlayerLastNamesToImport;
    //    private int slideshowCount;

    class TeamsInnerClass extends RecursiveTreeObject<TeamsInnerClass> {
        SimpleObjectProperty icon;
        SimpleStringProperty teamName;
        SimpleStringProperty howManyPlayers;
        SimpleStringProperty numOfMatches;
        SimpleStringProperty score;
        SimpleStringProperty goalsScored;
        SimpleStringProperty goalsAgainst;
        SimpleStringProperty won;
        SimpleStringProperty loss;
        SimpleStringProperty draw;

        public TeamsInnerClass(@NotNull ImageView icon, String teamName, String howManyPlayers, String numOfMatches, String score,
                               String goalsScored, String goalsAgainst, String won, String loss, String draw) {
            icon.setFitHeight(30);
            icon.setFitWidth(30);
            this.icon = new SimpleObjectProperty(icon);
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
        current1.setOnAction(event -> {
            whichIsLastClicked = 0;
            showTable();
        });
        current2.setOnAction(event -> {
            whichIsLastClicked = 1;
            showTable();
        });
        current3.setOnAction(event -> {
            whichIsLastClicked = 2;
            showTable();
        });
        current4.setOnAction(event -> {
            whichIsLastClicked = 3;
            showTable();
        });
        current5.setOnAction(event -> {
            whichIsLastClicked = 4;
            showTable();
        });
        current6.setOnAction(event -> {
            whichIsLastClicked = 5;
            showTable();
        });
    }

    public void Close(MouseEvent event) {
//        for waiting before closing the app
//        Thread.sleep(600);
        if (event.getSource() == closeButton) {
            System.exit(0);
        }
    }

    public void showTable() {
        fadeOut();
        matchMaker.setDisable(false);
        fadeIn();
        ScorePageOfALeague.setVisible(true);
//        clear the old data of the table:
        scoresTable.getColumns().clear();
        tableMaker();
    }

    private void tableMaker() {
//        String photoURL, String teamName, int howManyPlayers, int numOfMatches, int goalsScored,
//                            int goalsAgainst, int won, int loss, int draw, int score
        ObservableList<TeamsInnerClass> teamsInnerClass = FXCollections.observableArrayList();
        JFXTreeTableColumn<TeamsInnerClass, ImageView> icon = new JFXTreeTableColumn<>();
        icon.setPrefWidth(100);
        icon.setCellValueFactory(param -> param.getValue().getValue().icon);
        JFXTreeTableColumn<TeamsInnerClass, String> teamName = new JFXTreeTableColumn<>("Teams");
        teamName.setPrefWidth(100);
        teamName.setCellValueFactory(param -> param.getValue().getValue().teamName);
        JFXTreeTableColumn<TeamsInnerClass, String> howManyPlayers = new JFXTreeTableColumn<>("Players");
        howManyPlayers.setPrefWidth(50);
        howManyPlayers.setCellValueFactory(param -> param.getValue().getValue().howManyPlayers);
        JFXTreeTableColumn<TeamsInnerClass, String> numOfMatches = new JFXTreeTableColumn<>("Matches");
        numOfMatches.setPrefWidth(80);
        numOfMatches.setCellValueFactory(param -> param.getValue().getValue().numOfMatches);
        JFXTreeTableColumn<TeamsInnerClass, String> goalsScored = new JFXTreeTableColumn<>("GScored");
        goalsScored.setPrefWidth(80);
        goalsScored.setCellValueFactory(param -> param.getValue().getValue().goalsScored);
        JFXTreeTableColumn<TeamsInnerClass, String> goalsAgainst = new JFXTreeTableColumn<>("GAgainst");
        goalsAgainst.setPrefWidth(80);
        goalsAgainst.setCellValueFactory(param -> param.getValue().getValue().goalsAgainst);
        JFXTreeTableColumn<TeamsInnerClass, String> won = new JFXTreeTableColumn<>("W");
        won.setPrefWidth(20);
        won.setCellValueFactory(param -> param.getValue().getValue().won);
        JFXTreeTableColumn<TeamsInnerClass, String> loss = new JFXTreeTableColumn<>("L");
        loss.setPrefWidth(20);
        loss.setCellValueFactory(param -> param.getValue().getValue().loss);
        JFXTreeTableColumn<TeamsInnerClass, String> draw = new JFXTreeTableColumn<>("D");
        draw.setPrefWidth(20);
        draw.setCellValueFactory(param -> param.getValue().getValue().draw);
        JFXTreeTableColumn<TeamsInnerClass, String> score = new JFXTreeTableColumn<>("S");
        score.setPrefWidth(20);
        score.setCellValueFactory(param -> param.getValue().getValue().score);
//        teamsInnerClass.add(new TeamsInnerClass(new ImageView(new Image("League/backs/nurnberg.png")), "no",
//                "16", "1", "15", "4", "3", "5", "10", "5"));
        ImageView forIcon;
        String forTeamNames;
        String forNumberOfPlayers;
        String forNumberOfMatches;
        String forScores;
        String forGoalsScored;
        String forGoalsAgainst;
        String forGetWon;
        String forGetLoss;
        String forGetDrawn;

        for (int i = 0; i < numberOfTeams; i++) {
            forIcon = new ImageView(new Image(iconsAddress[i]));
            forTeamNames = String.valueOf(teamNames[i].getText());
            forNumberOfPlayers = String.valueOf(teamPlayerNumbers[i].getText());
            forNumberOfMatches = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getNumOfMatches());
            forScores = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getScore());
            forGoalsScored = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getGoalsScored());
            forGoalsAgainst = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getGoalsAgainst());
            forGetWon = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getWon());
            forGetLoss = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getLoss());
            forGetDrawn = String.valueOf(leagues.get(whichIsLastClicked).getTeam(i).getDraw());

            teamsInnerClass.add(new TeamsInnerClass(forIcon, forTeamNames, forNumberOfPlayers, forNumberOfMatches,
                    forScores, forGoalsScored, forGoalsAgainst, forGetWon, forGetLoss, forGetDrawn));
        }
        final TreeItem<TeamsInnerClass> root = new RecursiveTreeItem<>(teamsInnerClass, RecursiveTreeObject::getChildren);
        scoresTable.getColumns().addAll(icon, teamName, howManyPlayers, numOfMatches, goalsScored, goalsAgainst, won, loss, draw, score);
        scoresTable.setRoot(root);
        scoresTable.setShowRoot(false);

    }

    //    get Teams Data And Put It teams of leagues! You Know!
    public void setAllDAtaForALeague() {
        playersDataImport.setVisible(false);

    }

    public void setNewLeagueVisible(ActionEvent event) {
        event.getSource();
        playersDataImport.setVisible(false);
        hintForStart.setVisible(false);
        fadeOut();
        topWelcomeHint.setVisible(false);
        fadeOut();
        ScorePageOfALeague.setVisible(false);
        fadeOut();
        forStartLine.setVisible(false);
        fadeOut();
        teamsDataImport.setVisible(false);
        fadeOut();
        fadeIn();
        newLeaguePage.setVisible(true);
    }

    //    set name, start Date and number of teams in a league
    public void setNewLeagueAndTeamsPage() throws NullPointerException {
        playersDataImport.setVisible(false);
        VBox content = makingAVBox(teamScrollBarTop, initializingTeamsScroll);
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
        leagueName.setText(name);
        leagueStartDate.setText(String.valueOf(inputDate));

        JFXScrollPane.smoothScrolling((ScrollPane) initializingTeamsScroll.getChildren().get(0));
        for (int i = 0; i < numberOfTeams; i++) {
            HBox hBox = makingAHBox(content);
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.FILE);
            JFXButton getIconButton = new JFXButton("آیکن");
            getIconButton.setGraphic(iconView);
//            getIconButton.setId("team" + i);
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
//            teamName.setId(teamName + String.valueOf(i));
            teamNames[i] = teamName;
            hBox.getChildren().add(teamName);
            JFXTextField howManyPlayers = new JFXTextField();
            howManyPlayers.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            howManyPlayers.setPromptText("تعداد بازیکن های تیم " + (i + 1));
//            howManyPlayers.setId(howManyPlayers + String.valueOf(i));
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

    public void matchMaking() {
        if (makingAMatchPane.isVisible()) {
            makingAMatchPane.setVisible(false);
        } else {
            makingAMatchPane.setVisible(true);
        }
        reports.setDisable(false);
    }

    public void report() {
        if (reportAccordionPane.isVisible()) {
            reportAccordionPane.setVisible(false);
        } else {
            reportAccordionPane.setVisible(true);
        }
    }

    public void setNewTeamsAndPlayersPage() throws NullPointerException {
        newCount = count - 1;
        VBox content = makingAVBox(playersScrollBarTop, initializingPlayersScroll);
        for (int i = 0; i < numberOfTeams; i++) {
            leagues.get(newCount).setTeams(iconsAddress[i], teamNames[i].getText(),
                    Integer.parseInt(teamPlayerNumbers[i].getText()), i);
        }
        playerNames = new JFXTextField[numberOfTeams][];
        playerLastNames = new JFXTextField[numberOfTeams][];
        post = new JFXComboBox[numberOfTeams][];
        teamsDataImport.setVisible(false);
        fadeOut();

        JFXScrollPane.smoothScrolling((ScrollPane) initializingPlayersScroll.getChildren().get(0));
        for (int i = 0; i < numberOfTeams; i++) {
            playerNames[i] = new JFXTextField[Integer.parseInt(teamPlayerNumbers[i].getText())];
            playerLastNames[i] = new JFXTextField[Integer.parseInt(teamPlayerNumbers[i].getText())];
            post[i] = new JFXComboBox[Integer.parseInt(teamPlayerNumbers[i].getText())];
            for (int j = 0; j < Integer.parseInt(teamPlayerNumbers[i].getText()); j++) {
                HBox hBox = makingAHBox(content);
                JFXComboBox getPlayersPost = new JFXComboBox();
                getPlayersPost.setStyle("-fx-background-color:WHITE;");
                getPlayersPost.setPrefWidth(150);
                getPlayersPost.setPromptText("پست بازیکن در زمین");
                getPlayersPost.getItems().addAll(Player.Post.values());
                int finalI1 = i;
                int finalI = j;
                getPlayersPost.setOnAction(event -> {
                    getPlayersPost.setPromptText("تعیین شد");
                    getPlayersPost.setDisable(true);
                    System.out.println(getPlayersPost.getValue());
//                    leagues.get(count - 1).getTeam(finalI1).getPlayerOfNumber(finalI).setPost((Player.Post) getPlayersPost.getValue());
                    post[finalI1][finalI] = getPlayersPost;
                    postToImport[finalI1][finalI] = (Player.Post) post[finalI1][finalI].getValue();
                });
                hBox.getChildren().add(getPlayersPost);
                JFXTextField name = new JFXTextField();
                name.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                name.setPromptText("نام بازیکن شماره " + (j + 1) + " از تیم " + (i + 1));
                playerNames[i][j] = name;
                hBox.getChildren().add(name);
                JFXTextField lastName = new JFXTextField();
                lastName.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                lastName.setPromptText("فامیلی بازیکن شماره " + (j + 1) + " از تیم " + (i + 1));
                playerLastNames[i][j] = lastName;
                hBox.getChildren().add(lastName);
            }
        }
        postToImport = new Player.Post[numberOfTeams][];
        playerNamesToImport = new String[numberOfTeams][];
        PlayerLastNamesToImport = new String[numberOfTeams][];
        for (int i = 0; i < numberOfTeams; i++) {
            postToImport[i] = new Player.Post[Integer.parseInt(teamPlayerNumbers[i].getText())];
            playerNamesToImport[i] = new String[Integer.parseInt(teamPlayerNumbers[i].getText())];
            PlayerLastNamesToImport[i] = new String[Integer.parseInt(teamPlayerNumbers[i].getText())];
            for (int j = 0; j < Integer.parseInt(teamPlayerNumbers[i].getText()); j++) {
                playerNamesToImport[i][j] = playerNames[i][j].getText();
                PlayerLastNamesToImport[i][j] = playerLastNames[i][j].getText();
            }
        }
        fadeIn();
        playersDataImport.setVisible(true);
        /*switch (count) {
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
        count++;*/
    }

    private HBox makingAHBox(@NotNull VBox vBox) {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10));
        hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        vBox.getChildren().add(hBox);
        return hBox;
    }

    private VBox makingAVBox(@NotNull Label ScrollBarTop, @NotNull JFXScrollPane initializingScroll) {
        VBox content = new VBox();
        content.setPrefWidth(200);
        content.setSpacing(10);
        ScrollBarTop.setTextFill(Color.WHITE);
        ScrollBarTop.setStyle("-fx-text-fill:WHITE; -fx-font-size: 30;");
        initializingScroll.setContent(content);
        initializingScroll.getBottomBar().getChildren().add(ScrollBarTop);
        return content;
    }

    //  making a dialogue box
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

    //    browseButton for selecting a file for music
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

