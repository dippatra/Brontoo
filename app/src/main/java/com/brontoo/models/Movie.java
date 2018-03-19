package com.brontoo.models;


import java.util.ArrayList;



public class Movie {
    private int voteCount;
    private int ID;
    private boolean isVideo;
    private float voteAverage;
    private String title;
    private float popularity;
    private String posterImagePath;
    private String language;
    private ArrayList<Integer> genreIds=new ArrayList<>();
    private String backDropPath;
    private boolean isForAdult;
    private String summary;
    private String releaseDate;

    public int getVoteCount() {
        return voteCount;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
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

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
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
