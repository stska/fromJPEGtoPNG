package com.example.jpttopng.mvp.presenter;

import android.graphics.Bitmap;

import com.example.jpttopng.mvp.model.Model;
import com.example.jpttopng.mvp.view.MainView;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Presenter {
    Model mModel;
    private MainView view;

    public Presenter(MainView view) {
        mModel = new Model();
        this.view = view;
    }

    public void reformatPic(File file, Bitmap pic) {

        mModel.setmFile(file);
        mModel.setmPic(pic);
        mModel.getFileTest().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            view.reformatPicture(pic, s);
        });

    }
}
