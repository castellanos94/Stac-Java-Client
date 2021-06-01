package model;

import java.math.BigDecimal;
import java.util.Arrays;

public class RankingResult extends ParametricTest {
    private Boolean result;
    private BigDecimal p_value;
    private String[] names;
    private BigDecimal[] rankings;
    private BigDecimal statistic;

    public String[] getNames() {
        return names;
    }

    public BigDecimal[] getRankings() {
        return rankings;
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
    public Boolean getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RankingResult [names=" + Arrays.toString(names) + ", p_value=" + p_value + ", rankings="
                + Arrays.toString(rankings) + ", result=" + result + ", statistic=" + statistic + "]";
    }

}
