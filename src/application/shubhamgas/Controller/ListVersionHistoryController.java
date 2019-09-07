package application.shubhamgas.Controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import application.shubhamgas.Model.StockDetails;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListVersionHistoryController extends BaseController{

    @FXML
    Button viewFile;

    @FXML
    TableView<File> tableView;
    
    @FXML
    TableColumn<File, String> fileName;

    @FXML
    TableColumn<File, Date> dateModified;

    @FXML
    TableColumn<File, Long> size;

    @FXML
    Button back;

    public void goBack(ActionEvent event) {
        setScene("StockDetailsByDate.fxml");
    }
    
    public void viewFile(ActionEvent event) {
        File file = tableView.getSelectionModel().getSelectedItem();
        openFileInApplication(file.getName(), true);
    }
    
    public void init(Object object) {
        LocalDate date = (LocalDate) object;
        fileName.setCellValueFactory(new PropertyValueFactory<File, String>("getName()"));        
        StockDetails stockDetails = new StockDetails(date);
        tableView.setItems(FXCollections.observableArrayList(stockDetails.getVersionHistory()));

    };


}

