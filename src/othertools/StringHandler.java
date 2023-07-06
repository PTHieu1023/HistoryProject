package othertools;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHandler {

    public String normalize(String text) {
        String convertedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        convertedText = convertedText.toLowerCase();
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        convertedText = pattern.matcher(convertedText).replaceAll("");
        convertedText = convertedText.replaceAll(" ", "");
        convertedText = convertedText.replaceAll("[^a-z0-9]", "");
        return convertedText;
    }

}
