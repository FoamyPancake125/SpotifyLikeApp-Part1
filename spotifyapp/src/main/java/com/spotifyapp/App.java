package com.spotifyapp;

import java.util.*;

public class App {
    public static void main(String[] args) {
        String jsonPath = "tracks.json";
        TrackLibrary library = TrackLibrary.loadFromJson(jsonPath);
        AudioPlayer player = new AudioPlayer();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to Spotify-Like App ===");
            System.out.println("[H]ome");
            System.out.println("[S]earch by title");
            System.out.println("[L]ibrary");
            System.out.println("[Q]uit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "H":
                    showHome(library);
                    break;
                case "S":
                    searchMenu(library, player, scanner);
                    break;
                case "L":
                    libraryMenu(library, player, scanner);
                    break;
                case "Q":
                    System.out.println("Thanks for listening. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    static void showHome(TrackLibrary library) {
        System.out.println("\n--- Home ---");
        List<Track> tracks = library.getTracks();
        if (tracks.isEmpty()) {
            System.out.println("No tracks loaded.");
        } else {
            for (int i = 0; i < tracks.size(); i++) {
                System.out.println((i + 1) + ". " + tracks.get(i));
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
            Track t = results.get(choice - 1);
            System.out.println("Playing: " + t);
            player.play(t.getFilePath());
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
            Track t = tracks.get(choice - 1);
            System.out.println("Playing: " + t);
            player.play(t.getFilePath());
        }
    }
}
