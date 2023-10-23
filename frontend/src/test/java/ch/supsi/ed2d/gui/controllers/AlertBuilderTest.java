package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.GuiControllerTest;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.waitForAsyncFx;

/**
 * method showAndWait is not testable since it requires external input from a user to be executed correctly
 */


class AlertBuilderTest extends GuiControllerTest {

    private static final int timeout = 5000;

    @Test
    void typeCheck(FxRobot ignored) {
        System.out.println("Init typeCheck");

        waitForAsyncFx(timeout, ()->{
            var alert = new AlertBuilder(Alert.AlertType.INFORMATION).show();
            assertEquals(Alert.AlertType.INFORMATION, alert.getAlertType());
            alert.close();
        });
        System.out.println("End typeCheck");

    }


    @Test
    void withHeader(FxRobot ignored) {
        System.out.println("Init withHeader");
        waitForAsyncFx(timeout, ()->{
            var alert = new AlertBuilder(Alert.AlertType.INFORMATION).withHeader("test header").show();
            assertEquals("test header", alert.getHeaderText());
            alert.close();
        });
        System.out.println("End withHeader");
    }

    @Test
    void withTitle(FxRobot ignored) {
        System.out.println("Init withTitle");
        waitForAsyncFx(timeout, ()->{
            var alert = new AlertBuilder(Alert.AlertType.INFORMATION).withTitle("test header").show();
            assertEquals("test header", alert.getTitle());
            alert.close();
        });
        System.out.println("End withTitle");
    }

    @Test
    void withContent(FxRobot ignored) {
        System.out.println("Init withContent");
        waitForAsyncFx(timeout, ()->{
            var alert = new AlertBuilder(Alert.AlertType.INFORMATION).withContent("test header").show();
            assertEquals("test header", alert.getContentText());
            alert.close();
        });
        System.out.println("End withContent");
    }

    @Test
    void show(FxRobot ignored) {
        System.out.println("Init show");
        waitForAsyncFx(timeout, ()->{
            var ab = new AlertBuilder(Alert.AlertType.INFORMATION).withContent("test header").show();
            verifyThat("test header", NodeMatchers.isVisible());
            ab.close();
        });
        System.out.println("End show");
    }

    @Test
    void withButtonType(FxRobot ignored) {
        System.out.println("Init withButtonType");
        waitForAsyncFx(timeout, ()->{
            var ab = new AlertBuilder(Alert.AlertType.INFORMATION).withButtonType(ButtonType.APPLY).show();
            ab.getButtonTypes().forEach(bt-> assertEquals(bt, ButtonType.APPLY));
            ab.close();
        });
        System.out.println("End withButtonType");
    }
}