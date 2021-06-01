package model;

import com.google.gson.Gson;

/**
 * Assistant that estimate the best fitted statistical test for the data
 * provided by the user. The decision process takes into account the following
 * data:
 */
public class Assistant {
    /**
     * is paired data ?
     */
    private boolean paired;
    /**
     * The homocedasticity between groups (tested using Levene test with alpha 0.1)
     */
    private int homocedasticity;
    /**
     * Suggested test
     */
    private String test;
    /**
     * grap representation used at STAC web platform
     */
    private String graph;
    /**
     * The number of groups available
     */
    private int k;
    /**
     * The normality of each group (tested using a Shapiro-Wilks test with alpha
     * 0.1)
     */
    private int normality;
    /**
     * The number of samples per group
     */
    private int n;

    public static Assistant fromJsonString(String json) {
        return new Gson().fromJson(json, Assistant.class);
    }

    public boolean isPaired() {
        return paired;
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public int getHomocedasticity() {
        return homocedasticity;
    }

    public void setHomocedasticity(int homocedasticity) {
        this.homocedasticity = homocedasticity;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getNormality() {
        return normality;
    }

    public void setNormality(int normality) {
        this.normality = normality;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Assistant [homocedasticity=" + homocedasticity + ", k=" + k + ", n=" + n + ", normality=" + normality
                + ", paired=" + paired + ", test=" + test + "]";
    }

}
