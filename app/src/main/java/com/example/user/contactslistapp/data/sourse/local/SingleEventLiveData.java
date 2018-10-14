package com.example.user.contactslistapp.data.sourse.local;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleEventLiveData<T> extends MutableLiveData {
    private AtomicBoolean mPending = new AtomicBoolean(false);

    public SingleEventLiveData() {
        super();
    }

    @Override
    public void postValue(Object value) {
        super.postValue(value);
    }

    @Override
    public void setValue(Object value) {
        mPending.set(true);
        super.setValue(value);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        super.observe(owner, t -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }
}
