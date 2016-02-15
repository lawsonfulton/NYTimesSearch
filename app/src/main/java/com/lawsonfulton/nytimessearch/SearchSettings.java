package com.lawsonfulton.nytimessearch;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lawson on 2/14/16.
 */
public class SearchSettings implements Serializable {
    String beginDate;
    String prettyDate;
    String order;
    ArrayList<String> newsDesks;

    public SearchSettings() {
        beginDate = "";
        order = "Relevance";
        newsDesks = new ArrayList<>();
    }

    public SearchSettings(String beginDate, String prettyDate, String order, ArrayList<String> newsDesks) {
        this.beginDate = beginDate;
        this.order = order;
        this.newsDesks = newsDesks;
        this.prettyDate = prettyDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getOrder() {
        return order;
    }

    public ArrayList<String> getNewsDesks() {
        return newsDesks;
    }

    public String getPrettyDate() {
        return prettyDate;
    }
}
