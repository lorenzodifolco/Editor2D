package ch.supsi.ed2d.gui.models;

import ch.supsi.ed2d.gui.GuiWithFileOpenedControllerTest;
import ch.supsi.ed2d.gui.commands.*;
import ch.supsi.ed2d.gui.controllers.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SuppressWarnings({"unused"})
class PipelineManagerTest extends GuiWithFileOpenedControllerTest {

    @Init
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Stop
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Start
    public void start(Stage stage) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/MainView.fxml")));
        MainViewController controllerInstance = new MainViewController(stage);
        controllerInstance.setSaveFileChooser(saveFileChooserMock);
        controllerInstance.setOpenFileChooser(openFileChooserMock);
        loader.setController(controllerInstance);

        root = loader.load();
        controllerInstance.onLoaded();

        stage.setScene(new Scene(root));
        stage.show();
    }


    @BeforeAll
    static void getInstance() {
        System.out.println("Init getInstance");
        assertNotNull(PipelineManager.getInstance());
    }


    @Test
    void testDragAndDropToPipelineWithUndoRedo(FxRobot robot) {
        System.out.println("Init testDragAndDropToPipelineWithUndoRedo");



        ListView<AbstractFilterCommand> listview;
        do {
            listview = (ListView<AbstractFilterCommand>) robot.lookup("#pipelineListView").tryQuery().orElse(null);
        } while (listview == null);


        assertEquals(0, listview.getItems().size());


        robot.drag("Blur", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 1");
        });

        robot.drag("Sharpen", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 2");
        });

        robot.drag("Gray Scale", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 3");
        });

        robot.drag("Black & White", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 4");
        });

        robot.drag("Ridge Detection", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 5");
        });

        assertEquals(5, listview.getItems().size());


        System.out.println("compute 5 undo");
        for (int i = 0; i <5; i++) {
            robot.clickOn("#menuedit");
            robot.clickOn("Undo");
            System.out.println("undo button pressed");
        }

        assertEquals(0, listview.getItems().size());


        System.out.println("compute 4 redo");
        for (int i = 0; i < 4; i++) {
            robot.clickOn("#menuedit");
            robot.clickOn("Redo");
            System.out.println("redo button pressed");
        }

        assertEquals(4, listview.getItems().size());

        System.out.println("End testDragAndDropToPipelineWithUndoRedo");
    }




}