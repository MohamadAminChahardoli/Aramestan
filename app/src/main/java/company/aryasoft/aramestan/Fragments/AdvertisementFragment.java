package company.aryasoft.aramestan.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import company.aryasoft.aramestan.Activities.DetailActivity;
import company.aryasoft.aramestan.Adapters.AdvertisementRecyclerAdapter;
import company.aryasoft.aramestan.Adapters.NewsRecyclerAdapter;
import company.aryasoft.aramestan.Adapters.NotifySliderAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.AdvertisementsCallBackImpl;
import company.aryasoft.aramestan.Implementations.NewsCallBackImpl;
import company.aryasoft.aramestan.Implementations.SliderCallBackImpl;
import company.aryasoft.aramestan.Models.Advertisement;
import company.aryasoft.aramestan.Models.NewsModel;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.Networking;
import retrofit2.Call;
import retrofit2.Response;


public class AdvertisementFragment extends Fragment
        implements SliderCallBackImpl.OnSlidesDownloadedListener,
                   NewsCallBackImpl.OnNewsReceivedListener,
                   AdvertisementsCallBackImpl.OnAdvertisementsReceivedListener
{

    private RecyclerView recyclerAdvertisement;
    private AdvertisementRecyclerAdapter advertisementAdapter;
    private RecyclerView recyclerNews;
    private NewsRecyclerAdapter newsAdapter;
    private ViewPager sliderPager;
    private AVLoadingIndicatorView AVLoadingSlider;
    private ImageView ImgBg;
    private ImageView ImgToolbar;
    private ImageView ImgFooter;
    private RelativeLayout RelContent;
    private ScrollView ScrollAds;
    private static int currentPage = 0;
    private DeceasedApis Api;
    private Call<List<SliderDataModel>> SliderCall;
    private Call<List<NewsModel>> NewsCall;
    private Call<List<Advertisement>> AdvertisementsCall;
    Context context;
    private int NewsDefaultSkipItems = 0;
    private int NewsDefaultTakeItems = 10;
    private boolean NewsIsLoading=false;
    private boolean NewsDataEnded=false;
    private int AdvertisementDefaultSkipItems = 0;
    private int AdvertisementDefaultTakeItems = 10;
    private boolean AdvertisementIsLoading=false;
    private boolean AdvertisementDataEnded=false;
    private Snackbar SnackMessage;

    public AdvertisementFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api = ApiServiceGenerator.getApiService();
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
        Glide.with(getContext()).load(R.drawable.bg1).into(ImgBg);
        Glide.with(getContext()).load(R.drawable.about_cloud_rotat).into(ImgToolbar);
        Glide.with(getContext()).load(R.drawable.about_cloud).into(ImgFooter);
    }

    @Override
    public void onSlidesDownloaded(Response<List<SliderDataModel>> response) {
        ArrayList<SliderDataModel> slides = new ArrayList<>
                (response.body() == null ? new ArrayList<SliderDataModel>() : response.body());
        setupSlider(slides);
        getNews();
    }

    @Override
    public void onNewsReceived(Response<List<NewsModel>> response) {
        if (response.body() != null) {
            if (response.body().size() > 0)
            {
                ArrayList<NewsModel> news = new ArrayList<>
                        (response.body() == null ? new ArrayList<NewsModel>() : response.body());
                newsAdapter.AddNewsData(news);
                NewsIsLoading = false;
            }
            else
            {
                NewsDataEnded = true;
                NewsIsLoading = false;
            }
        }
        getAdvertisements();
    }

    @Override
    public void onAdvertisementsReceived(Response<List<Advertisement>> response) {
        if (response.body() != null) {
            if (response.body().size() > 0)
            {
                ArrayList<Advertisement> ads = new ArrayList<>
                        (response.body() == null ? new ArrayList<Advertisement>() : response.body());
                advertisementAdapter.addAdvertisementsDataList(ads);
                AdvertisementIsLoading = false;
            }
            else
            {
                AdvertisementDataEnded = true;
                AdvertisementIsLoading = false;
            }
        }
        hideLoading();
    }

    private void initializeComponents(final View view)
    {
        recyclerAdvertisement = view.findViewById(R.id.recycler_advertisement_fragment);
        sliderPager = view.findViewById(R.id.slider_pager);
        recyclerNews = view.findViewById(R.id.recycler_news);
        AVLoadingSlider = view.findViewById(R.id.av_loading_slider);
        ImgBg = view.findViewById(R.id.img_bg_ads);
        ImgToolbar = view.findViewById(R.id.img_toolbar_ads);
        ImgFooter = view.findViewById(R.id.img_footer_ads);
        RelContent = view.findViewById(R.id.rel_content_ads_parent);
        ScrollAds = view.findViewById(R.id.scroll_ads);
        setupNewsRecycler();
        setupAdvertisementsRecycler();
    }

    private void initializeComponentsEvents()
    {
        getSlider();
        showLoading();
    }
    
    private void setupSlider(final ArrayList<SliderDataModel> slides)
    {
        sliderPager.setAdapter(new NotifySliderAdapter(context, slides));
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

    private void setupNewsRecycler()
    {
        newsAdapter = new NewsRecyclerAdapter(getContext());
        recyclerNews.setAdapter(newsAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true );
        recyclerNews.setLayoutManager(mLayoutManager);
        recyclerNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if(!NewsDataEnded)
                {
                    int VisibleItemCount = mLayoutManager.getChildCount();
                    int TotalItemCount = mLayoutManager.getItemCount();
                    int PastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    if (NewsIsLoading)
                    {
                        return;
                    }
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        NewsDefaultSkipItems+=10;
                        NewsIsLoading = true;
                        loadMoreNews();
                    }
                }
            }
        });
    }

    private void setupAdvertisementsRecycler()
    {
        advertisementAdapter = new AdvertisementRecyclerAdapter(getContext());
        recyclerAdvertisement.setAdapter(advertisementAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerAdvertisement.setLayoutManager(mLayoutManager);
        recyclerAdvertisement.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if(!AdvertisementDataEnded)
                {
                    int VisibleItemCount = mLayoutManager.getChildCount();
                    int TotalItemCount = mLayoutManager.getItemCount();
                    int PastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    if (AdvertisementIsLoading)
                    {
                        return;
                    }
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        AdvertisementDefaultSkipItems+=10;
                        AdvertisementIsLoading = true;
                        //loadMoreAdvertisements();
                    }
                }
            }
        });
    }

    private void showLoading()
    {
        AVLoadingSlider.show();
    }

    private void hideLoading()
    {
        AVLoadingSlider.hide();
    }

    private void loadMoreNews()
    {
        NewsCall = Api.getAllNews(NewsDefaultSkipItems, NewsDefaultTakeItems);
        NewsCall.enqueue(new NewsCallBackImpl(this));
        showLoading();
    }

    private void loadMoreAdvertisements()
    {
        AdvertisementsCall = Api.getAdvertisements(NewsDefaultSkipItems, NewsDefaultTakeItems);
        AdvertisementsCall.enqueue(new AdvertisementsCallBackImpl(this));
        showLoading();
    }

    private void getSlider()
    {
        if (Networking.isNetworkAvailable(getContext()))
        {
            SliderCall = Api.getSlider();
            SliderCall.enqueue(new SliderCallBackImpl(this));
            showLoading();
            if (SnackMessage != null && SnackMessage.isShown())
            {
                SnackMessage.dismiss();
                ScrollAds.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            showDisconnectedInternetMessage();
            ScrollAds.setVisibility(View.INVISIBLE);
            hideLoading();
        }

    }

    private void getNews()
    {
        NewsCall = Api.getAllNews(NewsDefaultSkipItems, NewsDefaultTakeItems);
        NewsCall.enqueue(new NewsCallBackImpl(this));
    }

    private void getAdvertisements()
    {
        AdvertisementsCall = Api.getAdvertisements(NewsDefaultSkipItems, NewsDefaultTakeItems);
        AdvertisementsCall.enqueue(new AdvertisementsCallBackImpl(this));
    }

    private void showDisconnectedInternetMessage()
    {
        SnackMessage = Snackbar.make(RelContent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        SnackMessage.show();
        SnackMessage.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Networking.isNetworkAvailable(getContext()))
                {
                    getSlider();
                    SnackMessage.dismiss();
                }
                else
                {
                    showDisconnectedInternetMessage();
                }
            }
        });
    }

}
