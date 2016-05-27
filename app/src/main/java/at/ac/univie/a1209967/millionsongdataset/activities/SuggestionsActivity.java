package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.adapter.SongAdapter;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class SuggestionsActivity extends AppCompatActivity {

    ArrayList<Song> songList;
    ArrayList<Song> historyList;
    ArrayList<Song> sugList;
    ListView sugView;

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        Intent sugIntent = getIntent();
        sugView = (ListView) findViewById(R.id.sugListView);

        songList = (ArrayList<Song>) sugIntent.getSerializableExtra(PlayerActivity.EXTRA_SONGLIST);
        historyList = (ArrayList<Song>) sugIntent.getSerializableExtra(PlayerActivity.EXTRA_HIST);
        sugList = new ArrayList<Song>();

        sugList=historyList;

        SongAdapter sugAdt = new SongAdapter(this, sugList);
        sugView.setAdapter(sugAdt);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
