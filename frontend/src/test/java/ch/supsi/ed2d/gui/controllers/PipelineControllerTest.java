package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.GuiControllerTest;
import ch.supsi.ed2d.gui.GuiWithFileOpenedControllerTest;
import ch.supsi.ed2d.gui.commands.*;
import ch.supsi.ed2d.gui.models.Command;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.service.query.PointQuery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.waitForAsync;

public class PipelineControllerTest extends GuiWithFileOpenedControllerTest {

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



    @Test
    void testDragAndDropToPipeline(FxRobot robot) {
        System.out.println("Init testDragAndDropToPipeline");



        ListView<AbstractFilterCommand> listview;
        do {
            listview = (ListView<AbstractFilterCommand>) robot.lookup("#pipelineListView").tryQuery().orElse(null);
        } while (listview == null);


        assertEquals(0, listview.getItems().size());



        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 1");
        });


            robot.drag("Scale", MouseButton.PRIMARY).interact(()-> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 2");
        });


        TextField x= (TextField) robot.lookup("#x").tryQuery().orElse(null);
        TextField y= (TextField) robot.lookup("#y").tryQuery().orElse(null);
        x.setText("100");
        y.setText("200");

        robot.clickOn("Ok");



        robot.drag("Right Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 3");
        });

        robot.drag("Vertical Flip", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 4");
        });

        robot.drag("Orizontal Flip", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 5");
        });

        robot.drag("Blur", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 6");
        });

        robot.drag("Sharpen", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 7");
        });

        robot.drag("Gray Scale", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 8");
        });

        robot.drag("Black & White", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 9");
        });

        robot.drag("Ridge Detection", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped 10");
        });

        assertEquals(10, listview.getItems().size());

        System.out.println("End testDragAndDropToPipeline");
    }
    @Test
    void testRemoveFromPipelineInside(FxRobot robot) {
        System.out.println("Init testRemoveFromPipeline");



        ListView<AbstractFilterCommand> listview;
        do {
            listview = (ListView<AbstractFilterCommand>) robot.lookup("#pipelineListView").tryQuery().orElse(null);
        } while (listview == null);

        assertEquals(0, listview.getItems().size());


        System.out.println("dragging Left Rotate");
        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        System.out.println("dragging Sharpen");
        robot.drag("Sharpen", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Gray Scale");
        robot.drag("Gray Scale", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Left Rotate");
        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        assertEquals(4, listview.getItems().size());

        List<AbstractFilterCommand> list = new ArrayList<>();
        list.add(new RotateLeftCommand());
        list.add(new SharpenCommand());
        var gray=new GrayScaleCommand();
        list.add(gray);
        list.add(new RotateLeftCommand());

        assertEquals(list.size(), listview.getItems().size());

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i).getClass(), listview.getItems().get(i).getClass());
        }


        var nodeQuerys = robot.lookup("Gray Scale").queryAll().stream().toList();

        System.out.println("dragging Gray Scale for remove");
        robot.drag(nodeQuerys.get(3), MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#filtersListView");
            System.out.println("dropped");
        });

        list.remove(gray);

        assertEquals(list.size(), listview.getItems().size());

        for (int j = 0; j < list.size(); j++) {
            assertEquals(list.get(j).getClass(), listview.getItems().get(j).getClass());
        }

        System.out.println("End testRemoveFromPipeline");
    }
    @Test
    void testRemoveFromPipelineFirstAndLast(FxRobot robot) {
        System.out.println("Init testRemoveFromPipeline");



        ListView<AbstractFilterCommand> listview;
        do {
            listview = (ListView<AbstractFilterCommand>) robot.lookup("#pipelineListView").tryQuery().orElse(null);
        } while (listview == null);

        assertEquals(0, listview.getItems().size());


        System.out.println("dragging Left Rotate");
        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        System.out.println("dragging Sharpen");
        robot.drag("Sharpen", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Gray Scale");
        robot.drag("Gray Scale", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Ridge Detection");
        robot.drag("Ridge Detection", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        assertEquals(4, listview.getItems().size());

        List<AbstractFilterCommand> list = new ArrayList<>();
        var rotate=new RotateLeftCommand();
        list.add(rotate);
        list.add(new SharpenCommand());
        list.add(new GrayScaleCommand());

        var ridge=new RidgeDetectionCommand();
        list.add(ridge);

        assertEquals(list.size(), listview.getItems().size());

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i).getClass(), listview.getItems().get(i).getClass());
        }


        var nodeQuerys = robot.lookup("Left Rotate").queryAll().stream().toList();

        System.out.println("dragging Right Rotate for remove");
        robot.drag(nodeQuerys.get(3), MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#filtersListView");
            System.out.println("dropped");
        });


        list.remove(rotate);

        assertEquals(list.size(), listview.getItems().size());

        for (int j = 0; j < list.size(); j++) {
            assertEquals(list.get(j).getClass(), listview.getItems().get(j).getClass());
        }

        nodeQuerys = robot.lookup("Ridge Detection").queryAll().stream().toList();

        System.out.println("dragging Ridge Detection for remove");
        robot.drag(nodeQuerys.get(3), MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#filtersListView");
            System.out.println("dropped");
        });


        list.remove(ridge);

        assertEquals(list.size(), listview.getItems().size());

        for (int j = 0; j < list.size(); j++) {
            assertEquals(list.get(j).getClass(), listview.getItems().get(j).getClass());
        }
        System.out.println("End testRemoveFromPipeline");
    }
    @Test
    void testInsertInsideElementsPipeline(FxRobot robot) {
        System.out.println("Init testRemoveFromPipeline");

        ListView<AbstractFilterCommand> listview;
        do {
            listview = (ListView<AbstractFilterCommand>) robot.lookup("#pipelineListView").tryQuery().orElse(null);
        } while (listview == null);

        assertEquals(0, listview.getItems().size());


        System.out.println("dragging Left Rotate");
        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        System.out.println("dragging Sharpen");
        robot.drag("Sharpen", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Gray Scale");
        robot.drag("Gray Scale", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });

        System.out.println("dragging Left Rotate");
        robot.drag("Left Rotate", MouseButton.PRIMARY).interact(() -> {
            robot.dropTo("#pipelineListView");
            System.out.println("dropped");
        });


        assertEquals(4, listview.getItems().size());

        List<AbstractFilterCommand> list = new ArrayList<>();
        list.add(new RotateLeftCommand());
        list.add(new SharpenCommand());
        var gray=new GrayScaleCommand();
        list.add(gray);
        list.add(new RotateLeftCommand());

        assertEquals(list.size(), listview.getItems().size());

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i).getClass(), listview.getItems().get(i).getClass());
        }


        var nodeQuerys = robot.lookup("Sharpen").queryAll().stream().toList();

        System.out.println("dragging RightRotation for remove");
        robot.drag("Right Rotate" , MouseButton.PRIMARY).interact(() -> {
            robot.dropTo(nodeQuerys.get(3));
            System.out.println("dropped");
        });

        list.add(1,new RotateRightCommand());
        assertEquals(list.size(), listview.getItems().size());

        for (int j = 0; j < list.size(); j++) {
            assertEquals(list.get(j).getClass(), listview.getItems().get(j).getClass());
        }

        System.out.println("End testRemoveFromPipeline");
    }


}