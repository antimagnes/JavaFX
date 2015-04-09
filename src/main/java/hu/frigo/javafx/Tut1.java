package hu.frigo.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.stream.IntStream;

/**
 * Created by frigo on 15/04/08.
 */
public class Tut1 extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Abacus JavaFrX");
    Pane root = new Pane();

    IntStream.range(1, 11).forEach(row -> IntStream.range(1, 11).forEach(column -> {
      Circle circ = new Circle(column * 40 - 20, row * 40 - 20, 20);
      root.getChildren().add(circ);
    }));

    primaryStage.setScene(new Scene(root, 400, 400));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
