package company.aryasoft.aramestan.App;

import android.app.Application;
import android.content.Context;

import co.ronash.pushe.Pushe;
import company.aryasoft.aramestan.Activities.SettingActivity;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.PusheManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initializeCalligraphy();
        Pushe.initialize(this, true);
    }

    public static Context getContext() {
        return context;
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_yekan_mobile_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
