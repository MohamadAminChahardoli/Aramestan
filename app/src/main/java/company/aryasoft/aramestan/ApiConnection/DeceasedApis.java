package company.aryasoft.aramestan.ApiConnection;

import java.util.List;

import company.aryasoft.aramestan.Models.Advertisement;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.Models.DetailDeceasedApiModel;
import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.Models.NewsDetailModel;
import company.aryasoft.aramestan.Models.NewsModel;
import company.aryasoft.aramestan.Models.SearchModel;
import company.aryasoft.aramestan.Models.SliderDataModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeceasedApis {

    @Headers("User-Agent: <Aramestan>")
    @POST("api/GraveyardApi/SearchDeceased")
    Call<List<Deceased>> lookForDeceased(@Body SearchModel searchModel, @Query("skipItem") int skipItems,@Query("takeItem") int takeItems);

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetDeceasedDetail")
    Call<DetailDeceasedApiModel> getDeceasedDetails(@Query("deadId") int deadId);

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetAnnouncements")
    Call<List<Announcement>> getAnnouncements(@Query("skipItem") int skipItems, @Query("takeItem") int takeItems);

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetSliders")
    Call<List<SliderDataModel>> getSlider();

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetNews")
    Call<List<NewsModel>> getAllNews(@Query("skipItem") int skipItems, @Query("takeItem") int takeItems);

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetNewsDetail")
    Call<NewsDetailModel> getNewsDetails(@Query("newsId")int newsId);

    @Headers("User-Agent: <Aramestan>")
    @GET("api/GraveyardApi/GetAdvertisements")
    Call<List<Advertisement>> getAdvertisements(@Query("skipItem") int skipItems, @Query("takeItem") int takeItems);
}
