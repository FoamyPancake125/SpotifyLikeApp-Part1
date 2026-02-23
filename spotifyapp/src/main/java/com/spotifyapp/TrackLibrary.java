package com.spotifyapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class TrackLibrary {
    private List<Track> tracks;

    private TrackLibrary(List<Track> tracks) {
        this.tracks = tracks;
    }

    public static TrackLibrary loadFromJson(String path) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Track>>(){}.getType();
            List<Track> tracks = gson.fromJson(new FileReader(path), type);
            return new TrackLibrary(tracks);
        } catch (Exception e) {
            System.out.println("Failed to load JSON: " + e.getMessage());
            return new TrackLibrary(List.of());
        }
    }

    public List<Track> getTracks() { return tracks; }

    public List<Track> getFavorites() {
        return tracks.stream()
                .filter(Track::isFavorite)
                .collect(Collectors.toList());
    }

    public List<Track> searchByTitle(String text) {
        return tracks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }
}
