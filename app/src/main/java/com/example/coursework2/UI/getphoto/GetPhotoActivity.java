package com.example.coursework2.UI.getphoto;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            // вот тут надо вью модель
            imageView.setImageBitmap(photo);
            String filePath = getPhotoViewModel.saveImage(compressPhoto(photo));
            Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();

        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));

            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(selectedImage);
            Bitmap compressedBitmap = compressPhoto(selectedImage);
            String filePath = getPhotoViewModel.saveImage(compressedBitmap);
            Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //onSelectFromGalleryResult(data);
        }
    }


    private void openRecognizedItemsPage() {
        Intent intent = new Intent(GetPhotoActivity.this, RecognitionActivity.class);
        startActivity(intent);
    }

    private Bitmap compressPhoto(Bitmap photo) {
           Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, (int)(photo.getWidth() * 0.3), (int)(photo.getHeight() * 0.3), true);
           if (photo.getWidth() > photo.getHeight()) {
               Matrix matrix = new Matrix();
               matrix.postRotate(90);
               Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
               imageView.setRotation(90);
               return rotatedBitmap;
           }
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