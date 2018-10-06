package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.Deceased;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCallBackImpl implements Callback<List<Deceased>> {

    private OnResultReceived SearchResultListener;

    public SearchCallBackImpl(OnResultReceived searchResultListener) {
        SearchResultListener = searchResultListener;
    }

    @Override
    public void onResponse(Call<List<Deceased>> call, Response<List<Deceased>> response)
    {
        SearchResultListener.onReceived(response);
    }

    @Override
    public void onFailure(Call<List<Deceased>> call, Throwable t) {

    }

    public interface OnResultReceived
    {
        void onReceived(Response<List<Deceased>> response);
    }

}
