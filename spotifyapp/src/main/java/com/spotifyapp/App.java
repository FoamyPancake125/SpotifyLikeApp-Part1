package com.spotifyapp;

import java.util.*;

public class App {
    public static void main(String[] args) {
        TrackLibrary library = TrackLibrary.loadFromJson("tracks.json");
        AudioPlayer player = new AudioPlayer();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to Spotify-Like App ===");
            System.out.println("[H]ome");
            System.out.println("[S]earch by title");
            System.out.println("[L]ibrary");
            System.out.println("[F]avorites");
            System.out.println("[Q]uit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "H": showHome(library); break;
                case "S": searchMenu(library, player, scanner); break;
                case "L": libraryMenu(library, player, scanner); break;
                case "F": favoritesMenu(library, player, scanner); break;
                case "Q": System.out.println("Thanks for using Spotify-Like App. Goodbye!"); running = false; break;
                default: System.out.println("Invalid choice. Please enter H, S, L, F, or Q.");
            }
        }
        scanner.close();
    }

    static void showHome(TrackLibrary library) {
        List<Track> tracks = library.getTracks();
        System.out.println("\n--- Home (" + tracks.size() + " tracks) ---");
        if (tracks.isEmpty()) {
            System.out.println("No tracks loaded.");
        } else {
            for (int i = 0; i < tracks.size(); i++) {
                System.out.println((i + 1) + ". " + tracks.get(i));
            }
        }
    }

    static void playTrack(Track track, AudioPlayer player, Scanner scanner) {
        player.play(track.getFilePath());
        System.out.println("Playing: " + track);
        System.out.println("\n[P]ause  [S]top  [R]ewind  [F]orward  [H]eart/Favorite  [Q]uit playback");

        while (true) {
            System.out.print("Control: ");
            String cmd = scanner.nextLine().trim().toUpperCase();
            switch (cmd) {
                case "P": player.pause(); break;
                case "S": player.stop(); return;
                case "R": player.rewind(); break;
                case "F":
                    track.toggleFavorite();
                    System.out.println(track.isFavorite() ? "Added to favorites!" : "Removed from favorites.");
                    break;
                case "Q": player.stop(); return;
                default: System.out.println("Invalid control.");
            }
        }
    }

    static void searchMenu(TrackLibrary library, AudioPlayer player, Scanner scanner) {
        System.out.print("\nEnter title to search: ");
        String query = scanner.nextLine();
        List<Track> results = library.searchByTitle(query);
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }
        System.out.print("Enter number to play (0 to cancel): ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice > 0 && choice <= results.size()) {
            playTrack(results.get(choice - 1), player, scanner);
        }
    }

    static void libraryMenu(TrackLibrary library, AudioPlayer player, Scanner scanner) {
        System.out.println("\n--- Library ---");
        List<Track> tracks = library.getTracks();
        if (tracks.isEmpty()) {
            System.out.println("No tracks loaded.");
            return;
        }
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i));
        }
        System.out.print("Enter number to play (0 to cancel): ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice > 0 && choice <= tracks.size()) {
            playTrack(tracks.get(choice - 1), player, scanner);
        }
    }

    static void favoritesMenu(TrackLibrary library, AudioPlayer player, Scanner scanner) {
        System.out.println("\n--- Your Favorites ❤ ---");
        List<Track> favorites = library.getFavorites();
        if (favorites.isEmpty()) {
            System.out.println("No favorites yet! Play a song and press H to heart it.");
            return;
        }
        for (int i = 0; i < favorites.size(); i++) {
            System.out.println((i + 1) + ". " + favorites.get(i));
        }
        System.out.print("Enter number to play (0 to cancel): ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice > 0 && choice <= favorites.size()) {
            playTrack(favorites.get(choice - 1), player, scanner);
        }
    }
}
