package others.playlistRandomizer;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

import static others.playlistRandomizer.SongService.buildPlaylist;

public class Main {

    public static void main(String[] args) {
        final String fileName = "songs.json";
        List<Song> playlist;
        System.out.println("Your playlist:");
        try {
            playlist = buildPlaylist(fileName);
            for (Song song : playlist) {
                System.out.println(song.getTitle() + " by " + song.getArtist() + ". It's from the " + song.getAlbum() + " album.");
            }
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
