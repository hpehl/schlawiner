package org.jboss.schlawiner.console;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class Countdown {

    private TimeUnit timeUnit;
    private Long startValue;
    private Disposable disposable;

    public Countdown(long startValue, TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.startValue = startValue;
    }

    public abstract void onTick(long tickValue);

    public abstract void onFinish();

    public void start() {
        Observable.zip(Observable.range(0, startValue.intValue()), Observable.interval(1, timeUnit),
            (integer, aLong) -> {
                long l = startValue - integer;
                return l;
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposable = d;
                }

                @Override
                public void onNext(Long aLong) {
                    onTick(aLong);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    onFinish();
                }
            });
    }

    public void cancel() {
        if (disposable != null) { disposable.dispose(); }
    }
}
