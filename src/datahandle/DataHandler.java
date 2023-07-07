package datahandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Dynasty;
import entity.Festival;
import entity.Figure;
import entity.Historical;
import entity.Location;
import entity.War;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        dynasties = crawler.getCrawlerDynasty().getDynasties();
        figures = crawler.getCrawlerFigure().getFigures();
        wars = crawler.getCrawlerWar().getWars();
        festivals = crawler.getCrawlerFestival().getFestivals();
        locations = crawler.getCrawlerLocation().getLocations();
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
        List<Dynasty> cloneDynasties = new ArrayList<Dynasty>();
        for(Dynasty o: dynasties)
            cloneDynasties.add(o.clone());
        writer.print(gson.toJson(cloneDynasties)); 
        writer.close();

        writer = createAppendFileWriter(importer.figuresPath);
        List<Figure> cloneFigures = new ArrayList<Figure>();
        for(Figure o: figures)
            cloneFigures.add(o.clone());
        writer.print(gson.toJson(cloneFigures)); 
        writer.close();

        writer = createAppendFileWriter(importer.warPath);
        List<War> cloneWars = new ArrayList<War>();
        for(War o: wars)
            cloneWars.add(o.clone());
        writer.print(gson.toJson(cloneWars));
        writer.close(); 

        writer = createAppendFileWriter(importer.locationPath);
        List<Location> cloneLocations = new ArrayList<Location>();
        for(Location o: locations)
            cloneLocations.add(o.clone());
        writer.print(gson.toJson(cloneLocations)); 
        writer.close();

        writer = createAppendFileWriter(importer.festivalPath);
        List<Festival> cloneFestivals = new ArrayList<Festival>();
        for(Festival o: festivals)
            cloneFestivals.add(o.clone());
        writer.print(gson.toJson(cloneFestivals)); 
        writer.close();
    }
}
