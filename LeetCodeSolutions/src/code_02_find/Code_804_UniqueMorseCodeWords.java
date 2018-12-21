package code_02_find;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 804. Unique Morse Code Words
 *
 * International Morse Code defines a standard encoding where each letter
 * is mapped to a series of dots and dashes, as follows:
 * "a" maps to ".-",
 * "b" maps to "-...",
 * "c" maps to "-.-.", and so on.
 *
 * For convenience, the full table for the 26 letters of the English alphabet is given below:
 *
 * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
 *
 * Example:
 Input: words = ["gin", "zen", "gig", "msg"]
 Output: 2
 Explanation:
 The transformation of each word is:
 "gin" -> "--...-."
 "zen" -> "--...-."
 "gig" -> "--...--."
 "msg" -> "--...--."
 There are 2 different transformations, "--...-." and "--...--.".

 * Note:
 The length of words will be at most 100.
 Each words[i] will have length in range [1, 12].
 words[i] will only consist of lowercase letters.
 */
public class Code_804_UniqueMorseCodeWords {
    private  String[] morseCode={
            ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."
    };

    public int uniqueMorseRepresentations(String[] words) {
        if(words==null || words.length==0){
            return 0;
        }

        Set<String> set=new HashSet<>();
        for(String word:words){
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<word.length();i++){
                builder.append(morseCode[word.charAt(i)-'a']);
            }
            set.add(builder.toString());
        }
        return set.size();
    }

    @Test
    public void test(){
        String[] words={"gin", "zen", "gig", "msg"};
        System.out.println(uniqueMorseRepresentations(words));
    }
}
