package datahandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objectclass.Dynasty;
import objectclass.Festival;
import objectclass.Figure;
import objectclass.Historical;
import objectclass.Location;
import objectclass.War;

public class DataHandler {
    private ObservableList<Historical> dataList;
    private List<Dynasty> dynasties;
    private List<Figure> figures;
    private List<War> wars;
    private List<Festival> festivals;
    private List<Location> locations;
    private Crawler crawler;
    private Importer importer;

    public ObservableList<Historical> getDataList() {
        return dataList;
    }
    public Crawler getCrawler() {
        return crawler;
    }
    public List<Dynasty> getDynasties() {
        return dynasties;
    }
    public List<Figure> getFigures() {
        return figures;
    }
    public List<War> getWars() {
        return wars;
    }
    public List<Festival> getFestivals() {
        return festivals;
    }
    public List<Location> getLocations() {
        return locations;
    }
    public Importer getImporter() {
        return importer;
    }
    public DataHandler() {
        dataList = FXCollections.observableArrayList();
        crawler = new Crawler();
        importer = new Importer();
    }

    public void useCrawledData(){
        dataList = crawler.getDataList();
        dynasties = crawler.getDynasties();
        figures = crawler.getFigures();
        wars = crawler.getWars();
        festivals = crawler.getFestivals();
        locations = crawler.getLocations();
    }

    public void useImportedData() {
        dataList = importer.getDataList();
        dynasties = importer.getDynasties();
        figures = importer.getFigures();
        wars = importer.getWars();
        festivals = importer.getFestivals();
        locations = importer.getLocations();
    }

    private static PrintWriter createAppendFileWriter(String filePath) {
        try {
            File outputFile = new File(filePath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(outputFile, false);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
            return writer;
            }
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    public void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PrintWriter writer = createAppendFileWriter(importer.dynastyPath);
        writer.print(gson.toJson(dynasties)); 
        writer.close();
        writer = createAppendFileWriter(importer.figuresPath);
        writer.print(gson.toJson(figures)); 
        writer.close();
        writer = createAppendFileWriter(importer.warPath);
        writer.print(gson.toJson(wars));
        writer.close(); 
        writer = createAppendFileWriter(importer.locationPath);
        writer.print(gson.toJson(locations)); 
        writer.close();
        writer = createAppendFileWriter(importer.festivalPath);
        writer.print(gson.toJson(festivals)); 
        writer.close();
    }
}
