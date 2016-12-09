package edu.hutech.shippermanager.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.utils.LocationUtils;

import static edu.hutech.shippermanager.common.Config.GOOGLE_MAP_API_KEY;


public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DirectionCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    //    private List<Marker> originMarkers = new ArrayList<>();
//    private List<Marker> destinationMarkers = new ArrayList<>();
//    private List<Polyline> polylinePaths = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationManager locationManager;
    //private String currentAddress;
    private boolean isDirectEnable = false;
    private LatLng directLatLng;
    private String directAddress;
    private LatLng getCurrentLatLng;
    private LatLng l;

    @BindView(R.id.editTextAddress)
    EditText edtAddress;
    @BindView(R.id.textViewDistance)
    TextView tvDistance;

    @OnClick(R.id.buttonFindPath)
    public void FindPath(View view) {
        //sendRequest();
        directLatLng = LocationUtils.getLocationFromAddress(getContext(),edtAddress.getText().toString());
        L.Toast(directLatLng.toString());
        if (mMap != null)
            mMap.clear();
        googleDirection(directLatLng);
    }

    public void googleDirection(LatLng latLng) {
        GoogleDirection.withServerKey(GOOGLE_MAP_API_KEY)
                .from(getCurrentLatLng)
                .to(latLng)
                .avoid(AvoidType.HIGHWAYS)
                .transitMode(TransportMode.BICYCLING)
                .alternativeRoute(true)
                .language(Language.VIETNAMESE)
                .execute(this);
    } //Tìm đường dùng thư viện

//    private void sendRequest() {
//        String address = edtAddress.getText().toString();
//        if (address.isEmpty()) {
//            L.Toast("Vui lòng nhập địa điểm đến!");
//            return;
//        }
//        try {
//            new DirectionFinder(getActivity(), this, currentAddress, address).execute();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(double lat, double lng, String address) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        bundle.putString("address", address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        setupLocationButton();

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            double lat = bundle.getDouble("lat");
            double lng = bundle.getDouble("lng");
            directAddress = bundle.getString("address");
            isDirectEnable = true;
            directLatLng = new LatLng(lat, lng);
        }

    }

    private void setupLocationButton() {
        View mapView = mapFragment.getView();
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();
        Location location = LocationUtils.getLastKnownLoaction(false, getActivity());
        if (location != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.getLatLng(location), 18));
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.getLatLng(location), 18));
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);

    }
//
//    @Override
//    public void onDirectionFinderStart() {
//
//
//        if (originMarkers != null) {
//            for (Marker marker : originMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (destinationMarkers != null) {
//            for (Marker marker : destinationMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (polylinePaths != null) {
//            for (Polyline polyline : polylinePaths) {
//                polyline.remove();
//            }
//        }
//    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

//    @Override
//    public void onDirectionFinderSuccess(List<Routes> routes) {
//        polylinePaths = new ArrayList<>();
//        originMarkers = new ArrayList<>();
//        destinationMarkers = new ArrayList<>();
//        for (Routes route : routes) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
//            tvDistance.setText(route.distance.text);
//            originMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .title(route.startAddress)
//                    .position(route.startLocation)));
//            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .title(route.endAddress)
//                    .position(route.endLocation)));
//            PolylineOptions polylineOptions = new PolylineOptions().
//                    geodesic(true).
//                    color(Color.BLUE).
//                    width(10);
//            for (int i = 0; i < route.points.size(); i++)
//                polylineOptions.add(route.points.get(i));
//            polylinePaths.add(mMap.addPolyline(polylineOptions));
//        }
//    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        L.Toast(connectionResult.getErrorMessage());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.getLatLng(mLastLocation), 18));
        }
    }

//    public void directMap(double lat, double lng) {
//        String noiden = Double.toString(lat) + "," + Double.toString(lng);
//        //L.Toast(currentAddress);
//        try {
//            new DirectionFinder(getActivity(), this, currentAddress, noiden).execute();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onConnectionSuspended(int i) {
        L.Toast(i + "");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //currentAddress = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
            getCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (isDirectEnable) {
                edtAddress.setText(directAddress);
                //directMap(directLatLng.latitude, directLatLng.longitude);
                googleDirection(directLatLng);
                isDirectEnable = false;
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            Leg leg = route.getLegList().get(0);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.cycling)).title(leg.getStartAddress().toString()).position(getCurrentLatLng));
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.townhouse)).title(leg.getEndAddress().toString()).position(directLatLng));

            tvDistance.setText("Khoảng cách: " + leg.getDistance().getText());
            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
            PolylineOptions polylineOptions = DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED);
            mMap.addPolyline(polylineOptions);
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
}
