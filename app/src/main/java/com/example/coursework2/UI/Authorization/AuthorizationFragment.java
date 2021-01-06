package com.example.coursework2.UI.Authorization;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursework2.R;

public class AuthorizationFragment extends Fragment {


    private EditText mLogin;
    private EditText mPassword;
    private Button mSignUpButton;
    private Button mLogInButton;

    // todo: отом убрать отсбда
    private static final String SERVER_URL = "https://courseworkapi.azurewebsites.net/";

    public static AuthorizationFragment newInstance() {
        Bundle args = new Bundle();
        AuthorizationFragment fragment = new AuthorizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // todo: сделать биндинги
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_authorization, container, false);

        mLogin = view.findViewById(R.id.login);
        mPassword = view.findViewById(R.id.password);
        mSignUpButton = view.findViewById(R.id.sign_btn);
        mLogInButton = view.findViewById(R.id.login_btn);

        mLogInButton.setOnClickListener(LogButtonClicked);
        mSignUpButton.setOnClickListener(SignButtonClicked);;
        return view;
    }

    private View.OnClickListener LogButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    // todo: добавить модели и делать запросы через ретрофит2
    // todo: вынести это в вьюмодель
    private View.OnClickListener SignButtonClicked = new View.OnClickListener() {
        @Override
        public  void onClick(View view) {

            /*
            RequestBody formBody = new FormBody.Builder()
                    .add("login", mLogin.getText().toString())
                    .add("password", mPassword.getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url(SERVER_URL + "users/AddUser")
                    .post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                Handler handler = new Handler(getActivity().getMainLooper());

                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                try {
                                    Toast.makeText(getActivity(), response.body().string(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });

             */
        }

        // Toast.makeText(getContext(), response.body().string(), Toast.LENGTH_LONG).show();

            /*
            if (isEmailValid() && isPasswordValid()) {
                boolean isAdded = mSharedPreferencesHelper.addUser(new User(
                        mLogin.getText().toString(),
                        mPassword.getText().toString()
                ));

                if (isAdded) {
                    Toast.makeText(getActivity(), "User was sucessfully added", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Already exists", Toast.LENGTH_LONG ).show();
                }
            }

             */

    };

    /*
    private AuthorizationViewModel mViewModel;

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authorization_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        // TODO: Use the ViewModel
    }


     */
}