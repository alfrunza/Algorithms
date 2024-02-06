package others.playlistRandomizer;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

//Determine top 3 artists in the final playlist (um. %, two digits)
//Determine top 5 albums in the final playlist (um. %, two digits)
//Add menu for choosing what top to be displayed in the console - read from console


public class Main {

    public static void main(String[] args) {
        final String fileName = "songs.json";
        List<Song> playlist;
        try {
            playlist = SongService.buildPlaylist(fileName);
            System.out.println("Your playlist:");
            for (Song song : playlist) {
                System.out.println(song.getTitle() + " by " + song.getArtist() + ". It's from the " + song.getAlbum() + " album.");
            }
        } catch (IOException | ParseException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
