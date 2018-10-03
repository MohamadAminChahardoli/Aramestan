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
import android.widget.Toast;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

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
        implements Button.OnClickListener, SearchCallBackImpl.OnResultReceived {

    private DeceasedAdapter deceasedAdapter;
    private Button ButtonChooseDateOfDeath;
    private Button ButtonSearch;
    private RecyclerView RecyclerViewSearchResult;
    private DeceasedApis Api;
    private Call<List<Deceased>> SearchCall;
    private int DefaultSkipItems = 0;
    private int DefaultTakeItems = 20;
    private boolean IsLoading=false;
    private boolean DataEnded=false;

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
        if (view.getId() == R.id.btn_choose_date_of_death) {
            showPersianDatePicker();
        } else if (view.getId() == R.id.btn_search) {
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
     
    }

    private void initializeViews(View view)
    {
        ButtonChooseDateOfDeath = view.findViewById(R.id.btn_choose_date_of_death);
        ButtonSearch = view.findViewById(R.id.btn_search);
        RecyclerViewSearchResult = view.findViewById(R.id.recycler_view_search_result);
        ButtonChooseDateOfDeath.setOnClickListener(this);
        ButtonSearch.setOnClickListener(this);
        setupSearchResultRecyclerView();
    }

    private void initializeComponentsEvents()
    {

    }

    private void showPersianDatePicker() {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getActivity(), year + "/" + monthOfYear + 1 + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
                    }
                },
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(false);
        dpd.show(getActivity().getFragmentManager(), "");
    }

    private void search()
    {
        SearchCall = Api.lookForDeceased(getSearchModel(), DefaultSkipItems, DefaultTakeItems);
        SearchCall.enqueue(new SearchCallBackImpl(this));
    }

    private SearchModel getSearchModel()
    {
        SearchModel searchModel = new SearchModel();
        searchModel.setFirstName("");
        searchModel.setLastName("");
        searchModel.setFatherName("");
        searchModel.setDeadDate("");
        searchModel.setSex(true);
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

}
