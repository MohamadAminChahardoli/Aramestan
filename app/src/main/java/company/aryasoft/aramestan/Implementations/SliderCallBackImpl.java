package company.aryasoft.aramestan.Implementations;

import android.widget.Button;

import java.util.List;

import javax.security.auth.callback.Callback;

import company.aryasoft.aramestan.Models.SliderDataModel;
import retrofit2.Call;
import retrofit2.Response;

public class SliderCallBackImpl implements retrofit2.Callback<List<SliderDataModel>> {

    private OnSlidesDownloadedListener SlidesListener;

    public SliderCallBackImpl(OnSlidesDownloadedListener slidesListener) {
        SlidesListener = slidesListener;
    }

    @Override
    public void onResponse(Call<List<SliderDataModel>> call, Response<List<SliderDataModel>> response) {
        SlidesListener.onSlidesDownloaded(response);
    }

    @Override
    public void onFailure(Call<List<SliderDataModel>> call, Throwable t) {

    }

    public interface OnSlidesDownloadedListener
    {
        public void onSlidesDownloaded(Response<List<SliderDataModel>> response);
    }

}
