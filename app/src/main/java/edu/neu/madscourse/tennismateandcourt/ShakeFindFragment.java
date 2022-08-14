package edu.neu.madscourse.tennismateandcourt;





import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;


import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import android.content.res.Resources;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ShakeFindFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ShakeFindFragment extends Fragment {
    private SensorManager sensorManager = null;
    private Vibrator vibrator = null;
    private static final String TAG = "MainActivity";
    private static final int SENSOR_SHAKE = 10;





//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ShakeFindFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ShakeFindFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ShakeFindFragment newInstance(String param1, String param2) {
//        ShakeFindFragment fragment = new ShakeFindFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shake_find,container,false);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
//        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
//        Resources res = getResources();
//        String []turn = res.getStringArray(R.array.turn);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onStop() {

        super.onStop();
        if(sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            int value = 15;
            if (Math.abs(x) > value || Math.abs(y) > value || Math.abs(z) > value) {
                vibrator.vibrate(300);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Resources res = getResources();
            String []turn = res.getStringArray(R.array.turn);
            if (msg.what == SENSOR_SHAKE) {
                Toast.makeText(getActivity(), turn[(int)(Math.random()*2)], Toast.LENGTH_SHORT).show();
            }
        }
    };







}