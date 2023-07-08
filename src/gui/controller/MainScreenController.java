package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import othertools.SortingCollection;
import othertools.StringHandler;
import datahandle.DataHandler;
import entity.*;
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

    //Khai báo và xử lý phần tử FXML
    @FXML
    private Label lbPageCount;

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
    private VBox boxMain;

    @FXML 
    private Label lbRelation;

    //Sắp xếp dữ liệu theo thứ tự tăng dần
    @FXML
    void sortData(ActionEvent event) {
        SortingCollection.sort(filterList);
    }

    //Lưu dữ liệu vào các file
    @FXML
    void saveData(ActionEvent event) {
        dataHandler.saveData();
        JOptionPane.showMessageDialog(null, "Data saved");
    }

    //Chuyển màn hình sang dữ liệu được lấy từ các file
    @FXML 
    void viewDataFromFile(ActionEvent event) {
        dataHandler.useImportedData();
        setDataList();
    }

    //Chuyển màn hình sang các dữ liệu lấy từ web
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

    //Lấy dữ liệu từ file
    @FXML
    void importDataFromFile(ActionEvent event) {
        boxMain.setVisible(false);
        Task<Void> newThreadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                dataHandler.getImporter().importData();
                dataHandler.useImportedData();
                Platform.runLater(() ->{
                    setDataList();
                    lbPageCount.setText("" +filterList.size());
                    boxMain.setVisible(true);
                });
                return null;
            } 
        };
        Thread thread = new Thread(newThreadTask);
        thread.setDaemon(true);
        thread.start();
    }

    //Thu thập dữ liệu từ web
    @FXML
    void crawlDataFromWeb(ActionEvent event) {
        crawlWindow.setVisible(true);
    }

    // Show dữ liệu theo thể loại
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

    //Xử lý chương trình
    private ObservableList<Historical> filterList;
    private DataHandler dataHandler;
    private Task<Void> crawlingTask;
    private Task<Void> importTask;
    private Thread crawlingThread;
    private Thread importThread;

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boxMain.setVisible(false);
        filterList = FXCollections.observableArrayList();
        importTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                dataHandler = new DataHandler();
                dataHandler.useImportedData();
                Platform.runLater(() ->{
                    listviewCrawledData.setItems(dataHandler.getCrawler().getDataList());
                    setDataList();
                    lbPageCount.setText("" +filterList.size());
                    boxMain.setVisible(true);
                });
                return null;
            }
        };

        importThread = new Thread(importTask);
        importThread.setDaemon(true);

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
            lbPageCount.setText((filterList.indexOf(newValue)+1) + "/" + filterList.size());
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
    
        importThread.start();

    }

    //Thay đổi nội dung của GUI mỗi khi chọn 1 item khác trong list view
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
            lbRelation.setVisible(false);
            txDetail.setText(obj.toString());
            for(Historical ultity : dataHandler.getDataList()) {
                if(!obj.equals(ultity))
                    obj.setRelation(ultity);
            }
            if(obj.getRelative() != null) {
                lbRelation.setVisible(true);
                for(Historical link : obj.getRelative()) {
                    boxLink.getChildren().add(createRelativeLink(link));
                }
            }
        }
        
    }

    //Thay đỏi dataset của listview khi có sự kiện được cập nhật
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
            searchData(tfSearchBar.getText());
            lbPageCount.setText("" + filterList.size());

        } catch (NullPointerException e) {
            setContent(listviewData.getSelectionModel().getSelectedItem());
        }
    }

    //Thay đổi dataset của listview khi phần tìm kiếm được cập nhật
    private void searchData(String searchKey) {
        searchKey = StringHandler.normalize(searchKey);
        int i = 0;
        Historical tmp;
        while(i<filterList.size()) {
            tmp = filterList.get(i);
            if(!StringHandler.normalize(tmp.getName()).contains(searchKey))
                filterList.remove(tmp);
            else
                i++;
        }
        lbPageCount.setText("" + filterList.size());
    }

    //Tạo ra các nút liên kết đến thực thể
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
