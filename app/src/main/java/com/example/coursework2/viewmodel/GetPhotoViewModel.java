package com.example.coursework2.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursework2.R;
import com.example.coursework2.UI.getphoto.ChooseFragment;
import com.example.coursework2.repository.RecognitionRepository;
import com.example.coursework2.utils.ConnectionDetector;
import com.example.coursework2.utils.Dialogs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class GetPhotoViewModel extends ViewModel {

    private static final String IMAGE_DIRECTORY = "/ifinder";
    private final static int GALLERY = 1, CAMERA = 2;
    private RecognitionRepository recognitionRepository;
    private String filePath;

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

    public void showChooseFragment() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChooseFragment fragment = new ChooseFragment(this);
        transaction.add(R.id.fragment_container, fragment).commit();
        transaction.addToBackStack(null);
    }

    public void choosePhotoFromGallery() {
        Dialogs.requestCameraPermissions(activity);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(galleryIntent, GALLERY);
    }

    public void takePhotoFromCamera() {
     try
     {
         Dialogs.requestCameraPermissions(activity);
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

    public void saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            filePath = f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onUploadPhotoClicked() {
        SharedPreferences sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sp.getString("userid", "");
        String token = sp.getString("token", "");
        if (new ConnectionDetector(activity).isConnected()) {
            uploadPhotoToBlob(filePath, userId, token);
        }
        else {
            Toast.makeText(activity, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadPhotoToBlob(String filepath, String id, String token) {
            File photo = new File(filepath);
            recognitionRepository.uploadPhotoToBlob(photo, id, token);
    }
}
