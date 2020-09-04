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
     * <bold>Type: Normality Test</bold>
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
     * <bold>Type: Normality Test</bold>
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
     * <bold>Type: Normality Test</bold>
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
