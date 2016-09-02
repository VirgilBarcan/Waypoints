package com.moregoldbars.waypoints;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, WaypointInterface {

    private GoogleMap mMap;

    private ListView fromToWaypointsListView;
    private FromToWaypointsAdapter adapter;
    private List<String> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initViews();
    }

    private void initViews() {
        fromToWaypointsListView = (ListView) findViewById(R.id.from_to_waypoints_list_view);

        points = new ArrayList<>();
        points.add("START");
        points.add("WAYPOINT");
        points.add("FINISH");
        adapter = new FromToWaypointsAdapter(this, points);
        adapter.setListenerInterface(this);
        fromToWaypointsListView.setAdapter(adapter);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private int count = 0;

    @Override
    public void onButtonClicked(int position, int type) {
        System.out.println("MapsActivity.onButtonClicked: " + position + " " + type);
        switch (type) {
            case FromToWaypointsAdapter.TYPE_FROM:
                //TODO: Add new waypoint
                System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                points.add(points.size() - 1, "NEW WAYPOINT" + count);
                System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                ++count;
                break;
            case FromToWaypointsAdapter.TYPE_WAYPOINT:
                //TODO: Remove the waypoint
                if (points.size() > 2) {
                    System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                    points.remove(position);
                    System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                }
                else {
                    //TODO: Show message that the destination can't be deleted
                }
                break;
            case FromToWaypointsAdapter.TYPE_TO:
                //TODO: Remove the TO point and set the last waypoint as the current TO point
                if (points.size() > 2) {
                    System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                    points.remove(points.size() - 1);
                    System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
                }
                else {
                    //TODO: Show message that the destination can't be deleted
                }
                break;
            default:
                System.out.println("ERROR: Unknown TYPE");
        }

        adapter = new FromToWaypointsAdapter(this, points);
        adapter.setListenerInterface(this);
//        adapter.notifyDataSetChanged();
        fromToWaypointsListView.setAdapter(adapter);
    }

    @Override
    public void onTextInput(int position, String userInput) {
        System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
        points.set(position, userInput);
        adapter.notifyDataSetChanged();
        System.out.println("MapsActivity.onButtonClicked: " + Arrays.toString(points.toArray()));
    }
}
