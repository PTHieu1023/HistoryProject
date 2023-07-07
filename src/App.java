import gui.controller.MainScreenController;
import gui.fxml.ScreenScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;



public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainScreenController controller = new MainScreenController();
        primaryStage.setTitle("Lịch sử Việt Nam");

         primaryStage.setOnCloseRequest(event -> {
            event.consume();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("Do you want to save data before exit?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    controller.getDataHandler().saveData();
                    primaryStage.close();
                    Platform.exit();
                } else if (buttonType == buttonTypeNo) {
                    primaryStage.close();
                    Platform.exit();
                }
            });
    });

        ScreenScene mainScene = new ScreenScene();
        mainScene.getMainSceneLoader().setController(controller);
        Parent root = mainScene.getMainSceneLoader().load();
        
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
