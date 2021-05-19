package com.example.coursework2.UI.getphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.coursework2.R;
import com.example.coursework2.UI.recognition.RecognitionActivity;
import com.example.coursework2.model.User;
import com.example.coursework2.viewmodel.AuthorizationViewModel;
import com.example.coursework2.viewmodel.AuthorizationViewModelFactory;
import com.example.coursework2.viewmodel.GetPhotoViewModel;
import com.example.coursework2.viewmodel.GetPhotoViewModelFactory;

public class GetPhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    private GetPhotoViewModel getPhotoViewModel;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button okBtn;
    public static final int GALLERY = 1;
    public static final int CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);
        getPhotoViewModel =  ViewModelProviders.of(this, new GetPhotoViewModelFactory(this)).get(GetPhotoViewModel.class);
        imageView = findViewById(R.id.imagefromcamera);
        imageView.setImageResource(R.drawable.upload_icon);

        Button takeBtn = findViewById(R.id.take_pic_btn);;
        okBtn = findViewById(R.id.ok_pic_btn);
        okBtn.setEnabled(false);

        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoViewModel.showChooseFragment();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoViewModel.onUploadPhotoClicked();
            }
        });

        getPhotoViewModel.getBlobUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String blobUrl) {
                sp = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putString("bloburl", blobUrl);
                editor.apply();
                openRecognizedItemsPage();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
            onBackPressed();
            Glide.with(this)
                    .asBitmap()
                    .load(getPhotoViewModel.getFilePath())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            Bitmap compressedBitmap = compressPhoto(resource);
                            getPhotoViewModel.saveImage(compressedBitmap);
                            okBtn.setEnabled(true);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) { }
                    });
        }
        else if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {
             onBackPressed();
             imageView.setImageURI(data.getData());
             getPhotoFromGallery(data.getData());
             okBtn.setEnabled(true);
        }
    }

    private void getPhotoFromGallery(Uri uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap selectedImage, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap compressedBitmap = compressPhoto(selectedImage);
                        getPhotoViewModel.saveImage(compressedBitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) { }
                });
    }

    private void openRecognizedItemsPage() {
        Intent intent = new Intent(GetPhotoActivity.this, RecognitionActivity.class);
        startActivity(intent);
    }

    private Bitmap compressPhoto(Bitmap photo) {
        return Bitmap.createScaledBitmap(photo, (int)(photo.getWidth() * 0.3), (int)(photo.getHeight() * 0.3), true);
    }
}