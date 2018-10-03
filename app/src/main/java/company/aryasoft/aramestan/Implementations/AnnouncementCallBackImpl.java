package company.aryasoft.aramestan.Implementations;

import java.util.List;

import company.aryasoft.aramestan.Models.Announcement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementCallBackImpl implements Callback<List<Announcement>> {

    private OnAnnouncementReceivedListener AnnouncementListener;

    public AnnouncementCallBackImpl(OnAnnouncementReceivedListener announcementListener) {
        AnnouncementListener = announcementListener;
    }

    @Override
    public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
        AnnouncementListener.onReceived(response);
    }

    @Override
    public void onFailure(Call<List<Announcement>> call, Throwable t) {

    }

    public interface OnAnnouncementReceivedListener
    {
        void onReceived(Response<List<Announcement>> response);
    }

}
