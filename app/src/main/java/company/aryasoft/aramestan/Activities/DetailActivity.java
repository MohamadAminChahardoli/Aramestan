package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.DetailsCallBackImpl;
import company.aryasoft.aramestan.Models.DetailDeceasedApiModel;
import company.aryasoft.aramestan.R;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity
        implements Button.OnClickListener, DetailsCallBackImpl.OnDetailsReceivedListener, LocationListener {

    private Button ButtonShowLocation;
    private Criteria criteria;
    private LocationManager locationManager;
    private String Provider;
    double personLatitude = 0;
    double personLongitude = 0;
    double deceasedLatitude = 0;
    double deceasedLongitude = 0;
    private onPermissionVerifiedListener permissionVerifiedListener;
    private Bundle Deceased;
    private CircleImageView ImgDeceasedPhoto;
    private TextView TxtTitleFullName;
    private TextView TxtFatherName;
    private TextView TxtBirthDate;
    private TextView TxtDeadDate;
    private TextView TxtPlaceMartyr;
    private TextView TxtBlockName;
    private TextView TxtRowNumber;
    private TextView TxtGraveNumber;
    private AVLoadingIndicatorView AVLoading;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_location) {
            showGraveLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeViews();
        Deceased = getIntent().getBundleExtra("selected_deceased");
        getDeceasedDetails();
    }

    @Override
    public void onReceived(Response<DetailDeceasedApiModel> response) {
        bindViews(Deceased, response.body());
        ButtonShowLocation.setEnabled(true);
        hideLoading();
    }

    @Override
    public void onLocationChanged(Location location) {
        permissionVerifiedListener.onPermissionVerified(location);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101)
        {
            if (grantResults.length > 0)
            {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                Provider = locationManager.getBestProvider(criteria, false);
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(Provider, 12000, 7, DetailActivity.this);
                }
            }
        }
    }

    private void initializeViews() {
        ButtonShowLocation = findViewById(R.id.btn_show_location);
        ButtonShowLocation.setOnClickListener(this);
        ImgDeceasedPhoto = findViewById(R.id.img_deceased_photo_detail);
        TxtTitleFullName = findViewById(R.id.txt_title_full_name_detail);
        TxtFatherName = findViewById(R.id.txt_father_name_detail);
        TxtBirthDate = findViewById(R.id.txt_birth_date);
        TxtDeadDate = findViewById(R.id.txt_dead_date);
        TxtPlaceMartyr= findViewById(R.id.txt_place_martyr);
        TxtBlockName = findViewById(R.id.txt_block_name);
        TxtRowNumber = findViewById(R.id.txt_row_number);
        TxtGraveNumber = findViewById(R.id.txt_grave_number);
        AVLoading = findViewById(R.id.av_loading_search_detail);
    }

    public void setPermissionVerifiedListener(onPermissionVerifiedListener permissionVerifiedListener)
    {
        this.permissionVerifiedListener = permissionVerifiedListener;
    }

    private void OpenGoogleMap()
    {
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + personLatitude + "," + personLongitude + "&daddr=" + deceasedLatitude + "," + deceasedLongitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, "Select an application"));
    }

    private void bindViews(Bundle bundle, DetailDeceasedApiModel detailDeceased)
    {
        String imageUrl = getString(R.string.ImageFolderName) + getString(R.string.DeadImageFolder) + bundle.getString("image_name");
        Glide.with(this).load(imageUrl).into(ImgDeceasedPhoto);
        TxtTitleFullName.setText(String.format("%s %s", bundle.getString("defunct_title"), bundle.getString("full_name")));
        TxtFatherName.setText(String.format("%s %s", "فرزند", bundle.getString("father_name")));
        TxtBirthDate.setText(String.format("%s %s", "تاریخ تولد :", detailDeceased.getBirthDate()));
        TxtDeadDate.setText(String.format("%s %s", "تاریخ وفات :", detailDeceased.getDeadDate()));
        if (detailDeceased.getPlaceMartyr()=="" || detailDeceased.getPlaceMartyr() == null)
        {
            TxtPlaceMartyr.setVisibility(View.INVISIBLE);
        }
        else
        {
            TxtPlaceMartyr.setText(String.format("%s %s", "محل شهادت :", detailDeceased.getPlaceMartyr()));
        }
        TxtRowNumber.setText(String.format("%s %s", "شماره ردیف :", detailDeceased.getRowNumber()));
        TxtGraveNumber.setText(String.format("%s %s", "شماره قبر :", detailDeceased.getGraveNumber()));
        TxtBlockName.setText(String.format("%s %s", "نام قطعه :", detailDeceased.getBlockName()));
        deceasedLatitude = Double.parseDouble(detailDeceased.getLatitude());
        deceasedLongitude = Double.parseDouble(detailDeceased.getLongitude());
    }

    private void showGraveLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        else
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            Provider = locationManager.getBestProvider(criteria, false);
            locationManager.requestLocationUpdates(Provider, 12000, 7, DetailActivity.this);
        }
        setPermissionVerifiedListener(new onPermissionVerifiedListener()
        {
            @Override
            public void onPermissionVerified(Location location)
            {
                personLatitude = location.getLatitude();
                personLongitude = location.getLongitude();
                Toast.makeText(DetailActivity.this, location.getLatitude() + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(DetailActivity.this, location.getLongitude() + "", Toast.LENGTH_SHORT).show();
                OpenGoogleMap();
            }
        });
    }

    private void showLoading()
    {
        AVLoading.show();
    }

    private void hideLoading()
    {
        AVLoading.hide();
    }

    public interface onPermissionVerifiedListener
    {
        void onPermissionVerified(Location location);
    }

    private void getDeceasedDetails()
    {
        DeceasedApis api = ApiServiceGenerator.getApiService();
        Call<DetailDeceasedApiModel> detailCall = api.getDeceasedDetails(Deceased.getInt("dead_id"));
        detailCall.enqueue(new DetailsCallBackImpl(this));
        showLoading();
    }

}
