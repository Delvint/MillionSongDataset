package at.ac.univie.a1209967.millionsongdataset.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import at.ac.univie.a1209967.millionsongdataset.R;
import at.ac.univie.a1209967.millionsongdataset.adapter.SongAdapter;
import at.ac.univie.a1209967.millionsongdataset.controller.MusicController;
import at.ac.univie.a1209967.millionsongdataset.entities.Song;
import at.ac.univie.a1209967.millionsongdataset.service.MusicService;

/**
 * Created by Adlbert on 04.05.2016.
 * Diese Klasse wurde aus dem Tutorium zur Erstellung eines Musikplayers entnommen.
 * http://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778
 */

public class PlayerActivity extends AppCompatActivity implements MediaPlayerControl, View.OnClickListener {

    private ArrayList<Song> songList;
    private ListView songView;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private MusicController controller;
    private boolean paused=false, playbackPaused=true, pref=false, shuffle=false, loop=false;

    private Button nextBtn;
    private Button prefBtn;
    private ImageButton playBtn;
    private ImageButton shuffleBtn;
    private ImageButton skipfBtn;
    private ImageButton skipbBtn;
    private ImageButton repeatBtn;
    private ImageButton favBtn;
    private ImageButton infoBtn;
    private TextView titelView;
    private TextView interpretView;
    private TextView albumView;
    private SeekBar timeBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        songView = (ListView)findViewById(R.id.song_list);

        songList = new ArrayList<Song>();

        getSongList();

        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        timeBar = (SeekBar)findViewById(R.id.seekBar);
        timeBar.setMax(songList.get(0).getLength());
        timeBar.setProgress(0);

        nextBtn = (Button)findViewById(R.id.next_Btn);
        nextBtn.setOnClickListener(this);
        prefBtn = (Button)findViewById(R.id.pref_Btn);
        prefBtn.setOnClickListener(this);
        playBtn = (ImageButton)findViewById(R.id.play_Btn);
        playBtn.setImageResource(R.drawable.play);
        playBtn.setOnClickListener(this);
        shuffleBtn = (ImageButton)findViewById(R.id.shuffle_Btn);
        shuffleBtn.setOnClickListener(this);
        shuffleBtn.setImageResource(R.drawable.shuffle_not);
        skipfBtn = (ImageButton)findViewById(R.id.skipf_Btn);
        skipfBtn.setOnClickListener(this);
        skipbBtn = (ImageButton)findViewById(R.id.skipb_Btn);
        skipbBtn.setOnClickListener(this);
        repeatBtn = (ImageButton)findViewById(R.id.repeat_Btn);
        repeatBtn.setOnClickListener(this);
        repeatBtn.setImageResource(R.drawable.repeat_not);
        favBtn = (ImageButton)findViewById(R.id.fav_Btn);
        favBtn.setOnClickListener(this);
        infoBtn = (ImageButton)findViewById(R.id.info_Btn);
        infoBtn.setOnClickListener(this);

        titelView = (TextView)findViewById(R.id.titel_View);
        titelView.setText(songList.get(0).getTitle());
        interpretView = (TextView)findViewById(R.id.int_View);
        interpretView.setText(songList.get(0).getTitle());
        albumView = (TextView)findViewById(R.id.album_View);
        albumView.setText(songList.get(0).getAlbum());

        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                seekTo(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        setController();
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                musicSrv.setShuffle();
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    */


    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        setText();
        setTimeBar();
        playBtn.setImageResource(R.drawable.pause);
        paused = !paused;
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        //controller.show(0);
    }

    private void setController(){
        controller = new MusicController(this);

        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }


    //play next
    private void playNext(){
        musicSrv.playNext();
        setText();
        setTimeBar();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        //controller.show(0);
    }

    //play previous
    private void playPrev(){
        musicSrv.playPrev();
        setText();
        setTimeBar();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
    }

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColum = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ALBUM);
            int lengthColum = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                int length = musicCursor.getInt(lengthColum);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColum);
                songList.add(new Song(thisId, thisTitle, thisArtist, thisAlbum, length));
            }
            while (musicCursor.moveToNext());
        }
    }


    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
        return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_Btn: {
                if (pref) {
                    View songView = findViewById(R.id.song_list);
                    View genreView = findViewById(R.id.genre_spinner);
                    songView.setVisibility(View.VISIBLE);
                    genreView.setVisibility(View.INVISIBLE);
                    pref = !pref;
                }
                break;
            }

            case R.id.pref_Btn: {
                if (!pref) {
                    View songView = findViewById(R.id.song_list);
                    View genreView = findViewById(R.id.genre_spinner);
                    songView.setVisibility(View.INVISIBLE);
                    genreView.setVisibility(View.VISIBLE);
                    pref = !pref;
                }
                break;
            }

            case R.id.play_Btn: {
                if (paused) {
                    musicSrv.playSong();
                    setText();
                    setTimeBar();
                    playBtn.setImageResource(R.drawable.pause);
                    paused = !paused;
                } else {
                    musicSrv.pausePlayer();
                    playBtn.setImageResource(R.drawable.play);
                    paused = !paused;
                }
                break;
            }

            case R.id.shuffle_Btn: {
                musicSrv.setShuffle();
                if (shuffle)
                    shuffleBtn.setImageResource(R.drawable.shuffle_not);
                else
                    shuffleBtn.setImageResource(R.drawable.shuffle);
                shuffle = !shuffle;
                break;
            }

            case R.id.repeat_Btn: {
                musicSrv.setLooping();
                if (loop)
                    repeatBtn.setImageResource(R.drawable.repeat_not);
                else
                    repeatBtn.setImageResource(R.drawable.repeat);
                loop = !loop;
                break;
            }

            case R.id.skipf_Btn: {
                playNext();
                playBtn.setImageResource(R.drawable.pause);
                paused = !paused;
                break;
            }


            case R.id.skipb_Btn: {
                playPrev();
                playBtn.setImageResource(R.drawable.pause);
                paused = !paused;
                break;
            }

        }
    }

    public void setText(){
        titelView.setText(songList.get(musicSrv.getSongPosn()).getTitle());
        interpretView.setText(songList.get(musicSrv.getSongPosn()).getArtist());
        albumView.setText(songList.get(musicSrv.getSongPosn()).getAlbum());

    }

    public void setTimeBar(){
        timeBar.setMax(songList.get(musicSrv.getSongPosn()).getLength());
        timeBar.setProgress(0);
    }
}
