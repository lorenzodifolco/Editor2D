package ch.supsi.ed2d.gui.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

@SuppressWarnings("unused")
public class AlertBuilder {
    private final Alert alert;
    private boolean withButtonType = false;

    public AlertBuilder(Alert.AlertType type)
    {
        this.alert = new Alert(type);
    }

    public AlertBuilder withHeader(String header)
    {
        this.alert.setHeaderText(header);
        return this;
    }

    public AlertBuilder withButtonType(ButtonType t)
    {
        if(!withButtonType) {
            this.alert.getButtonTypes().clear();
            this.withButtonType = true;
        }
        this.alert.getButtonTypes().add(t);
        var button = (Button) alert.getDialogPane().lookupButton(t);
        button.setId("alertButton"+t.getText());
        return this;
    }

    public AlertBuilder withTitle(String title)
    {
        this.alert.setTitle(title);
        return this;
    }

    public AlertBuilder withContent(String content)
    {
        this.alert.setContentText(content);
        return this;
    }

    public Alert show()
    {
        this.alert.show();
        return alert;
    }

    public Alert getAlert()
    {
        return alert;
    }

    public Optional<ButtonType> showAndWait()
    {
        return this.alert.showAndWait();
    }
}
