package edu.neu.madscourse.tennismateandcourt;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapViewModel mViewModel;
    GoogleMap map;

    private RecyclerView recyclerView;
    private TennisCourtAdapter adapter;
    private List<TennisCourtModel> listTennisCourts = new ArrayList();

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        // display map view first:
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
                LatLng temp_court;
                for(int i =0; i <listTennisCourts.size();i++ ){
                    temp_court = new LatLng(listTennisCourts.get(i).getLatitudes(), listTennisCourts.get(i).getLongitudes());
                    map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(0)).position(temp_court).title(listTennisCourts.get(i).getName()));
                    map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(120)).position(temp_court).title(listTennisCourts.get(i).getName()));
                    moveMapToLocation(listTennisCourts.get(i));
                }

            }

            private void moveMapToLocation(TennisCourtModel a_tennis_court)
            {
                LatLng court_lat_lng;
                court_lat_lng = new LatLng(a_tennis_court.getLatitudes(), a_tennis_court.getLongitudes());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(court_lat_lng,15));
                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                map.animateCamera(CameraUpdateFactory.zoomTo(11), 3000, null);
            }
        });


        // Display recycelr tennis court list view & hardcode some test data
        recyclerView = rootView.findViewById(R.id.photos_recyclerview);
        TennisCourtModel court1= new TennisCourtModel(1,"NEU Boston Tennis Court",4.5,42.33962289703249,-71.08975307364507,"addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court2= new TennisCourtModel(2,"Second Tennis Court",4.5,42.34962289704246,-71.07975307364508,"addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court3= new TennisCourtModel(3,"333 Tennis Court",4.5,42.33942289703345,-71.06975307362505,"addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court4= new TennisCourtModel(4,"444 Tennis Court",4.5,42.32962289703444,-71.05975307363503,"addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        TennisCourtModel court5= new TennisCourtModel(5,"555 Tennis Court",4.5,42.31962289703143,-71.04975307363502,"addres1111", "10AM-9PM","www.tennisss.com","762-301-231","2021-01-30");
        listTennisCourts.add(court1);
        listTennisCourts.add(court2);
        listTennisCourts.add(court3);
        listTennisCourts.add(court4);
        listTennisCourts.add(court5);
        adapter = new TennisCourtAdapter(this.getContext(), listTennisCourts, mapFragment);
        Log.d("Check court list: ", "test111" + listTennisCourts.get(0).getName());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

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