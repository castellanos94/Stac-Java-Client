import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/Toxina-Friedman-min3.csv";
        path = "src/main/resources/dtlz1.csv";
        System.out.println("Kolmogorov-Smirnov: " + Client.KOLMOGOROV(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Agostino: " + Client.AGOSTINO(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Shapiro: " + Client.SHAPIRO(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Friedman: " + Client.FRIEDMAN(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("FriedmanA: " + Client.FRIEDMAN_ALIGNED_RANK(path, Client.SIGNIFICANCE_LEVEL[3]));

        System.out.println("ANOVA: " + Client.ANOVA(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("ANOVA WITHIN CASES: ");
        Map map = Client.ANOVA_WITHIN(path, Client.SIGNIFICANCE_LEVEL[0]);
        map.forEach((k, v) -> System.out.println(k + " : " + v));

    }
}
