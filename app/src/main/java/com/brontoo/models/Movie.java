package com.brontoo.models;


import android.os.Parcel;
import android.os.Parcelable;



public class Movie implements Parcelable{
    private int voteCount;
    private int ID;
    private boolean isVideo;
    private float voteAverage;
    private String title;
    private float popularity;
    private String posterImagePath;
    private String language;
    private String backDropPath;
    private boolean isForAdult;
    private String summary;
    private String releaseDate;
    public Movie(){

    }

    protected Movie(Parcel in) {
        voteCount = in.readInt();
        ID = in.readInt();
        isVideo = in.readByte() != 0;
        voteAverage = in.readFloat();
        title = in.readString();
        popularity = in.readFloat();
        posterImagePath = in.readString();
        language = in.readString();
        backDropPath = in.readString();
        isForAdult = in.readByte() != 0;
        summary = in.readString();
        releaseDate = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt(ID);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeFloat(voteAverage);
        dest.writeString(title);
        dest.writeFloat(popularity);
        dest.writeString(posterImagePath);
        dest.writeString(language);
        dest.writeString(backDropPath);
        dest.writeByte((byte) (isForAdult ? 1 : 0));
        dest.writeString(summary);
        dest.writeString(releaseDate);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getVoteCount() {
        return voteCount;
    }



    public boolean isForAdult() {
        return isForAdult;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public float getPopularity() {
        return popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public int getID() {
        return ID;
    }

    public String getLanguage() {
        return language;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public void setForAdult(boolean forAdult) {
        isForAdult = forAdult;
    }



    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

}
