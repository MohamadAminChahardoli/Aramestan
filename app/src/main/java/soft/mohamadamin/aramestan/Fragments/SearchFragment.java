package soft.mohamadamin.aramestan.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import soft.mohamadamin.aramestan.R;

public class SearchFragment extends Fragment {

    private Button ButtonChooseDateOfDeath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews(view);

        ButtonChooseDateOfDeath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPersianDatePicker();
            }
        });
    }

    private void initializeViews(View view)
    {
        ButtonChooseDateOfDeath = view.findViewById(R.id.btn_choose_date_of_death);
    }

    private void showPersianDatePicker()
    {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getActivity(), year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
                    }
                },
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.show(getActivity().getFragmentManager(), "");
    }
}
