package model;

import java.math.BigDecimal;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Tests the null hypothesis that the means of the results of two or more groups
 * are the same. For this, the test analyzes the variation between samples as
 * well as their inner variation with the variance. The statistic of the ANOVA
 * test, is estimated by the f-distribution. <br>
 * Box, G. E. (1953). Non-normality and tests on variance. Biometrika, 40,
 * 318–335.
 */
public class Anova extends ParametricTest {
    private BigDecimal p_value;
    private Boolean result;
    private BigDecimal statistic;

    @Override
    public BigDecimal getPValue() {
        return p_value;
    }

    @Override
    public BigDecimal getStatistic() {
        return statistic;
    }

    @Override
    public Boolean getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ParametricTestTwoGroups [p_value=" + p_value + ", result=" + result + ", statistic=" + statistic + "]";
    }

    public static Anova fromJsonString(String response) {
        return new Gson().fromJson(response, Anova.class);
    }
}
