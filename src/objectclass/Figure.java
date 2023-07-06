package objectclass;

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

    public String getDeath() {
        return death;
    }  

    public String toString() {
        StringBuilder info = new StringBuilder(" - Sinh: ");
        info.append(birth);
        info.append("\n - Mất: ");
        info.append(death);
        info.append("\n\t");
        info.append(super.getDetail());
        return info.toString();
    }
}
