package soft.mohamadamin.aramestan.Implementations;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.OnTabSelectListener;

import soft.mohamadamin.aramestan.Activities.MainActivity;
import soft.mohamadamin.aramestan.Fragments.AdvertisementsFragment;
import soft.mohamadamin.aramestan.Fragments.NotificationsFragment;
import soft.mohamadamin.aramestan.Fragments.SearchFragment;
import soft.mohamadamin.aramestan.R;

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
            replaceFragment(new AdvertisementsFragment());
        }
        else if (tabId == R.id.tab_search)
        {
            replaceFragment(new SearchFragment());
        }
        else if (tabId == R.id.tab_notifications)
        {
            replaceFragment(new NotificationsFragment());
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
