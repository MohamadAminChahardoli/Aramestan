package company.aryasoft.aramestan.Implementations;

import company.aryasoft.aramestan.Models.DetailDeceasedApiModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsCallBackImpl implements Callback<DetailDeceasedApiModel> {

    private OnDetailsReceivedListener DetailListener;

    public DetailsCallBackImpl(OnDetailsReceivedListener detailListener) {
        DetailListener = detailListener;
    }

    @Override
    public void onResponse(Call<DetailDeceasedApiModel> call, Response<DetailDeceasedApiModel> response) {
        DetailListener.onReceived(response);
    }

    @Override
    public void onFailure(Call<DetailDeceasedApiModel> call, Throwable t) {

    }

    public interface OnDetailsReceivedListener
    {
        void onReceived(Response<DetailDeceasedApiModel> response);
    }

}
