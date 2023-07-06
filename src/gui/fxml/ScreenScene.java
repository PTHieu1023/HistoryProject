package gui.fxml;

import javafx.fxml.FXMLLoader;

public class ScreenScene{
    private FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));

    public FXMLLoader getMainSceneLoader() {
        return mainSceneLoader;
    }     
}

