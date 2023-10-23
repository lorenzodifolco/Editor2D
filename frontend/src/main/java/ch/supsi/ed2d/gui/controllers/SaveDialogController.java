package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.commands.AbstractSaveFileChooserCommand;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.imageproc.PNM.PNMInterpreter;
import ch.supsi.ed2d.imageproc.model.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class SaveDialogController implements Controller {
    @FXML
    private VBox savingOptionsVbox;
    private final ToggleGroup tg = new ToggleGroup();
    private Stage stage;
    private final Controller parentController;
    private final CompletableFuture<SaveDialogResult> completableFuture;
    private final HashMap<RadioButton, Type> toggles = new HashMap<>();
    private final HashMap<Type, String> extensions = new HashMap<>();

    private AbstractSaveFileChooserCommand saveFileChooserCommand;

    public SaveDialogController(Controller parentController) {
        this.parentController = parentController;
        this.completableFuture = new CompletableFuture<>();
    }

    public CompletableFuture<SaveDialogResult> loadAndShowView() throws IOException
    {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dialogs/SaveDialogView.fxml"));
        loader.setController(this);
        root = loader.load();
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentController.getWindow());
        stage.setTitle("Save file");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setIconified(false);

        savingOptionsVbox.setSpacing(12);
        savingOptionsVbox.getChildren().add(getOptionsForPNM());

        stage.setOnCloseRequest((e)->completableFuture.complete(null));
        stage.show();
        return completableFuture;
    }

    private VBox getOptionsForPNM()
    {
        VBox vBox = new VBox();
        vBox.setSpacing(12);

        var lb = new Label("PNM file options:");
        lb.setFont(new Font("System",15));
        lb.setPadding(new Insets(0,12,0,0));

        vBox.getChildren().add(lb);

        RadioButton[] radioButtons = {new RadioButton(".pbm black and white image"),
                new RadioButton(".pgm gray scale image"), new RadioButton(".ppm full color image")};

        radioButtons[0].setId("pbmRadio");
        radioButtons[1].setId("pgmRadio");
        radioButtons[2].setId("ppmRadio");

        toggles.put(radioButtons[0], PNMInterpreter.PNMTypes.P1);
        toggles.put(radioButtons[1], PNMInterpreter.PNMTypes.P2);
        toggles.put(radioButtons[2], PNMInterpreter.PNMTypes.P3);

        extensions.put(PNMInterpreter.PNMTypes.P1,"pbm");
        extensions.put(PNMInterpreter.PNMTypes.P2,"pgm");
        extensions.put(PNMInterpreter.PNMTypes.P3,"ppm");

        for(RadioButton rb : radioButtons)
        {
            rb.setToggleGroup(tg);
            vBox.getChildren().add(rb);
        }

        return vBox;
    }

    @FXML
    private void save(ActionEvent ignored) {
        if(tg.getSelectedToggle() != null)
        {
            String extension = extensions.get(toggles.get((RadioButton) tg.getSelectedToggle()));

            saveFileChooserCommand.setExtension(extension).execute().whenComplete((f, throwable)->{
                if (f != null && !f.exists() && throwable == null) {
                    completableFuture.complete(new SaveDialogResult(toggles.get((RadioButton) tg.getSelectedToggle()), f.getAbsolutePath() + "." + extension));
                    stage.close();
                }
                else if(throwable != null) {
                    completableFuture.completeExceptionally(throwable);
                    stage.close();
                }
                else
                    completableFuture.complete(null);
            });
        }
        else
            new AlertBuilder(Alert.AlertType.WARNING).withTitle("Error").withHeader("Choose a file type!").withContent("A file encoding format must be selected to save this image").show();
    }

    @FXML
    private void cancel(ActionEvent ignored) {
        completableFuture.complete(null);
        this.stage.close();
    }

    @Override
    public Window getWindow() {
        return this.parentController.getWindow();
    }

    public void setSaveFileChooserCommand(AbstractSaveFileChooserCommand saveFileChooserCommand)
    {
        this.saveFileChooserCommand = saveFileChooserCommand;
    }
    public static class SaveDialogResult {
        private final Type t;
        private final String path;

        public SaveDialogResult(Type t, String path) {
            this.t = t;
            this.path = path;
        }

        public Type getType() {
            return t;
        }

        public String getPath() {
            return path;
        }
    }
}
