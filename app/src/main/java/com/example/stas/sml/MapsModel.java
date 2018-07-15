package com.example.stas.sml;

import android.util.Log;
import com.example.stas.sml.Model.PlaceResponce;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsModel implements MapsContract.Model{

    private Api serverApi = RetrofitClient.getInstance().getApi();

    @Override
    public void loadVenueId(String latLng, LoadVenueIdCallback callback){
        Call<PlaceResponce> searches = serverApi.search(latLng,
                10000.0);
        searches.enqueue(new Callback<PlaceResponce>() {
            @Override
            public void onResponse(Call<PlaceResponce> call, Response<PlaceResponce> response) {
                if (response.isSuccessful()) {
                    String venueId = response.body().getResponse().getVenues().get(0).getId();
                    callback.onLoad(venueId);
                }
                else{
                    Log.d("id", "Unsuccessful response");
                }
            }
            @Override
            public void onFailure(Call<PlaceResponce> call, Throwable t) {
                if(t instanceof IOException){
                    t.getStackTrace();
                }
            }
        });
    }
    interface LoadVenueIdCallback {
        void onLoad(String venueId);
    }
}
