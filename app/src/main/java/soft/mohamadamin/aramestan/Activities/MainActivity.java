package soft.mohamadamin.aramestan.Activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;

import soft.mohamadamin.aramestan.Fragments.SearchFragment;
import soft.mohamadamin.aramestan.Implementations.BottomBarTabSelectListener;
import soft.mohamadamin.aramestan.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity   {

    private BottomBar MainBottomBar;

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

    private void initializeViews()
    {
        MainBottomBar = findViewById(R.id.main_bottom_bar);
    }

    private void setupMainBottomBar()
    {
        MainBottomBar.setOnTabSelectListener(new BottomBarTabSelectListener(this));
        //By following method call, not only the 'search tab' sets as
        //default at start time, but also the 'search fragment' sets as
        //default at start time happens because of onTabSelectedListener.
        MainBottomBar.setDefaultTab(R.id.tab_search);
    }

}
