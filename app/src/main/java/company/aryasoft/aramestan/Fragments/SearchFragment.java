package company.aryasoft.aramestan.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import company.aryasoft.aramestan.Adapters.DeceasedAdapter;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.R;

public class SearchFragment extends Fragment implements Button.OnClickListener {

    private Button ButtonChooseDateOfDeath;
    private Button ButtonSearch;
    private RecyclerView RecyclerViewSearchResult;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, parent, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose_date_of_death)
        {
            showPersianDatePicker();
        }
        else if (view.getId() == R.id.btn_search)
        {
            search();
        }
    }

    private void search() {
        List<Deceased> deceasedList = new ArrayList<>();
        deceasedList.add(new Deceased(1, "موسی اکبری", "حیدر", "No Image", "شهید"));
        deceasedList.add(new Deceased(1, "میرزا قاسمی", "جلال آل احمد", "No Image", "هنرمند"));
        deceasedList.add(new Deceased(1, "صابر رجبی", "حجت الله", "No Image", ""));

        DeceasedAdapter deceasedAdapter = new DeceasedAdapter(deceasedList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        RecyclerViewSearchResult.setLayoutManager(mLayoutManager);
        RecyclerViewSearchResult.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewSearchResult.setAdapter(deceasedAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews(view);

    }

    private void initializeViews(View view)
    {
        ButtonChooseDateOfDeath = view.findViewById(R.id.btn_choose_date_of_death);
        ButtonSearch = view.findViewById(R.id.btn_search);
        RecyclerViewSearchResult = view.findViewById(R.id.recycler_view_search_result);
        ButtonChooseDateOfDeath.setOnClickListener(this);
        ButtonSearch.setOnClickListener(this);
    }

    private void showPersianDatePicker()
    {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getActivity(), year + "/" + monthOfYear+1 + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
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
