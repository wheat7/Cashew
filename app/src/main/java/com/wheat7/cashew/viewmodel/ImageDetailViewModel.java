package com.wheat7.cashew.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableInt;

/**
 * Created by wheat7 on 23/08/2017.
 */

public class ImageDetailViewModel extends BaseObservable {
    public ObservableInt pos = new ObservableInt();
    public ObservableInt total = new ObservableInt();
}
