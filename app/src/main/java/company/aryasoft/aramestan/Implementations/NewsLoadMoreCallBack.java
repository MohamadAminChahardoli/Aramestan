package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsLoadMoreCallBack implements Callback<List<NewsModel>> {

    private OnNewsLoadedMore NewsListener;

    public NewsLoadMoreCallBack(OnNewsLoadedMore newsListener) {
        NewsListener = newsListener;
    }

    @Override
    public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
        NewsListener.onMoreNewsLoaded(response);
    }

    @Override
    public void onFailure(Call<List<NewsModel>> call, Throwable t) {

    }

    public interface OnNewsLoadedMore
    {
        void onMoreNewsLoaded(Response<List<NewsModel>> response);
    }

}
