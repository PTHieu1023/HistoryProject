package crawlertool;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import objectclass.Figure;
import objectclass.Historical;
import othertools.StringHandler;

public class CrawlerFigure {
    private Document doc = new Document("UTF-8");
    private ObservableList<Historical> dataList;
    private List<Figure> figures;

    public List<Figure> getFigures() {
        return figures;
    }

    public CrawlerFigure(ObservableList<Historical> dataList, List<Figure> figures) {
        this.dataList = dataList;
        this.figures = figures;
    }

    private boolean isFigure(Document charDoc) {
        try{
            String type = doc.select("div.infobox tr:nth-child(2) > td").text().toLowerCase();
            String[] keyWord = {"xã", "huyện", "tỉnh", "thành phố", "địa danh", "đảo"}; 
            for(String key: keyWord)
                if(type.contains(key))
                    return false;
            return true;
        } catch(NullPointerException e) {
            return false;
        }
    }

    public void crawlData() {
        String source = "https://nguoikesu.com/nhan-vat/an-duong-vuong";
        String name;
        String detail;
        String birth;
        String death;
        int i = 0;
        do {
            try{
                doc = Jsoup.connect(source).get();
                name = doc.select("#content > div.com-content-article.item-page.page-list-items .page-header > h2").first().text();
                if(!doc.select("div.infobox").isEmpty() && isFigure(doc)){
                    System.out.println((++i) + " : " + source);
                    birth = "";
                    death = "";
                    for(Element tr: doc.select("div.infobox > table > tbody tr")) {
                        if(tr.select("th").text().toLowerCase().contains("sinh"))
                            birth = tr.select("td").text();
                        if(tr.select("th").text().toLowerCase().contains("mất"))
                            death = tr.select("td").text();
                    }

                    detail = doc.select("#content .com-content-article p").first().text();
                    detail = detail.replaceAll("\\[.*?\\]", "");
                    Figure figure = new Figure(name, source, birth, death);
                    figure.setDetail(detail);
                    figure.setRelativeKeyWord(StringHandler.normalize(name+birth+death+detail));
                    if(!figures.contains(figure)) {
                        figures.add(figure);
                        Platform.runLater(() -> {
                            dataList.add(figure);
                        });
                    }
                }
            } catch (IOException e) {
                continue;
            } catch (NullPointerException e) {
                continue;
            }

            source = "https://nguoikesu.com" + doc.select("a.btn.btn-sm.btn-secondary.next").attr("href");
        }while(!source.equals("https://nguoikesu.com"));
    }
}
