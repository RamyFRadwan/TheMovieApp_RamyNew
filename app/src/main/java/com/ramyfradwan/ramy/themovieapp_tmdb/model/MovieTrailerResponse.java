package com.ramyfradwan.ramy.themovieapp_tmdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;

import java.util.List;

public class MovieTrailerResponse extends BaseResponseModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results = null;

    public MovieTrailerResponse() {

    }

    public MovieTrailerResponse(int id, List<Trailer> results) {

        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}