package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.GuiControllerTest;
import ch.supsi.ed2d.gui.commands.AbstractSaveFileChooserCommand;
import ch.supsi.ed2d.gui.models.Command;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.*;

import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

//todo verify close works in menu when a filter is applied and the image hasn't already been saved... It should ask if you want to save the image

@TestMethodOrder(MethodOrderer.MethodName.class)
@SuppressWarnings({"StatementWithEmptyBody","unused"})
public class MainViewControllerTest  extends GuiControllerTest {

    private File fileInstance;

    @TempDir
    private File tempDir;
    private final Command<File> openFileChooserMock = () -> CompletableFuture.supplyAsync(() -> fileInstance);

    private static final int timeout = 5000;

    private final AbstractSaveFileChooserCommand saveFileChooserMock = new AbstractSaveFileChooserCommand() {
        @Override
        public CompletableFuture<File> execute() {
            return CompletableFuture.supplyAsync(() -> fileInstance);
        }
    };

    @Init
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }


    @Start
    public void start(Stage stage) throws IOException
    {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/MainView.fxml")));
        MainViewController controllerInstance = new MainViewController(stage);
        controllerInstance.setSaveFileChooser(saveFileChooserMock);
        controllerInstance.setOpenFileChooser(openFileChooserMock);
        loader.setController(controllerInstance);
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Stop
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    /**
     * Check the layout of main view is correct
     */
    @Test
    void test00(FxRobot robot)
    {
        System.out.println("Init test0");
        ClassLoader classLoader = MainViewControllerTest.class.getClassLoader();
        fileInstance = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
        verifyThat("#menubar", NodeMatchers.isVisible());
        verifyThat("#welcomeLabel", NodeMatchers.isVisible());
        verifyThat("#welcomeLabel", LabeledMatchers.hasText("First things first, you should load an image"));
        verifyThat("#bottomBarLabel",LabeledMatchers.hasText(""));
        verifyThat("#filtersListView", NodeMatchers.isDisabled());
        verifyThat("#pipelineListView", NodeMatchers.isDisabled());
        robot.clickOn("Help").clickOn("About");
        waitForAsync(timeout, ()->{
            Node dialogPane = robot.lookup(".dialog-pane").query();
            while(robot.from(dialogPane).lookup((Text t) -> t.getText().equals("SUPSI 2022\nCicco Adriano, Crugnola Fabio, Di Folco Lorenzo")).tryQuery().isEmpty());
        });
        robot.clickOn("OK");
        System.out.println("End test0");
    }

    /**
     * Opens file and then tries to save it... This test is done in this way because we can't save an image if nothing has been opened.
     */
    @Test
    void test01(FxRobot robot)
    {
        System.out.println("Init test1");
        ClassLoader classLoader = MainViewControllerTest.class.getClassLoader();
        fileInstance = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
        robot.clickOn("File").clickOn("Open");
        assertTrue(Objects.requireNonNull(robot.lookup("#bottomBarProgressBar").tryQuery().orElse(null)).isVisible());
        assertEquals("Loading image...", ((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText());
        waitForAsync(timeout, ()-> {
            while(robot.lookup("#mainImageView").tryQuery().isEmpty());
            while(!((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("Successfully loaded: "));
        });
        assertTrue(((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("bliss_ascii.ppm"));
        verifyThat("#bottomBarProgressBar", NodeMatchers.isInvisible());
        verifyThat("#filtersListView", NodeMatchers.isEnabled());
        verifyThat("#pipelineListView", NodeMatchers.isEnabled());

        fileInstance = new File(tempDir,"test");
        robot.clickOn("File").clickOn("Save").clickOn("#pgmRadio").clickOn("Save");
        waitForAsync(timeout, ()-> {
            while(robot.lookup("#mainImageView").tryQuery().isEmpty());
            while(!((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("Saved new image to "));
        });
        assertTrue(((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("test.pgm"));
        File f = new File(tempDir,"test.pgm");
        assertTrue(f.exists());
        assertTrue(f.delete());
        System.out.println("End test1");
    }


    /**
     * Opens non existent file
     */
    @Test
    void test02(FxRobot robot)
    {
        System.out.println("Init test2");
        fileInstance = new File("nonexistent.ppm");

        robot.clickOn("File").clickOn("Open");
        waitForAsync(timeout,()->{
            Node dialogPane = robot.lookup(".dialog-pane").query();
            while(robot.from(dialogPane).lookup((Text t) -> t.getText().contains("FileNotFound")).tryQuery().isEmpty());
        });
        robot.clickOn("OK");
        assertEquals("", ((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText());
        verifyThat("#bottomBarProgressBar", NodeMatchers.isInvisible());
        verifyThat("#filtersListView", NodeMatchers.isDisabled());
        verifyThat("#pipelineListView", NodeMatchers.isDisabled());
        System.out.println("End test2");
    }

    /**
     * Opens not supported file
     */
    @Test
    void test03(FxRobot robot)
    {
        System.out.println("Init test3");
        ClassLoader classLoader = MainViewControllerTest.class.getClassLoader();
        fileInstance = new File(Objects.requireNonNull(classLoader.getResource("")).getPath()+"/file_with_unsupported_exception.yhj");

        robot.clickOn("File").clickOn("Open");
        waitForAsync(timeout,()->{
            Node dialogPane = robot.lookup(".dialog-pane").query();
            while(robot.from(dialogPane).lookup((Text t) -> t.getText().contains("FileNotSupported")).tryQuery().isEmpty());
        });
        robot.clickOn("OK");
        assertEquals("", ((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText());
        verifyThat("#bottomBarProgressBar", NodeMatchers.isInvisible());
        verifyThat("#filtersListView", NodeMatchers.isDisabled());
        verifyThat("#pipelineListView", NodeMatchers.isDisabled());
        System.out.println("End test3");
    }


    /**
     * saves without image loaded
     */
    @Test
    void test04(FxRobot robot)
    {
        System.out.println("Init test4");
        robot.clickOn("File").clickOn("Save");
        waitForAsync(timeout,()->{
            Node dialogPane = robot.lookup(".dialog-pane").query();
            while(robot.from(dialogPane).lookup((Text t) -> t.getText().contains("You should open a file to save something")).tryQuery().isEmpty());
        });
        robot.clickOn("OK");
        System.out.println("End test4");
    }
}