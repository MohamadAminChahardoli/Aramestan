package company.aryasoft.aramestan.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import company.aryasoft.aramestan.Adapters.DeceasedAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Implementations.SearchCallBackImpl;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.Models.SearchModel;
import company.aryasoft.aramestan.R;
import retrofit2.Call;
import retrofit2.Response;

public class SearchFragment extends Fragment
        implements Button.OnClickListener, SearchCallBackImpl.OnResultReceived, NumberPicker.OnValueChangeListener {

    private DeceasedAdapter deceasedAdapter;
    private Button ButtonSearch;
    private EditText EdtFirstName;
    private EditText EdtLastName;
    private EditText EdtFatherName;
    private RadioButton RBMeal;
    private RadioButton RBFeMeal;
    private RecyclerView RecyclerViewSearchResult;
    private NumberPicker NumberPickerDeadYear;
    private AVLoadingIndicatorView AVLoading;
    private DeceasedApis Api;
    private Call<List<Deceased>> SearchCall;
    private int DefaultSkipItems = 0;
    private int DefaultTakeItems = 20;
    private boolean IsLoading=false;
    private boolean DataEnded=false;
    private int YearOfDead = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api = ApiServiceGenerator.getApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, parent, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            search();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        initializeViews(view);
        initializeComponentsEvents();
    }

    @Override
    public void onReceived(Response<List<Deceased>> response)
    {
        if (response.body().size() > 0)
        {
            deceasedAdapter.addDeceasedListData(response.body());
            IsLoading = false;
        }
        else
        {
            DataEnded = true;
            IsLoading = false;
        }

        hideLoading();
     
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        YearOfDead = newVal;
    }

    private void initializeViews(View view)
    {
        ButtonSearch = view.findViewById(R.id.btn_search);
        RecyclerViewSearchResult = view.findViewById(R.id.recycler_view_search_result);
        EdtFirstName = view.findViewById(R.id.edt_first_name);
        EdtLastName = view.findViewById(R.id.edt_last_name);
        EdtFatherName = view.findViewById(R.id.edt_father_name);
        RBMeal = view.findViewById(R.id.rb_male);
        RBFeMeal = view.findViewById(R.id.rb_female);
        NumberPickerDeadYear = view.findViewById(R.id.number_picker);
        AVLoading = view.findViewById(R.id.av_loading);
        ButtonSearch.setOnClickListener(this);
        setupSearchResultRecyclerView();
        setupNumberPicker();
    }

    private void initializeComponentsEvents()
    {

    }

    private void search()
    {
        SearchCall = Api.lookForDeceased(getSearchModel(), DefaultSkipItems, DefaultTakeItems);
        SearchCall.enqueue(new SearchCallBackImpl(this));
        showLoading();
    }

    private SearchModel getSearchModel()
    {
        SearchModel searchModel = new SearchModel();
        searchModel.setFirstName(EdtFirstName.getText().toString());
        searchModel.setLastName(EdtLastName.getText().toString());
        searchModel.setFatherName(EdtFatherName.getText().toString());
        searchModel.setDeadDate(YearOfDead);
        searchModel.setSex(RBMeal.isChecked());
        return searchModel;
    }

    private void setupSearchResultRecyclerView()
    {
        deceasedAdapter = new DeceasedAdapter(getContext());
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        RecyclerViewSearchResult.setLayoutManager(mLayoutManager);
        RecyclerViewSearchResult.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewSearchResult.setAdapter(deceasedAdapter);
        RecyclerViewSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if(!DataEnded)
                {
                    int VisibleItemCount = mLayoutManager.getChildCount();
                    int TotalItemCount = mLayoutManager.getItemCount();
                    int PastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    if (IsLoading)
                    {
                        return;
                    }
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        DefaultSkipItems+=20;
                        IsLoading = true;
                        search();
                    }
                }
            }
        });
    }

    private void setupNumberPicker()
    {
        NumberPickerDeadYear.setMinValue(1350);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int differenceBetweenDateOfADAndDateOfShem = 621;
        NumberPickerDeadYear.setMaxValue(currentYear - differenceBetweenDateOfADAndDateOfShem);
        NumberPickerDeadYear.setValue(1380);
        NumberPickerDeadYear.setFadingEdgeEnabled(true);
        NumberPickerDeadYear.setScrollerEnabled(true);
        NumberPickerDeadYear.setWrapSelectorWheel(true);
        NumberPickerDeadYear.setOnValueChangedListener(this);
    }

    private void showLoading()
    {
        AVLoading.show();
    }

    private void hideLoading()
    {
        AVLoading.hide();
    }


}
