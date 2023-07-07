package othertools;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHandler {

    private static final String alphabet = " AÁÀẢÃẠaáàảãạĂẮẰẲẴẶăắằẳẵặÂẤẦẨẪẬâấầẩẫậBbCcDdĐđEÉÈẺẼẸeéèẻẽẹÊẾỀỂỄỆêếềểễệFfGgHhIÍÌỈĨỊiíìỉĩịJjKkLlMmNnOÓÒỎÕỌoóòỏõọÔỐỒỔỖỘôốồổỗộƠỚỜỞỠỢơớờởỡợPpQqRrSsTtUÚÙỦŨỤuúùủũụƯỨỪỬỮỰưứừửữựVvWwXxYyZz0123456789+-*/!#$%^&()[]{}`~<>.,;:'\"\\?";

    public static String normalize(String text) {
        String convertedText = text.toLowerCase();
        convertedText = convertedText.replaceAll("đ", "d");
        convertedText = Normalizer.normalize(convertedText, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        convertedText = pattern.matcher(convertedText).replaceAll("");
        convertedText = convertedText.replaceAll("[^a-z0-9]", "");
        return convertedText;
    }

    public static String keyForm(String str) {
        str.replaceAll(" ", "");
        return str.toLowerCase();
    }

    public static int compare(String a, String b) {
        int dif;
        int len = a.length() < b.length() ? a.length() : b.length();
        for(int i = 0; i < len; i++) {
            dif = alphabet.indexOf(a.charAt(i)) - alphabet.indexOf(b.charAt(i));
            if(dif != 0)
                return dif;
        }   
        if(b.length() > a.length())
            return -1; 
        return 0;
    }
}
