package company.aryasoft.aramestan.Activities;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import company.aryasoft.aramestan.ApiConnection.ApiServiceGenerator;
import company.aryasoft.aramestan.ApiConnection.DeceasedApis;
import company.aryasoft.aramestan.Implementations.NewsDetailCallBackImpl;
import company.aryasoft.aramestan.Models.NewsDetailModel;
import company.aryasoft.aramestan.R;
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
    }

    private void getNewsDetails(int newsId)
    {
        NewsDetailCall = Api.getNewsDetails(newsId);
        NewsDetailCall.enqueue(new NewsDetailCallBackImpl(this));
        showLoading();
    }

    private void bindViews(NewsDetailModel newsDetailModel)
    {
        String imageUrl = getString(R.string.ImageFolderName) + getString(R.string.NewsImageFolder) + News.getString("news_image_name");
        Glide.with(this).load(imageUrl).into(ImgNewsImage);
        TxtNewsTitle.setText(News.getString("news_title"));
        TxtNewsSummary.setText(newsDetailModel.getNewsSummery());
        TxtNewsDescription.setText(Html.fromHtml(newsDetailModel.getDescription()).toString());
        TxtNewsSource.setText(newsDetailModel.getNewsSource());
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
