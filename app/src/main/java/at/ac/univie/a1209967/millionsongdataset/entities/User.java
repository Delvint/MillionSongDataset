package at.ac.univie.a1209967.millionsongdataset.entities;

import java.util.ArrayList;

/**
 * Created by Adlbert on 04.05.2016.
 */
public class User {

    private String email;
    private String password;
    private ArrayList<Song> history;
    private ArrayList<Song> favourites;

    public User(String email, String password, ArrayList<Song> favourites, ArrayList<Song> history) {
        this.email = email;
        this.favourites = favourites;
        this.history = history;
        this.password = password;
    }

    public ArrayList<Song> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Song> history) {
        this.history = history;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Song> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Song> favourites) {
        this.favourites = favourites;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
