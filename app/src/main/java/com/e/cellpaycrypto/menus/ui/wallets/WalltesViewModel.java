package com.e.cellpaycrypto.menus.ui.wallets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WalltesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public WalltesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Transfer Page");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
