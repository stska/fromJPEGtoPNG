package com.example.jpttopng.mvp.view;


import android.graphics.Bitmap;

import java.io.File;

public interface MainView {
    void reformatPicture(Bitmap pic, File file);
}
