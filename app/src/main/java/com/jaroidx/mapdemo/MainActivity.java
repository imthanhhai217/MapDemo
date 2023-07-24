package com.jaroidx.mapdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView rvListCity;
    @BindView(R.id.motionLayout)
    CustomMotionLayout motionLayout;
    @BindView(R.id.clAnchorView)
    ConstraintLayout clAnchorView;
    @BindView(R.id.mapView)
    MapView mMapView;
    private GoogleMap mGoogleMap;

    CityAdapter mCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); //needed to get the map to display immediately
        MapsInitializer.initialize(this);
        mMapView.getMapAsync(this);
        mCityAdapter = new CityAdapter(getFakeData());
        rvListCity.setAdapter(mCityAdapter);

        motionLayout.setAnchorView(clAnchorView);
        motionLayout.setRecyclerView(rvListCity);

    }

    private ArrayList<String> getFakeData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("New city " + (i + 1));
        }
        return data;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);

        changePositionCompass();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    private void changePositionCompass() {
        new Handler(Looper.getMainLooper()).post(() -> {
            View viewCompass = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            int positionWidth = viewCompass.getLayoutParams().width;
            int positionHeight = viewCompass.getLayoutParams().height;
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(positionWidth, positionHeight);
            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            rlp.setMargins(0, DeviceUtils.getDisplayScale(this, 50), DeviceUtils.getDisplayScale(this, 8), 0);
            viewCompass.setLayoutParams(rlp);
        });
    }
}