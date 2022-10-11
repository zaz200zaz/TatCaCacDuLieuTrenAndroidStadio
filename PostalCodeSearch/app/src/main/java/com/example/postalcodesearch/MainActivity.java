package com.example.postalcodesearch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Location location;
    Locale locale;
    double describeContents;
    List<Address> addresses;
    Geocoder geocoder,iGeocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        locale = new Locale("JAPANESE","JAPAN");
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        geocoder= new Geocoder(this,locale);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                        Address address = addresses.get(0);
                        textView.setText("" + address.getPostalCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
            Address address = addresses.get(0);
            for(Address address1: addresses){

//                if (address1.getPostalCode().equals("252-0324")){
                    textView.setText("" + address1.getCountryName()+address1.getPostalCode()+
                            address1.getAdminArea()
                            +address1.getLocality()
                            +address1.getThoroughfare());
//                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Address getAdress(String aUserInput) throws IOException {

        List<Address> tAddressList = iGeocoder.getFromLocationName(aUserInput, 1000, 47.060940, 8.564278, 51.526396, 13.736392);
        if(tAddressList != null &&
                tAddressList.size() > 0) {
            for(Address tAddress : tAddressList) {

                // return the first adress found for germany.
                Log.e("TAG", "returning Adress: " + tAddress );
                if(tAddress.getCountryCode().equals("JAPAN")) {

                    return tAddress;
                }

            }
            return null;
        } else {
            return null;
        }
    }

}