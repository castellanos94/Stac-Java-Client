package model;

import java.math.BigDecimal;

import com.google.gson.Gson;

public class ParametricTestMultiple extends ParametricTest {
    private PostHocResult post_hoc;
    private Anova anova;

    public static ParametricTestMultiple fromJsonString(String json) {
        return new Gson().fromJson(json, ParametricTestMultiple.class);
    }

    @Override
    public BigDecimal getPValue() {
        return anova.getPValue();
    }

    @Override
    public BigDecimal getStatistic() {
        return anova.getStatistic();
    }

    @Override
    public Boolean getResult() {
        return anova.getResult();
    }

    @Override
    public String toString() {
        return "ParametricTestMultiple [anova=" + anova + ", post_hoc=" + post_hoc + "]";
    }

}
