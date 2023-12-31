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

    public Historical(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDetail() {
        return detail;
    }

    public List<Historical> getRelative() {
        return relative;
    }

    public String getRelativeKeyWord() {
        return relativeKeyWord;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setRelativeKeyWord(String relativeKeyWord) {
        this.relativeKeyWord = relativeKeyWord;
    }

    //Tìm và tạo mối liên kết giữa 2 thực thể
    public void setRelation(Historical b) {
        if(relativeKeyWord == null)
            relativeKeyWord = StringHandler.normalize(name + detail);
        String key = StringHandler.normalize(b.getName());
        if(relativeKeyWord.contains(key) || b.getRelativeKeyWord().contains(StringHandler.normalize(name))) {
            addRelation(b);
            b.addRelation(this);
        }
    }

    //Tạo liên kết 2 chiều giữa 2 thực thể
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

}
