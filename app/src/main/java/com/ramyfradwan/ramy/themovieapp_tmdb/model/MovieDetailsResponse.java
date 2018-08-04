package com.ramyfradwan.ramy.themovieapp_tmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;

public class MovieDetailsResponse extends BaseResponseModel implements Parcelable
{

    private Integer id;
    private String name;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private float vote_average;
    private long vote_count;
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

    public static final Creator<MovieDetailsResponse> CREATOR = new Creator<MovieDetailsResponse>() {
        @Override
        public MovieDetailsResponse createFromParcel(Parcel in) {
            return new MovieDetailsResponse(in);
        }

        @Override
        public MovieDetailsResponse[] newArray(int size) {
            return new MovieDetailsResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.overview);
        parcel.writeString(this.release_date);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.backdropPath);
        parcel.writeDouble(this.vote_average);
        parcel.writeLong(this.vote_count);

    }
}