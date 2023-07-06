package objectclass;

public class Festival extends Historical {
    private String location;
    private String occurTime;
    public String getLocation() {
        return location;
    }
    public String getOccurTime() {
        return occurTime;
    }
    public Festival(String name, String source, String location, String occurTime) {
        super(name, source);
        this.location = location;
        this.occurTime = occurTime;
    }

    public String toString() {
        StringBuilder info = new StringBuilder(" - Địa điểm: ");
        info.append(location);
        info.append("\n - Thời gian: ");
        info.append(occurTime);
        info.append("\n\t");
        info.append(super.getDetail());

        return info.toString();
    }

    public Festival clone() { 
        Festival clone = new Festival(super.getName(), super.getSource(), location, occurTime);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }
}
