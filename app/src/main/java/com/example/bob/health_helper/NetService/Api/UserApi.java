package com.example.bob.health_helper.NetService.Api;

        import com.example.bob.health_helper.Bean.Response;
        import com.example.bob.health_helper.Bean.User;

        import io.reactivex.Observable;
        import retrofit2.http.Body;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.Query;

public interface UserApi {
    @POST("/users")
    Observable<Response> addUser(@Body User user);
    @GET("/users/sig")
    Observable<Response<String>> getSig(@Query("user_name") String name);
}
