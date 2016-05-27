package at.ac.univie.a1209967.millionsongdataset;

import java.util.ArrayList;

/**
 * Created by Adlbert on 02.05.2016.
 */
public class Management {

    private static Management thisInstance = new Management();

    public static Management getInstance() {
        return thisInstance;
    }

    public static ArrayList<String> genres = new ArrayList<>();

    public void saveGenres(ArrayList genres){
        this.genres = genres;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
}
