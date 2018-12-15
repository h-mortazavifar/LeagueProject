package League;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private double xOffset;
    private double yOffset;
    private ImageView current;
    private ImageView next;
    private int height = 800;
    private int width = 600;
    private ScannerLoader loader;
    private BlockingQueue<Image> images = new ArrayBlockingQueue(5);
    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("League3.fxml"));
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = fxmlLoader.load();
//        accessing controller class:
        controller = (Controller) fxmlLoader.getController();
//        accessing controller's StackPane:
//        controller.forSlide.setStyle("-fx-background-color: #000000;");
//        Parent root = fxmlLoader.load(getClass().getResource("League3.fxml"));
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
//        for removing title bar of scene:
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        for rounded corners and removing title bar of scene while there is no background and rounded corners are defined in css:
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        round corners with a background:
        Rectangle rect = new Rectangle(800, 600);
        rect.setArcHeight(60.0);
        rect.setArcWidth(60.0);
        root.setClip(rect);
        scene.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Purple League");
        primaryStage.getIcons().add(new Image("League/backs/Asset 13x.png"));
        primaryStage.show();

        //        for background slideshow if you don't want it you can comment the following lines:
//         Start worker thread, and kick off first fade in.
        loader = new ScannerLoader();
        loader.start();
        Image image = getNextImage();
        if (image != null) {
            startImage(image);
        }
    }

    /**
     * Scans directories and loads images one at a time.
     */
    class ScannerLoader extends Thread implements FileVisitor<Path> {
        // Directory to start scanning for pics
        String dialogue = "C:\\Users\\hmort\\IdeaProjects\\LeagueProject\\src\\League\\backs\\slideshow";
        boolean complete;

        @Override
        public void run() {
            System.out.println("scanning");
            try {
                Files.walkFileTree(Paths.get(dialogue), this);
                System.out.println("complete");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                complete = true;
            }
        }

        @Override
        public FileVisitResult preVisitDirectory(Path t, BasicFileAttributes bfa) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path t, BasicFileAttributes bfa) {
            try {
                Image image = new Image(t.toUri().toString(),
                        width, height, true, true, false);
                if (!image.isError()) {
                    images.put(image);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path t, IOException ioe) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path t, IOException ioe) {
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public void stop() throws Exception {
        loader.interrupt();
        loader.join();
        super.stop();
    }

    private Image getNextImage() {
        if (loader.complete) {
            return images.poll();
        }
        try {
            return images.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void startImage(Image image) {
        ObservableList<Node> c = controller.forSlide.getChildren();
        if (current != null)
            c.remove(current);
        current = next;
        next = null;
        // Create fade-in for new image.
        next = new ImageView(image);
        next.setFitHeight(height);
        next.setFitHeight(width);
        next.setPreserveRatio(true);
        next.setOpacity(0);
        c.add(next);
        FadeTransition fadein = new FadeTransition(Duration.seconds(1), next);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        SequentialTransition st;
        if (current != null) {
            ScaleTransition dropout;
            dropout = new ScaleTransition(Duration.seconds(1), current);
            dropout.setInterpolator(Interpolator.EASE_BOTH);
            dropout.setFromX(1);
            dropout.setFromY(1);
            dropout.setToX(0.75);
            dropout.setToY(0.75);
            st = new SequentialTransition(
                    new ParallelTransition(fadein, dropout), delay);
        } else {
            st = new SequentialTransition(
                    fadein, delay);
        }
        st.setOnFinished(t -> {
            Image image1 = getNextImage();
            if (image1 != null)
                startImage(image1);
        });
        st.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

