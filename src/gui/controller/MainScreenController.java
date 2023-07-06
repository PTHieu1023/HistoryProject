package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import objectclass.*;
import othertools.StringHandler;
import datahandle.DataHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainScreenController implements Initializable{

    @FXML
    private Label lbName;

    @FXML
    private Label lbType;

    @FXML
    private ListView<Historical> listviewData;

    @FXML
    private ListView<Historical> listviewCrawledData;

    @FXML
    private MenuButton mbType;

    @FXML
    private StackPane root;

    @FXML
    private TextField tfSearchBar;

    @FXML
    private Text txDetail;

    @FXML
    private Button btnCrawlTrigger;

    @FXML
    private Label lbCrawlUpdate;

    @FXML 
    private ProgressIndicator animCrawling;

    @FXML
    private VBox crawlWindow;

    @FXML
    private VBox boxLink;

    @FXML 
    private Label lbRelation;

    @FXML
    void saveData(ActionEvent event) {
        dataHandler.saveData();
        JOptionPane.showMessageDialog(null, "Data saved");
    }

    @FXML 
    void viewDataFromFile(ActionEvent event) {
        dataHandler.useImportedData();
        setDataList();
    }

    @FXML
    void viewDataFromWeb(ActionEvent event) {
        dataHandler.useCrawledData();
        setDataList();
    } 

    @FXML
    void btnCrawlTriggerPressed(ActionEvent event) {
            btnCrawlTrigger.setDisable(true);
            lbCrawlUpdate.setText("Crawling...");
            animCrawling.setVisible(true);
            crawlingThread.start();
    }

    @FXML
    void hideCrawlWindow(ActionEvent event) {
        crawlWindow.setVisible(false);
    }

    @FXML
    void importDataFromFile(ActionEvent event) {
        dataHandler.getImporter().importData();
        dataHandler.useImportedData();
        setDataList();
    }

    @FXML
    void crawlDataFromWeb(ActionEvent event) {
        crawlWindow.setVisible(true);
    }

    @FXML
    void showAllData(ActionEvent event) {
        mbType.setText("Tất cả");
        setDataList();
    }

    @FXML
    void showCharacter(ActionEvent event) {
        mbType.setText("Nhân vật");
        setDataList();
    }

    @FXML
    void showDynasty(ActionEvent event) {
        mbType.setText("Thời kì");
        setDataList();
    }

    @FXML
    void showFestival(ActionEvent event) {
        mbType.setText("Lễ hội");
        setDataList();
    }

    @FXML
    void showLocation(ActionEvent event) {
        mbType.setText("Di tích");
        setDataList();
    }

    @FXML
    void showWar(ActionEvent event) {
        mbType.setText("Trận chiến");
        setDataList();
    }

    private ObservableList<Historical> filterList;
    private DataHandler dataHandler;
    private Task<Void> crawlingTask;
    private Thread crawlingThread;
    private StringHandler stringHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stringHandler = new StringHandler();
        dataHandler = new DataHandler();

        listviewCrawledData.setItems(dataHandler.getCrawler().getDataList());

        listviewCrawledData.setCellFactory(param -> new ListCell<Historical>() {
            @Override
            protected void updateItem(Historical item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });

        lbCrawlUpdate.setText("Crawl Data");
        animCrawling.setVisible(false);

        mbType.setText("Tất cả");
        filterList = FXCollections.observableArrayList();
        dataHandler.useImportedData();
        setDataList();

        listviewData.setCellFactory(param -> new ListCell<Historical>() {
        
            @Override
            protected void updateItem(Historical item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        listviewData.setItems(filterList);

        listviewData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setContent(newValue);
        });

        crawlingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                dataHandler.getCrawler().crawlDataFromWeb();  
                Platform.runLater(()-> {
                    crawlWindow.setVisible(true);
                    animCrawling.setVisible(false);
                    lbCrawlUpdate.setText("Crawl Completed!");
                });
                return null; 
            }
        };
        crawlingThread = new Thread(crawlingTask);
        crawlingThread.setDaemon(true);

        tfSearchBar.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setDataList();
                searchData(newValue);
            }
            
        });
    }

    private void setContent(Historical obj) {
        if(obj != null) {
            lbName.setText(obj.getName());
            if(obj instanceof Dynasty) { 
                lbType.setText("Thời kì");
            } else if(obj instanceof Figure){
                lbType.setText("Nhân vật");
            } else if(obj instanceof War){
                lbType.setText("Trận chiến");
            } else if(obj instanceof Festival) {
                lbType.setText("Lễ hội");
            } else if(obj instanceof Location){
                lbType.setText("Di tích");
            } else{
                lbType.setText("Tư liệu Lịch sử");
            }
            boxLink.getChildren().clear();
            txDetail.setText(obj.toString());
            if(obj.getRelative() != null) {
                lbRelation.setVisible(true);
                for(Historical link : obj.getRelative()) {
                    boxLink.getChildren().add(createRelativeLink(link));
                }
            }
        }
        
    }

    private void setDataList() {
        try {
            if(mbType.getText().equals("Tất cả")) {
                filterList.clear();
                filterList.addAll(dataHandler.getDataList());
            } else if(mbType.getText().equals("Nhân vật")){
                filterList.clear();
                filterList.addAll(dataHandler.getFigures());
            } else if(mbType.getText().equals("Thời kì")){
                filterList.clear();
                filterList.addAll(dataHandler.getDynasties());
            } else if(mbType.getText().equals("Lễ hội")){
                filterList.clear();
                filterList.addAll(dataHandler.getFestivals());
            } else if(mbType.getText().equals("Di tích")){
                filterList.clear();
                filterList.addAll(dataHandler.getLocations());
            } else if(mbType.getText().equals("Trận chiến")){
                filterList.clear();
                filterList.addAll(dataHandler.getWars());
            }
        } catch (NullPointerException e) {
            setContent(listviewData.getSelectionModel().getSelectedItem());
        }
    }

    private void searchData(String searchKey) {
        searchKey = stringHandler.normalize(searchKey);
        int i = 0;
        Historical tmp;
        while(i<filterList.size()) {
            tmp = filterList.get(i);
            if(!stringHandler.normalize(tmp.getName()).contains(searchKey))
                filterList.remove(tmp);
            else
                i++;
        }
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    Button createRelativeLink(Historical data){
        Button btn = new Button();
        btn.setText("+ " + data.getName());
        btn.getStyleClass().add("btn-link");
        btn.setFont(Font.font("sanserif", FontWeight.MEDIUM, 20));
        btn.setOnAction(e ->{
            listviewData.scrollTo(data);
            setContent(data);
            listviewData.getSelectionModel().select(data);
        });
        return btn;
    }

}
