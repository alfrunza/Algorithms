package others.playlistRandomizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SongService {

    public static List<Song> getLikedSongs(String fileName) throws IOException, ParseException {

        //Get liked songs from whole list, filtered by liked flag
        return getAllSongs(fileName).stream().filter(Song::getLiked).collect(Collectors.toList());
    }

    public static List<Song> getNotLikedSongs(String fileName) throws IOException, ParseException {

        //Get not liked songs from whole list, filtered by liked flag
        return getAllSongs(fileName).stream().filter(Predicate.not(Song::getLiked)).collect(Collectors.toList());
    }

    public static List<Song> getAllSongs(String fileName) throws IOException, ParseException {
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
        } catch (IOException | ParseException e) {
            throw e;
        }
    }

    public static List<Song> buildPlaylist(String fileName) throws IOException, ParseException {
        Pattern p = Pattern.compile("\\bjson\\b", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(fileName);

        if (!m.find()) {
            throw new IOException("Wrong file extension");
        }

        //Define likedSongsCollection
        List<Song> likedSongsCollection;
        likedSongsCollection = getLikedSongs(fileName);

        List<Song> playlist = new ArrayList<>();

        //Define max playlist capacity and number of liked songs
        //To be added to the playlist
        //Condition is that more than 50% of the playlist are liked songs
        final int capacity = 15;
        Random rand = new Random();
        int rnd = rand.nextInt(capacity / 2 + 1, capacity);

        //Check to see if number of liked songs is larger than rnd
        //If not enough liked songs to fulfill condition,
        //add as many as possible
        if (rnd > likedSongsCollection.size()) {
            rnd = likedSongsCollection.size();
        }

        //Add rnd number of liked songs to the playlist
        Collections.shuffle(likedSongsCollection);
        playlist.addAll(likedSongsCollection.subList(0, rnd - 1));

        //Define collection of not liked songs and shuffle it
        List<Song> notLikedSongsCollection = getNotLikedSongs(fileName);
        Collections.shuffle(notLikedSongsCollection);

        //Fill remainder of playlist with unliked songs
        playlist.addAll(notLikedSongsCollection.subList(0, capacity - rnd - 1));


        //For now, the playlist is ordered by liked
        //Shuffle one more time to randomize it further
        Collections.shuffle(playlist);
        return playlist;
    }

    public static Map<String, Long> getArtistsAndOccurrences(List<Song> playlist) {
        //Create a map containing the artist as key and how many times they appear in the playlist as value
        return playlist.stream().collect(Collectors.groupingBy(Song::getArtist, Collectors.counting()));
//        LinkedHashMap<String, Long> sorted = new LinkedHashMap<>();
//        ArrayList<Long> occurrences = new ArrayList<>();
//        for(Map.Entry<String, Long> entry : unsortedArtistsAndOccurrences.entrySet()){
//            occurrences.add(entry.getValue());
//        }
//        Collections.sort(occurrences);
//        for(long occ : occurrences){
//            for(Map.Entry<String, Long> entry : unsortedArtistsAndOccurrences.entrySet()){
//                if(entry.getValue().equals(occ)) {
//                    sorted.put(entry.getKey(), occ);
//                }
//            }
//        }
//        return sorted;
    }

    public static Map<String, Long> getTopThreeArtists(Map<String, Long> artistsAndOccurrences) {
        return artistsAndOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> Long.compare(v2, v1)))
                .limit(3)
                //Lambda checks if entry1 exists. If it exists,keep the existing one
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
    }

    public static void main(String[] args) {
        try {
            List<Song> playlist = buildPlaylist("songs.json");
            for(Song s : playlist){
                System.out.println(s.getTitle() + " by " + s.getArtist());
            }
//            System.out.println(getArtistsAndOccurrences(playlist));
            System.out.println(getTopThreeArtists(getArtistsAndOccurrences(buildPlaylist("songs.json"))));
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
