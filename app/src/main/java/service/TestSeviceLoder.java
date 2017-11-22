package service;


import android.app.Activity;

import http.ObjectLoader;
import http.RetrofitServiceManager;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lei on 2017/11/21.
 */

public class TestSeviceLoder extends ObjectLoader {
    private TestService testService;

    public TestSeviceLoder() {
        testService = RetrofitServiceManager.getInstance().create(TestService.class);
    }

    public Observable<Object> getData(String key, String key2) {
        return observe(testService.getData(key, key2).map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return s;
            }
        }));
    }

    public Observable<String> load() {
        return observe(testService.load().map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                System.out.println(s);
                return s;
            }
        }));
    }

    public interface TestService {
        @FormUrlEncoded
        @POST("abc")
        Observable<String> getData(@Field("key") String i, @Field("key2") String p);

        @GET("/")
        Observable<String> load();
    }
}
