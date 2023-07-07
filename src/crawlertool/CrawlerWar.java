package crawlertool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import entity.Historical;
import entity.War;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import othertools.StringHandler;

public class CrawlerWar implements Crawlable{

    private ObservableList<Historical> dataList;
    private List<War> wars;
    private Document doc = new Document("UTF-8");

    public CrawlerWar(ObservableList<Historical> dataList) {
        this.dataList = dataList;
        wars = new ArrayList<War>();
    }
    
    public List<War> getWars() {
        return wars;
    }

    @Override
    public void crawlData() {
        
        if(wars == null)
            wars = new ArrayList<War>();
        else 
            wars.clear();

        String name;
        String place;
        String result;
        String time;
        String keyword;
        String source = "https://nguoikesu.com/tu-lieu/quan-su/tran-dien-bien-phu-tren-khong-nam-1972";
        while(!source.equals("https://nguoikesu.com")) {
            try {
                keyword = "";
                time = "?";
                place = "?";
                result = "?";
                doc = Jsoup.connect(source).get();
                name = doc.select("#content div.com-content-article.item-page h1").first().text();
                for(Element tr: doc.select("#content > div.com-content-article.item-page > div.com-content-article__body div.infobox tr")) {
                    if(tr.text().equals("Chỉ huy"))
                        keyword = tr.nextElementSibling().text();
                    else if(tr.select("td:nth-child(1)").text().equals("Thời gian"))
                        time = tr.select("td:nth-child(2)").text();
                    else if(tr.select("td:nth-child(1)").text().equals("Địa điểm"))
                        place = tr.select("td:nth-child(2)").text();
                    else if(tr.select("td:nth-child(1)").text().equals("Kết quả"))
                        result = tr.select("td:nth-child(2)").text();
                }
                
                War war = new War(name, source, time, place, result);
                war.setDetail(doc.select("#content > div.com-content-article.item-page > div.com-content-article__body p").first().text());
                war.setRelativeKeyWord(StringHandler.normalize(keyword + name + time + place + result));
                wars.add(war);
                
                Platform.runLater(() -> {
                    dataList.add(war);
                });
                
                source = "https://nguoikesu.com" + doc.select("#content > div.com-content-article.item-page a.btn.btn-sm.btn-secondary.next").attr("href");
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error on connecting to " + source);
            }
        }
    }
    
}
