package com.example.jpttopng;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jpttopng.mvp.presenter.Presenter;
import com.example.jpttopng.mvp.view.MainView;

import java.io.File;
import java.io.FileNotFoundException;


public class MainActivity extends AppCompatActivity implements MainView {
    private ImageView picture;
    private Button button;
    private File file;
    private static String MY_PIC_DIR = "Pictures";
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new Presenter(this);
        setContentView(R.layout.activity_main);
        picture = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> pickImage());

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap newProfilePic = extras.getParcelable("data");
                file = new File(getExternalCacheDir(), "newImage" + ".jpg");
                mPresenter.reformatPic(file, newProfilePic);
            }
        }
    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 1);
    }

    public String addImageToGallery(ContentResolver cr, File filepath) {
        try {
            return MediaStore.Images.Media.insertImage(cr, filepath.toString(),
                    filepath.getName(), "The image has been successfully added to the Gallery");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void reformatPicture(Bitmap pic, File file) {
        String sentToGallery = addImageToGallery(getContentResolver(), file);
        Log.v("image sent result", sentToGallery);
        picture.setImageBitmap(pic);
    }

}