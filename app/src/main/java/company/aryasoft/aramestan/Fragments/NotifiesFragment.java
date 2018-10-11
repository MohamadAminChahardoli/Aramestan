package company.aryasoft.aramestan.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import company.aryasoft.aramestan.Activities.DetailActivity;
import company.aryasoft.aramestan.Activities.SettingActivity;
import company.aryasoft.aramestan.Adapters.NotifiesRecyclerAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Implementations.AnnouncementCallBackImpl;
import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.Networking;
import retrofit2.Call;
import retrofit2.Response;


public class NotifiesFragment extends Fragment
    implements AnnouncementCallBackImpl.OnAnnouncementReceivedListener, View.OnClickListener
{
    private RecyclerView recyclerNotifies;
    private NotifiesRecyclerAdapter notifiesAdapter;
    private DeceasedApis Api;
    private Call<List<Announcement>> AnnouncementCall;
    private int DefaultSkipItems = 0;
    private int DefaultTakeItems = 20;
    private boolean IsLoading=false;
    private boolean DataEnded=false;
    private AVLoadingIndicatorView AVLoadingAnnouncement;
    private ImageView ImgBg;
    private ImageView ImgToolbar;
    private ImageView ImgBgEffect;
    private RelativeLayout RelContentParent;
    private ImageButton ButtonSettings;
    private Snackbar SnackMessage;
    private Context ContextInstance;

    public NotifiesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_settings)
            startActivity(new Intent(ContextInstance, SettingActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextInstance = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_notifies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents(view);
        Glide.with(ContextInstance).load(R.drawable.bg1).into(ImgBg);
        Glide.with(ContextInstance).load(R.drawable.about_cloud).into(ImgToolbar);
        Glide.with(ContextInstance).load(R.drawable.mydetail).into(ImgBgEffect);
        getAnnouncements();
    }

    @Override
    public void onReceived(Response<List<Announcement>> response) {
        if (response.body().size() > 0)
        {
            ArrayList<Announcement> announcements = new ArrayList<>
                    (response.body() == null ? new ArrayList<Announcement>() : response.body());
            notifiesAdapter.addNotifiesDataList(announcements);
            IsLoading = false;
        }
        else
        {
            DataEnded = true;
            IsLoading = false;
        }
        hideLoading();
    }

    private void initializeComponents(View view)
    {
        recyclerNotifies = view.findViewById(R.id.recycler_notifies_activity);
        AVLoadingAnnouncement = view.findViewById(R.id.av_loading_announcement);
        ImgBg = view.findViewById(R.id.img_bg_notify);
        ImgToolbar = view.findViewById(R.id.img_toolbar_notify);
        ImgBgEffect = view.findViewById(R.id.img_bg_effect_notify);
        RelContentParent = view.findViewById(R.id.rel_content_notify_parent);
        ButtonSettings = view.findViewById(R.id.btn_settings);
        ButtonSettings.setOnClickListener(this);
        TextView txtNotifyTitle = view.findViewById(R.id.txt_notify_title);
        Typeface tf = Typeface.createFromAsset(ContextInstance.getAssets(), "fonts/iran_nastaliq.ttf");
        txtNotifyTitle.setTypeface(tf);
        setupRecyclerViewNotifies();
    }

    private void setupRecyclerViewNotifies()
    {
        notifiesAdapter = new NotifiesRecyclerAdapter(getContext());
        recyclerNotifies.setAdapter(notifiesAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerNotifies.setLayoutManager(mLayoutManager);
        recyclerNotifies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (notifiesAdapter.getItemCount() >= DefaultTakeItems)
                {
                    if(!DataEnded)
                    {
                        int VisibleItemCount = mLayoutManager.getChildCount();
                        int TotalItemCount = mLayoutManager.getItemCount();
                        int PastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                        if (IsLoading)
                        {
                            return;
                        }
                        if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                        {
                            DefaultSkipItems+=20;
                            IsLoading = true;
                            loadMore();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void loadMore()
    {
        AnnouncementCall = Api.getAnnouncements(DefaultSkipItems, DefaultTakeItems);
        AnnouncementCall.enqueue(new AnnouncementCallBackImpl(this));
        showLoading();
    }

    private void initializeComponentsEvents()
    {
    }

    private void showLoading()
    {
        AVLoadingAnnouncement.show();
    }

    private void hideLoading()
    {
        AVLoadingAnnouncement.hide();
    }

    private void getAnnouncements() {
        if (Networking.isNetworkAvailable(ContextInstance))
        {
            Api = ApiServiceGenerator.getApiService();
            AnnouncementCall = Api.getAnnouncements(DefaultSkipItems, DefaultTakeItems);
            AnnouncementCall.enqueue(new AnnouncementCallBackImpl(this));
            if (SnackMessage != null && SnackMessage.isShown())
            {
                SnackMessage.dismiss();
                recyclerNotifies.setVisibility(View.VISIBLE);
            }
            showLoading();
        }
        else
        {
            showDisconnectedInternetMessage();
            recyclerNotifies.setVisibility(View.INVISIBLE);
            hideLoading();
        }

    }

    private void showDisconnectedInternetMessage()
    {
        SnackMessage = Snackbar.make(RelContentParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        SnackMessage.show();
        SnackMessage.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Networking.isNetworkAvailable(ContextInstance))
                {
                    getAnnouncements();
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
