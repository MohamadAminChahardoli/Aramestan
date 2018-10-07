package company.aryasoft.aramestan.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;
import java.util.List;

import company.aryasoft.aramestan.Activities.DetailActivity;
import company.aryasoft.aramestan.Adapters.DeceasedAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Implementations.SearchCallBackImpl;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.Models.SearchModel;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.Networking;
import retrofit2.Call;
import retrofit2.Response;

import android.text.TextUtils;
import android.widget.ViewFlipper;

public class SearchFragment extends Fragment
        implements View.OnClickListener, SearchCallBackImpl.OnResultReceived, NumberPicker.OnValueChangeListener {

    private DeceasedAdapter deceasedAdapter;
    private Button ButtonSearch;
    private EditText EdtFirstName;
    private EditText EdtLastName;
    private EditText EdtFatherName;
    private RadioButton RBMeal;
    private RadioButton RBFeMeal;
    private RecyclerView RecyclerViewSearchResult;
    private NumberPicker NumberPickerDeadYear;
    private AVLoadingIndicatorView AVLoadingSearch;
    private ImageView ImgBg;
    private ImageView ImgToolbar;
    private ViewFlipper Flipper;
    private TextView TxtSearchSummery;
    private TextView TxtAppTitle;
    private CheckBox ChkUnknownDeadYear;
    private RelativeLayout RelContent;
    private DeceasedApis Api;
    private Call<List<Deceased>> SearchCall;
    private int DefaultSkipItems = 0;
    private final int DefaultTakeItems = 20;
    private boolean IsLoading = false;
    private boolean DataEnded = false;
    private String YearOfDead = null;
    private int DifferenceBetweenDateOfADAndDateOfShem = 621;
    private Snackbar SnackMessage;


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
        } else if (view.getId() == R.id.txt_search_summery) {
            deceasedAdapter.clearAllItems();
            Flipper.setDisplayedChild(0);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews(view);
        initializeComponentsEvents();
        Toast.makeText(getContext(), YearOfDead + "", Toast.LENGTH_SHORT).show();
        Glide.with(getContext()).load(R.drawable.bg1).into(ImgBg);
        Glide.with(getContext()).load(R.drawable.about_cloud).into(ImgToolbar);
        setupFlipper();
    }

    @Override
    public void onReceived(Response<List<Deceased>> response) {
        if (response.body().size() > 0) {
            deceasedAdapter.addDeceasedListData(response.body());
            IsLoading = false;
            TxtSearchSummery.setText(getSummery());
        } else {
            DataEnded = true;
            IsLoading = false;
            TxtSearchSummery.setText(getContext().getString(R.string.not_found));
        }

        hideLoading();
        Flipper.setDisplayedChild(1);

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        YearOfDead = newVal + "";
    }

    private void initializeViews(View view) {
        ButtonSearch = view.findViewById(R.id.btn_search);
        RecyclerViewSearchResult = view.findViewById(R.id.recycler_view_search_result);
        EdtFirstName = view.findViewById(R.id.edt_first_name);
        EdtLastName = view.findViewById(R.id.edt_last_name);
        EdtFatherName = view.findViewById(R.id.edt_father_name);
        RBMeal = view.findViewById(R.id.rb_male);
        RBFeMeal = view.findViewById(R.id.rb_female);
        NumberPickerDeadYear = view.findViewById(R.id.number_picker);
        AVLoadingSearch = view.findViewById(R.id.av_loading_search);
        ImgBg = view.findViewById(R.id.img_bg);
        ImgToolbar = view.findViewById(R.id.img_toolbar);
        Flipper = view.findViewById(R.id.view_flipper_search);
        TxtSearchSummery = view.findViewById(R.id.txt_search_summery);
        TxtAppTitle = view.findViewById(R.id.txt_app_title);
        RelContent = view.findViewById(R.id.rel_content_search);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/iran_nastaliq.ttf");
        TxtAppTitle.setTypeface(tf);
        ChkUnknownDeadYear = view.findViewById(R.id.chk_unknown_dead_year);
        ChkUnknownDeadYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerDeadYear.setEnabled(!ChkUnknownDeadYear.isChecked());
                if (ChkUnknownDeadYear.isChecked()) {
                    YearOfDead = null;
                } else {
                    YearOfDead = NumberPickerDeadYear.getValue() + "";
                }
            }
        });
        ButtonSearch.setOnClickListener(this);
        TxtSearchSummery.setOnClickListener(this);
        setupSearchResultRecyclerView();
        setupNumberPicker();
    }

    private void initializeComponentsEvents() {

    }

    private void search() {
        if (Networking.isNetworkAvailable(getActivity())) {
            SearchCall = Api.lookForDeceased(getSearchModel(), DefaultSkipItems, DefaultTakeItems);
            Toast.makeText(getContext(), DefaultSkipItems + "", Toast.LENGTH_SHORT).show();
            SearchCall.enqueue(new SearchCallBackImpl(this));
            showLoading();
            if (SnackMessage != null && SnackMessage.isShown()) {
                SnackMessage.dismiss();
            }
        } else {
            showDisconnectedInternetMessage();
        }

    }

    private SearchModel getSearchModel() {
        SearchModel searchModel = new SearchModel();

        if (!TextUtils.isEmpty(EdtFirstName.getText())) {
            searchModel.setFirstName(EdtFirstName.getText().toString());
        } else {
            searchModel.setFirstName(null);
        }
        if (!TextUtils.isEmpty(EdtLastName.getText())) {
            searchModel.setLastName(EdtLastName.getText().toString());
        } else {
            searchModel.setLastName(null);
        }
        if (!TextUtils.isEmpty(EdtFatherName.getText())) {
            searchModel.setFatherName(EdtFatherName.getText().toString());
        } else {
            searchModel.setFatherName(null);
        }
        if (YearOfDead != null) {
            int year = Integer.parseInt(YearOfDead);
            searchModel.setDeadDate((year + DifferenceBetweenDateOfADAndDateOfShem) + "");
        }

        searchModel.setSex(RBMeal.isChecked());
        Gson GsonInstance = new GsonBuilder().setLenient().serializeNulls().create();
        Log.i("mymodel", GsonInstance.toJson(searchModel));
        return searchModel;
    }

    private void setupSearchResultRecyclerView() {
        deceasedAdapter = new DeceasedAdapter(getContext());
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        RecyclerViewSearchResult.setLayoutManager(mLayoutManager);
        RecyclerViewSearchResult.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewSearchResult.setAdapter(deceasedAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        RecyclerViewSearchResult.addItemDecoration(itemDecorator);
        RecyclerViewSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (deceasedAdapter.getItemCount() >= DefaultTakeItems) {
                    if (!DataEnded) {
                        int VisibleItemCount = mLayoutManager.getChildCount();
                        int TotalItemCount = mLayoutManager.getItemCount();
                        int PastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                        if (IsLoading) {
                            return;
                        }
                        if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount) {
                            DefaultSkipItems += DefaultTakeItems;
                            IsLoading = true;
                            //search();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setupNumberPicker() {
        NumberPickerDeadYear.setMinValue(1350);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        NumberPickerDeadYear.setMaxValue(currentYear - DifferenceBetweenDateOfADAndDateOfShem);
        NumberPickerDeadYear.setValue(1380);
        NumberPickerDeadYear.setFadingEdgeEnabled(true);
        NumberPickerDeadYear.setScrollerEnabled(true);
        NumberPickerDeadYear.setWrapSelectorWheel(true);
        NumberPickerDeadYear.setOnValueChangedListener(this);
    }

    private void showLoading() {
        AVLoadingSearch.show();
    }

    private void hideLoading() {
        AVLoadingSearch.hide();
    }

    private void setupFlipper() {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.flip_in);
        Animation outAnim = AnimationUtils.loadAnimation(getContext(), R.anim.flip_out);
        Flipper.setInAnimation(inAnim);
        Flipper.setInAnimation(outAnim);
    }

    private void showDisconnectedInternetMessage() {
        SnackMessage = Snackbar.make(RelContent, getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        SnackMessage.show();
    }

    private String getSummery() {
        String summery = "";
        if (RBMeal.isChecked())
            summery += "آقای ";
        if (RBFeMeal.isChecked())
            summery += "خانم ";
        if (!EdtFirstName.getText().toString().equals(""))
            summery += String.format("%s ", EdtFirstName.getText());
        if (!EdtLastName.getText().toString().equals(""))
            summery += String.format("%s ", EdtLastName.getText());
        if (!EdtFatherName.getText().toString().equals(""))
            summery += String.format("فرزند %s ", EdtFatherName.getText());
        if (YearOfDead != null && YearOfDead != "")
            summery += String.format("،سال فوت %s", YearOfDead);

        if (summery.equals("آقای "))
            summery = "آقایان";
        else if (summery.equals("خانم "))
            summery = "بانوان";

        return summery;
    }

}
