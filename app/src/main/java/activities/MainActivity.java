package activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import config.AppConfig;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import service.TestSeviceLoder;

/**
 * Created by lei on 2017/11/21.
 */

public class MainActivity extends Activity {
    private TestSeviceLoder testSeviceLoder;
    private CompositeSubscription sCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("creat");
        testSeviceLoder = new TestSeviceLoder();
        if (sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()) {
            sCompositeSubscription = new CompositeSubscription();
        }
        AppConfig.KEY = "325";
        load();

    }

    private void getData() {
        Subscription subscription = testSeviceLoder.getData("1", "2").subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        sCompositeSubscription.add(subscription);
    }

    private void load() {
        Subscription subscription = testSeviceLoder.load().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println(throwable.toString());
            }
        });
        sCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sCompositeSubscription != null)
            sCompositeSubscription.unsubscribe();
    }
}
