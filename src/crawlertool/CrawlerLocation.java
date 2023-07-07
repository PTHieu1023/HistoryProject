package crawlertool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import entity.Historical;
import entity.Location;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import othertools.StringHandler;

public class CrawlerLocation implements Crawlable{

    private ObservableList<Historical> dataList;
    private List<Location> locations;

    public CrawlerLocation(ObservableList<Historical> dataList) {
        this.dataList = dataList;
        locations = new ArrayList<Location>();
    }

    public CrawlerLocation(ObservableList<Historical> dataList, List<Location> locations) {
        this.dataList = dataList;
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public void crawlData() {

        if(locations == null) 
            locations = new ArrayList<Location>();
        else
            locations.clear();

        String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
        String name;
        String position;
        String province;
        String category;
        String recYear;
        Element table;
        StringBuilder source;
        Document doc = new Document("UTF-8");
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error on connecting to: " + url);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error on connecting to: " + url);
        }
            
        for(Element h3 : doc.selectFirst("#mw-content-text .mw-parser-output").select("h3")){
            province = h3.select(".mw-headline").text();
            if(province.equals("Bắc Ninh"))
                table = h3.nextElementSibling().nextElementSibling();
            else
                table = h3.nextElementSibling();
            table = table.selectFirst(".wikitable > tbody");
            if(table != null)
            for(Element tr : table.select("tr")) {
                name = tr.select("td:nth-child(1)").text();
                if(!name.equals("")) {
                    source = new StringBuilder("https://vi.wikipedia.org");
                    source = source.append(tr.select("td:nth-child(1) a").attr("href"));
                    position = tr.select("td:nth-child(2)").text() + ", " + province;
                    category = tr.select("td:nth-child(3)").text();
                    recYear = tr.select("td:nth-child(4)").text();
                    
                    StringBuilder detail = new StringBuilder("Di tích cấp quốc gia Việt Nam. ");
                    if(!recYear.equals(""))
                        detail.append("Được công nhận " + recYear + ". ");
                    if(source.toString().contains("/wiki/"))
                        detail.append(getDetail(source.toString()));
                    else    
                        source.setLength(0);
                    Location location = new Location(name, source.toString(), position, category);
                    location.setDetail(detail.toString().replaceAll("\\[.*?\\]", ""));
                    location.setRelativeKeyWord(StringHandler.normalize(name + position + category + detail));
                    locations.add(location);

                    Platform.runLater(() -> {
                            dataList.add(location);
                    });
                }
            }
        } 
    }

    private String getDetail(String url){
        String detail;

        try{
            Document detailDoc = new Document("UTF-8");
            detailDoc = Jsoup.connect(url).get();
            detail = detailDoc.selectFirst("#mw-content-text > div.mw-parser-output > p").text();
        } catch (IOException e) {
            detail = "";
        } catch (NullPointerException e) {
            detail = "";
        }
        return detail.replaceAll("\\[.*?\\]", "");
    }

}