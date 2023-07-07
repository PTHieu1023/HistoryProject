package crawlertool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import entity.Figure;
import entity.Historical;
import othertools.StringHandler;

public class CrawlerFigure implements Crawlable{

    private Document doc = new Document("UTF-8");
    private ObservableList<Historical> dataList;
    private List<Figure> figures;

    public CrawlerFigure(ObservableList<Historical> dataList) {
        this.dataList = dataList;
        figures = new ArrayList<Figure>();
    }

    public List<Figure> getFigures() {
        return figures;
    }

    @Override
    public void crawlData() {
        if(figures == null)
            figures = new ArrayList<Figure>();
        else
            figures.clear();
        crawlFigure();
        crawlKings();
    }

    private void crawlFigure() {
        String name;
        String source = "https://nguoikesu.com/nhan-vat/an-duong-vuong";
        String detail;
        String birth;
        String death;
        do {
            try{
                doc = Jsoup.connect(source).get();
                name = doc.select("#content > div.com-content-article.item-page.page-list-items .page-header > h2").first().text();
                if(!doc.select("div.infobox").isEmpty() && isFigure(doc) && !name.contains("nhà")){
                    birth = "?";
                    death = "?";
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
                    addToList(figure);
                }
            } catch (IOException e) {
                continue;
            } catch (NullPointerException e) {
                continue;
            }

            source = "https://nguoikesu.com" + doc.select("a.btn.btn-sm.btn-secondary.next").attr("href");
        }while(!source.equals("https://nguoikesu.com"));
    }

  	private void crawlKings() {
        String name;
        String source;
        String detail;
        String birth;
        String death;
        String relativeKeyWord;
        try {
            doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#").get();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fail to connect to: https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#");
        }
        for(Element tr: doc.select("#mw-content-text > div.mw-parser-output > div.navbox > table > tbody > tr")) {
            if(!tr.select("td").isEmpty()) {
                Element td = tr.select("td").first();
                
                for(Element a: td.select("a")) {
                    name = a.text();
                    source = "https://vi.wikipedia.org/" + a.attr("href");
                    birth = "?";
                    death = "?";
                    if(!source.contains("/wiki/"))
                        continue;
                    Document detaiDoc = new Document("UTF-8");
                    try {
                        detaiDoc = Jsoup.connect(source).get();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error on connecting to: " + source);
                    }
                    detail = detaiDoc.select("#mw-content-text > div.mw-parser-output p").first().text();
                    relativeKeyWord = StringHandler.normalize(name + detail);
                    
                    for(Element info : detaiDoc.select("#mw-content-text > div.mw-parser-output > table.infobox tr")) {
                        if(info.select("th").text().equals("Sinh"))
                            birth = info.select("td").text().replaceAll("\\[.*?\\]", "");
                        else if(info.select("th").text().equals("Mất")) 
                            death = info.select("td").text().replaceAll("\\[.*?\\]", "");
                    }

                    Figure figure = new Figure(name, source, birth, death);
                    figure.setDetail(detail);
                    figure.setRelativeKeyWord(relativeKeyWord + StringHandler.normalize(detaiDoc.select("#mw-content-text > div.mw-parser-output > table.infobox").text()));
                    addToList(figure);
                }                   
            }
        }
	}

    private void addToList(Figure f) {
        for(Figure figure : figures) {
            if(figure.equals(f)) {
                mergeData(figure, f);
                return;
            }
        }
        figures.add(f);
        Platform.runLater(() -> {
            dataList.addAll(f);
        });
    }

    //Hợp nhất 2 thực thể trùng lặp
    private  static void mergeData(Figure a, Figure b) {
        if(a.equals(b)) {
            if(a.getBirth().equals("?")) {
                a.setBirth(b.getBirth());
                a.setRelativeKeyWord(a.getRelativeKeyWord() + StringHandler.normalize(a.getBirth()));
            }
            if(a.getDeath().equals("?")) {
                a.setDeath(b.getDeath());
                a.setRelativeKeyWord(a.getRelativeKeyWord() + StringHandler.normalize(a.getDeath()));
            }
            if(b.getDetail() != null && !a.getDetail().equals(b.getDetail())) {
                a.setDetail(a.getDetail().length() > b.getDetail().length() ? a.getDetail() : b.getDetail());
                a.setRelativeKeyWord(a.getRelativeKeyWord() + b.getRelativeKeyWord());
            }
        }
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

}