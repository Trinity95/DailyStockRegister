package application;
	
import application.shubhamgas.repository.SharedConstants;
import application.shubhamgas.repository.StockDetailRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        primaryStage.setTitle("Daily Stock Register");
            primaryStage.setResizable(false);
            getInstance().setStage(primaryStage);
		    Parent root = FXMLLoader.load(getClass().getResource("/application/StockDetailsByDate.fxml"));
		    Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getStage() {
	    return primaryStage;
	}
	
	private void setStage(Stage primaryStage) {
	    this.primaryStage = primaryStage;
	}
	
	private Stage primaryStage;
	
	private static Main instance;
	
	private StockDetailRepository stockDetailRepository;
	
	public synchronized static Main getInstance() {
	    if(instance == null) {
	        instance = new Main();
	    }
	    return instance;
	}
	
	public StockDetailRepository getRepo() {
	    if(stockDetailRepository==null) {
	        stockDetailRepository = new StockDetailRepository(SharedConstants.STOCK_REGISTER);
	    }
	    return stockDetailRepository;
	}

}
