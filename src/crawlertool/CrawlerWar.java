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
    private static final String[] warRecognizeKey = {"chiến", "tranh", "cuộc", "trận", "nạn", "mâu", "thuẫn", "nổi", "dậy", "khởi", "nghĩa", "phong", "trào", "biến","bạo", "loạn", "cách mạng", "đảo chính", "xung đột"};

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
        crawlFromNKS();
        crawlFromWiki();
    }

    private void crawlFromNKS() {

        String name;
        String place;
        String result;
        String time;
        String keyword;
        String source = "https://nguoikesu.com/tu-lieu/quan-su/tran-dien-bien-phu-tren-khong-nam-1972";
        while(!source.equals("https://nguoikesu.com")) {
            
            keyword = "";
            time = "?";
            place = "?";
            result = "?";
            doc = connectURL(source);
            name = doc.select("#content div.com-content-article.item-page h1").first().text();
            if(!isWar(name))
                continue;
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
        }
    }
    
    private void crawlFromWiki() {

        String name;
        String source;
        String time;
        String place;
        String result;
        String detail;
        String keyword;
        Element detailDoc;

        doc = connectURL("https://vi.wikipedia.org/wiki/C%C3%A1c_cu%E1%BB%99c_chi%E1%BA%BFn_tranh_Vi%E1%BB%87t_Nam_tham_gia#");

        for(Element table: doc.select("#mw-content-text > div.mw-parser-output > .wikitable")) {
            for(Element tr : table.selectFirst("tbody").select("tr")) {
                if(tr.selectFirst("td") != null) {
                    time = "?";
                    place = "?";
                    result = "?";
                    detail = "?";
                    source = "https://vi.wikipedia.org" + tr.select("td:nth-child(1) > a").attr("href");
                    if(source.contains("/wiki/"))
                        detailDoc = connectURL(source);
                    else 
                        continue;
                    name = detailDoc.select("#firstHeading").text();
                    if(!isWar(name))
                        continue;
                    detail = detailDoc.select("#mw-content-text > div.mw-parser-output > p").first().text().replaceAll("\\[.*\\]", "");
                    keyword = StringHandler.normalize(name + detail);
                    for(Element info : detailDoc.select("#mw-content-text > div.mw-parser-output .infobox tr")) {
                        keyword += StringHandler.normalize(info.text());
                        if(info.select("th").text().toLowerCase().equals("thời gian"))
                            time = info.select("td").text().replaceAll("\\[.*\\]", "");
                        else if(info.select("th").text().toLowerCase().equals("địa điểm"))
                            place = info.select("td").text().replaceAll("\\[.*\\]", "");
                        else if(info.select("th").text().toLowerCase().equals("kết quả"))
                            result = info.select("td").text().replaceAll("\\[.*\\]", "");      
                    };
                    if(time.equals("?")) {
                        time = tr.select("td:nth-child(1)").text().replaceAll("\\[.*\\]", "");
                        time = time.substring(time.indexOf("(") + 1, time.indexOf(")"));
                    }
                    if(result.equals("?"))
                        result = tr.select("td:nth-child(4)").text().replaceAll("\\[.*\\]", "");
                    War war = new War(name, source, time, place, result);
                    war.setDetail(detail);
                    war.setRelativeKeyWord(StringHandler.normalize(name+detail));
                    war.setRelativeKeyWord(keyword);
                    addUltity(war);
                }
            }
        }

    }

    private Document connectURL(String url) {
        try {
            Document document = new Document("UTF-8");
            document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error on connecting to: " + url);
            return null;
        }
    }

    private void addUltity(War war){
        String keyName = StringHandler.normalize(war.getName().replaceAll("\\(.*\\)", ""));
        for(War saved: wars){
            if(StringHandler.normalize(saved.getName() + "  ").contains(keyName)) {
                merge(saved, war);
                return;
            } 
        }
        wars.add(war);
        Platform.runLater(() ->{
            dataList.add(war);
        });
    }

    private void merge(War a, War b) {
        if(!b.getOccurTime().equals("?"))
            a.setOccurTime(b.getOccurTime());
        if(b.getDetail().length() > a.getDetail().length())
            a.setDetail(b.getDetail());
        a.setRelativeKeyWord(a.getRelativeKeyWord() + b.getRelativeKeyWord());
    }

    private boolean isWar(String name) {
        for(String key: warRecognizeKey) {
            if(name.toLowerCase().contains(key))
                return true;
        }
        return false;
    }
}
