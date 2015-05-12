package com.example.mmbuw.hellomaps;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener ,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    boolean mClick;
    private static final LatLng Brazil = new LatLng(10.00,55.00);
    private static final LatLng Paris = new LatLng(48.50,02.20);
    private static final LatLng Barcelona = new LatLng(41.3887900,02.1589900);
    private static final LatLng Manchester = new LatLng(53.4809500,-2.374300);
    private EditText texxt;
    private String textt;
    private Set<String> marker_s;
    private Set<String> markerr_s;
    private double i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        texxt = (EditText)findViewById(R.id.locationn);

        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();

            }
        }
    }



    private void setUpMap() {
        customMarkers();

        mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().
        Location myLocation = mMap.getMyLocation();

        if (myLocation != null) {
            LatLng curr_pos = new LatLng(myLocation.getLatitude(),
                    myLocation.getLongitude());
            Marker currpos = mMap.addMarker(new MarkerOptions().position(curr_pos).title("Current Position"));
            currpos.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            currpos.showInfoWindow();
        }

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);


    }
    /*@Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }*/
    public void customMarkers(){
        Marker pos = mMap.addMarker(new MarkerOptions().position(new LatLng(50.98,11.32)).title("Weimar").snippet("BAuhaus Unversity"));
        Marker poss = mMap.addMarker(new MarkerOptions().position(new LatLng(50.1155200,8.6841700)).title("FRankfurt").snippet("Frankfurt University"));
        //pos.showInfoWindow();
        //poss.showInfoWindow();
        Marker poss2 = mMap.addMarker(new MarkerOptions().position(Barcelona).title("BARCELONA").snippet(" University"));
        Marker poss22 = mMap.addMarker(new MarkerOptions().position(Manchester).title("MANCHESTER").snippet(" Manchester United"));
        Marker poss222 = mMap.addMarker(new MarkerOptions().position(Paris).title("PARIS").snippet(" Effiel Tower"));
    }
    @Override
    public void onMapLongClick(LatLng point) {

        textt = texxt.getText().toString();
        Marker clicke = mMap.addMarker(new MarkerOptions().position(point).title(texxt.getText().toString()).snippet(textt));
        markerSets(clicke);
        markerSSets(clicke);
        //circle_drawing( markerr_s);
        texxt.setText("");
    }
    @Override
    public boolean onMarkerClick (Marker marker){
         marker.showInfoWindow();
         LatLng position = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
         mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
         mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
         circleDraw(marker);
        return true;
    }


    public void markerSets (Marker marker) {
        SharedPreferences share = getSharedPreferences("marker", MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();marker_s = new HashSet<String>();
        marker_s.add(String.valueOf("title_" + marker.getTitle()));marker_s.add(String.valueOf("latti_" + marker.getPosition().latitude));
        marker_s.add(String.valueOf("longi_" + marker.getPosition().longitude));

        editor.putStringSet(String.valueOf(i),marker_s);
        editor.commit();
        i++;

    }
    public void markerSSets (Marker marker) {
        SharedPreferences shared = getSharedPreferences("marker", MODE_PRIVATE);
        SharedPreferences.Editor editors = shared.edit();markerr_s = new HashSet<String>();
        markerr_s.add(String.valueOf( marker.getPosition().latitude));
        markerr_s.add(String.valueOf( marker.getPosition().longitude));

        editors.putStringSet(String.valueOf(i),markerr_s);
        editors.commit();
        i++;
        //circleDrawing( markerr_s);

    }
    public void circleDrawing(Set<String> markerr_s)
    {
        /*CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(37.4, -122.1))
                .radius(1000));
      Circle circle = mMap.addCircle(circleOptions);*/
        for (String s:markerr_s){
            System.out.println(s);
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(Double.parseDouble(s),Double.parseDouble(s)))
                    .radius(1000);
            Circle circle = mMap.addCircle(circleOptions);
        }
    }
    public void circleDraw(Marker marker){
        CircleOptions circleOptions = new CircleOptions()
                .center(marker.getPosition())
                .radius(2000);
        Circle circle = mMap.addCircle(circleOptions);
    }
}

