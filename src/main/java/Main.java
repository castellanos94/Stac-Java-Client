import java.util.Map;

import model.Assistant;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/Toxina-Friedman-min3.csv";
        Assistant assistant = StacConsumer.assistMe(path);
        System.out.println(assistant);
        System.out.println("Friedman: " + StacConsumer.FRIEDMAN(path, 0.05, POST_HOC.HOLM));
        System.out.println("Kolmogorov-Smirnov: " + StacConsumer.KOLMOGOROV(path, 0.05));

        path = "src/main/resources/dtlz1.csv";
        /*System.out.println("Kolmogorov-Smirnov: " + StacConsumer.KOLMOGOROV(path, StacConsumer.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Agostino: " + StacConsumer.AGOSTINO(path, StacConsumer.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Shapiro: " + StacConsumer.SHAPIRO(path, StacConsumer.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Friedman: " + StacConsumer.FRIEDMAN(path, StacConsumer.SIGNIFICANCE_LEVEL[3], POST_HOC.HOLM));
        System.out.println("Friedman Aligned Ranks: " + StacConsumer.FRIEDMAN_ALIGNED_RANK(path, StacConsumer.SIGNIFICANCE_LEVEL[1], POST_HOC.HOCHBERG));
        System.out.println("QUADE-NEMENYI: " + StacConsumer.QUADE(path, StacConsumer.SIGNIFICANCE_LEVEL[0], POST_HOC.NEMENYI));
        System.out.println("QUADE-HOLM: " + StacConsumer.QUADE(path, StacConsumer.SIGNIFICANCE_LEVEL[0], POST_HOC.HOLM));
        System.out.println("QUADE-FINNER: " + StacConsumer.QUADE(path, StacConsumer.SIGNIFICANCE_LEVEL[0], POST_HOC.FINNER));
        System.out.println("QUADE-HOCHBERG: " + StacConsumer.QUADE(path, StacConsumer.SIGNIFICANCE_LEVEL[0], POST_HOC.HOCHBERG));

        System.out.println("ANOVA: " + StacConsumer.ANOVA(path, StacConsumer.SIGNIFICANCE_LEVEL[3]));
        System.out.println("ANOVA WITHIN CASES: ");
        Map<String, Object> map = StacConsumer.ANOVA_WITHIN(path, StacConsumer.SIGNIFICANCE_LEVEL[0]);
        map.forEach((k, v) -> System.out.println(k + " : " + v));*/


    }
}
