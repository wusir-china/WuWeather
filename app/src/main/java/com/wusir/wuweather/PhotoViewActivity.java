package com.wusir.wuweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.wusir.StatusBarCompat;

public class PhotoViewActivity extends AppCompatActivity {
    private PhotoView mPhotoView;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        StatusBarCompat.compat(this,R.color.colorGreen);
        mPhotoView= (PhotoView) findViewById(R.id.photoview);
        mAttacher=new PhotoViewAttacher(mPhotoView);
        mPhotoView.setImageResource(R.mipmap.a);
        mAttacher.update();
    }
}
