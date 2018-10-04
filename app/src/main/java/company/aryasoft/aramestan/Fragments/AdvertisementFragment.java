package company.aryasoft.aramestan.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import company.aryasoft.aramestan.Adapters.AdvertisementRecyclerAdapter;
import company.aryasoft.aramestan.Adapters.NotifySliderAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.SliderCallBackImpl;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdvertisementFragment extends Fragment
        implements SliderCallBackImpl.OnSlidesDownloadedListener
{

    private RecyclerView recyclerAdvertisement;
    private AdvertisementRecyclerAdapter advertisementAdapter;
    private ViewPager sliderPager;
    private AVLoadingIndicatorView AVLoadingSlider;
    private static int currentPage = 0;
    private DeceasedApis Api;
    private Call<List<SliderDataModel>> SliderCall;
    Context context;

    public AdvertisementFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api = ApiServiceGenerator.getApiService();
        SliderCall = Api.getSlider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_advertisement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        context=view.getContext();
        initializeComponents(view);
        initializeComponentsEvents();
    }

    @Override
    public void onSlidesDownloaded(Response<List<SliderDataModel>> response) {
        ArrayList<SliderDataModel> slides = new ArrayList<>
                (response.body() == null ? new ArrayList<SliderDataModel>() : response.body());
        setupSlider(slides);
        hideLoading();
    }

    private void initializeComponents(final View view)
    {
        recyclerAdvertisement = view.findViewById(R.id.recycler_advertisement_fragment);
        sliderPager = view.findViewById(R.id.slider_pager);
        AVLoadingSlider = view.findViewById(R.id.av_loading_slider);
        advertisementAdapter=new AdvertisementRecyclerAdapter(view.getContext());
        recyclerAdvertisement.setAdapter(advertisementAdapter);
    }

    private void initializeComponentsEvents()
    {
        SliderCall.enqueue(new SliderCallBackImpl(this));
        showLoading();
    }
    
    private void setupSlider(final ArrayList<SliderDataModel> slides)
    {
        sliderPager.setAdapter(new NotifySliderAdapter(context, slides));
        Toast.makeText(context, slides.size()+"", Toast.LENGTH_SHORT).show();
       final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == slides.size())
                {
                    currentPage = 0;
                }
                sliderPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(Update);
            }
        }, 3000, 3000);

    }

    private void showLoading()
    {
        AVLoadingSlider.show();
    }

    private void hideLoading()
    {
        AVLoadingSlider.hide();
    }

}
