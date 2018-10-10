package company.aryasoft.aramestan.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import company.aryasoft.aramestan.R;

public class CuteToast {

    public static void show(Activity activity, String text, int duration)
    {
        LayoutInflater inflater = activity.getLayoutInflater();
        Toast toast = new Toast(activity);
        View toastView = inflater.inflate(R.layout.custom_toast_layout, null);
        TextView txtMessage = toastView.findViewById(R.id.txt_toast_message);
        toast.setDuration(duration);
        txtMessage.setText(text);
        toast.setView(toastView);
        toast.show();
    }
}
