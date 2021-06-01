package model;

import java.math.BigDecimal;
import java.util.Arrays;

import com.google.gson.Gson;

public class PostHocResult extends ParametricTest {
    private String control;
    private Integer[] result;
    private String[] comparisons;
    private BigDecimal[] p_value;
    private BigDecimal[] statistic;

    public String getControl() {
        return control;
    }

    public String[] getComparisons() {
        return comparisons;
    }

    @Override
    public BigDecimal[] getPValue() {
        return p_value;
    }

    @Override
    public BigDecimal[] getStatistic() {
        return statistic;
    }

    @Override
    public Integer[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PostHocResult [comparisons=" + Arrays.toString(comparisons) + ", control=" + control + ", p_value="
                + Arrays.toString(p_value) + ", result=" + Arrays.toString(result) + ", statistic="
                + Arrays.toString(statistic) + "]";
    }

    public static PostHocResult fromJsonString(String response) {
        return new Gson().fromJson(response, PostHocResult.class);
    }

}
