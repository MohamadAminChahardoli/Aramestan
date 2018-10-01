package company.aryasoft.aramestan.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import company.aryasoft.aramestan.Adapters.NotifiesRecyclerAdapter;
import company.aryasoft.aramestan.R;



public class NotifiesFragment extends Fragment
{
    private RecyclerView recyclerNotifies;
    private RelativeLayout relDataBehaviour;
    private NotifiesRecyclerAdapter notifiesAdapter;

    public NotifiesFragment()
    {
        // Required empty public constructor
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
    private void initializeComponents(View view)
    {
        recyclerNotifies = view.findViewById(R.id.recycler_notifies_activity);
        relDataBehaviour = view.findViewById(R.id.rel_data_behaviour);
        notifiesAdapter = new NotifiesRecyclerAdapter(view.getContext());
        recyclerNotifies.setAdapter(notifiesAdapter);
        recyclerNotifies.setLayoutManager(new GridLayoutManager(getContext(),2));
    }


    private void initializeComponentsEvents()
    {

    }
}
