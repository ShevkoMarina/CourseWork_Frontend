package com.example.coursework2.UI.getphoto;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.coursework2.R;
import com.example.coursework2.utils.Dialogs;
import com.example.coursework2.viewmodel.GetPhotoViewModel;

@SuppressLint("ValidFragment")
public class ChooseFragment extends Fragment {

    private GetPhotoViewModel viewModel;

    @SuppressLint("ValidFragment")
    public ChooseFragment(GetPhotoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);

        ImageView cameraBtn = view.findViewById(R.id.cameraV);
        ImageView galleryBtn = view.findViewById(R.id.galleryV);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.takePhotoFromCamera();

            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.choosePhotoFromGallery();

            }
        });
        return view;
    }
}