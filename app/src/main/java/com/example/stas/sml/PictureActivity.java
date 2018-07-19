package com.example.stas.sml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stas.sml.model.VenueDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureActivity extends AppCompatActivity {

    private Api serverApi = RetrofitClient.getInstance().getApi();
    String url;
    static final String VENUE_ID = "venueId";
    @BindView(R.id.placeImage) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String venueId = intent.getStringExtra(VENUE_ID);
        Toast.makeText(this, "venueId", Toast.LENGTH_SHORT).show();

        Call<VenueDetailsResponse> venuePhotos = serverApi.getVenue(venueId);

        venuePhotos.enqueue(new Callback<VenueDetailsResponse>() {
            @Override
            public void onResponse(Call<VenueDetailsResponse> call, Response<VenueDetailsResponse> response) {
                if (response.isSuccessful()) {
                    url = response.body().getVenueDto().getVenue().getBestPhoto().getPrefix() + "400x400" +
                            response.body().getVenueDto().getVenue().getBestPhoto().getSuffix();

                    GlideApp.with(PictureActivity.this)
                            .load(url)
                            .centerCrop()
                            .into(imageView);
                    Log.d("FourSquare: ", url);
                }
            }
            @Override
            public void onFailure(Call<VenueDetailsResponse> call, Throwable t) {
                Log.d("FourSquare: ", "Internet lost");
               t.printStackTrace();
            }
        });

    }
}
