package uk.co.ribot.androidboilerplate.data.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.Photo;

public interface UnSplashService {
        @GET("photos")
        Observable<List<Photo>> listRepos(@Query("page") String page, @Query("client_id") String clientId);
}
