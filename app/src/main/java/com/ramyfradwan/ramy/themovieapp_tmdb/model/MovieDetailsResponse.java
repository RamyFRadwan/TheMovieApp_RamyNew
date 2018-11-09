package com.ramyfradwan.ramy.themovieapp_tmdb.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;

import java.io.Serializable;

public class MovieDetailsResponse extends BaseResponseModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_title")
    @Expose
    private String name;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("vote_average")
    @Expose
    private float vote_average;
    @SerializedName("vote_count")
    @Expose
    private long vote_count;
    @SerializedName("release_date")
    @Expose
    private String release_date;

    public MovieDetailsResponse(Integer id, String name, String posterPath, String backdropPath, String overview, float vote_average, long vote_count, String release_date) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.release_date = release_date;
    }

    public MovieDetailsResponse() {
    }

    protected MovieDetailsResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        vote_average = in.readFloat();
        vote_count = in.readLong();
        release_date = in.readString();
    }

    public MovieDetailsResponse(int id, String title, String overView, String date, String im_path, String pd_path, double rating) {
        super();
        this.id = id;
        this.name = title;
        this.overview = overView;
        this.release_date = date;
        this.posterPath = im_path;
        this.backdropPath = pd_path;
        this.vote_average = (float) rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}