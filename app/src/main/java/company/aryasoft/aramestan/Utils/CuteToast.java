package company.aryasoft.aramestan.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import company.aryasoft.aramestan.R;

public class CuteToast {

    private String ToastText;
    private int ToastDuration;
    private Activity ActivityInstance;
    private View ContentView;

    public CuteToast() {
        ToastText = "";
        ToastDuration = Toast.LENGTH_SHORT;
        ContentView = null;
    }

    public CuteToast setText(String text)
    {
        ToastText = text;
        return  this;
    }

    public CuteToast setDuration(int duration)
    {
        ToastDuration = duration;
        return this;
    }

    public CuteToast with(Activity activity)
    {
        ActivityInstance = activity;
        return  this;
    }

    public CuteToast setContentView(View contentView)
    {
        return this;
    }
    public void show()
    {
        Toast toast = new Toast(ActivityInstance);
        toast.setDuration(ToastDuration);
        if (ContentView == null)
        {
            LayoutInflater inflater = ActivityInstance.getLayoutInflater();
            ContentView = inflater.inflate(R.layout.custom_toast_layout, null);
            TextView txtMessage = ContentView.findViewById(R.id.txt_toast_message);
            txtMessage.setText(ToastText);
            toast.setView(ContentView);
        }
        else if (ContentView != null)
        {
            toast.setView(ContentView);
        }
        toast.show();
    }
}
