package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.Timer;
import java.util.TimerTask;

import co.ronash.pushe.Pushe;
import company.aryasoft.aramestan.Fragments.SearchFragment;
import company.aryasoft.aramestan.Implementations.BottomBarTabSelectListener;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.CuteToast;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity{

    private AHBottomNavigation MainBottomBar;
    private boolean IS_ALLOWED_TO_EXIT = false;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setupMainBottomBar();
    }

    @Override
    public void onBackPressed()
    {
        if (SearchFragment.DisplayedChild == 1)
        {
            SearchFragment.closeFlipper();
        }
        else
        {
            if (IS_ALLOWED_TO_EXIT)
            {
                super.onBackPressed();
            }
            else
            {
                //CuteToast.show(this, getString(R.string.exit_message), Toast.LENGTH_LONG);
                CuteToast cuteToast = new CuteToast();
                cuteToast.with(this)
                        .setDuration(Toast.LENGTH_LONG)
                        .setText(getString(R.string.exit_message))
                        .show();
                IS_ALLOWED_TO_EXIT = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        IS_ALLOWED_TO_EXIT = false;
                    }
                }, 5000);
            }

        }
    }

    private void initializeViews()
    {
        MainBottomBar = findViewById(R.id.main_bottom_bar);
    }

    private void setupMainBottomBar()
    {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_ads_title, R.drawable.ads, R.color.bottomBarIconColor);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_search_title, R.drawable.loupe, R.color.bottomBarIconColor);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_notifications_title, R.drawable.notification, R.color.bottomBarIconColor);
        MainBottomBar.addItem(item1);
        MainBottomBar.addItem(item2);
        MainBottomBar.addItem(item3);
        MainBottomBar.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
        MainBottomBar.setAccentColor(getResources().getColor(R.color.colorAccent));
        MainBottomBar.setInactiveColor(Color.parseColor("#BDBDBD"));
        MainBottomBar.setBehaviorTranslationEnabled(true);
        MainBottomBar.setForceTint(true);
        MainBottomBar.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        MainBottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        MainBottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        MainBottomBar.setOnTabSelectedListener(new BottomBarTabSelectListener(this));
        MainBottomBar.setCurrentItem(BottomBarTabSelectListener.TAB_SEARCH);
    }

}

