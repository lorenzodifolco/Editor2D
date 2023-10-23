package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.GuiControllerTest;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.imageproc.model.filters.Filter;
import ch.supsi.ed2d.imageproc.model.filters.Scale;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SuppressWarnings({"unused"})
class ScaleDialogControllerTest extends GuiControllerTest {

    private ScaleDialogController scaleDialogController;
    private CompletableFuture<Filter> completableFuture;

    private static final int timeout = 5000;


    @Init
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Start
    public void start(Stage stage)
    {
        System.out.print("creating border pane...");
        final var bp = new BorderPane();
        System.out.println("done");

        System.out.print("creating and setting stage scene...");
        stage.setScene(new Scene(bp));
        System.out.println("done");

        System.out.print("showing stage...");
        stage.show();
        System.out.println("done");

        Controller mockController = ()->bp.getScene().getWindow();

        scaleDialogController = new ScaleDialogController(mockController);

        assertDoesNotThrow(()->{
            completableFuture = scaleDialogController.loadAndShowView();
        });
    }

    @Stop
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testOpen(FxRobot fxRobot) {
        System.out.print("testOpen");
        assertNotNull(scaleDialogController.getWindow());
        fxRobot.clickOn("Ok");
        System.out.println("done");
    }

    @Test
    void testCloseX(FxRobot fxRobot)
    {
        System.out.println("testCloseX");
        assertNotNull(scaleDialogController.getWindow());
        TextField x= (TextField) fxRobot.lookup("#x").tryQuery().orElse(null);
        assertNotNull(x);
        System.out.println("set only x");

        x.setText("100");
        fxRobot.clickOn("Ok");

        assertFalse(completableFuture.isDone());
        System.out.println("done");

    }

    @Test
    void testCloseY(FxRobot fxRobot)
    {
        System.out.println("testCloseY");
        assertNotNull(scaleDialogController.getWindow());
        TextField y= (TextField) fxRobot.lookup("#y").tryQuery().orElse(null);
        assertNotNull(y);
        y.setText("100");
        fxRobot.clickOn("Ok");
        assertFalse(completableFuture.isDone());
        System.out.println("done");
    }

    @Test
    void testCloseBoth(FxRobot fxRobot) {
        System.out.println("testCloseBoth");
        assertNotNull(scaleDialogController.getWindow());
        TextField x= (TextField) fxRobot.lookup("#x").tryQuery().orElse(null);
        assertNotNull(x);
        TextField y= (TextField) fxRobot.lookup("#y").tryQuery().orElse(null);
        assertNotNull(y);

        assertFalse(completableFuture.isDone());

        System.out.println("set boot");
        x.setText("100");
        y.setText("100");
        fxRobot.clickOn("Ok");
        System.out.println("done");

        assertDoesNotThrow(()->{
            assertEquals(new Scale(100,100), completableFuture.get());
        });

        assertTrue(completableFuture.isDone());
        System.out.println("done");

    }






}