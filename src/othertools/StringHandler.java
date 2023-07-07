package othertools;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHandler {

    public static String normalize(String text) {
        String convertedText = text.toLowerCase();
        convertedText = convertedText.replaceAll("đ", "d");
        convertedText = Normalizer.normalize(convertedText, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        convertedText = pattern.matcher(convertedText).replaceAll("");
        convertedText = convertedText.replaceAll("[^a-z0-9]", "");
        return convertedText;
    }
}
