package crawlertool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import entity.Dynasty;
import entity.Historical;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import othertools.StringHandler;

public class CrawlerDynasty implements Crawlable{

    private Document doc = new Document("UTF-8");
    private List<Dynasty> dynasties;
    private ObservableList<Historical> dataList;

    public CrawlerDynasty(ObservableList<Historical> dataList) {
        this.dataList = dataList;
        dynasties = new ArrayList<Dynasty>();
    }

    public CrawlerDynasty(ObservableList<Historical> dataList, List<Dynasty> dynasties) {
        this.dataList = dataList;
        this.dynasties = dynasties;
    }

    public List<Dynasty> getDynasties() {
        return dynasties;
    }

    @Override
    public void crawlData() {
        if(dynasties == null)
            dynasties = new ArrayList<Dynasty>();
        else
            dynasties.clear();
        String url  = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in connect to url: \"https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#\"");
        }

        String kings; 
        for(Element tr: doc.select("#mw-content-text > div.mw-parser-output > div.navbox > table > tbody > tr")) {
            if(!tr.select("td").isEmpty()) {
                detailHandle(tr.select("th").first());
                kings = getRelativeLink(tr.select("td").first());
                Dynasty dynasty = new Dynasty(name, source, timeline);
                detail = detail.replaceAll("\\[.*?\\]", "");
                dynasty.setRelativeKeyWord(StringHandler.normalize(kings + name + timeline + detail));
                dynasty.setDetail(detail);
                dynasties.add(dynasty);
                Platform.runLater(() -> {
                    dataList.add(dynasty);
                });
            }
        }
    }

    private String name;
    private String source;
    private String detail;
    private String timeline;

    private void detailHandle(Element el){
        source = "https://vi.wikipedia.org/" + el.selectFirst("a").attr("href");
    
        try {
            Document detailDoc = new Document("UTF-8");
            StringBuilder detailBuild = new StringBuilder();
            detailDoc = Jsoup.connect(source).get();

            for(Element paragraph : detailDoc.select("#mw-content-text > div.mw-parser-output > p")) {
                if(!paragraph.text().isEmpty() && !paragraph.select("b").text().isEmpty()) {
                    detailBuild.append(paragraph.text());
                    break;
                }
            }
                
            detail = detailBuild.toString();
            name = detailDoc.select("#firstHeading > span").text();
            timeline = detailDoc.select("#mw-content-text > div.mw-parser-output > table.infobox > tbody > tr:nth-child(3) > td").text();
            if(timeline.isEmpty())
                timeline = el.text().substring(el.text().indexOf("(") + 1, el.text().indexOf(")"));
            

        } catch (IOException e){
            detail = "Data hasn't been update";
            name = el.text();
            timeline = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
            name = name.substring(0, name.indexOf(timeline)-2);
        }
    }

    private String getRelativeLink(Element el) {
        StringBuilder keyword = new StringBuilder();
        for(Element a : el.select("div > span > a")) {
            keyword.append(a.text() + ", ");
        }
        return keyword.toString();
    }

}
