package company.aryasoft.aramestan.Implementations;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.OnTabSelectListener;

import company.aryasoft.aramestan.Activities.MainActivity;
import company.aryasoft.aramestan.Fragments.AdvertisementFragment;
import company.aryasoft.aramestan.Fragments.NotifiesFragment;
import company.aryasoft.aramestan.Fragments.SearchFragment;
import company.aryasoft.aramestan.R;

public class BottomBarTabSelectListener implements OnTabSelectListener {

    private MainActivity MainActivityInstance;

    public BottomBarTabSelectListener(MainActivity mainActivityInstance) {
        MainActivityInstance = mainActivityInstance;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        showTabContent(tabId);
    }

    private void showTabContent(int tabId) {
        if (tabId == R.id.tab_ads)
        {
            replaceFragment(new AdvertisementFragment());
        }
        else if (tabId == R.id.tab_search)
        {
            replaceFragment(new SearchFragment());
        }
        else if (tabId == R.id.tab_notifications)
        {
            replaceFragment(new NotifiesFragment());
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction =
                MainActivityInstance.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_container, fragment);
        fragmentTransaction.commit();
    }

}
