package regular_expressions_4;

import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class RegexTests {
    @Test
    public void testGrouping() {
        String regex = "(abba)";
        assertEquals("abba", RegexProcessor.generateValidString(regex));
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testGroupingError() {
        String regex = "(abba";
        assertThrows(IllegalArgumentException.class, () -> {
            RegexProcessor.generateValidString(regex);
        });
    }

    @Test
    public void testChoiceExtraction() {
        String element = "ab|c|3|d3";
        ArrayList<String> expected = new ArrayList<>(List.of("ab", "c", "3", "d3"));
        ArrayList<String> actual = RegexProcessor.choiceExtractor(element);

        assertEquals(expected, actual);
    }

    @Test
    public void testStar() {
        String regex = "a*";
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testStar2() {
        String regex = "a*b";
        assertTrue(RegexProcessor.generateValidString(regex).contains("b"));
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testPlus() {
        String regex = "a+";
        assertTrue(RegexProcessor.generateValidString(regex).contains("a"));
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testQuestionMark() {
        String regex = "a?";
        if(RegexProcessor.generateValidString(regex).contains("a")){
            System.out.println("Contains");
        }
        else {
            System.out.println("Does not contain");
        }
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testBrackets() {
        String regex = "a{3}";
        assertEquals("aaa", RegexProcessor.generateValidString(regex));
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testChoice() {
        String regex = "(a|b|c)";
        String result = RegexProcessor.generateValidString(regex);
        if(result.contains("a")){
            System.out.println("a");
        }
        else if(result.contains("b")){
            System.out.println("b");
        }
        else if(result.contains("c")){
            System.out.println("c");
        }
        else{
            System.out.println("Error in choiceTest");
        }
        System.out.println(RegexProcessor.generateValidString(regex));
    }

    @Test
    public void testGroupBrackets() {
        String regex = "(ab){2}";
        assertEquals("abab", RegexProcessor.generateValidString(regex));
        System.out.println(RegexProcessor.generateValidString(regex));
    }


}