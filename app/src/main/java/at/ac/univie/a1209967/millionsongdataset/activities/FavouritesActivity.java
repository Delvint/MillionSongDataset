package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.adapter.SongAdapter;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class FavouritesActivity extends AppCompatActivity {

    ArrayList<Song> songList;
    ArrayList<Song> favList;
    ListView favView;
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Intent resultIntent = getIntent();
        favList = new ArrayList<Song>();
        songList = (ArrayList<Song>) resultIntent.getSerializableExtra(PlayerActivity.EXTRA_SONGLIST);

        favView = (ListView) findViewById(R.id.favListView);

        for(int i=0; i<songList.size(); i++){
            if(songList.get(i).getFav()){
                favList.add(songList.get(i));
            }
        }

        if (favList.size() == 0)
            Toast.makeText(getApplicationContext(), "There were no songs added to favourites yet.", Toast.LENGTH_LONG).show();

        //favList=songList;

        SongAdapter favAdt = new SongAdapter(this, favList);
        favView.setAdapter(favAdt);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
