package entity;

public class Dynasty extends Historical implements CloneAndDisplay<Dynasty>{
    
    private String timeline;

    public Dynasty(String name, String source, String timeline) {
        super(name, source);
        this.timeline = timeline;
    }

    public String getTimeline() {
        return timeline;
    }   

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder(" - Thời gian: ");
        info.append(timeline);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }

    @Override
    public Dynasty clone() {
        Dynasty clone = new Dynasty(super.getName(), super.getSource(), timeline);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }

}