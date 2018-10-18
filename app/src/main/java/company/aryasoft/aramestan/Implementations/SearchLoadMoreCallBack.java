package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.Deceased;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLoadMoreCallBack implements Callback<List<Deceased>> {

    private OnMoreResultReceived MoreListener;

    public SearchLoadMoreCallBack(OnMoreResultReceived moreListener) {
        MoreListener = moreListener;
    }

    @Override
    public void onResponse(Call<List<Deceased>> call, Response<List<Deceased>> response) {
        MoreListener.onMoreReceived(response);
    }

    @Override
    public void onFailure(Call<List<Deceased>> call, Throwable t) {

    }

    public interface OnMoreResultReceived
    {
        void onMoreReceived(Response<List<Deceased>> response);
    }

}
