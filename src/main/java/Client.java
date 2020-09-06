import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Example of consume web service from STAC Web Platform
 */
public class Client {
    public static final double[] SIGNIFICANCE_LEVEL = {0.10, 0.05, 0.02, 0.01, 0.005, 0.001};
    private static final String BASE_URL = "https://tec.citius.usc.es/stac/api/";
    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * <bold>D'Agostino-Pearson</bold>
     * It's less powerful than Shapiro-Wilk.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map AGOSTINO(String path_csv_file, double significance_level) {
        String end_point = "agostino/" + significance_level;
        return getMap(path_csv_file, end_point);
    }

    /**
     * <bold>Shapiro-Wilk</bold>
     * The best of the three methods, especially for samples of less than 30 elements.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map SHAPIRO(String path_csv_file, double significance_level) {
        String end_point = "shapiro/" + significance_level;
        return getMap(path_csv_file, end_point);
    }


    /**
     * <bold>Kolmogorov-Smirnov</bold>
     * The less powerful.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map KOLMOGOROV(String path_csv_file, double significance_level) {
        String end_point = "kolmogorov/" + significance_level;
        return getMap(path_csv_file, end_point);
    }

    /**
     * <bold>ANOVA between cases</bold>
     * Tests the null hypothesis that the means of the results of two or more groups are the same. For this, the test
     * analyzes the variation between samples as well as their inner variation with the variance. The statistic of the ANOVA test, is estimated by the f-distribution.
     * <p>
     * <bold>Type:Parametric Test</bold>
     * <p>
     * <bold>Subtype: multiple groups </bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map ANOVA(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "anova/" + significance_level);
    }

    /**
     * <bold>ANOVA WITHIN CASES</bold>
     * Tests the null hypothesis that the means of the results of two or more groups are the same. For this, the test
     * analyzes the variation between samples as well as their inner variation with the variance. The statistic of the ANOVA test, is estimated by the f-distribution.
     * <p>
     * <bold>Type:Parametric Test</bold>
     * <p>
     * <bold>Subtype: multiple groups</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map ANOVA_WITHIN(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "anova-within/" + significance_level);
    }


    /**
     * <bold>Friedman</bold>
     * This test makes comparisons and assigns rankings to each data set. The statistic follows a chis-quared
     * distribution with K−1 degrees of freedom, being K the number of related variables (or number of algorithms).
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map FRIEDMAN(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "friedman/finner_multitest/" + significance_level);
    }

    /**
     * <bold>Friedman Aligned Ranks</bold>
     * It makes comparisons an assigns rankings considering all the data sets. It is usually employed when the number
     * of algorithms in the comparison is low
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map FRIEDMAN_ALIGNED_RANK(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "friedman-aligned-ranks/finner_multitest/" + significance_level);
    }


    /**
     * <bold>Quade</bold>
     * Similar to ImanDavenport, only that it takes into account that some problems are more difficult or that the
     * results obtained from different algorithms present higher discrepancies (weighting).
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map QUADE(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "quade/finner_multitest/" + significance_level);

    }

    /**
     * <bold>Nemenyi</bold>
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Post-hoc Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map NEMENYI(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "quade/nemenyi_multitest/" + significance_level);
    }

    /**
     * <bold>Holm</bold>
     * It compares each pi (starting from the most significant or the lowest) with: α(K−i), where i∈[1,K−1].
     * If the hypothesis is rejected the test continues the comparisons. When an hypothesis is accepted, all the other hypothesis are accepted as well. It is better (more power) than Bonferroni-Dunn test, because it controls the FWER (familywise error rate), which is the probability of committing one or more type I errors among all hypothesis
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Post-hoc Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map HOLM(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "quade/holm_multitest/" + significance_level);
    }

    /**
     * <bold>Finner</bold>
     * Finner's test is similar to Holm's but each p-value associated with the hypothesis Hi is compared with:
     * pi≤1−(1−α)(K−1)i, where i∈[1,K−1]. It is more powerful than Bonferroni-Dunn, Holm, Hochberg and Li (only in some cases).
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Post-hoc Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map FINNER(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "quade/finner_multitest/" + significance_level);
    }

    /**
     * <bold>Hochberg</bold>
     * It compares in the opposite direction to Holm. As soon as an acceptable hypothesis is found, all the other
     * hypothesis are accepted. It is better (more power) than Holm test, but the differences between them are small in practice.
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Post-hoc Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    public static Map HOCHBERG(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "quade/hochberg_multitest/" + significance_level);
    }

    /**
     * <bold>Shaffer</bold>
     * This test is like Holm's but each p-value associated with the hypothesis Hi is compared as pi≤αti, where ti is
     * the maximum number of possible hypothesis assuming that the previous (j−1) hypothesis have been rejected.
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Post-hoc Test</bold>
     * <p>
     * <h1>it does not work</h1>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it is true.
     *                           Also known as confidence level or Type I error (false positive)
     * @return Map object response
     */
    @Deprecated
    public static Map SHAFFTER(String path_csv_file, double significance_level) {
        return getMap(path_csv_file, "friedman/shaffer_multitest/" + significance_level);
    }

    private static String transform_to_json(String path_csv_file) throws IOException {
        Table table = Table.read().csv(path_csv_file);
        HashMap<String, ArrayList> data = new HashMap<>();
        for (Column c : table.columns()) {
            data.put(c.name(), new ArrayList(c.asList()));
        }
        return gson.toJson(data);

    }


    private static Map getMap(String path_csv_file, String end_point) {
        try {
            String content = transform_to_json(path_csv_file);
            HttpPost request = new HttpPost(BASE_URL + end_point);
            StringEntity params = new StringEntity(content);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            HttpResponse response = httpClient.execute(request);
            return (Map) gson.fromJson(EntityUtils.toString(response.getEntity()), Object.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
