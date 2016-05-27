package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.adapter.SongAdapter;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ArrayList<Song> resultList=new ArrayList<Song>();
    private ListView resultView;
    private Button backBtn;
    private String searchStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        backBtn = (Button) findViewById(R.id.backBtn);

        Intent resultIntent = getIntent();
        songList = (ArrayList<Song>) resultIntent.getSerializableExtra(SearchActivity.EXTRA_SONGLIST);
        searchStr = resultIntent.getStringExtra(SearchActivity.EXTRA_SEARCHSTR);

        for(int i=0; i<songList.size(); i++){
            if(songList.get(i).getArtist().equals(searchStr) || songList.get(i).getTitle().equals(searchStr) ){
                resultList.add(songList.get(i));
            }
        }

        resultView = (ListView) findViewById(R.id.resListView);
        SongAdapter resultAdt = new SongAdapter(this, resultList);
        resultView.setAdapter(resultAdt);

    }

}
