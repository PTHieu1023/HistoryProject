package entity;

public class Festival extends Historical implements CloneAndDisplay<Festival> {
    
    private String location;
    private String occurTime;

    public Festival(String name, String source, String location, String occurTime) {
        super(name, source);
        this.location = location;
        this.occurTime = occurTime;
    }
    
    public String getLocation() {
        return location;
    }

    public String getOccurTime() {
        return occurTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder(" - Địa điểm: ");
        info.append(location);
        info.append("\n - Thời gian: ");
        info.append(occurTime);
        info.append("\n\t");
        info.append(super.getDetail());

        return info.toString();
    }

    @Override
    public Festival clone() { 
        Festival clone = new Festival(super.getName(), super.getSource(), location, occurTime);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }

}