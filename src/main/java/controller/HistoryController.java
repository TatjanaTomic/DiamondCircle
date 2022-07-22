package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import model.history.GameHistory;
import model.util.HistoryUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private static final String ERROR_MESSAGE = "Pogresan format fajla.";

    @FXML
    private TextArea fileContentTextArea;
    @FXML
    private ListView<File> filesList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File[] data = HistoryUtil.getHistoryFiles();

        ObservableList<File> files = FXCollections.observableArrayList();
        files.addAll(data);

        filesList.setItems(files);
        filesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        filesList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.getName());

                    setOnMousePressed(e -> showSelectedFile());
                }
            }
        });

        fileContentTextArea.setEditable(false);
        fileContentTextArea.setWrapText(true);
    }

    private void showSelectedFile() {
        File selectedFile = filesList.getSelectionModel().getSelectedItems().get(0);

        GameHistory gameHistory = HistoryUtil.deserialize(selectedFile.getName());

        if(gameHistory == null) {
            fileContentTextArea.setText(ERROR_MESSAGE);
        }
        else {
            fileContentTextArea.setText(gameHistory.toString());
        }
    }
}
