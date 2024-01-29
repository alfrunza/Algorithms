package others.playlistRandomizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongService {



    public static List<Song> getLikedSongs(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray songList = (JSONArray) parser.parse(new FileReader(fileName));

        List<Song> likedSongs = new ArrayList<>();

        for (Object s : songList) {
            JSONObject songJson = (JSONObject) s;
            String liked = (String) songJson.get("liked");
            String title = (String) songJson.get("title");
            String artist = (String) songJson.get("artist");
            String album = (String) songJson.get("album");
            boolean isLiked = false;
            if("y".equals(liked)) {
                isLiked = true;
                Song song = new Song(title, artist, album, isLiked);
                likedSongs.add(song);
            }
        }

        return likedSongs;
    }


    public static void main(String[] args) {
        try {
            List<Song> likedSongs = getLikedSongs("songs.json");
            for(Song song : likedSongs) {
                System.out.println(song.getTitle());
            }
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
