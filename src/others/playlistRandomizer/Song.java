package others.playlistRandomizer;

public class Song {

    private String title;

    private String artist;

    private String album;

    private boolean liked;

    public Song(String title, String artist, String album, boolean liked) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.liked = liked;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }


}
