package entity;

public class Figure extends Historical{
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

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getDeath() {
        return death;
    }  

    //Nội dung hiển thị trên GUI
    public String toString() {
        StringBuilder info = new StringBuilder(" - Sinh: ");
        info.append(birth);
        info.append("\n - Mất: ");
        info.append(death);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }

    //Clone 1 object không relative = null để lưu vào file json
    public Figure clone() {
        Figure clone = new Figure(super.getName(), super.getSource(), birth, death);
        clone.setDetail(super.getDetail());
        clone.setRelativeKeyWord(super.getRelativeKeyWord());
        return clone;
    }
}
