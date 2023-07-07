package entity;

import java.util.ArrayList;
import java.util.List;

import othertools.StringHandler;

public class Historical {
    private String name;
    private String source;
    private String detail;
    private List<Historical> relative;
    private String relativeKeyWord;
    public String getName() {
        return name;
    }
    public String getSource() {
        return source;
    }
    public List<Historical> getRelative() {
        return relative;
    }
    public String getRelativeKeyWord() {
        return relativeKeyWord;
    }

    public void setRelativeKeyWord(String relativeKeyWord) {
        this.relativeKeyWord = relativeKeyWord;
    }
    public Historical(String name, String source) {
        this.name = name;
        this.source = source;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void addRelation(Historical obj) {
        if(relative == null)
            relative = new ArrayList<Historical>();
        if(!relative.contains(obj))
            relative.add(obj);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;
        Historical otherHistotical = (Historical) other;
        if (name == null) {
            if (otherHistotical.name != null)
                return false;
        } else if (name.equals(otherHistotical.name))
            return true;
        if (source == null) {
            if (otherHistotical.source != null)
                return false;
        } else if (source.equals(otherHistotical.source))
            return true;
        if (detail == null) {
            if (otherHistotical.detail != null)
                return false;
        } else if (detail.equals(otherHistotical.detail))
            return true;
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setRelation(Historical b) {
        if(relativeKeyWord == null)
            relativeKeyWord = StringHandler.normalize(name + detail);
        if(relativeKeyWord.contains(StringHandler.normalize(b.getName()))) {
            addRelation(b);
            b.addRelation(this);
        }
    }

}
