package com.alarcon.proyecto2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Button btnOrdenar;
    VideoView videoMuestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOrdenar = (Button) findViewById(R.id.btn1);
        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PedidoActivity.class));
            }
        });

        videoMuestra = (VideoView) findViewById(R.id.videoMuestra);
        videoMuestra.setMediaController(new MediaController(this));
        videoMuestra.setVideoURI(Uri.parse("https://ak2.picdn.net/shutterstock/videos/26978842/preview/stock-footage-close-up-of-woman-puts-cheese-on-the-dough-while-making-pizza-in-the-kitchen.mp4"));
        videoMuestra.start();

    }
}
