package edu.neu.madscourse.tennismateandcourt;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel{
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
    // TODO: Implement the ViewModel

//    GoogleMap map;
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_map);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap){
//        map = googleMap;
//        LatLng NEUBoston = new LatLng(42.33962289703249, -71.08975307364507);
//        map.addMarker(new MarkerOptions().position(NEUBoston).title("NEU Boston"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(NEUBoston));
//    }
}