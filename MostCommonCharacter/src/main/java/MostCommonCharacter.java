import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MostCommonCharacter {
    /**
     * Find the most common character in str.
     * You could use a HashMap that maps a Character key to an Int value to
     * represent how many times a Character has
     * been spotted.
     * 
     * @param str A String.
     * @return the most common character within str.
     */
    public char recurringChar(String str) {
        Map<Character, Integer> mostCommonCharMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character currChar = str.charAt(i);
            if (mostCommonCharMap.containsKey(currChar))
                mostCommonCharMap.put(currChar, mostCommonCharMap.get(currChar) + 1);
            else
                mostCommonCharMap.put(currChar, 1);
        }

        Map.Entry<Character, Integer> mostCommonChar = null;
        for (Map.Entry<Character, Integer> entry : mostCommonCharMap.entrySet()) {
            if (mostCommonChar == null || entry.getValue().compareTo(mostCommonChar.getValue()) > 0) {
                mostCommonChar = entry;
            }
        }

        return mostCommonChar.getKey();
    }
}
