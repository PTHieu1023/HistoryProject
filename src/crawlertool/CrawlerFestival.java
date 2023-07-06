package crawlertool;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import objectclass.Festival;
import objectclass.Historical;
import othertools.StringHandler;

public class CrawlerFestival {
    private List<Festival> festivals;
    private ObservableList<Historical> dataList;

    public List<Festival> getFestivals() {
        return festivals;
    }

    public CrawlerFestival(ObservableList<Historical> dataList, List<Festival> festivals) {
        this.dataList = dataList;
        this.festivals = festivals;
    }

    private Document doc = new Document("UTF-8");

    public void crawlData() {
        String name;
        String location;
        String occurTime;
        String buffInfo;
        StringBuilder detail;

        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
        
        try {
            doc = Jsoup.connect(url).get();
            for(Element tr : doc.select("#mw-content-text > div.mw-parser-output > table.prettytable.wikitable > tbody > tr")) {
                if(!tr.attr("bgcolor").equals("#CCCCCC")) {
                    occurTime = tr.select("td:nth-child(1)").text();
                    location = tr.select("td:nth-child(2)").text();
                    name = tr.select("td:nth-child(3)").text();
                    buffInfo = tr.select("td:nth-child(6)").text();

                    if(!buffInfo.isEmpty()) {
                        detail = new StringBuilder("Lễ hội truyền thống tổ chức ");
                        detail.append(buffInfo + ". ");
                    } else {
                        detail = new StringBuilder("Lễ hội truyền thống Việt Nam. ");
                    }
                    buffInfo = tr.select("td:nth-child(5)").text();
                    if(!buffInfo.isEmpty()) {
                        detail.append("Có liên quan đến " + buffInfo);
                    }

                    Festival festival = new Festival(name, url, location, occurTime);
                    festival.setRelativeKeyWord(StringHandler.normalize(name + url + location + occurTime + detail));
                    festival.setDetail(detail.toString());
                    festivals.add(festival);
                    Platform.runLater(() -> {
                        dataList.add(festival);
                    });
                }
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error on connecting to \"https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam\"");
        } 
    }
}
