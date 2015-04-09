package hu.frigo.javafx;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.stream.IntStream;

/**
 * Created by frigo on 15/04/09.
 */
public class Tut6 extends Application {

  public static final int ROW_COUNT = 10;
  public static final int COL_COUNT = 10;
  public static final Circle[][] circs = new Circle[ROW_COUNT+1][COL_COUNT+1];
  public static final int RADIUS = 20;
  public static final int DIAMETER = RADIUS * 2;
  public static final int WIDTH = COL_COUNT * DIAMETER;
  public static final int HEIGHT = ROW_COUNT * DIAMETER;
  public static final int MOVE_WAY = 100;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Abacus JavaFrX " + this.getClass().getName());
    Pane root = new Pane();

    IntStream.range(1, ROW_COUNT + 1).forEach(row -> {
      Rectangle rail = new Rectangle(0, -5 + RADIUS + (row - 1) * DIAMETER, WIDTH + MOVE_WAY, 10);
      rail.getStyleClass().add("rail");
      root.getChildren().add(rail);
      IntStream.range(1, COL_COUNT + 1).forEach(column -> {
        Circle circ = new Circle(column * DIAMETER - RADIUS, row * DIAMETER - RADIUS, RADIUS - 1);
        circ.getStyleClass().add(column <= COL_COUNT / 2 ? "left" : "right");
        root.getChildren().add(circ);
        circ.setOnMouseClicked(event -> {
          TranslateTransition move = new TranslateTransition(Duration.millis(200), circ);
          move.setToX(Math.abs(circ.getTranslateX() - MOVE_WAY));
          move.playFromStart();
        });
        Text numba = new Text(circ.getCenterX() - 3, circ.getCenterY() + 4, "" + column);
        numba.getStyleClass().add("text");
        numba.setFill(Color.WHITE);
        numba.setOnMouseClicked(circ.getOnMouseClicked());
        numba.translateXProperty().bind(circ.translateXProperty());
        root.getChildren().add(numba);
        circs[row-1][column-1] = circ;
      });
    });

    IntStream.range(0, ROW_COUNT).forEach(row -> IntStream.range(1, COL_COUNT).forEach(column -> {
      Circle prevC = circs[row][column - 1];
      Circle currC = circs[row][column];
      prevC.translateXProperty().addListener((obsValue, oldX, newX) -> {
        if ((Double) newX > currC.getTranslateX()) {
          currC.setTranslateX((Double) newX);
        }
      });
      currC.translateXProperty().addListener((obsValue, oldX, newX) -> {
        if ((Double) newX < prevC.getTranslateX()) {
          prevC.setTranslateX((Double) newX);
        }
      });
    }));

    final Scene scene = new Scene(root, WIDTH + MOVE_WAY, HEIGHT);
    scene.getStylesheets().add("/abacus.css");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
