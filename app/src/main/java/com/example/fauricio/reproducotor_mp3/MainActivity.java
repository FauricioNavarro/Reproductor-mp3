package com.example.fauricio.reproducotor_mp3;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Item> ArrayItem = null;
    private listViewAdapter adapter;
    private Button play_button;
    private SeekBar seekBarVolumen,seekBarAvance;
    private ImageView letra;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private int cancion_actual=0;
    private int maxVolume,currentVolume,duration,progress;
    private boolean estado = true;
    private int[] play_list = {R.raw.everlong,R.raw.saint_cecilia,R.raw.drive,R.raw.whiskey_in_the_jar,
    R.raw.even_flow,R.raw.yellow_ledbetter,R.raw.black,R.raw.snow,R.raw.dont_let_me_down,R.raw.wild_horses};
    private int[] lista_letra= {R.drawable.everlong_lyrics,R.drawable.saint_cecilia,R.drawable.drive,R.drawable.whiskey_in_the_jar,
    R.drawable.even_flow,R.drawable.yellow,R.drawable.black,R.drawable.snow,R.drawable.dont_let_me_down,R.drawable.wild_horses};
    private int[] image_size = {1050,1050,1050,1800,1200,1200,1900,1900,1500,1500};
    private float[] image_time = {-1020f,-1020f,-1420f,-1500f,-1050f,-1050f,-2400f,-2000f,-1500f,-1500f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView)findViewById(R.id.LV_items);
        play_button = (Button) findViewById(R.id.play_button);
        seekBarVolumen = (SeekBar) findViewById(R.id.seekBarvolumen);
        seekBarAvance = (SeekBar) findViewById(R.id.seekBaravance);
        letra = findViewById(R.id.letra);
        int[] location = new int[2];
        letra.getLocationOnScreen(location);
        //letra.set
        Log.i("x:",String.valueOf(location[0]));
        Log.i("y:",String.valueOf(location[1]));letra.setImageResource(lista_letra[0]);


        letra.setX(0f);
        letra.setY(200f);

        ArrayItem = new ArrayList<>();
        cargarLista(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[cancion_actual]);
        audioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBarVolumen.setMax(maxVolume);
        seekBarVolumen.setProgress(currentVolume);

        seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                Log.d("volume:", Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        duration = mediaPlayer.getDuration();
        progress = mediaPlayer.getCurrentPosition();
        seekBarAvance.setMax(duration);
        seekBarAvance.setProgress(progress);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                seekBarAvance.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        seekBarAvance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                mediaPlayer.seekTo(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Onclick del listview de canciones a reproducir
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* if(estado){ } */
                mediaPlayer.stop();
                cancion_actual = i;
                mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[cancion_actual]);
                //letra.setScaleY(1.0f);
                //Log.i("eje y: ",String.valueOf(letra.getLocationInWindow()));
                int[] location = new int[2];
                letra.getLocationOnScreen(location);
                //letra.set
                Log.i("x:",String.valueOf(location[0]));
                Log.i("y:",String.valueOf(location[1]));
                letra.setImageResource(lista_letra[i]);

                letra.setX(0f);
                letra.setY(200f);

                letra.getLayoutParams().height = image_size[i];
                letra.animate()
                        .translationYBy(image_time[i])
                        .setDuration(duration);

                mediaPlayer.start();
                estado = false;
                play_button.setBackgroundResource(R.drawable.pause);
            }
        });

        /* Ejecucion del movimiento de la letra

        letra.animate()
                .translationYBy(image_time[])
                .setDuration(duration);
        */
    }

    public void cargarLista(Context context){
        ArrayItem.add(new Item(R.drawable.foo_fighters,"Everlong","Foo Fighters"));
        ArrayItem.add(new Item(R.drawable.foo_fighters,"Saint Cecilia","Foo Fighters"));
        ArrayItem.add(new Item(R.drawable.incubus,"Drive","Incubus"));
        ArrayItem.add(new Item(R.drawable.metallica,"Whiskey In The Jar","Metallica"));
        ArrayItem.add(new Item(R.drawable.pearl_jam,"Even Flow","Pearl Jam"));
        ArrayItem.add(new Item(R.drawable.pearl_jam,"Yellow Ledbetter","Pearl Jam"));
        ArrayItem.add(new Item(R.drawable.pearl_jam,"Black","Pearl Jam"));
        ArrayItem.add(new Item(R.drawable.red_hot,"Snow","Red Hot Chili Peppers"));
        ArrayItem.add(new Item(R.drawable.the_beatles,"Don't Let Me Down","The Beatles"));
        ArrayItem.add(new Item(R.drawable.rolling_stones,"Wild Horses","The Rolling Stones"));
        adapter = new listViewAdapter(ArrayItem, context);
        lista.setAdapter(adapter);
    }

    public void play_click(View view){
        if(estado){
            mediaPlayer.start();
            //letra.setScaleY(1.0f);
            letra.animate().translationY(1.0f);
            letra.getLayoutParams().height = image_size[cancion_actual];
            letra.setImageResource(lista_letra[cancion_actual]);
            letra.animate()
                    .translationYBy(image_time[cancion_actual])
                    .setDuration(duration);
            estado = false;
            play_button.setBackgroundResource(R.drawable.pause);
        }else{
            mediaPlayer.pause();
            estado = true;
            play_button.setBackgroundResource(R.drawable.play);
        }
    }

    public void next_click(View view){
        mediaPlayer.stop();
        if(cancion_actual==9){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[0]);
            cancion_actual = 0;
        }else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[cancion_actual+1]);
            cancion_actual += 1;
        }
        mediaPlayer.start();
        estado = false;
        play_button.setBackgroundResource(R.drawable.pause);
    }

    public void previous_click(View view){
        mediaPlayer.stop();
        if(cancion_actual==0){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[9]);
            cancion_actual = 9;
        }else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), play_list[cancion_actual-1]);
            cancion_actual -= 1;
        }
        mediaPlayer.start();
        estado = false;
        play_button.setBackgroundResource(R.drawable.pause);
    }
}