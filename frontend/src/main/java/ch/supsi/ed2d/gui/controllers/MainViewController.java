package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.commands.*;
import ch.supsi.ed2d.gui.models.Command;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.gui.models.PipelineManager;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pipeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"unused", "SameParameterValue"})
public class MainViewController implements Controller {
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label bottomBarLabel;
    @FXML
    private ProgressBar bottomBarProgressBar;

    @FXML
    private ListView<AbstractFilterCommand> filtersListView;

    @FXML
    private ListView<AbstractFilterCommand> pipelineListView;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;

    @FXML
    private Menu menuedit;
    private final Pipeline pipeline = new Pipeline();
    private final Stage currentStage;
    private final AtomicReference<Image> currentImage;
    private Image lastSavedImage;
    private Command<File> fileOpenChooser;
    private AbstractSaveFileChooserCommand saveFileChooserCommand;

    private final HashMap<String, AbstractFilterCommand> filterCommands;

    private FilterListController filterListController;
    private PipelineController pipelineController;

    public MainViewController(Stage stage) {
        this.currentStage = stage;
        this.currentImage = new AtomicReference<>();
        this.currentStage.setOnCloseRequest((e) -> handleWindowClosing());
        this.currentStage.setTitle("ed2d");
        this.filterCommands = new HashMap<>();
        loadFilterCommands();
        PipelineManager.getInstance().setMainViewController(this);
    }

    private void loadFilterCommands() {
        filterCommands.put("Left Rotate",RotateLeftCommand.getInstance());
        filterCommands.put("Scale", new ScaleCommand(this));
        filterCommands.put("Right Rotate",RotateRightCommand.getInstance());
        filterCommands.put("Vertical Flip",VerticalFlipCommand.getInstance());
        filterCommands.put("Orizontal Flip",OrizontalFlipCommand.getInstance());
        filterCommands.put("Blur",BlurCommand.getInstance());
        filterCommands.put("Sharpen",SharpenCommand.getInstance());
        filterCommands.put("Gray Scale",GrayScaleCommand.getInstance());
        filterCommands.put("Black & White",BlackAndWhiteCommand.getInstance());
        filterCommands.put("Ridge Detection",RidgeDetectionCommand.getInstance());
    }

    @FXML
    private void close(ActionEvent ignored) {
        handleWindowClosing();
    }

    @FXML
    private void open(ActionEvent ignored) {
        checkIfSavingIsNeeded();
        updateBottomBar("Loading image...", true);
        fileOpenChooser.execute().whenComplete(this::handleFileOpenChooser);
    }

    @FXML
    private void save(ActionEvent ignored) {
        beginSavingProcess();
    }

    @FXML
    private void about(ActionEvent ignored) {
        new AlertBuilder(Alert.AlertType.INFORMATION).withTitle("Info").withHeader("About ed2d").withContent("SUPSI 2022\nCicco Adriano, Crugnola Fabio, Di Folco Lorenzo").show();
    }

    @FXML
    private void undo(ActionEvent ignored) {
        computeUndo();
    }

    @FXML
    private void redo(ActionEvent ignored) {
        computeRedo();
    }

    private void computeUndo() {
        PipelineManager.getInstance().undo();
    }

    private void computeRedo() {
        PipelineManager.getInstance().redo();
    }



    public Image getLastSavedImage() {
        return lastSavedImage;
    }

    private void beginSavingProcess() {
        if (currentImage.get() != null) {
            updateBottomBar("Saving started", true);
            try {
                var sdc = new SaveDialogController(this);
                sdc.setSaveFileChooserCommand(saveFileChooserCommand);
                sdc.loadAndShowView().whenComplete(this::handleNewSaveDialogResult);
            } catch (IOException ex) {
                new AlertBuilder(Alert.AlertType.ERROR).withTitle("Error").withHeader("Can't open save window").withContent("An error has occurred while opening the saving window " + ex.getMessage()).show();
                updateBottomBar("", false);
            }
        } else {
            new AlertBuilder(Alert.AlertType.INFORMATION).withTitle("Info").withHeader("You should open a file to save something").show();
            updateBottomBar("", false);
        }
    }

    private void handleFileOpenChooser(File file, Throwable throwable) {
        if (file != null && throwable == null) {
            disableFilterViews(true);
            disactivateCtrl();
            menuedit.setVisible(false);

            new LoadCommand(file).execute().whenComplete(this::handleImageLoading);
        } else {
            updateBottomBar("", false);
            if (throwable != null)
                new AlertBuilder(Alert.AlertType.ERROR).withTitle("Error").withHeader("Can't open file").withContent(throwable.getMessage()).show();

        }
    }

    private void disactivateCtrl() {
        currentStage.getScene().getAccelerators().clear();
    }

    private void handleImageLoading(LoadCommand.LoadCommandResult result, Throwable throwable) {
        Platform.runLater(() -> {
            if (result != null) {
                String path = result.getPath();
                setImage(result.getImg());
                updateLastCommandImage();


                disableFilterViews(false);
                menuedit.setVisible(true);
                activateCtrlZ();
                activateCtrlY();

                pipelineListView.getItems().clear();
                pipeline.clearPipeline();
                PipelineManager.getInstance().clear();
                PipelineManager.getInstance().notifyLoadedImage();

                updateBottomBar("Successfully loaded: " + path, false);
                currentStage.setTitle(path + " - ed2d");



            } else {
                updateBottomBar("", false);
                if (throwable != null)
                    new AlertBuilder(Alert.AlertType.ERROR).withTitle("Error").withHeader("Can't open file").withContent(throwable.getMessage()).show();
            }
        });
    }

    private void handleNewSaveDialogResult(SaveDialogController.SaveDialogResult saveDialogResult, Throwable throwable) {
        if (saveDialogResult != null && throwable == null) {
            new SaveCommand(this.currentImage.get(), saveDialogResult.getPath(), saveDialogResult.getType()).execute().whenComplete(this::handleImageSaving);
        } else {
            Platform.runLater(() -> {
                updateBottomBar("", false);
                if (throwable != null)
                    new AlertBuilder(Alert.AlertType.ERROR).withTitle("Error").withHeader("Can't pick image save location").withContent(throwable.getMessage()).show();
            });
        }
    }

    private void handleImageSaving(String path, Throwable throwable) {
        Platform.runLater(() -> {
            if (path != null && throwable == null) {
                updateLastCommandImage();
                updateBottomBar("Saved new image to " + path, false);
            } else {
                updateBottomBar("", false);
                if (throwable != null)
                    new AlertBuilder(Alert.AlertType.ERROR).withTitle("Error").withHeader("Can't save file").withContent(throwable.getMessage()).show();
            }
        });
    }

    private void handleWindowClosing() {
        checkIfSavingIsNeeded();
        Platform.exit();
    }

    private void checkIfSavingIsNeeded() {
        if (currentImage.get() != null && (lastSavedImage == null || !currentImage.get().equals(lastSavedImage))) {
            var pressedButton = new AlertBuilder(Alert.AlertType.WARNING)
                    .withTitle("Warning")
                    .withHeader("Last edits weren't saved")
                    .withContent("Do you want to save your image?")
                    .withButtonType(ButtonType.NO)
                    .withButtonType(ButtonType.YES)
                    .showAndWait().orElse(ButtonType.NO);

            if (pressedButton.equals(ButtonType.OK))
                beginSavingProcess();
        }
    }

    private void disableFilterViews(boolean disable) {
        this.pipelineListView.setDisable(disable);
        this.filtersListView.setDisable(disable);
    }

    @Override
    public Window getWindow() {
        return currentStage.getScene().getWindow();
    }

    public void setImage(Image img) {
        this.currentImage.set(img);
        var bpane = new BorderPane();
        var iv = new ImageView(WritableImageConverter.convertImage(this.currentImage.get()));
        iv.setId("mainImageView");
        bpane.setCenter(iv);
        scrollpane.setContent(bpane);
    }

    private void updateLastCommandImage() {
        try {
            lastSavedImage = new Image(this.currentImage.get());
        } catch (InvalidImageException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateBottomBar(String labelMessage, boolean progress) {
        this.bottomBarLabel.setText(labelMessage);
        this.bottomBarProgressBar.setVisible(progress);
    }

    public void setSaveFileChooser(AbstractSaveFileChooserCommand saveFileChooserCommand) {
        this.saveFileChooserCommand = saveFileChooserCommand;
    }

    public void setOpenFileChooser(Command<File> openFileChooserCommand) {
        this.fileOpenChooser = openFileChooserCommand;
    }

    public void onLoaded() {
        filterListController = new FilterListController(this, filtersListView, filterCommands.values());
        filterListController.load();
        pipelineController = new PipelineController(this, pipelineListView, filterCommands);
        pipelineController.load();
    }

    public void activateCtrlZ() {
        KeyCombination kc = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        currentStage.getScene().getAccelerators().put(kc, this::computeUndo);
    }

    public void activateCtrlY() {
        KeyCombination kc = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        currentStage.getScene().getAccelerators().put(kc, this::computeRedo);
    }

    public void disableUndoItem(boolean disable) {
        undo.setDisable(disable);
    }

    public void disableRedoItem(boolean disable) {
        redo.setDisable(disable);
    }

    public boolean UndoIsDisabled()
    {
        return undo.isDisable();
    }

    public boolean RedoIsDisabled()
    {
        return redo.isDisable();
    }



}