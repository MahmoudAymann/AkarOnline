package com.tkmsoft.akarat.LoginRegister;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tkmsoft.akarat.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.api.Api;
import com.tkmsoft.akarat.model.LoginModel;
import com.tkmsoft.akarat.model.UserInfModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.ConnectNetwork;
import com.tkmsoft.akarat.util.ListSharePreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button singup, login;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    boolean isPasswordShown;
    ImageButton ImagePasswrdVisible;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    String api_token;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        setSharedPreference = new ListSharePreference.Set(LoginFragment.this.getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(LoginFragment.this.getActivity().getApplicationContext());
        ButterKnife.bind(this, root);
        iniui(root);
        return root;
    }

    private void iniui(View root) {

        singup = root.findViewById(R.id.signup_button);
        login = root.findViewById(R.id.login);
        mEmailEditText = root.findViewById(R.id.email_login);
        mPasswordEditText = root.findViewById(R.id.password_login);
        ImagePasswrdVisible = root.findViewById(R.id.btn_pass_visibility);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().replace(R.id.frame_home, new RegisterFragment()).addToBackStack(null).commit();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectNetwork cd = new ConnectNetwork(getActivity());
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                } else {
                    attemptLogin();
                }
            }
        });


        ImagePasswrdVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });


    }

    private void attemptLogin() {
        if (isEmailValid(mEmailEditText.getText().toString()) &&
                isPasswordValid(mPasswordEditText.getText().toString())) {
            try {
                serverLogin();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void serverLogin() {
        progressBar.setVisibility(View.VISIBLE);
        Api api = MyRetrofitClient.getBase().create(Api.class);
        Call<LoginModel> loginModelCall = api.login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        loginModelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            api_token = response.body().getData().getApi_token();
                            setSharedPreference.setIsLogged(true);
                            setSharedPreference.settokenId(api_token);
                            serverUserInfo();
                        } else
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    private void serverUserInfo() {
        progressBar.setVisibility(View.VISIBLE);
        Api api = MyRetrofitClient.getBase().create(Api.class);
        Call<UserInfModel> loginModelCall = api.inf(api_token);
        loginModelCall.enqueue(new Callback<UserInfModel>() {
            @Override
            public void onResponse(@NonNull Call<UserInfModel> call, @NonNull Response<UserInfModel> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getData() != null) {

                        String id = String.valueOf(response.body().getData().getUser_info().getId());
                        String name = response.body().getData().getUser_info().getName();
                        String email = response.body().getData().getUser_info().getEmail();
                        String mobile = response.body().getData().getUser_info().getPhone();
                        String image = response.body().getData().getUser_info().getAvatar();

                        saveUserInfo(id, email, name, mobile, image);

                        Intent myIntent = new Intent();
                        myIntent.setClassName("com.tkmsoft.akarat", MainActivity.class.getCanonicalName());
                        startActivity(myIntent);
                        getActivity().finish();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }

    private void saveUserInfo(String id, String email, String name, String mobile, String image) {
        setSharedPreference.setUId(id);
        setSharedPreference.setname(name);
        setSharedPreference.setemail(email);
        setSharedPreference.setimage(image);
        setSharedPreference.setmobil(mobile);
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@") && email.contains(".com") || mEmailEditText.length() > 0)
            return true;
        else {
            mEmailEditText.setError(getString(R.string.wrong_email_format));
            YoYo.with(Techniques.Shake)
                    .playOn(mEmailEditText);
            return false;
        }

    }

    private boolean isPasswordValid(String password) {
        if (password.length() > 2 || password.length() == 0)
            return true;
        else {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(mPasswordEditText);
            return false;
        }
    }


}

