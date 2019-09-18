
package application.shubhamgas.Controller;

import java.io.File;

import application.Main;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {
    
    public void init(Object object) {        
    };

    private Stage getStage() {
        // Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        return Main.getInstance().getStage();
    }

    public void setScene(String controllerFileName) {
        setScene(controllerFileName, null);
    }
    public void setScene(String controllerFileName, Object object) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/" + controllerFileName));
            Parent root = loader.load();

            if (object != null) {
                ListVersionHistoryController controller = loader.getController();
                controller.init(object);
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            Stage primaryStage = getStage();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void openFileInApplication(String filePath, boolean readOnly) {
        File file = new File(filePath);
        if (readOnly) {
            file.setReadOnly();
        }
        HostServices hostServices = Main.getInstance().getHostServices();
        hostServices.showDocument(filePath);
    }
}
