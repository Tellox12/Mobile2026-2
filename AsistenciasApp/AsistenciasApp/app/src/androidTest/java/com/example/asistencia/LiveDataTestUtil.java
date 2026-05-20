package com.example.asistencia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class LiveDataTestUtil {

    private static final long TIMEOUT_SECONDS = 2;

    @SuppressWarnings("unchecked")
    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        Object[] data  = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = value -> {
            data[0] = value;
            latch.countDown();
        };
        liveData.observeForever(observer);

        boolean done = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        liveData.removeObserver(observer);

        if (!done) throw new RuntimeException("LiveData no emitió valor en " + TIMEOUT_SECONDS + "s");
        return (T) data[0];
    }

    private LiveDataTestUtil() {}
}
