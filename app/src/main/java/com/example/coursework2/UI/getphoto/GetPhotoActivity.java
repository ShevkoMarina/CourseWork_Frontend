package com.example.coursework2.UI.getphoto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.coursework2.R;
import com.example.coursework2.UI.recognition.RecognitionActivity;
import com.example.coursework2.viewmodel.GetPhotoViewModel;
import com.example.coursework2.databinding.ActivityRecognitionBinding;
import com.example.coursework2.viewmodel.GetPhotoViewModelFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GetPhotoActivity extends AppCompatActivity {

    private Button btn;
    private ImageView imageView;
    private GetPhotoViewModel getPhotoViewModel;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);

        ActivityRecognitionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recognition);
        getPhotoViewModel = ViewModelProviders.of(this, new GetPhotoViewModelFactory(this)).get(GetPhotoViewModel.class);
        binding.setGetphotomodel(getPhotoViewModel);
        binding.setLifecycleOwner(this);

        imageView = (ImageView) findViewById(R.id.imagefromcamera);
        imageView.setImageResource(R.drawable.upload_image);

        getPhotoViewModel.getBlobUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String blobUrl) {
                sp = getSharedPreferences("BlobData", Context.MODE_PRIVATE);
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
        // Camera
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            Glide.with(this)
                    .asBitmap()
                    .load(getPhotoViewModel.getFilePath())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            Bitmap compressedBitmap = compressPhoto(resource);
                            getPhotoViewModel.saveImage(compressedBitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
            // Gallery
        }
        else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
             imageView.setImageURI(data.getData());
             getPhotoFromGallery(data.getData());

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
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private Bitmap rotateBitmap(Bitmap sourceBitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
    }


    private void openRecognizedItemsPage() {
        Intent intent = new Intent(GetPhotoActivity.this, RecognitionActivity.class);
        startActivity(intent);
    }

    private Bitmap compressPhoto(Bitmap photo) {
           Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, (int)(photo.getWidth() * 0.3), (int)(photo.getHeight() * 0.3), true);
           return scaledBitmap;
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap selectedPhoto = null;
        if (data != null) {
            try {
                selectedPhoto = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(selectedPhoto);
    }
}