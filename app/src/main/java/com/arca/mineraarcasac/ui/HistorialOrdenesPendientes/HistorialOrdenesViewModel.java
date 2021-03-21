package com.arca.mineraarcasac.ui.HistorialOrdenesPendientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistorialOrdenesViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public HistorialOrdenesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}