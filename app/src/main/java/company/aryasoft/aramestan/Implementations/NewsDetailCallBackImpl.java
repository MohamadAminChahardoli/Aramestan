package company.aryasoft.aramestan.Implementations;

import company.aryasoft.aramestan.Models.NewsDetailModel;
import company.aryasoft.aramestan.Models.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailCallBackImpl implements Callback<NewsDetailModel> {

    private OnNewsDetailReceivedListener DetailListener;

    public NewsDetailCallBackImpl(OnNewsDetailReceivedListener detailListener) {
        DetailListener = detailListener;
    }

    @Override
    public void onResponse(Call<NewsDetailModel> call, Response<NewsDetailModel> response) {
        DetailListener.onNewsDetailReceived(response);
    }

    @Override
    public void onFailure(Call<NewsDetailModel> call, Throwable t) {

    }

    public interface OnNewsDetailReceivedListener
    {
        void onNewsDetailReceived(Response<NewsDetailModel> response);
    }

}
