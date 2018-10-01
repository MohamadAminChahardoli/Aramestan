package company.aryasoft.aramestan.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import company.aryasoft.aramestan.Adapters.AdvertisementRecyclerAdapter;
import company.aryasoft.aramestan.Adapters.NotifySliderAdapter;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;


public class AdvertisementFragment extends Fragment
{

    private RecyclerView recyclerAdvertisement;
    private AdvertisementRecyclerAdapter advertisementAdapter;
    private ViewPager sliderPager;
    private static int currentPage = 0;
    private ArrayList<SliderDataModel> sliderList;

    public AdvertisementFragment()
    {
        // Required empty public constructor
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
        initializeComponents(view);
        initializeComponentsEvents();
    }


    private void initializeComponents(View view)
    {
        recyclerAdvertisement = view.findViewById(R.id.recycler_advertisement_fragment);
        sliderPager = view.findViewById(R.id.slider_pager);
        advertisementAdapter=new AdvertisementRecyclerAdapter(view.getContext());
        recyclerAdvertisement.setAdapter(advertisementAdapter);
        //---
        sliderPager.setAdapter(new NotifySliderAdapter(view.getContext(), GetDummySlider()));
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == sliderList.size())
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

    private void initializeComponentsEvents()
    {

    }

    private ArrayList<SliderDataModel> GetDummySlider()
    {
        sliderList = new ArrayList<>();
        SliderDataModel objSlider1 = new SliderDataModel();
        objSlider1.ImageName = R.drawable.slider1;
        objSlider1.ImageTitle = "متن اول از اسلایدر ";
        sliderList.add(objSlider1);

        SliderDataModel objSlider2 = new SliderDataModel();
        objSlider2.ImageName = R.drawable.slider2;
        objSlider2.ImageTitle = "متن دوم از اسلایدر ";
        sliderList.add(objSlider2);

        SliderDataModel objSlider3 = new SliderDataModel();
        objSlider3.ImageName = R.drawable.slider3;
        objSlider3.ImageTitle = "متن سوم از اسلایدر ";
        sliderList.add(objSlider3);
        return sliderList;
    }

}
