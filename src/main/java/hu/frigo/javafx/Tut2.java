package hu.frigo.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.stream.IntStream;

/**
 * Created by frigo on 15/04/09.
 */
public class Tut2 extends Application {

  public static final int ROW_COUNT = 10;
  public static final int COL_COUNT = 10;
  public static final int RADIUS = 20;
  public static final int DIAMETER = RADIUS * 2;
  public static final int WIDTH = COL_COUNT * DIAMETER;
  public static final int HEIGHT = ROW_COUNT * DIAMETER;
  public static final int MOVE_WAY = 100;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Abacus JavaFrX 2");
    Pane root = new Pane();

    IntStream.range(1, 11).forEach(row -> IntStream.range(1, 11).forEach(column -> {
      Circle circ = new Circle(column * DIAMETER - RADIUS, row * DIAMETER - RADIUS, RADIUS - 1);
      root.getChildren().add(circ);
      circ.setOnMouseClicked(event -> circ.setTranslateX(Math.abs(circ.getTranslateX() - MOVE_WAY)));
    }));

    primaryStage.setScene(new Scene(root, WIDTH + MOVE_WAY, HEIGHT));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
