import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/Toxina-Friedman-min3.csv";
        path = "src/main/resources/dtlz1.csv";
        System.out.println("Kolmogorov-Smirnov: " + Client.KOLMOGOROV(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Agostino: " + Client.AGOSTINO(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Shapiro: " + Client.SHAPIRO(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Friedman: " + Client.FRIEDMAN(path, Client.SIGNIFICANCE_LEVEL[3], POST_HOC.HOLM));
        System.out.println("Friedman Aligned Ranks: " + Client.FRIEDMAN_ALIGNED_RANK(path, Client.SIGNIFICANCE_LEVEL[1], POST_HOC.HOCHBERG));
        System.out.println("QUADE-NEMENYI: " + Client.QUADE(path, Client.SIGNIFICANCE_LEVEL[0], POST_HOC.NEMENYI));
        System.out.println("QUADE-HOLM: " + Client.QUADE(path, Client.SIGNIFICANCE_LEVEL[0], POST_HOC.HOLM));
        System.out.println("QUADE-FINNER: " + Client.QUADE(path, Client.SIGNIFICANCE_LEVEL[0], POST_HOC.FINNER));
        System.out.println("QUADE-HOCHBERG: " + Client.QUADE(path, Client.SIGNIFICANCE_LEVEL[0], POST_HOC.HOCHBERG));

        System.out.println("ANOVA: " + Client.ANOVA(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("ANOVA WITHIN CASES: ");
        Map<String, Object> map = Client.ANOVA_WITHIN(path, Client.SIGNIFICANCE_LEVEL[0]);
        map.forEach((k, v) -> System.out.println(k + " : " + v));


    }
}
