package soft.mohamadamin.aramestan.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mohamadamin.aramestan.R;

public class AdvertisementsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertisements, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews();
    }

    private void initializeViews()
    {

    }
}