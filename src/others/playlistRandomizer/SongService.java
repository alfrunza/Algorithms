package others.playlistRandomizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SongService {


    public static List<Song> getLikedSongs(String fileName) throws IOException, ParseException {
        return getAllSongs(fileName)
                .stream()
                .filter(Song::getLiked)
                .collect(Collectors.toList());
    }

    public static List<Song> getAllSongs(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray songList = (JSONArray) parser.parse(new FileReader(fileName));
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


    public static void main(String[] args) {
        try {
            List<Song> likedSongs = getLikedSongs("songs.json");
            for (Song song : likedSongs) {
                System.out.println(song.getTitle());
            }
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
