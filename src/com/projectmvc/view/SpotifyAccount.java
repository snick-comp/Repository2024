package com.projectmvc.view;
import com.projectmvc.controller.*;
import com.projectmvc.model.*;
import com.projectmvc.service.SongService;
import com.projectmvc.service.SongServiceImpl;
import java.util.Scanner;
import java.util.Collection;
import com.projectmvc.validations.*;

public class SpotifyAccount {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserController userController = UserControllerImpl.getInstance();
    private static final PlaylistController playlistController = PlaylistControllerImpl.getInstance();
    private static final SongService songService = SongServiceImpl.getInstance();

    public static void main(final String[] args) {
        while (true) {
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Create Playlist");
            System.out.println("4. Add Song into Playlist");
            System.out.println("5. Exit");

            int choice = getInputAsInt();
            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    signIn();
                    break;
                case 3:
                    createPlaylist();
                    break;
                case 4:
                    addSongIntoPlaylist();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void signUp() {
        System.out.println("Enter Name:");
        final String name = scanner.nextLine();

        System.out.println("Enter Email Address:");
        final String emailAddress = scanner.nextLine();

        System.out.println("Enter Mobile Number:");
        final String mobileNumber = scanner.nextLine();

        System.out.println("Enter Password:");
        final String password = scanner.nextLine();

        try {
            Validation.validateEmailAddress(emailAddress);
            Validation.validatePassword(password);

            final User user = new User();
            user.setName(name);
            user.setEmailAddress(emailAddress);
            user.setMobileNumber(mobileNumber);
            user.setPassword(password);

            userController.signUp(user);
            System.out.println("Sign Up Successful!");

        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void signIn() {
        System.out.println("Enter Email Address:");
        final String emailAddress = scanner.nextLine();

        System.out.println("Enter Password:");
        final String password = scanner.nextLine();

        try {
            Validation.validateEmailAddress(emailAddress);
            Validation.validatePassword(password);

            final boolean success = userController.signIn(emailAddress, password);
            if (success) {
                System.out.println("Sign In Successful!");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createPlaylist() {
        System.out.println("Enter Playlist ID:");
        final long id = getInputAsLong();

        System.out.println("Enter Playlist Name:");
        final String name = scanner.nextLine();

        final Playlist playlist = new Playlist();
        playlist.setId(id);
        playlist.setName(name);

        playlistController.create(playlist);
        System.out.println("Playlist Created Successfully!");
    }

    private static void addSongIntoPlaylist() {
        System.out.println("Enter Playlist ID:");
        final long playlistId = getInputAsLong();

        System.out.println("Available Songs:");
        final Collection<Song> songs = songService.getAll();
        for (Song song : songs) {
            System.out.println("ID: " + song.getId() + ", Name: " + song.getName());
        }

        System.out.println("Enter Song ID to Add into Playlist:");
        final long songId = getInputAsLong();

        final Song songToAdd = findSongById(songs, songId);
        if (songToAdd != null) {
            playlistController.addSongIntoPlaylist(playlistId, songToAdd);
            System.out.println("Song Added into Playlist Successfully!");
        } else {
            System.out.println("Song not found. Please try again.");
        }
    }

    private static Song findSongById(final Collection<Song> songs, final long songId) {
        for (Song song : songs) {
            if (song.getId() == songId) {
                return song;
            }
        }
        return null;
    }

    private static int getInputAsInt() {
        while (true) {
            try {
                final int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }
    }

    private static long getInputAsLong() {
        while (true) {
            try {
                final long input = scanner.nextLong();
                scanner.nextLine(); 
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
