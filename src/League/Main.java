package League;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("League3.fxml"));
//        make the window movable by click on and dragging from any vacant place:
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Purple League");
//        primaryStage.getIcons();
        primaryStage.show();
        //        final StackPane layout = new StackPane();
        /*ArrayList<String> images = new ArrayList<>();
        images.add("League/backs/1.jpg");
        images.add("League/backs/2.jpg");
        images.add("League/backs/3.jpg");
        images.add("League/backs/4.jpg");
        images.add("League/backs/5.jpg");
        images.add("League/backs/6.jpg");*/

       /* String[] images = {"League/backs/1.jpg",
                "League/backs/2.jpg",
                "League/backs/3.jpg",
                "League/backs/4.jpg",
                "League/backs/5.jpg",
                "League/backs/6.jpg"};

        Pagination pagination = new Pagination(images.length);
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            int pos = (pagination.getCurrentPageIndex() + 1) % pagination.getPageCount();
            pagination.setCurrentPageIndex(pos);
            try {
                new ImageView(new Image(new FileInputStream(images[pos])));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        primaryStage.setScene(new Scene(pagination));
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}

