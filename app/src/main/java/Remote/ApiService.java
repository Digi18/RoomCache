package Remote;

import java.util.List;

import Model.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("getUsers")
    Call<List<User>> getUser();

    @POST("retrofitUsers")
    @FormUrlEncoded
    Observable<String> saveRemoteData(@Field("name") String name,
                                      @Field("age") String age);

    @POST("deleteRemoteUser")
    @FormUrlEncoded
    Observable<String> deleteRemoteUser(@Field("_id") String id);
}
