package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.NewsDetailCallBackImpl;
import company.aryasoft.aramestan.Models.NewsDetailModel;
import company.aryasoft.aramestan.R;
import company.aryasoft.aramestan.Utils.Networking;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailCallBackImpl.OnNewsDetailReceivedListener {

    private DeceasedApis Api;
    private Call<NewsDetailModel> NewsDetailCall;
    private Bundle News;
    private ImageView ImgNewsImage;
    private TextView TxtNewsTitle;
    private TextView TxtNewsSummary;
    private TextView TxtNewsDescription;
    private TextView TxtNewsSource;
    private AVLoadingIndicatorView AVLoading;
    private ScrollView ScrollNews;
    private Snackbar SnackMessage;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initializeViews();
        Api = ApiServiceGenerator.getApiService();
        News = getIntent().getBundleExtra("selected_news");
        getNewsDetails(News.getInt("news_id"));
    }


    @Override
    public void onNewsDetailReceived(Response<NewsDetailModel> response) {
        bindViews(response.body());
        hideLoading();
    }

    private void initializeViews()
    {
        ImgNewsImage = findViewById(R.id.img_news_image_detailActivity);
        TxtNewsTitle = findViewById(R.id.txt_news_title_detailActivity);
        TxtNewsSummary = findViewById(R.id.txt_news_summery);
        TxtNewsDescription= findViewById(R.id.txt_news_description);
        TxtNewsSource = findViewById(R.id.txt_news_source);
        AVLoading = findViewById(R.id.av_loading_news_detail);
        ScrollNews = findViewById(R.id.scroll_news_details);
    }

    private void getNewsDetails(int newsId)
    {
        if (Networking.isNetworkAvailable(NewsDetailsActivity.this))
        {
            NewsDetailCall = Api.getNewsDetails(newsId);
            NewsDetailCall.enqueue(new NewsDetailCallBackImpl(this));
            showLoading();
            if (SnackMessage != null && SnackMessage.isShown())
            {
                SnackMessage.dismiss();
                ScrollNews.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            showDisconnectedInternetMessage();
            ScrollNews.setVisibility(View.INVISIBLE);
        }

    }

    private void bindViews(NewsDetailModel newsDetailModel)
    {
        String imageUrl = getString(R.string.ImageFolderName) + getString(R.string.NewsImageFolder) + News.getString("news_image_name");
        Glide.with(this).load(imageUrl).into(ImgNewsImage);
        TxtNewsTitle.setText(News.getString("news_title"));
        TxtNewsSummary.setText(newsDetailModel.getNewsSummery());
        TxtNewsDescription.setText(Html.fromHtml(newsDetailModel.getDescription()).toString());
        TxtNewsSource.setText(String.format("منبع خبر: %s", newsDetailModel.getNewsSource()));
    }

    private void showLoading()
    {
        AVLoading.show();
    }

    private void hideLoading()
    {
        AVLoading.hide();
    }

    private void showDisconnectedInternetMessage()
    {
        SnackMessage = Snackbar.make(ScrollNews, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        SnackMessage.show();
        SnackMessage.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Networking.isNetworkAvailable(NewsDetailsActivity.this))
                {
                    getNewsDetails(News.getInt("news_id"));
                    SnackMessage.dismiss();
                }
                else
                {
                    showDisconnectedInternetMessage();
                }
            }
        });
    }

}
