package company.aryasoft.aramestan.Utils;

import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class VectorDrawablePreLollipopHelper
{
    public enum MyDirType
    {
        start,
        top,
        end,
        bottom
    }

    public static void SetVectors(android.content.res.Resources res, ArrayList<VectorView> list)
    {

        VectorDrawableCompat vdc ;
        for (int i = 0; i < list.size(); i++)
        {
            vdc = VectorDrawableCompat.create(res, list.get(i).draws, list.get(i).view.getContext().getTheme());
            if (list.get(i).view instanceof TextView)
            {
                TextView txt = (TextView) list.get(i).view;

                switch (list.get(i).dirs)
                {
                    case start:
                        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                        break;
                    case top:
                        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                        break;
                    case end:
                        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                        break;
                    case bottom:
                        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                        break;
                }

            }
            else if (list.get(i).view instanceof Button)
            {
                Button btn = (Button) list.get(i).view;
                switch (list.get(i).dirs)
                {
                    case start:
                        btn.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                        break;
                    case top:
                        btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                        break;
                    case end:
                        btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                        break;
                    case bottom:
                        btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                        break;
                }
            }
            else if (list.get(i).view instanceof EditText)
            {
                EditText edt = (EditText) list.get(i).view;
                switch (list.get(i).dirs)
                {
                    case start:
                        edt.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                        break;
                    case top:
                        edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                        break;
                    case end:
                        edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                        break;
                    case bottom:
                        edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                        break;
                }
            }

        }
    }

    public static void SetVectors(android.content.res.Resources res, VectorView ItemView)
    {

        VectorDrawableCompat vdc = null;

        vdc = VectorDrawableCompat.create(res, ItemView.draws, ItemView.view.getContext().getTheme());
        if (ItemView.view instanceof TextView)
        {
            TextView txt = (TextView) ItemView.view;

            switch (ItemView.dirs)
            {
                case start:
                    txt.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                    break;
                case top:
                    txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                    break;
                case end:
                    txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                    break;
                case bottom:
                    txt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                    break;
            }

        }
        else if (ItemView.view instanceof Button)
        {
            Button btn = (Button) ItemView.view;
            switch (ItemView.dirs)
            {
                case start:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                    break;
                case top:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                    break;
                case end:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                    break;
                case bottom:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                    break;
            }
        }
        else if (ItemView.view instanceof EditText)
        {
            EditText edt = (EditText) ItemView.view;
            switch (ItemView.dirs)
            {
                case start:
                    edt.setCompoundDrawablesRelativeWithIntrinsicBounds(vdc, null, null, null);
                    break;
                case top:
                    edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, vdc, null, null);
                    break;
                case end:
                    edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, vdc, null);
                    break;
                case bottom:
                    edt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, vdc);
                    break;
            }
        }


    }
}

