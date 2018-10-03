package company.aryasoft.aramestan.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import company.aryasoft.aramestan.Adapters.NotifiesRecyclerAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Implementations.AnnouncementCallBackImpl;
import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;
import retrofit2.Call;
import retrofit2.Response;


public class NotifiesFragment extends Fragment
    implements AnnouncementCallBackImpl.OnAnnouncementReceivedListener
{
    private RecyclerView recyclerNotifies;
    private RelativeLayout relDataBehaviour;
    private NotifiesRecyclerAdapter notifiesAdapter;
    private DeceasedApis Api;
    private Call<List<Announcement>> AnnouncementCall;
    private int DefaultSkipItems = 0;
    private int DefaultTakeItems = 20;
    private boolean IsLoading=false;
    private boolean DataEnded=false;

    public NotifiesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api = ApiServiceGenerator.getApiService();
        AnnouncementCall = Api.getAnnouncements(DefaultSkipItems, DefaultTakeItems);
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
        initializeComponentsEvents();
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
    }

    private void initializeComponents(View view)
    {
        recyclerNotifies = view.findViewById(R.id.recycler_notifies_activity);
        relDataBehaviour = view.findViewById(R.id.rel_data_behaviour);
        setupRecyclerViewNotifies();
    }

    private void setupRecyclerViewNotifies()
    {
        notifiesAdapter = new NotifiesRecyclerAdapter(getContext());
        recyclerNotifies.setAdapter(notifiesAdapter);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerNotifies.setLayoutManager(mLayoutManager);
        recyclerNotifies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
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
        });
    }

    private void loadMore()
    {
        AnnouncementCall = Api.getAnnouncements(DefaultSkipItems, DefaultTakeItems);
        AnnouncementCall.enqueue(new AnnouncementCallBackImpl(this));
    }

    private void initializeComponentsEvents()
    {
        AnnouncementCall.enqueue(new AnnouncementCallBackImpl(this));
    }

}
