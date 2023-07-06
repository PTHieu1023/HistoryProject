package objectclass;

public class Dynasty extends Historical{
    private String timeline;

    public Dynasty(String name, String source, String timeline) {
        super(name, source);
        this.timeline = timeline;
    }

    public String getTimeline() {
        return timeline;
    }   

    public String toString() {
        StringBuilder info = new StringBuilder(" - Thời gian: ");
        info.append(timeline);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }
}
