package others.playlistRandomizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SongService {

    public static List<Song> getLikedSongs(String fileName) throws IOException, ParseException {

        //Get liked songs from whole list, filtered by liked flag
        return getAllSongs(fileName)
                .stream()
                .filter(Song::getLiked)
                .collect(Collectors.toList());
    }

    public static List<Song> getNotLikedSongs(String fileName) throws IOException, ParseException {

        //Get not liked songs from whole list, filtered by liked flag
        return getAllSongs(fileName)
                .stream()
                .filter(Predicate.not(Song::getLiked))
                .collect(Collectors.toList());
    }

    public static List<Song> getAllSongs(String fileName) {
        try {
            //Parse JSON file
            JSONParser parser = new JSONParser();
            JSONArray songList = (JSONArray) parser.parse(new FileReader(fileName));

            //Create List from parsed Song objects
            List<Song> allSongs = new ArrayList<>();
            for (Object s : songList) {
                JSONObject songJson = (JSONObject) s;
                allSongs.add(new Song(songJson));
            }
            return allSongs;
        } catch (IOException iex) {
            System.out.println("Wrong file");
            System.out.println(iex.getMessage());
            return null;
        } catch (ParseException pex) {
            System.out.println("Wrong JSON Format");
            System.out.println(pex.getMessage());
            return null;
        }
    }

    public static List<Song> buildPlaylist(String fileName) throws IOException, ParseException {
        List<Song> playlist = new ArrayList<>();

        //Define max playlist capacity and number of liked songs
        //To be added to the playlist
        //Condition is that more than 50% of the playlist are liked songs
        final int capacity = 15;
        Random rand = new Random();
        int rnd = rand.nextInt(capacity / 2 + 1, capacity);

        //Define collection of liked songs
        List<Song> likedSongsCollection = getLikedSongs(fileName);

        //Check to see if number of liked songs is larger than rnd
        //If not enough liked songs to fulfill condition,
        //add as many as possible
        if (rnd > likedSongsCollection.size()) {
            rnd = likedSongsCollection.size();
        }

        //Add rnd number of liked songs to the playlist
        Collections.shuffle(likedSongsCollection);
        playlist.addAll(likedSongsCollection.subList(0,rnd-1));

        //Define collection of not liked songs and shuffle it
        List<Song> notLikedSongsCollection = getNotLikedSongs(fileName);
        Collections.shuffle(notLikedSongsCollection);

        //Fill remainder of playlist with unliked songs
        playlist.addAll(notLikedSongsCollection.subList(0,capacity-rnd-1));


        //For now, the playlist is ordered by liked
        //Shuffle one more time to randomize it further
        Collections.shuffle(playlist);
        return playlist;
    }
}
