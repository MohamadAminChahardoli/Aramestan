package company.aryasoft.aramestan.Implementations;

import java.util.List;


import company.aryasoft.aramestan.Models.Advertisement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementsCallBackImpl implements Callback<List<Advertisement>> {

    private OnAdvertisementsReceivedListener AdListener;

    public AdvertisementsCallBackImpl(OnAdvertisementsReceivedListener adListener) {
        AdListener = adListener;
    }

    @Override
    public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
        AdListener.onAdvertisementsReceived(response);
    }

    @Override
    public void onFailure(Call<List<Advertisement>> call, Throwable t) {

    }

    public interface OnAdvertisementsReceivedListener
    {
        void onAdvertisementsReceived(Response<List<Advertisement>> response);
    }

}
