package at.ac.univie.a1209967.millionsongdataset.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import at.ac.univie.a1209967.millionsongdataset.R;

public class InfoActivity extends AppCompatActivity {

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


        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
