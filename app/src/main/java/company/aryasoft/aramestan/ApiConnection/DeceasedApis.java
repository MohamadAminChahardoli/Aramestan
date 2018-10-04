package company.aryasoft.aramestan.ApiConnection;

import java.util.List;

import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.Models.DetailDeceasedApiModel;
import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.Models.SearchModel;
import company.aryasoft.aramestan.Models.SliderDataModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeceasedApis {

    @POST("api/GraveyardApi/SearchDeceased")
    Call<List<Deceased>> lookForDeceased(@Body SearchModel searchModel, @Query("skipNumber") int skipItems,@Query("takeNumber") int takeItems);

    @GET("api/GraveyardApi/GetDeceasedDetail")
    Call<DetailDeceasedApiModel> getDeceasedDetails(@Query("deadId") int deadId);

    @GET("api/GraveyardApi/GetAnnouncements")
    Call<List<Announcement>> getAnnouncements(@Query("skipItem") int skipItems, @Query("takeItem") int takeItems);

    @GET("api/GraveyardApi/GetSliders")
    Call<List<SliderDataModel>> getSlider();
}
