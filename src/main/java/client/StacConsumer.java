package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import model.Anova;
import model.Assistant;
import model.NonParametricTestAll;
import model.ParametricTest;
import model.ParametricTestTwoGroups;
import model.PostHocResult;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example of consume web service from STAC Web Platform. <br>
 * Null hypothesis (H0): The medians of the differences between the two group
 * samples are equal. <br>
 * If ParametricTest.getResult() == 1 then H0 is rejected otherwise H0 is
 * accepted
 * 
 * @see ParametricTest
 */
public class StacConsumer {
    public static final double[] SIGNIFICANCE_LEVEL = { 0.10, 0.05, 0.02, 0.01, 0.005, 0.001 };
    private static final String BASE_URL = "https://tec.citius.usc.es/stac/api/";
    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * <bold>D'Agostino-Pearson</bold>
     * <p>
     * It's less powerful than Shapiro-Wilk.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return ParametricTest response
     */
    public static PostHocResult AGOSTINO(String path_csv_file, double significance_level) {
        String end_point = "agostino/" + significance_level;
        return PostHocResult.fromJsonString(getResponse(path_csv_file, end_point));
    }

    /**
     * <bold>Shapiro-Wilk</bold>
     * <p>
     * The best of the three methods, especially for samples of less than 30
     * elements.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return ParametricTest response
     */
    public static PostHocResult SHAPIRO(String path_csv_file, double significance_level) {
        String end_point = "shapiro/" + significance_level;
        return PostHocResult.fromJsonString(getResponse(path_csv_file, end_point));
    }

    /**
     * <bold>Kolmogorov-Smirnov</bold>
     * <p>
     * The less powerful.
     * <p>
     * <bold>Type: Parametric Test</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return ParametricTest response
     */
    public static ParametricTest KOLMOGOROV(String path_csv_file, double significance_level) {
        return PostHocResult.fromJsonString(getResponse(path_csv_file, "kolmogorov/" + significance_level));
    }

    /**
     * <bold>ANOVA between cases</bold>
     * <p>
     * Tests the null hypothesis that the means of the results of two or more groups
     * are the same. For this, the test analyzes the variation between samples as
     * well as their inner variation with the variance. The statistic of the ANOVA
     * test, is estimated by the f-distribution.
     * <p>
     * <bold>Type:Parametric Test</bold>
     * <p>
     * <bold>Subtype: multiple groups </bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Anova response
     */
    public static Anova ANOVA(String path_csv_file, double significance_level) {
        return Anova.fromJsonString(getResponse(path_csv_file, "anova/" + significance_level));
    }

    /**
     * <bold>ANOVA WITHIN CASES</bold>
     * <p>
     * Tests the null hypothesis that the means of the results of two or more groups
     * are the same. For this, the test analyzes the variation between samples as
     * well as their inner variation with the variance. The statistic of the ANOVA
     * test, is estimated by the f-distribution.
     * <p>
     * <bold>Type:Parametric Test</bold>
     * <p>
     * <bold>Subtype: multiple groups</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Anova response
     */
    public static Anova ANOVA_WITHIN(String path_csv_file, double significance_level) {
        return Anova.fromJsonString(getResponse(path_csv_file, "anova-within/" + significance_level));
    }

    /**
     * <bold>Friedman</bold>
     * <p>
     * This test makes comparisons and assigns rankings to each data set. The
     * statistic follows a chis-quared distribution with K−1 degrees of freedom,
     * being K the number of related variables (or number of algorithms).
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @param post_hoc           Post-hoc multiple comparison
     * @return Non Parametric Test Response
     */
    public static NonParametricTestAll FRIEDMAN(String path_csv_file, double significance_level, POST_HOC post_hoc) {
        return NonParametricTestAll
                .fromJsonString(getResponse(path_csv_file, "friedman/" + post_hoc + "/" + significance_level));
    }

    /**
     * <bold>Friedman Aligned Ranks</bold>
     * <p>
     * It makes comparisons an assigns rankings considering all the data sets. It is
     * usually employed when the number of algorithms in the comparison is low
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @param post_hoc           Post-hoc multiple comparison
     * @return Non Parametric Test Response
     */
    public static NonParametricTestAll FRIEDMAN_ALIGNED_RANK(String path_csv_file, double significance_level,
            POST_HOC post_hoc) {
        return NonParametricTestAll.fromJsonString(
                getResponse(path_csv_file, "friedman-aligned-ranks/" + post_hoc + "/" + significance_level));
    }

    /**
     * <bold>Quade</bold>
     * <p>
     * Similar to ImanDavenport, only that it takes into account that some problems
     * are more difficult or that the results obtained from different algorithms
     * present higher discrepancies (weighting).
     * <p>
     * <bold>Type: Non Parametric Test</bold>
     * <p>
     * <bold>Subtype: Ranking Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @param post_hoc           Post-hoc multiple comparison
     * @return Non Parametric Test Response
     */
    public static NonParametricTestAll QUADE(String path_csv_file, double significance_level, POST_HOC post_hoc) {
        return NonParametricTestAll
                .fromJsonString(getResponse(path_csv_file, "quade/" + post_hoc + "/" + significance_level));
    }

    /**
     * <bold>Wilcoxon</bold>
     * <p>
     * paired data. Assumes that the differences between samples are symmetrical
     * with respect to the median.
     * <p>
     * <bold>Type: Non Parametric Test Two groups</bold>
     * <p>
     * <bold>Subtype: Normality Test</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param firstGroup
     * @param secondGroup
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Parametric Test Response
     */
    public static ParametricTestTwoGroups WILCOXON(String path_csv_file, String firstGroup, String secondGroup,
            double significance_level) {
        String end_point = "wilcoxon/" + significance_level;
        return ParametricTestTwoGroups.fromJsonString(getResponse(path_csv_file, firstGroup, secondGroup, end_point));
    }

    /**
     * <bold>Mann-Whitney-U</bold>
     * <p>
     * Unpaired data. The medians of the differences between the two group samples
     * are equal.
     * <p>
     * <bold>Type: Non Parametric Test Two groups</bold>
     * <p>
     * Mann, H. B., Whitney, D. R. (1947). On a test of whether one of two random
     * variables is stochastically larger than the other. The annals of mathematical
     * statistics, 50-60.
     *
     * @param path_csv_file      load data
     * @param firstGroup
     * @param secondGroup
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Parametric Test Response
     */
    public static ParametricTestTwoGroups MANN_WHITNEY_U(String path_csv_file, String firstGroup, String secondGroup,
            double significance_level) {
        String end_point = "mannwhitneyu/" + significance_level;
        return ParametricTestTwoGroups.fromJsonString(getResponse(path_csv_file, firstGroup, secondGroup, end_point));
    }

    /**
     * <bold>T-test paired</bold>
     * <p>
     * <bold>Type: Parametric Test Two groups</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param firstGroup
     * @param secondGroup
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Parametric Test Response
     */
    public static ParametricTestTwoGroups T_TESTPaired(String path_csv_file, String firstGroup, String secondGroup,
            double significance_level) {
        String end_point = "ttest/" + significance_level;
        return ParametricTestTwoGroups.fromJsonString(getResponse(path_csv_file, firstGroup, secondGroup, end_point));
    }

    /**
     * <bold>T-test unpaired</bold>
     * <p>
     * <bold>Type: Parametric Test Two groups</bold>
     * <p>
     *
     * @param path_csv_file      load data
     * @param firstGroup
     * @param secondGroup
     * @param significance_level Probability of rejecting a null hypothesis when it
     *                           is true. Also known as confidence level or Type I
     *                           error (false positive)
     * @return Parametric Test Response
     */
    public static ParametricTestTwoGroups T_TESTUnpaired(String path_csv_file, String firstGroup, String secondGroup,
            double significance_level) {
        String end_point = "ttest-ind/" + significance_level;
        return ParametricTestTwoGroups.fromJsonString(getResponse(path_csv_file, firstGroup, secondGroup, end_point));
    }

    /**
     * Assistant that estimate the best fitted statistical test for the data
     * provided by the user. The decision process takes into account the following
     * data:
     * 
     * @param path_csv_file load data
     * @return Assistant model
     */
    public static Assistant assistMe(String path_csv_file) {
        return Assistant.fromJsonString(getResponse(path_csv_file, "assistant"));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static String transform_to_json(String path_csv_file) throws IOException {
        Table table = Table.read().csv(path_csv_file);
        HashMap<String, ArrayList> data = new HashMap<>();
        for (Column c : table.columns()) {
            data.put(c.name(), new ArrayList(c.asList()));
        }
        return gson.toJson(data);
    }

    private static String getResponse(String path_csv_file, String end_point) {
        try {
            String content = transform_to_json(path_csv_file);
            HttpPost request = new HttpPost(BASE_URL + end_point);
            StringEntity params = new StringEntity(content);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            return EntityUtils.toString(httpClient.execute(request).getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getResponse(String path_csv_file, String firstGroup, String secondGroup, String end_point) {
        try {
            String content = transform_to_json(path_csv_file, firstGroup, secondGroup);
            HttpPost request = new HttpPost(BASE_URL + end_point);
            StringEntity params = new StringEntity(content);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            return EntityUtils.toString(httpClient.execute(request).getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static String transform_to_json(String path_csv_file, String firstGroup, String secondGroup)
            throws IOException {
        Table table = Table.read().csv(path_csv_file);
        HashMap<String, ArrayList> data = new HashMap<>();
        int count = 0;
        for (Column c : table.columns()) {
            if (c.name().equals(firstGroup)) {
                data.put("group1", new ArrayList(c.asList()));
                count++;
            }
            if (c.name().equals(secondGroup)) {
                data.put("group2", new ArrayList(c.asList()));
                count++;
            }
            if (count == 2) {
                break;
            }
        }
        return gson.toJson(data);
    }
}
