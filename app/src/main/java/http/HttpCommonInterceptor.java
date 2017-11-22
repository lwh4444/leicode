package http;

/**
 * Created by lei on 2017/11/1.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 拦截器
 * <p>
 * 向请求体里添加公共参数
 * Created by lei on 17/11/08.
 */

public class HttpCommonInterceptor implements Interceptor {

    private Map<String, String> mBodyParamsMap = new HashMap<>();

    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request.Builder newRequest = originRequest.newBuilder();
        System.out.println(originRequest.url());
        if (originRequest.method().equals("POST")) {
            RequestBody body = originRequest.body();
            if (body != null && body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                Map<String, String> formBodyParamMap = new HashMap<>();
                int bodySize = formBody.size();
                for (int i = 0; i < bodySize; i++) {
                    formBodyParamMap.put(formBody.name(i), formBody.value(i));
                }

                Map<String, String> newFormBodyParamMap = mBodyParamsMap;
                if (newFormBodyParamMap != null) {
                    formBodyParamMap.putAll(newFormBodyParamMap);
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : formBodyParamMap.entrySet()) {
                        bodyBuilder.add(entry.getKey(), entry.getValue());
                    }
                    newRequest.method(originRequest.method(), bodyBuilder.build());
                }
            }
            return chain.proceed(newRequest.build());
        } else {
            newRequest.method(originRequest.method(), originRequest.body());
            if (mHeaderParamsMap.size() > 0) {
                for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                    newRequest.header(params.getKey(), params.getValue());
                }
            }
            return chain.proceed(newRequest.build());
        }

    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addBodyParams(String key, String value) {
            mHttpCommonInterceptor.mBodyParamsMap.put(key, value);
            return this;
        }

        public Builder addBodyParams(String key, int value) {
            return addBodyParams(key, String.valueOf(value));
        }

        public Builder addBodyParams(String key, float value) {
            return addBodyParams(key, String.valueOf(value));
        }

        public Builder addBodyParams(String key, long value) {
            return addBodyParams(key, String.valueOf(value));
        }

        public Builder addBodyParams(String key, double value) {
            return addBodyParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }


        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}