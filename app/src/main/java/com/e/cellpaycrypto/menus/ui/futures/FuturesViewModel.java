package com.e.cellpaycrypto.menus.ui.futures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FuturesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public FuturesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Redeem Page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
