
package application.shubhamgas.Controller;

import java.time.LocalDate;

import application.shubhamgas.Model.StockDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class StockDetailsByDateController extends BaseController {

    @FXML
    ListView<String> listView = new ListView<>();

    @FXML
    Button editFile;

    @FXML
    Button finalizeStock;

    @FXML
    Button viewHistory;

    @FXML
    Button startStock;

    @FXML
    DatePicker datePicker;

    @FXML
    public void initialize() {
        disableAllButton();
    }

    public void uponDateSelect(ActionEvent event) {
        disableAllButton();
        LocalDate date = datePicker.getValue();
        StockDetails stockDetails = new StockDetails(date);
        setButtonState(stockDetails);
        updateListView(stockDetails);
    }

    public void getVersionHistory(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        setScene("ListVersionHistory.fxml", date);
    }

    public void finalizeStock(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        StockDetails stockDetails = new StockDetails(date);
        stockDetails.finishStock();
        updateListView(stockDetails);
        setButtonState(stockDetails);
    }

    public void resumeFile(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        StockDetails stockDetails = new StockDetails(date);
        stockDetails.resumeStock();
        setButtonState(stockDetails);
        updateListView(stockDetails);
        openFileInApplication(stockDetails.getPartFile().getAbsolutePath(), false);
    }

    public void startStock(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        StockDetails stockDetails = new StockDetails(date);
        stockDetails.startStock();
        setButtonState(stockDetails);
        updateListView(stockDetails);
        openFileInApplication(stockDetails.getPartFile().getAbsolutePath(), false);
    }

    private void disableAllButton() {
        disableButton(startStock);
        disableButton(viewHistory);
        disableButton(finalizeStock);
        disableButton(editFile);
    }

    private void disableButton(Button button) {
        button.setDisable(true);
    }

    private void enableButton(Button button) {
        button.setDisable(false);
    }

    private void setButtonState(StockDetails stockDetails) {
        disableAllButton();
        if (stockDetails.getPartFile() == null) {
            enableButton(startStock);
            return;
        }
        if (stockDetails.getFinalFile() != null) {
            enableButton(viewHistory);
            return;
        }
        enableButton(viewHistory);
        enableButton(finalizeStock);
        enableButton(editFile);
    }

    private void updateListView(StockDetails stockDetails) {
        listView.getItems().clear();
        if (stockDetails.getPartFile() != null) {
            listView.getItems().add(stockDetails.getPartFile().getName());
        }
        if (stockDetails.getFinalFile() != null) {
            listView.getItems().add(stockDetails.getFinalFile().getName());
        }
    }
}
