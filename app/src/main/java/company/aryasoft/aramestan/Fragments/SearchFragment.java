package company.aryasoft.aramestan.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shawnlin.numberpicker.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;
import java.util.List;

import company.aryasoft.aramestan.Activities.MainActivity;
import company.aryasoft.aramestan.Adapters.DeceasedAdapter;
import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.App.MyApplication;
import company.aryasoft.aramestan.Implementations.SearchCallBackImpl;
import company.aryasoft.aramestan.Implementations.SearchLoadMoreCallBack;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.Models.SearchModel;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.Networking;
import company.aryasoft.aramestan.Utils.VectorDrawablePreLollipopHelper;
import company.aryasoft.aramestan.Utils.VectorView;
import retrofit2.Call;
import retrofit2.Response;

import android.widget.Toast;
import android.widget.ViewFlipper;

public class SearchFragment extends Fragment
        implements View.OnClickListener, SearchCallBackImpl.OnResultReceived,
        SearchLoadMoreCallBack.OnMoreResultReceived,
        NumberPicker.OnValueChangeListener, ViewFlipper.OnLayoutChangeListener,
        TextView.OnEditorActionListener {

    private DeceasedAdapter deceasedAdapter;
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
    private static ViewFlipper Flipper;
    private TextView TxtSearchSummery;
    private CheckBox ChkUnknownDeadYear;
    private RelativeLayout RelContent;
    private DeceasedApis Api;
    private Call<List<Deceased>> SearchCall;
    private static int DefaultSkipItems = 0;
    private final int DefaultTakeItems = 20;
    private boolean IsLoading = false;
    private boolean DataEnded = false;
    private String YearOfDead = null;
    private int DifferenceBetweenDateOfADAndDateOfShem = 621;
    private Snackbar SnackMessage;
    private final static int FORM_CHILD = 0;
    private final static int RESULT_CHILD = 1;
    public static int DisplayedChild = FORM_CHILD;

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
            Flipper.setDisplayedChild(FORM_CHILD);
            DisplayedChild = FORM_CHILD;
            DefaultSkipItems = 0;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews(view);
        initializeComponentsEvents();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Glide.with(getContext()).load(R.drawable.bg1).into(ImgBg);
        Glide.with(getContext()).load(R.drawable.about_cloud).into(ImgToolbar);
        setupFlipper();
    }

    @Override
    public void onReceived(Response<List<Deceased>> response) {
        if (response.body().size() > 0) {
            deceasedAdapter.addDeceasedListData(response.body());
            TxtSearchSummery.setText(getSummery());
        } else {
            TxtSearchSummery.setText(getContext().getString(R.string.not_found));
        }
        hideLoading();
        Flipper.setDisplayedChild(RESULT_CHILD);
        DisplayedChild = RESULT_CHILD;
    }

    @Override
    public void onMoreReceived(Response<List<Deceased>> response) {
        if (response.body().size() > 0) {
            deceasedAdapter.addDeceasedListData(response.body());
            IsLoading = false;
        } else {
            DataEnded = true;
            IsLoading = false;
        }
        hideLoading();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        YearOfDead = newVal + "";
    }

    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (view instanceof ViewFlipper && Flipper.getDisplayedChild() == FORM_CHILD) {
            deceasedAdapter.clearAllItems();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
            search();
        return false;
    }

    private void initializeViews(View view) {
        Button buttonSearch = view.findViewById(R.id.btn_search);
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
        VectorDrawablePreLollipopHelper.SetVectors(getResources(), new VectorView(R.drawable.ic_youtube_searched_for_black_24dp, TxtSearchSummery, VectorDrawablePreLollipopHelper.MyDirType.start));
        TextView txtAppTitle = view.findViewById(R.id.txt_app_title);
        TextView txtYearOfDeadCaption = view.findViewById(R.id.txt_choose_date_of_death);
        VectorDrawablePreLollipopHelper.SetVectors(getResources(), new VectorView(R.drawable.event, txtYearOfDeadCaption, VectorDrawablePreLollipopHelper.MyDirType.end));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/iran_nastaliq.ttf");
        RelContent = view.findViewById(R.id.rel_content_search);
        RelContent.requestFocus();
        txtAppTitle.setTypeface(tf);
        ChkUnknownDeadYear = view.findViewById(R.id.chk_unknown_dead_year);
        ChkUnknownDeadYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerDeadYear.setEnabled(ChkUnknownDeadYear.isChecked());
                if (!ChkUnknownDeadYear.isChecked()) {
                    YearOfDead = null;
                } else {
                    YearOfDead = NumberPickerDeadYear.getValue() + "";
                }
            }
        });
        buttonSearch.setOnClickListener(this);
        TxtSearchSummery.setOnClickListener(this);
        setupSearchResultRecyclerView();
        setupNumberPicker();

        EdtFirstName.setOnEditorActionListener(this);
        EdtLastName.setOnEditorActionListener(this);
        EdtFatherName.setOnEditorActionListener(this);
    }

    private void initializeComponentsEvents() {

    }

    private void search() {
        if (Networking.isNetworkAvailable(getActivity())) {
            SearchCall = Api.lookForDeceased(getSearchModel(), DefaultSkipItems, DefaultTakeItems);
            SearchCall.enqueue(new SearchCallBackImpl(this));
            showLoading();
            if (SnackMessage != null && SnackMessage.isShown()) {
                SnackMessage.dismiss();
            }
        } else {
            showDisconnectedInternetMessage();
            hideLoading();
        }

    }

    private void loadMoreResult() {
        if (Networking.isNetworkAvailable(getActivity())) {
            SearchCall = Api.lookForDeceased(getSearchModel(), DefaultSkipItems, DefaultTakeItems);
            SearchCall.enqueue(new SearchLoadMoreCallBack(this));
            showLoading();
            if (SnackMessage != null && SnackMessage.isShown()) {
                SnackMessage.dismiss();
            }
        } else {
            showDisconnectedInternetMessage();
            hideLoading();
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
                            loadMoreResult();
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
        NumberPickerDeadYear.setEnabled(false);
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
        Flipper.addOnLayoutChangeListener(this);
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
            summery += String.format("، سال فوت %s", YearOfDead);

        if (summery.equals("آقای "))
            summery = "آقایان";
        else if (summery.equals("خانم "))
            summery = "بانوان";

        return summery;
    }

    public static void closeFlipper() {
        Flipper.setDisplayedChild(FORM_CHILD);
        DisplayedChild = FORM_CHILD;
        DefaultSkipItems = 0;
    }

}
