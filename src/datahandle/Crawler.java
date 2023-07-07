package datahandle;

import crawlertool.CrawlerDynasty;
import crawlertool.CrawlerFestival;
import crawlertool.CrawlerFigure;
import crawlertool.CrawlerLocation;
import crawlertool.CrawlerWar;
import entity.Historical;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Crawler {

    private ObservableList<Historical> dataList = FXCollections.observableArrayList();
    private CrawlerDynasty crawlerDynasty;
    private CrawlerFestival crawlerFestival;
    private CrawlerFigure crawlerFigure;
    private CrawlerLocation crawlerLocation;
    private CrawlerWar crawlerWar;

    public Crawler() {
        crawlerDynasty = new CrawlerDynasty(dataList);
        crawlerFestival = new CrawlerFestival(dataList);
        crawlerFigure = new CrawlerFigure(dataList);
        crawlerWar = new CrawlerWar(dataList);
        crawlerLocation = new CrawlerLocation(dataList);
    }

    public ObservableList<Historical> getDataList() {
        return dataList;
    }

    public CrawlerDynasty getCrawlerDynasty() {
        return crawlerDynasty;
    }

    public CrawlerFestival getCrawlerFestival() {
        return crawlerFestival;
    }

    public CrawlerFigure getCrawlerFigure() {
        return crawlerFigure;
    }

    public CrawlerLocation getCrawlerLocation() {
        return crawlerLocation;
    }

    public CrawlerWar getCrawlerWar() {
        return crawlerWar;
    }

    public void crawlDataFromWeb() {
        dataList.clear();        
        crawlerWar();
        crawlerFestival();
        crawlDynasty();
        crawlerLocation();
        crawlerFigure();

        //Link data
        int size = dataList.size();
        for(int i = 0; i<size-1; i++)
            for(int j = i+1; j < size; j++)
                dataList.get(i).setRelation(dataList.get(j));
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

}