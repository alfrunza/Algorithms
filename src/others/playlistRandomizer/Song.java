package others.playlistRandomizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Song {

    private String title;

    private String artist;

    private String album;

    private boolean liked;

    public Song(JSONObject songList) {
        this.title = songList.get("title").toString();
        this.artist = songList.get("artist").toString();
        this.album = songList.get("album").toString();
        this.liked = songList.get("liked").toString().equalsIgnoreCase("y");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }


}
