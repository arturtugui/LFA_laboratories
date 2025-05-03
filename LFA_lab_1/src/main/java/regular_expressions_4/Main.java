package regular_expressions_4;

public class Main {
    public static void main(String[] args) {
        // Varianta 2
        String[] regexes = {"M?N{2}(O|P){3}Q*R+", "(X|Y|Z){3}8+(9|0)", "(H|i)(J|K)L*N?"};

        RegexProcessor.generateValidStringExplanation(regexes[0]);
        RegexProcessor.generateValidStringExplanation(regexes[1]);
        RegexProcessor.generateValidStringExplanation(regexes[2]);
    }
}
