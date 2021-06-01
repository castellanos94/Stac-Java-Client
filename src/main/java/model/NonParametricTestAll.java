package model;

import java.math.BigDecimal;

import com.google.gson.Gson;

/**
 * Non-parametric multiple groups All vs All
 */
public class NonParametricTestAll extends ParametricTest {
    private RankingResult ranking;
    private PostHocResult post_hoc;

    public static NonParametricTestAll fromJsonString(String json) {
        return new Gson().fromJson(json, NonParametricTestAll.class);
    }

    @Override
    public BigDecimal getPValue() {
        return ranking.getPValue();
    }

    @Override
    public BigDecimal getStatistic() {
        return ranking.getStatistic();
    }

    @Override
    public Boolean getResult() {
        return ranking.getResult();
    }

    public PostHocResult getPost_hoc() {
        return post_hoc;
    }

    public RankingResult getRanking() {
        return ranking;
    }

    @Override
    public String toString() {
        return "NonParametricTestAll [post_hoc=" + post_hoc + ", ranking=" + ranking + "]";
    }

}
