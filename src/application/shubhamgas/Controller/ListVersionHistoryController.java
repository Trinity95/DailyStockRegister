
package application.shubhamgas.Controller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.shubhamgas.Model.FileDetails;
import application.shubhamgas.Model.StockDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListVersionHistoryController extends BaseController {

    @FXML
    Button viewFile;

    @FXML
    TableView<FileDetails> tableView;

    @FXML
    TableColumn<FileDetails, String> fileName;

    @FXML
    TableColumn<FileDetails, String> dateModified;

    @FXML
    TableColumn<FileDetails, Long> size;

    @FXML
    Button back;

    public void goBack(ActionEvent event) {
        setScene("StockDetailsByDate.fxml");
    }

    public void viewFile(ActionEvent event) {
        FileDetails file = tableView.getSelectionModel().getSelectedItem();
        openFileInApplication(file.getFileName(), true);
    }

    public void init(Object object) {
        fileName.setCellValueFactory(new PropertyValueFactory<FileDetails, String>("fileName"));
        size.setCellValueFactory(new PropertyValueFactory<FileDetails, Long>("size"));
        dateModified.setCellValueFactory(new PropertyValueFactory<FileDetails, String>("dateModified"));
        LocalDate date = (LocalDate) object;
        StockDetails stockDetails = new StockDetails(date);
        List<FileDetails> versionHistory = new ArrayList<>();

        for (File file : stockDetails.getVersionHistory()) {
            FileDetails fileDetails = new FileDetails(file);
            versionHistory.add(fileDetails);
        }

        ObservableList<FileDetails> data = FXCollections.observableArrayList(versionHistory);
        tableView.setItems(data);
    };

}
