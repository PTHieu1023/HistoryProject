package datahandle;

import java.util.ArrayList;
import java.util.List;

import crawlertool.CrawlerDynasty;
import crawlertool.CrawlerFestival;
import crawlertool.CrawlerFigure;
import crawlertool.CrawlerLocation;
import crawlertool.CrawlerWar;
import entity.Dynasty;
import entity.Festival;
import entity.Figure;
import entity.Historical;
import entity.Location;
import entity.War;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Crawler {
    private ObservableList<Historical> dataList = FXCollections.observableArrayList();
    private List<Dynasty> dynasties = new ArrayList<Dynasty>();
    private List<Figure> figures  = new ArrayList<Figure>();
    private List<Location> locations = new ArrayList<Location>();
    private List<Festival> festivals = new ArrayList<Festival>();
    private List<War> wars = new ArrayList<War>();
    private CrawlerDynasty crawlerDynasty;
    private CrawlerFestival crawlerFestival;
    private CrawlerFigure crawlerFigure;
    private CrawlerLocation crawlerLocation;
    private CrawlerWar crawlerWar;

    public Crawler() {
        crawlerDynasty = new CrawlerDynasty(dataList, dynasties);
        crawlerFestival = new CrawlerFestival(dataList, festivals);
        crawlerFigure = new CrawlerFigure(dataList, figures);
        crawlerWar = new CrawlerWar(dataList, wars);
        crawlerLocation = new CrawlerLocation(dataList, locations);
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

    public List<Location> getLocations() {
        return locations;
    }

    public List<Festival> getFestivals() {
        return festivals;
    }

    public List<War> getWars() {
        return wars;
    }
 
    private void crawlDynasty() {
        crawlerDynasty.crawlData();
    }
 
    private void crawlerLocation() {
        crawlerLocation.crawlData();
    }

    private void crawlerFigure() {
        crawlerFigure.crawlData();
    }

    private void crawlerFestival() {
        crawlerFestival.crawlData();
    }

    private void crawlerWar() {
        crawlerWar.crawlData();
    }

    public void crawlDataFromWeb() {
        dataList.clear();        
        //crawlerWar();
        //crawlerFestival();
        //crawlDynasty();
        //crawlerLocation();
        crawlerFigure();
        int size = dataList.size();
        for(int i = 0; i<size-1; i++)
            for(int j = i+1; j < size; j++)
                dataList.get(i).setRelation(dataList.get(j));
    }

    public void crawlDataFromWeb(boolean getDynasties, boolean getFigures, boolean getWars, boolean getFestivals, boolean getLocations) {
        dataList.clear();
        if(getWars)
            crawlerWar();
        if(getFestivals)
            crawlerFestival();
        if(getDynasties)
            crawlDynasty();
        if(getLocations)
            crawlerLocation();
        if(getFigures)
            crawlerFigure();

        //Link data
        int size = dataList.size();
        for(int i = 0; i<size-1; i++)
            for(int j = i+1; j < size; j++)
                dataList.get(i).setRelation(dataList.get(j));
    }
}
