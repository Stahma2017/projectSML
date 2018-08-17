package com.example.stas.sml.data.repository;

import android.Manifest;
import android.location.Location;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;


public class LocationRepository implements LocationGateway {

    @Inject
    LocationRepository(){

    }

    @Override
    public Single<Location> getCurrentLocation() {
        //RxPermissions rxPermissions = null;
        // return rxPermissions.request(Manifest.permission.CAMERA)
        //         .map(aBoolean -> {
        //             if(aBoolean){
        //                 return new Location("Lol");
        //             }else{
        //                 throw new Exception("Not Granted");
        //             }
        //         }).singleOrError();
        return null;
    }

    /* private LocationManager locationManager;
    private Context context;
    private RxPermissions rxPermissions;
    private Location location;


    @Inject
    public LocationRepository(RxPermissions rxPermissions, LocationManager locationManager, Context context){
        this.rxPermissions = rxPermissions;
        this.locationManager = locationManager;
        this.context = context;
    }

    Integer state;

    @Override
    public Single<Location> getCurrentLocationIfGranted() {

        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Permission>() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){
                            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            String provider = locationManager.getBestProvider(criteria, true);
                            location = locationManager.getLastKnownLocation(provider);
                        } else if (permission.shouldShowRequestPermissionRationale){
                            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                                                Criteria criteria = new Criteria();
                                                String provider = locationManager.getBestProvider(criteria, true);
                                                location = locationManager.getLastKnownLocation(provider);
                                            }else {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                                intent.setData(uri);
                                                if (intent.resolveActivity(context.getPackageManager()) != null)
                                                    context.startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
        return Single.just(location);
    }

//    Location location = locationManager.getLastKnownLocation(provider);



//    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//                            intent.setData(uri);
//                            if (intent.resolveActivity(context.getPackageManager()) != null)
//                                    context.startActivity(intent);

    *//*private void ckeckPermission(String permission){

            rxPermissions.requestEach(permission)
                    .subscribe(permission1 -> {
                        if (permission1.granted) {
                           state = 0;
                        } else if (permission1.shouldShowRequestPermissionRationale){
                            state = 1;
                        } else {
                            state = 2;
                        }
                    });
    };
*/

}
