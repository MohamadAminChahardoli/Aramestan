package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCallBackImpl implements Callback<List<NewsModel>> {

    private OnNewsReceivedListener NewsListener;

    public NewsCallBackImpl(OnNewsReceivedListener newsListener) {
        NewsListener = newsListener;
    }

    @Override
    public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
        NewsListener.onNewsReceived(response);
    }

    @Override
    public void onFailure(Call<List<NewsModel>> call, Throwable t) {

    }

    public interface OnNewsReceivedListener
    {
        void onNewsReceived(Response<List<NewsModel>> response);
    }

}
