package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.Advertisement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementLoadMoreCallBack implements Callback<List<Advertisement>> {

    private OnAdvertisementsLoadedMoreListener AdListener;

    public AdvertisementLoadMoreCallBack(OnAdvertisementsLoadedMoreListener adListener) {
        AdListener = adListener;
    }

    @Override
    public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
        AdListener.onAMoreAdvertisementsLoaded(response);
    }

    @Override
    public void onFailure(Call<List<Advertisement>> call, Throwable t) {

    }

    public interface OnAdvertisementsLoadedMoreListener
    {
        void onAMoreAdvertisementsLoaded(Response<List<Advertisement>> response);
    }
}


