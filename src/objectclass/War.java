package objectclass;

public class War extends Historical{
    private String occurTime;
    private String place;
    private String result;
    public String getOccurTime() {
        return occurTime;
    }
    public String getPlace() {
        return place;
    }
    public String getResult() {
        return result;
    }
    public War(String name, String source, String occurTime, String place, String result) {
        super(name, source);
        this.occurTime = occurTime;
        this.place = place;
        this.result = result;
    }

    public String toString() {
        StringBuilder info = new StringBuilder("- Thời gian: ");
        info.append(occurTime);
        info.append("\n - Địa điểm: ");
        info.append(place);
        info.append("\n - Kết quả: ");
        info.append(result);
        info.append("\n\t");
        info.append(super.getDetail());
        
        return info.toString();
    }

    public War clone() {
        War clone = new War(super.getName(), super.getSource(), occurTime, place, result);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }
}
