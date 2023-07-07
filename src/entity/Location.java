package entity;

public class Location extends Historical implements CloneAndDisplay<Historical>{
    private String location;
    private String category;
    public String getLocation() {
        return location;
    }
    public String getCategory() {
        return category;
    }
    public Location(String name, String source, String location, String category) {
        super(name, source);
        this.location = location;
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder(" - Vị trí: ");
        info.append(location);
        info.append("\n - Loại di tích: ");
        info.append(category);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }

    @Override
    public Location clone() {
        Location clone = new Location(super.getName(), super.getSource(), location, category);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }
}
