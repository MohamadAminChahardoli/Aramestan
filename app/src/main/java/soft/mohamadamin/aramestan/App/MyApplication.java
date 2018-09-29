package soft.mohamadamin.aramestan.App;

import android.app.Application;
import android.content.Context;

import soft.mohamadamin.aramestan.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        initializeCalligraphy();
    }

    public static Context getContext()
    {
        return context;
    }

    private void initializeCalligraphy()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_yekan_mobile_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
