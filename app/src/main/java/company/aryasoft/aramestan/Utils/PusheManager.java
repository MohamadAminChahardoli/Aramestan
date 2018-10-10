package company.aryasoft.aramestan.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PusheManager
{
    private static final String KEY_IS_NOTIFICATIONS_ON="is_on";
    private SharedPreferences Shared;

    public PusheManager(Context context)
    {
        Shared = context.getSharedPreferences(KEY_IS_NOTIFICATIONS_ON,context.MODE_PRIVATE);
    }

    public void setPusheState(boolean isOn)
    {
        SharedPreferences.Editor editor = Shared.edit();
        editor.putBoolean(KEY_IS_NOTIFICATIONS_ON,isOn);
        editor.apply();
    }

    public boolean getPusheState()
    {
        return Shared.getBoolean(KEY_IS_NOTIFICATIONS_ON,true);
    }

}
