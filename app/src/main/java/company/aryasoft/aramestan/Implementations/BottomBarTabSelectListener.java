package company.aryasoft.aramestan.Implementations;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import company.aryasoft.aramestan.Activities.MainActivity;
import company.aryasoft.aramestan.Fragments.AdvertisementFragment;
import company.aryasoft.aramestan.Fragments.NotifiesFragment;
import company.aryasoft.aramestan.Fragments.SearchFragment;
import company.aryasoft.aramestan.R;

public class BottomBarTabSelectListener implements AHBottomNavigation.OnTabSelectedListener {

    private MainActivity MainActivityInstance;
    public static final int TAB_ADS = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_ANNOUNCEMENT = 2;

    public BottomBarTabSelectListener(MainActivity mainActivityInstance) {
        MainActivityInstance = mainActivityInstance;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected)
            showTabContent(position);
        return true;
    }

    private void showTabContent(int tabId) {
        if (tabId == TAB_ADS) {
            replaceFragment(new AdvertisementFragment());
        } else if (tabId == TAB_SEARCH) {
            replaceFragment(new SearchFragment());
        } else if (tabId == TAB_ANNOUNCEMENT) {
            replaceFragment(new NotifiesFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                MainActivityInstance.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_container, fragment);
        fragmentTransaction.commit();
    }

}
