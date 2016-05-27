package at.ac.univie.a1209967.millionsongdataset.entities;

import java.io.Serializable;

/**
 * Created by Adlbert on 02.05.2016.
 * Diese Klasse wurde aus dem Tutorium zur Erstellung eines Musikplayers entnommen.
 * http://code.tutsplus.com/tutorials/create-a-music-player-on-android-project-setup--mobile-22764
 */
public class Song implements Serializable {
    private long id;
    private int length;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private boolean fav;

    public Song(long songID, String songTitle, String songArtist, String songAlbum, int l, String g) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        album=songAlbum;
        length=l;
        genre = g;
        fav = false;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getAlbum(){return album;}
    public int getLength(){return length;}
    public String getGenre(){return genre;}
    public void setFav(boolean f){ fav = f;}
    public boolean getFav() {return fav;}
}
