package com.example.jpttopng.mvp.model;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;


public class Model {
    File mFile;
    Bitmap mPic;

    public Model() {

    }


    public void setmFile(File file) {
        this.mFile = file;
    }

    public void setmPic(Bitmap pic) {
        this.mPic = pic;
    }

    public Flowable<File> getFileTest() {
        return Flowable.just(mFile).doOnNext(t -> {
            Thread.sleep(2000);//просто для того,чтобы посмотреть как работает. Картинка появится
            compressAndSaveImageTest();
        });
    }

    public Observable<Bitmap> getPicTest() {
        return Observable.just(mPic);
    }

    public void compressAndSaveImageTest()  {
        try {
            FileOutputStream fos = new FileOutputStream(mFile);
            if (mPic.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
                Log.v("image manager: ", "Compression success");
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
