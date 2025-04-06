package lab_4;

import java.util.*;
import java.util.ArrayList;

public class RegexProcessor {
    public static int i;

    public static String generateValidString(String regex){
        i = 0;
        StringBuilder validString = new StringBuilder();
        Random rand = new Random();
        ArrayList<String> choices;

        while (i < regex.length()){
            choices = null;
            String element = String.valueOf(regex.charAt(i));
            //System.out.println(element);
            String nextElement;

            // check if Group (...)
            if (element.charAt(0) == '('){
                int rightParenthesisIndex = regex.indexOf(')', i);

                // Extract the insides
                if (rightParenthesisIndex != -1){
                    element = regex.substring(i+1, rightParenthesisIndex);
                }
                else {
                    throw new IllegalArgumentException("Right parenthesis ')' not found starting from index " + i);
                }


                i = rightParenthesisIndex; // correct

                if (i+1 < regex.length()){
                    nextElement = String.valueOf(regex.charAt(i+1));
                }
                else {
                    nextElement = "";
                }

                // check if Group has choice (... | ...) and Handle
                if (element.contains("|")){
                    choices = choiceExtractor(element);
                    element = handleQuantifiersChoice(choices, regex);
                    validString.append(element);
                }
                //System.out.println("After parenthesis " + i);
            }

            // if not Group, then Alphanumeric A-Z | a-z | 0-9
            if (i+1 < regex.length()){
                nextElement = String.valueOf(regex.charAt(i+1));
            }
            else {
                nextElement = "";
            }

            // handle Simple Group or Alphanumeric
            if (choices == null){
                element = handleQuantifiers(element, regex);
                validString.append(element);
            }

            i++; // correct
            //System.out.println("After iteration " + i);
        }

        return validString.toString();
    }

    public static ArrayList<String> choiceExtractor(String element){
        return new ArrayList<>(Arrays.asList(element.split("\\|")));
    }

    public static String handleQuantifiers(String element, String regex) {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        String nextElement;
        if (i+1 < regex.length()){
            nextElement = String.valueOf(regex.charAt(i+1));
        }
        else {
            nextElement = "";
        }

        switch (nextElement) {
            case "*" -> {
                int randomInt = rand.nextInt(6);
                result.append(element.repeat(randomInt));
                i++;
            }
            case "+" -> {
                int randomInt = rand.nextInt(1, 6);
                result.append(element.repeat(randomInt));
                i++;
            }
            case "?" -> {
                int randomInt = rand.nextInt(2);
                result.append(element.repeat(randomInt));
                i++;
            }
            case "{" -> {
                int rightBracketIndex = regex.indexOf('}', i);

                if (rightBracketIndex != -1) {
                    int times = Integer.parseInt(regex.substring(i + 2, rightBracketIndex));
                    result.append(element.repeat(times));
                    i = rightBracketIndex;
                    //System.out.println("After right bracket handle " + i);
                } else {
                    throw new IllegalArgumentException("Right bracket '}' not found starting from index " + i);
                }
            }
            default -> {
                result.append(element);
            }
        }

        //System.out.println("After handle " + i);

        return result.toString();
    }

    public static String handleQuantifiersChoice(ArrayList<String> choices, String regex) {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        String nextElement;
        if (i+1 < regex.length()){
            nextElement = String.valueOf(regex.charAt(i+1));
        }
        else {
            nextElement = "";
        }


        switch (nextElement) {
            case "*" -> {
                int randomInt = rand.nextInt(6);
                for (int j = 0; j < randomInt; j++){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
            }
            case "+" -> {
                int randomInt = rand.nextInt(1, 6);
                for (int j = 0; j < randomInt; j++){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
            }
            case "?" -> {
                int randomInt = rand.nextInt(2);
                if (randomInt == 1){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
            }
            case "{" -> {
                int rightBracketIndex = regex.indexOf('}', i);

                if (rightBracketIndex != -1) {
                    int times = Integer.parseInt(regex.substring(i + 2, rightBracketIndex));

                    for (int j = 0; j < times; j++){
                        int randomElementIndex = rand.nextInt(choices.size());
                        result.append(choices.get(randomElementIndex));
                    }

                    i = rightBracketIndex;
                    //System.out.println("After right bracket handle choice" + i);
                }
                else {
                    throw new IllegalArgumentException("Right bracket '}' not found starting from index " + i);
                }
            }
            default -> {
                int randomElementIndex = rand.nextInt(choices.size());
                result.append(choices.get(randomElementIndex));
            }
        }

        //System.out.println("After handle choice " + i);

        return result.toString();
    }











    public static String generateValidStringExplanation(String regex){
        i = 0;
        StringBuilder validString = new StringBuilder();
        Random rand = new Random();
        ArrayList<String> choices;

        System.out.println("Processing steps for " + regex + ":\n");
        while (i < regex.length()){
            choices = null;
            String element = String.valueOf(regex.charAt(i));
            //System.out.println(element);
            String nextElement;

            // check if Group (...)
            if (element.charAt(0) == '('){
                int rightParenthesisIndex = regex.indexOf(')', i);

                // Extract the insides
                if (rightParenthesisIndex != -1){
                    element = regex.substring(i+1, rightParenthesisIndex);

                    System.out.println("Group identified: (" + element + ")");
                }
                else {
                    throw new IllegalArgumentException("Right parenthesis ')' not found starting from index " + i);
                }


                i = rightParenthesisIndex; // correct

                if (i+1 < regex.length()){
                    nextElement = String.valueOf(regex.charAt(i+1));
                }
                else {
                    nextElement = "";
                }

                // check if Group has choice (... | ...) and Handle
                if (element.contains("|")){
                    System.out.println("Group contains choice: (" + element + ")");
                    choices = choiceExtractorExplanation(element);
                    element = handleQuantifiersChoiceExplanation(choices, regex);
                    validString.append(element);
                }
                //System.out.println("After parenthesis " + i);
            }

            // if not Group, then Alphanumeric A-Z | a-z | 0-9
            if (i+1 < regex.length()){
                nextElement = String.valueOf(regex.charAt(i+1));
            }
            else {
                nextElement = "";
            }

            // handle Simple Group or Alphanumeric
            if (choices == null){
                if(element.length() == 1){
                    System.out.println("Identified single element '" + element + "'");
                }
                element = handleQuantifiersExplanation(element, regex);
                validString.append(element);
            }

            i++; // correct
            //System.out.println("After iteration " + i);
        }

        System.out.println("Final result: " + validString.toString() + "\n\n");
        return validString.toString();
    }

    public static ArrayList<String> choiceExtractorExplanation(String element){
        return new ArrayList<>(Arrays.asList(element.split("\\|")));
    }

    public static String handleQuantifiersExplanation(String element, String regex) {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        String nextElement;
        if (i+1 < regex.length()){
            nextElement = String.valueOf(regex.charAt(i+1));
        }
        else {
            nextElement = "";
        }

        switch (nextElement) {
            case "*" -> {
                int randomInt = rand.nextInt(6);
                result.append(element.repeat(randomInt));
                i++;
                System.out.println("* identified. Append '" + element + "' 0-5 times -> " + result);
            }
            case "+" -> {
                int randomInt = rand.nextInt(1, 6);
                result.append(element.repeat(randomInt));
                i++;
                System.out.println("+ identified. Append '" + element + "' 1-5 times -> " + result);
            }
            case "?" -> {
                int randomInt = rand.nextInt(2);
                result.append(element.repeat(randomInt));
                i++;
                System.out.println("? identified. Append '" + element + "' 0-1 times -> " + result);
            }
            case "{" -> {
                int rightBracketIndex = regex.indexOf('}', i);

                if (rightBracketIndex != -1) {
                    int times = Integer.parseInt(regex.substring(i + 2, rightBracketIndex));
                    result.append(element.repeat(times));
                    i = rightBracketIndex;
                    //System.out.println("After right bracket handle " + i);
                    System.out.println("Repetition identified. Append '" + element + "' " + times + " times -> " + result);
                } else {
                    throw new IllegalArgumentException("Right bracket '}' not found starting from index " + i);
                }
            }
            default -> {
                System.out.println("Append '" + element + "' -> " + result);
                result.append(element);
            }
        }

        //System.out.println("After handle " + i);

        return result.toString();
    }

    public static String handleQuantifiersChoiceExplanation(ArrayList<String> choices, String regex) {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        String nextElement;
        if (i+1 < regex.length()){
            nextElement = String.valueOf(regex.charAt(i+1));
        }
        else {
            nextElement = "";
        }


        switch (nextElement) {
            case "*" -> {
                int randomInt = rand.nextInt(6);
                for (int j = 0; j < randomInt; j++){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
                System.out.println("* identified. Choose an element from " + choices + " 0-5 times -> " + result);
            }
            case "+" -> {
                int randomInt = rand.nextInt(1, 6);
                for (int j = 0; j < randomInt; j++){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
                System.out.println("+ identified. Choose an element from " + choices + " 1-5 times -> " + result);
            }
            case "?" -> {
                int randomInt = rand.nextInt(2);
                if (randomInt == 1){
                    int randomElementIndex = rand.nextInt(choices.size());
                    result.append(choices.get(randomElementIndex));
                }
                i++;
                System.out.println("? identified. Choose an element from " + choices + " 0-1 times -> " + result);
            }
            case "{" -> {
                int rightBracketIndex = regex.indexOf('}', i);

                if (rightBracketIndex != -1) {
                    int times = Integer.parseInt(regex.substring(i + 2, rightBracketIndex));

                    for (int j = 0; j < times; j++){
                        int randomElementIndex = rand.nextInt(choices.size());
                        result.append(choices.get(randomElementIndex));
                    }

                    i = rightBracketIndex;
                    //System.out.println("After right bracket handle choice" + i);
                    System.out.println("Repetition identified. Choose an element from " + choices + " " + times + " times -> " + result);
                }
                else {
                    throw new IllegalArgumentException("Right bracket '}' not found starting from index " + i);
                }
            }
            default -> {
                int randomElementIndex = rand.nextInt(choices.size());
                result.append(choices.get(randomElementIndex));
                System.out.println("Choose an element from " + choices + " -> " + result);
            }
        }

        //System.out.println("After handle choice " + i);

        return result.toString();
    }
}
