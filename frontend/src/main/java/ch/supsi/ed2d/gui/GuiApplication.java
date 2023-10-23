package ch.supsi.ed2d.gui;

import ch.supsi.ed2d.gui.controllers.MainViewController;
import ch.supsi.ed2d.gui.commands.OpenFileChooserCommand;
import ch.supsi.ed2d.gui.commands.SaveFileChooserCommand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GuiApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/MainView.fxml")));
        stage = new Stage();
        MainViewController newMainController = new MainViewController(stage);
        newMainController.setOpenFileChooser(new OpenFileChooserCommand(stage.getOwner()));
        newMainController.setSaveFileChooser(new SaveFileChooserCommand(stage.getOwner()));
        loader.setController(newMainController);
        root = loader.load();
        newMainController.onLoaded();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(GuiApplication.class,args);
    }
}
