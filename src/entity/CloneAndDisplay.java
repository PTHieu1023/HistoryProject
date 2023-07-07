package entity;

public interface CloneAndDisplay<T> {

    //CLone thực thể để lưu vào file json nếu không sẽ sinh ra lỗi
    T clone();
    //Ghi đè toString để hiển thị lên content của GUI
    String toString();
}