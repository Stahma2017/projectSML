package com.example.stas.sml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PictureReceiverActivity extends AppCompatActivity {

    public static final String KEY_PICTURE_URL = "uri_key";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_receiver);
        imageView = (ImageView) findViewById(R.id.placeImage);

        Bundle b = getIntent().getExtras();
        String url = b.getString(KEY_PICTURE_URL);

        Glide.with(this)
                .load(url)
                .into(imageView);

    }
}
