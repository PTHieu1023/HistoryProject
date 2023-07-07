package entity;

public class Figure extends Historical implements CloneAndDisplay<Figure> {
    
    private String birth;
    private String death;

    public Figure(String name, String source, String birth, String death) {
        super(name, source);
        this.birth = birth;
        this.death = death;
    }

    public String getBirth() {
        return birth;
    }
    
    public String getDeath() {
        return death;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setDeath(String death) {
        this.death = death;
    }  

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder(" - Sinh: ");
        info.append(birth);
        info.append("\n - Mất: ");
        info.append(death);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }

    @Override
    public Figure clone() {
        Figure clone = new Figure(super.getName(), super.getSource(), birth, death);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }

}