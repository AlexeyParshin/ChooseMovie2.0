package chooseMovie.gui;

import chooseMovie.parsing.Movie;
import chooseMovie.parsing.ParsingThread;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Application {
    private Timeline timeline;

    @FXML
    private ListView<Movie> listMovies;

    @FXML
    private ImageView poster;

    @FXML
    private VBox listOfchMovies;

    @FXML
    private VBox listOfScores;

    @FXML
    private Label rate;

    @FXML
    public void handleMouseClick(MouseEvent ae) {
        if(listMovies != null) {
            if (ae.getButton().equals(MouseButton.PRIMARY) && (ae.getClickCount() == 2)) {
                mov = listMovies.getSelectionModel().getSelectedItem();
                Label newlabel = new Label(mov.getName());
                newlabel.setFont(new Font(20));
                if (listOfchMovies.getChildren().size() < 8)
                    listOfchMovies.getChildren().add(newlabel);
            } else {
                mov = listMovies.getSelectionModel().getSelectedItem();
                Image image = new Image(mov.getImgURL());
                poster.setImage(image);
                rate.setText(mov.getRate());
            }
        }
    }

    public void initialize(){
        try{
            Thread.sleep(500);
        }catch (InterruptedException exc){
            System.out.println(exc);
        }
        for (int i = 0; i < 18; i++) {
            listMovies.getItems().add(movies.get((int) (Math.random() * movies.size())));
        }
    }

    @FXML
    public void onEnter(KeyEvent ae) {
        if (ae.getCode() == KeyCode.ENTER) {
            mov = listMovies.getSelectionModel().getSelectedItem();
            Label newlabel = new Label(mov.getName());
            newlabel.setFont(new Font(20));
            newlabel.setMaxSize(300, 30);
            if (listOfchMovies.getChildren().size() < 8)
                listOfchMovies.getChildren().add(newlabel);
        }

    }

    private Movie mov;

    private List<Movie> movies = new ArrayList<>();

    public Controller() {
        List<ParsingThread> allthreads = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            allthreads.add(new ParsingThread("https://www.kinopoisk.ru/s/type/film/list/1/order/rating/m_act%5Bgenre%5D%5B0%5D/6/m_act%5Bmpaa%5D/R/m_act%5Btype%5D/film/set_result_type/simple/page/" + i + "/"));
        }

        allthreads.forEach(thrd -> {
            try {
                thrd.join();
                movies.addAll(thrd.getMovies());
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
        });
    }

    public void pognalei(ActionEvent ae) {
        listOfScores.getChildren().forEach(hb -> ((HBox) hb).getChildren().clear());
        int numOfMovs = listOfchMovies.getChildren().size();
        Image spanishGuy = new Image(getClass().getResourceAsStream("/spanishGuy.jpg"));

        if (numOfMovs > 0) {
            timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(1000),
                    act -> {
                        HBox newHBox = (HBox) listOfScores.getChildren().get((int) (Math.random() * numOfMovs));
                        Label spanishLabel = new Label();
                        spanishLabel.setGraphic(new ImageView(spanishGuy));

                        newHBox.getChildren().add(spanishLabel);

                        if (newHBox.getChildren().size() == 5) {
                            Media laugh = new Media(this.getClass().getResource("/sound/laugh.mp3").toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(laugh);
                            mediaPlayer.setVolume(1);
                            mediaPlayer.play();
                            try {
                                timeline.stop();
                            } catch (Exception exc) {
                                System.out.println(exc);
                            }

                        }
                    }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public void refresh(ActionEvent ae){
        timeline.stop();
        listOfchMovies.getChildren().clear();
        listOfScores.getChildren().forEach(hb -> ((HBox) hb).getChildren().clear());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("ChooseMovie2.0");
        primaryStage.setScene(new Scene(root, 700, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void generate(ActionEvent ae) {
        listMovies.getItems().clear();
        for (int i = 0; i < 18; i++) {
            listMovies.getItems().add(movies.get((int) (Math.random() * movies.size())));
        }
    }
}
