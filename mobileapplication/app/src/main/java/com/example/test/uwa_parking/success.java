package com.example.test.uwa_parking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class success extends Activity {

    Location global_location = null;
    Button button_in = null;
    Button button_out = null;
    Button button_show = null;
    Button button_gps = null;
    TextView park_result = null;
    OutputStream outputStream = null;
    Bundle bundle;
    String username;
    String read = "";
    String parkinglots_name;
    String parking_status = "out";

    private Socket socket;
    private ExecutorService mThreadPool;
    private TextView lat;
    private TextView lon;
    private LocationManager locationManager;
    private String locationProvider;
    static Handler mMainHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);

        // use intent to transfer the username from login page to the main page
        bundle = getIntent().getExtras();
        username = bundle.getString("username");

        button_in = (Button) findViewById(R.id.button_in);
        button_out = (Button) findViewById(R.id.button_out);
        button_show = (Button) findViewById(R.id.button_show);
        park_result = (TextView)findViewById(R.id.park_result);
        button_gps = (Button)findViewById(R.id.button_gps);

        mThreadPool = Executors.newCachedThreadPool();

        lat = (TextView) findViewById(R.id.ed1);
        lon = (TextView) findViewById(R.id.ed2);
        // get the GPS data
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle arg2) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
            @Override
            public void onLocationChanged(Location location) {
                location.getAccuracy();
                global_location = location;
            }
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        final List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "Please open the location service", Toast.LENGTH_SHORT).show();
        }

        final Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            global_location = location;
        }
        locationManager.requestLocationUpdates(locationProvider, 2000, 0, locationListener);
        // handler to show the feed back message from server
        mMainHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (read.charAt(1) == 'f')
                        {
                            read = "";
                            park_result.setText("unexceptted mistake happen");
                        }
                        else if (read.charAt(1) == 'p')
                        {
                            park_result.setText("successful");
                        }
                        break;
                }
            }
        };
        // press "show location" to see the current GPS on the page
        button_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocation(location);
            }
        });

        button_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                park_result.setText("");
                double a = global_location.getLatitude();
                double b = global_location.getLongitude();

                compare(b,a);   // compare the current GPS location with parking lots coordinates

                if (parkinglots_name.equals("0"))
                {
                    park_result.setText("you are not in parking lots");
                }
                else if (parking_status.equals("in"))
                {
                    park_result.setText("Don't press the button repeatedly");
                }
                else
                {
                    parking_status = "in";  // parking_status is a flag to avoid repeated pressing "in" and "out" button
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket("106.14.213.85", 9999);
                                outputStream = socket.getOutputStream();
                                DataOutputStream writer = new DataOutputStream(outputStream);
                                InputStream is = socket.getInputStream();
                                DataInputStream dis = new DataInputStream(is);
                                String temp = 'i' + username + '/' + parkinglots_name + '/';
                                writer.writeUTF(temp);
                                read = dis.readUTF();
                                if (read.charAt(0) == 'i') {
                                    Message msg = Message.obtain();
                                    msg.what = 0;
                                    mMainHandler.sendMessage(msg);
                                }
                                dis.close();
                                is.close();
                                writer.close();
                                outputStream.close();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });


        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                park_result.setText("");
                double a = global_location.getLatitude();
                double b = global_location.getLongitude();
                compare(b,a);
                if (parkinglots_name.equals("0"))
                {
                    park_result.setText("you are not in parking lots");
                }
                else if (parking_status.equals("out"))
                {
                    park_result.setText("Don't press the button repeatedly");
                }
                else {
                    parking_status = "out";
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket("106.14.213.85", 9999);
                                outputStream = socket.getOutputStream();
                                DataOutputStream writer = new DataOutputStream(outputStream);
                                InputStream is = socket.getInputStream();
                                DataInputStream dis = new DataInputStream(is);
                                String temp = 'o' + username + '/' + parkinglots_name + '/';
                                writer.writeUTF(temp);
                                read = dis.readUTF();
                                if (read.charAt(0) == 'o')
                                {
                                    Message msg = Message.obtain();
                                    msg.what = 0;
                                    mMainHandler.sendMessage(msg);
                                }
                                dis.close();
                                is.close();
                                writer.close();
                                outputStream.close();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        // if the user press "show map", turn to another activity to open the map
        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                park_result.setText("");
                Intent sucess_to_map = new Intent(success.this,map.class);
                startActivity(sucess_to_map);
            }
        });
    }

    void compare(double lng, double lat)
    {
        if ( lat<=(-31.979192) && lat>=(-31.978872) && lng<=(115.820110) && lng>=(115.818399)){
            //System.out.println("you are now in Reid_park, send 6.0");
            parkinglots_name = "Reid_park";
        }else if(lat>=(-31.981313) && lat<=(-31.979265) && lng<=(115.820231) && lng>=(115.820051)){
            //System.out.println("you are now in Oaklawn_park, send 5.0");
            parkinglots_name = "Oaklawn_park";
        }else if(lat>=(-31.985531) && lat<=(-31.985147) && lng<=(115.821585) && lng>=(115.820168)){
            //System.out.println("you are now in Business_park, send 4.0");
            parkinglots_name = "Business_park";
        }else if(lat>=(-31.977451) && lat<=(-31.977131) && lng<=(115.825889) && lng>=(115.816277)){
            //System.out.println("you are now in Csse_park, send 3.0");
            parkinglots_name = "Csse_park";
        }else if(lat>=(-31.976332) && lat<=(-31.975558) && lng<=(115.820026) && lng>=(115.818764)){
            //System.out.println("you are now in Recreation_park, send 2.0");
            parkinglots_name = "Recreation_park";
        }else if(lat>=(-31.981828) && lat<=(-31.981022) && lng<=(115.818034) && lng>=(115.817325)){
            //System.out.println("you are now in ECM_park, send 1.0");
            parkinglots_name = "ECM_park";
        }else{
            //System.out.println("you are not in fencing area, send 0.0");
            parkinglots_name = "0";
        }
    }

    private void showLocation(Location location)
    {
        lat.setText(String.valueOf(location.getLatitude()));
        lon.setText(String.valueOf(location.getLongitude()));
    }
}
