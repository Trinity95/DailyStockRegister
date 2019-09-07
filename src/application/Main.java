package application;
	
import application.shubhamgas.repository.SharedConstants;
import application.shubhamgas.repository.StockDetailRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
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
