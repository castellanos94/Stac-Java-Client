public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/Toxina-Friedman-min3.csv";
        System.out.println("Kolmogorov-Smirnov: " + Client.KOLMOGOROV(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Agostino: " + Client.AGOSTINO(path, Client.SIGNIFICANCE_LEVEL[3]));
        System.out.println("Shapiro: " + Client.SHAPIRO(path, Client.SIGNIFICANCE_LEVEL[3]));

    }
}
