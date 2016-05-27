package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class SearchActivity extends AppCompatActivity {

    TextView resultText;
    EditText searchText;
    private ArrayList<Song> songList;
    Button backBtn;


    public final static String EXTRA_SEARCHSTR="at.ac.univie.a1209967.millionsongdataset.SEARCHSTR";
    public final static String EXTRA_SONGLIST="at.ac.univie.a1209967.millionsongdataset.SONGLIST";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Button searchBtn=(Button) findViewById(R.id.searchbutton);
        searchText=(EditText) findViewById(R.id.searchText);
        resultText=(TextView) findViewById(R.id.resultTextView);

        Intent searchIntent = getIntent();
        songList=(ArrayList<Song>) searchIntent.getSerializableExtra(PlayerActivity.EXTRA_SONGLIST);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchRsltIntent = new Intent(getApplicationContext(), SearchResultActivity.class);
                String searchString = searchText.getText().toString();
                searchRsltIntent.putExtra(EXTRA_SEARCHSTR, searchString);
                searchRsltIntent.putExtra(EXTRA_SONGLIST, songList);
                startActivity(searchRsltIntent);
            }
        });

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}
