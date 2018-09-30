package company.aryasoft.aramestan.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import company.aryasoft.aramestan.R;

public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews();
    }

    private void initializeViews()
    {

    }
}
