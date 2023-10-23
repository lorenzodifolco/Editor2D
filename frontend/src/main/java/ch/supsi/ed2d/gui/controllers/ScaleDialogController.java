package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.listeners.TextFieldListener;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.imageproc.model.filters.Filter;
import ch.supsi.ed2d.imageproc.model.filters.Scale;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.concurrent.CompletableFuture;

public class ScaleDialogController implements Controller {
    private final CompletableFuture<Filter> completableFuture;

    private final Controller parentController;

    public ScaleDialogController(Controller parentController) {
        this.completableFuture = new CompletableFuture<>();
        this.parentController = parentController;
    }

    public CompletableFuture<Filter> loadAndShowView() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentController.getWindow());
        stage.setTitle("Scale");

        TextField x = new TextField();
        TextField y = new TextField();

        x.setPromptText("width");
        y.setPromptText("height");

        x.setId("x");
        y.setId("y");

        Text subtitle = new Text("Scaling options:");
        Text text1 = new Text("Input X");
        Text text2 = new Text("Input Y");
        Button ok = new Button("Ok");





        x.textProperty().addListener(new TextFieldListener(x));
        y.textProperty().addListener(new TextFieldListener(y));


        ok.setOnAction(e -> {
            if(x.getText().isEmpty() || y.getText().isEmpty()) {
                return;
            }

            if(x.getText().equals("0") || y.getText().equals("0")) {
                return;
            }


            var scale = new Scale(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()));
            completableFuture.complete(scale);
            stage.close();
        });

        GridPane root = new GridPane();

        //Setting size for the pane
        root.setMinSize(400, 200);

        //Setting the padding
        root.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        root.setVgap(5);
        root.setHgap(5);

        //Setting the Grid alignment
        root.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        root.add(subtitle,0,0);
        root.add(text1, 0, 1);
        root.add(x, 1, 1);
        root.add(text2, 0, 2);
        root.add(y, 1, 2);
        root.add(ok,1,3);
        GridPane.setHalignment(ok, HPos.RIGHT);
        Scene sc = new Scene(root, 300, 150);
        stage.getIcons().add(new Image("views/icons/scale.png"));
        stage.setResizable(false);
        stage.setScene(sc);
        stage.show();
        return completableFuture;
    }

    @Override
    public Window getWindow() {
        return this.parentController.getWindow();
    }
}
