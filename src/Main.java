import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This Application manages the inventory of parts and products
 * This class contains the main method
 * @author manaf
 * @RUNTIMEERRORS most runtime errors happened because I forgot to throw IOExceptions actionEvent methods
 * @FUTUREENHANCEMENTS I would in the future connect the app to a database and save inventory in it.
 */
public class Main extends Application {

    /**
     * this method starts the main menu form.
     * @param primaryStage sets the primary stage
     * @throws Exception if code has errors
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //loads the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        // sets the title of the primary stage
        primaryStage.setTitle("Inventory Management System");
        // adds the scene to primary stage
        primaryStage.setScene(new Scene(root));
        //shows the stage
        primaryStage.show();
    }

    /**
     * Main method  launches the App
     * JavaDOC PATH: MASoftware1Project/resources/JavaDoc
     * @param args for the JVM to know where to start the application.
     */
    public static void main(String[] args) {
        //launch the app
        launch(args);
    }
}
