package com.example.coursework2.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coursework2.UI.getphoto.GetPhotoActivity;
import com.example.coursework2.repository.RecognitionRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.UUID;

import okhttp3.MediaType;
import tk.jamun.ui.gallery.classes.ActivityGallery;

public class GetPhotoViewModel extends ViewModel {

    private static final String IMAGE_DIRECTORY = "/ifinder";
    private final static int GALLERY = 1, CAMERA = 2;
    private RecognitionRepository recognitionRepository;
    private String filePath;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public String getFilePath() {
        return filePath;
    }

    public LiveData<String> getBlobUrl() {
        return recognitionRepository.getBlobUrl();
    }

    @SuppressLint("StaticFieldLeak")
    private Activity activity;

    public GetPhotoViewModel(Activity activity) {
        this.activity = activity;
        recognitionRepository = RecognitionRepository.getInstance();
    }

    public void showCameraDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("Select Action");

        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(galleryIntent, GALLERY);

    }

    private void takePhotoFromCamera() {
        /*
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, CAMERA);

         */
     try
     {
         String fileName = "photo";
         File storageDirectory = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
         File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
         filePath = imageFile.getAbsolutePath();

         Uri imageUri = FileProvider.getUriForFile(activity, "com.example.coursework2.fileprovider", imageFile);
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
         activity.startActivityForResult(intent, CAMERA);
     }
     catch (Exception ex) { }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            filePath = f.getAbsolutePath();
            return filePath;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void onUploadPhotoClicked() {
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        uploadPhotoToBlob(filePath, userId);
    }

    public void uploadPhotoToBlob(String filepath, String id) {
        try {
            File photo = new File(filepath);
            // если пусто то вывести тост
            recognitionRepository.uploadPhotoToBlob(photo, id);

        } catch (Exception ex) {
            // do nothing
        }
    }
}
