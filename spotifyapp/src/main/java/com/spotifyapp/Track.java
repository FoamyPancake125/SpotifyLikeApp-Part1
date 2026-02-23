package com.spotifyapp;

public class Track {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private boolean isFavorite;
    private String filePath;

    public Track(String title, String artist, int year, String genre, boolean isFavorite, String filePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.isFavorite = isFavorite;
        this.filePath = filePath;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public String getFilePath() { return filePath; }
    public boolean isFavorite() { return isFavorite; }
    public void toggleFavorite() { isFavorite = !isFavorite; }

    public String toString() {
        String heart = isFavorite ? " ♥" : "";
        return title + " by " + artist + " (" + year + ") [" + genre + "]" + heart;
    }
}
