package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;

public class InfoActivity extends AppCompatActivity {

    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        TextView artist = (TextView) findViewById(R.id.artTextView);
        TextView title = (TextView) findViewById(R.id.titleTextView);
        TextView album = (TextView) findViewById(R.id.albTextView);
        TextView duration = (TextView) findViewById(R.id.durTextView);
        TextView year = (TextView) findViewById(R.id.yearTextView);
        TextView genre = (TextView) findViewById(R.id.genreTextView);

        Intent infoIntent= getIntent();
        song = (Song) infoIntent.getSerializableExtra(PlayerActivity.EXTRA_SONG);

        artist.setText(song.getArtist());
        title.setText(song.getTitle());
        album.setText(song.getAlbum());
        duration.setText((String.format("%02d : %02d ",
                TimeUnit.MILLISECONDS.toMinutes(song.getLength()),
                TimeUnit.MILLISECONDS.toSeconds(song.getLength()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(song.getLength()))
        )));
        year.setText(""+song.getYear());
        genre.setText(song.getGenre());


        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
