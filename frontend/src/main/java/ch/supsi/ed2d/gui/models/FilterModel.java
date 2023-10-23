package ch.supsi.ed2d.gui.models;

public class FilterModel {

    /*public static final ObservableList<FilterModel> filters = FXCollections.observableArrayList(
            new FilterModel("Gray Scale", "views/icons/grayscale.png"));
    */

    private final String filterName;

    private final String iconPath;

    public FilterModel(String filterName, String iconPath) {
        this.filterName = filterName;
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getFilterName() {
        return filterName;
    }
}
