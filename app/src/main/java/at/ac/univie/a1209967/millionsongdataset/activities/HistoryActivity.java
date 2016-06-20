package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.adapter.SongAdapter;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<Song> historyList;
    private ListView historyView;
    private Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_history);

        backBtn = (Button) findViewById(R.id.backBtn);


        Intent historyIntent= getIntent();
        historyList = (ArrayList<Song>) historyIntent.getSerializableExtra(PlayerActivity.EXTRA_HIST);

        if(historyList!=null) {
            historyView = (ListView) findViewById(R.id.historyList);

            SongAdapter histAdt = new SongAdapter(this, historyList);
            historyView.setAdapter(histAdt);
        }
        if (historyList.size() == 0)
            Toast.makeText(getApplicationContext(), "There were no songs played yet.", Toast.LENGTH_LONG).show();

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
