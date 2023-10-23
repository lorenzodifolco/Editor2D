package ch.supsi.ed2d.gui.models;

import ch.supsi.ed2d.gui.commands.AbstractFilterCommand;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class FilterListCell extends ListCell<AbstractFilterCommand> {
    @Override
    protected void updateItem(AbstractFilterCommand item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            ImageView imageView = new ImageView(new javafx.scene.image.Image(item.getFilterModel().getIconPath()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            setGraphic(imageView);
            setText(item.getFilterModel().getFilterName());
        }
    }
}