package model;

import java.math.BigDecimal;

import com.google.gson.Gson;

public class ParametricTestTwoGroups extends ParametricTest {
    private BigDecimal p_value;
    private Integer result;
    private BigDecimal statistic;

    public static ParametricTestTwoGroups fromJsonString(String json) {
        return new Gson().fromJson(json, ParametricTestTwoGroups.class);
    }

    @Override
    public BigDecimal getPValue() {
        return p_value;
    }

    @Override
    public BigDecimal getStatistic() {
        return statistic;
    }

    @Override
    public Integer getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ParametricTestTwoGroups [p_value=" + p_value + ", result=" + result + ", statistic=" + statistic + "]";
    }

}
