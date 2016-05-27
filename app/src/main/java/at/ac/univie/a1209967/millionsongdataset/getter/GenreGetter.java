package at.ac.univie.a1209967.millionsongdataset.getter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.Management;

/**
 * Created by Adlbert on 27.05.2016.
 */
public class GenreGetter extends AsyncTask<Void, Void, String> {
    public SharedPreferences settings;

    private Exception exception;
    private Management management;
    private Context context;
    private ProgressBar progressBar;
    private ArrayList<String> genres = new ArrayList<>();


    public GenreGetter(Context context, Management management){
        super();
        this.management = management;
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void... urls) {
        try {
            try {
                //Get the local static weather data.
                //With this data is only worked if nothing else works
                InputStream stream = context.getAssets().open("genres.json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        try {
            JSONArray array = new JSONObject(response).getJSONArray("genres");

            ArrayList<String> result = new ArrayList<>();

            for(int i = 0; i < array.length(); i++){
                result.add(array.getString(i));
            }

            management.saveGenres(result);
            System.err.println("                                  " + management.getGenres().size()  + "                       sdfsdf");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
