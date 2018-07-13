package com.example.stas.sml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stas.sml.VenueDetailedModel.VenueDetailed;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureActivity extends AppCompatActivity {

    ImageView imageView;
    private Api serverApi = RetrofitClient.getInstance().getApi();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_receiver);
        imageView = findViewById(R.id.placeImage);

        Intent intent = getIntent();
        String venueId = intent.getStringExtra("VENUE_id");
        Toast.makeText(this, venueId, Toast.LENGTH_SHORT).show();

        Call<VenueDetailed> venuePhotos = serverApi.getVenue(venueId);


        venuePhotos.enqueue(new Callback<VenueDetailed>() {
            @Override
            public void onResponse(Call<VenueDetailed> call, Response<VenueDetailed> response) {
                if (response.isSuccessful()) {
                    url = response.body().getResponse().getVenue().getPage().getUser().getPhoto().getPrefix() +
                            response.body().getResponse().getVenue().getPage().getUser().getPhoto().getSuffix();
                    Glide.with(PictureActivity.this)
                            .load(url)
                            .into(imageView);
                    Log.d("FourSquare: ", url);
                }
            }
            @Override
            public void onFailure(Call<VenueDetailed> call, Throwable t) {
                Log.d("FourSquare: ", "Internet lost");
                if(t instanceof IOException){
                    t.getStackTrace();
                }
            }
        });

    }
}
