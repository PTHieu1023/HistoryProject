package datahandle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objectclass.Dynasty;
import objectclass.Festival;
import objectclass.Figure;
import objectclass.Historical;
import objectclass.Location;
import objectclass.War;

public class Importer {
    final String dynastyPath = "data\\Dynasty.json";
    final String figuresPath = "data\\Figure.json";
    final String warPath = "data\\War.json";
    final String festivalPath = "data\\Festival.json";
    final String locationPath = "data\\Location.json";

    private final Gson gson = new Gson();

    private ObservableList<Historical> dataList = FXCollections.observableArrayList();
    private List<Dynasty> dynasties = new ArrayList<Dynasty>();
    private List<Figure> figures = new ArrayList<Figure>();
    private List<War> wars = new ArrayList<War>();
    private List<Festival> festivals = new ArrayList<Festival>();
    private List<Location> locations = new ArrayList<Location>();

    public Importer() {
        importData();
    }

    public ObservableList<Historical> getDataList() {
        return dataList;
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

    private void importDynasties() {
        dynasties.clear();
        String jsonData = readJsonFile(dynastyPath);
        Dynasty[] objects = gson.fromJson(jsonData, Dynasty[].class);
        for(Dynasty object: objects) {
            dynasties.add(object);
            dataList.add(object);
        }
    }

    private void importFigures() {
        figures.clear();
        String jsonData = readJsonFile(figuresPath);
        Figure[] objects = gson.fromJson(jsonData, Figure[].class);
        for(Figure object: objects) {
            figures.add(object);
            dataList.add(object);
        }
    }

    private void importWars() {
        wars.clear();
        String jsonData = readJsonFile(warPath);
        War[] objects = gson.fromJson(jsonData, War[].class);
        for(War object: objects) {
            wars.add(object);
            dataList.add(object);
        }
    }

    private void importFestivals() {
        festivals.clear();
        String jsonData = readJsonFile(festivalPath);
        Festival[] objects = gson.fromJson(jsonData, Festival[].class);
        for(Festival object: objects) {
            festivals.add(object);
            dataList.add(object);
        }
    }

    private void importLocations() {
        locations.clear();
        String jsonData = readJsonFile(locationPath);
        Location[] objects = gson.fromJson(jsonData, Location[].class);
        for(Location object: objects) {
            locations.add(object);
            dataList.add(object);
        }
    }

    public void importData() {
        importDynasties();
        importWars();
        importFigures();
        importLocations();
        importFestivals();
        //Link data
        int size = dataList.size();
        for(int i = 0; i<size-1; i++)
            for(int j = i+1; j < size; j++)
                dataList.get(i).setRelation(dataList.get(j));
    }

    private String readJsonFile(String path) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
