package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import co.ronash.pushe.Pushe;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.PusheManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingActivity extends AppCompatActivity {

    private Switch SwitchPush;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initializeViews();
        pushSwitchConfig();
    }

    private void initializeViews() {
        SwitchPush = findViewById(R.id.switch_push);
        SwitchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Pushe.setNotificationOn(SettingActivity.this);
                    PusheManager pusheManager = new PusheManager(SettingActivity.this);
                    pusheManager.setPusheState(isChecked);
                } else if (!isChecked) {
                    Pushe.setNotificationOff(SettingActivity.this);
                    PusheManager pusheManager = new PusheManager(SettingActivity.this);
                    pusheManager.setPusheState(isChecked);
                }
            }
        });
    }

    private void pushSwitchConfig() {
        PusheManager pusheManager = new PusheManager(this);
        SwitchPush.setChecked(pusheManager.getPusheState());
    }

}
