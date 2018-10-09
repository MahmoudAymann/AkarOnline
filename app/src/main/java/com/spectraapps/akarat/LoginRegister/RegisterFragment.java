package com.spectraapps.akarat.LoginRegister;


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
import com.spectraapps.akarat.LoginActivity;
import com.spectraapps.akarat.MainActivity;
import com.spectraapps.akarat.R;
import com.spectraapps.akarat.api.Api;
import com.spectraapps.akarat.model.RegisterModel;
import com.spectraapps.akarat.model.UserInfModel;
import com.spectraapps.akarat.network.MyRetrofitClient;
import com.spectraapps.akarat.util.ConnectNetwork;
import com.spectraapps.akarat.util.ListSharePreference;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    EditText name, email, mobile, password, confirmpassword;
    Button login, singin;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    ProgressBar progressBar;
    String apitoken;
    boolean isPasswordShown;
    boolean isPasswordShown1;
    ImageButton ImagePasswrdVisible;
    ImageButton ImagePasswrdVisible1;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        iniui(root);
        return root;
    }

    private void iniui(View root) {
        name = root.findViewById(R.id.name_register);
        email = root.findViewById(R.id.email_register);
        mobile = root.findViewById(R.id.mobileET);
        password = root.findViewById(R.id.password_login);
        confirmpassword = root.findViewById(R.id.password_Register);
        singin = root.findViewById(R.id.signin_button);
        login = root.findViewById(R.id.login_register);
        setSharedPreference = new ListSharePreference.Set(RegisterFragment.this.getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(RegisterFragment.this.getActivity().getApplicationContext());
        progressBar = root.findViewById(R.id.progressBar);
        ImagePasswrdVisible = root.findViewById(R.id.btn_pass_visibility);
        ImagePasswrdVisible1 = root.findViewById(R.id.btn_pass_visibility1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectNetwork cd = new ConnectNetwork(getActivity());
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getActivity(), "no internet connect", Toast.LENGTH_SHORT).show();
                } else {
                    if (name.length() > 0 && email.length() > 0 && mobile.length() > 0 && password.length() > 0 && confirmpassword.length()> 0)
                        attemptLogin();
                }

            }
        });
        ImagePasswrdVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_black_24dp);
                    password.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });
        ImagePasswrdVisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown1) {
                    ImagePasswrdVisible1.setImageResource(R.drawable.ic_visibility_black_24dp);
                    confirmpassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown1 = false;
                } else {
                    ImagePasswrdVisible1.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    confirmpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown1 = true;
                }
            }
        });
    }


    @OnClick(R.id.signin_button)
    protected void onSignin_button() {
        getFragmentManager().beginTransaction().replace(R.id.frame_home, new LoginFragment())
                .addToBackStack(null).commit();

    }

    private void attemptLogin() {

        if (isEmailValid(email.getText().toString()) &&
                isPasswordValid(password.getText().toString())) {
            serverRegister();
        }
    }

    private void serverRegister() {
        progressBar.setVisibility(View.VISIBLE);
        String mname = name.getText().toString();
        String memail = email.getText().toString();
        String mmobile = mobile.getText().toString();
        String mpassword = password.getText().toString();
        String mconfirmpassword = confirmpassword.getText().toString();
        Api api = MyRetrofitClient.getBase().create(Api.class);
        Call<RegisterModel> RegisterModelCall = api.register(mname, memail, mmobile, mpassword, mconfirmpassword);
        RegisterModelCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<RegisterModel> call, @NonNull Response<RegisterModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            apitoken = response.body().getData().getApi_token();
                            setSharedPreference.setIsLogged(true);
                            setSharedPreference.settokenId(apitoken);
                            serverUserInfo();
                        } else
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.errorregister, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    private void serverUserInfo() {
        Api api = MyRetrofitClient.getBase().create(Api.class);
        Call<UserInfModel> loginModelCall = api.inf(apitoken);
        loginModelCall.enqueue(new Callback<UserInfModel>() {
            @Override
            public void onResponse(@NonNull Call<UserInfModel> call, @NonNull Response<UserInfModel> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {

                        String id = String.valueOf(response.body().getData().getUser_info().getId());
                        String name = response.body().getData().getUser_info().getName();
                        String email = response.body().getData().getUser_info().getEmail();
                        String mobile = response.body().getData().getUser_info().getPhone();
                        String image = response.body().getData().getUser_info().getAvatar();

                        saveUserInfo(id, email, name, mobile, image);

                        Intent myIntent = new Intent();
                        myIntent.setClassName("com.spectraapps.akarat", "com.spectraapps.akarat.MainActivity");
                        startActivity(myIntent);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfModel> call, @NonNull Throwable t) {
//              Toast.makeText(MainActivity.this, "error connection", Toast.LENGTH_SHORT).show();
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

    private boolean isEmailValid(String emaill) {
        if (emaill.contains("@") && emaill.contains(".com"))
            return true;

        else {
            email.setError(getString(R.string.wrong_email_format));
            YoYo.with(Techniques.Shake).playOn(email);
            return false;
        }

    }

    private boolean isPasswordValid(String passwordd) {
        if (passwordd.length() > 2 || passwordd.length() == 0)
            return true;
        else {
            password.setError(getString(R.string.error_invalid_password));
            YoYo.with(Techniques.Shake)
                    .playOn(password);
            return false;
        }
    }


}
