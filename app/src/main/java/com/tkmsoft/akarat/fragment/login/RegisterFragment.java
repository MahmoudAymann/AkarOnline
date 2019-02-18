package com.tkmsoft.akarat.fragment.login;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.model.RegisterModel;
import com.tkmsoft.akarat.model.UserInfModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.BaseBackPressedListener;
import com.tkmsoft.akarat.util.ConnectNetwork;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;
import com.tkmsoft.akarat.util.MyApp;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    EditText nameET, emailET, mobileET, passwordET, confirmPasswordET;
    Button login, singin;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    ProgressBar progressBar;
    String apitoken;
    boolean isPasswordShown;
    boolean isPasswordShown1;
    ImageButton ImagePasswrdVisible;
    ImageButton ImagePasswrdVisible1;
    private FragmentActivity mContext;
    private String mLanguage;
    private MoveToFragment moveToFragment;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof FragmentActivity)
            mContext = (FragmentActivity) context;
        super.onAttach(context);

        setSharedPreference = new ListSharePreference.Set(mContext);
        getSharedPreference = new ListSharePreference.Get(mContext);
        moveToFragment = new MoveToFragment(mContext);
        fireBackButtonEvent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLanguage = getSharedPreference.getLanguage();
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
        nameET = root.findViewById(R.id.name_register);
        emailET = root.findViewById(R.id.email_register);
        mobileET = root.findViewById(R.id.mobileET);
        passwordET = root.findViewById(R.id.password_login);
        confirmPasswordET = root.findViewById(R.id.password_Register);
        singin = root.findViewById(R.id.signin_button);
        login = root.findViewById(R.id.login_register);

        progressBar = root.findViewById(R.id.progressBar);
        ImagePasswrdVisible = root.findViewById(R.id.btn_pass_visibility);
        ImagePasswrdVisible1 = root.findViewById(R.id.btn_pass_visibility1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectNetwork cd = new ConnectNetwork(getActivity());
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                } else {
                    if (nameET.length() > 0 &&
                            emailET.length() > 0 &&
                            mobileET.length() > 0 &&
                            passwordET.length() > 0 &&
                            confirmPasswordET.length() > 0)
                        attemptLogin();
                }

            }
        });
        ImagePasswrdVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_black_24dp);
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    ImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });
        ImagePasswrdVisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown1) {
                    ImagePasswrdVisible1.setImageResource(R.drawable.ic_visibility_black_24dp);
                    confirmPasswordET.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown1 = false;
                } else {
                    ImagePasswrdVisible1.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    confirmPasswordET.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown1 = true;
                }
            }
        });
    }


    @OnClick(R.id.signin_button)
    protected void onSignin_button() {
        getFragmentManager().beginTransaction().replace(R.id.login_frame, new LoginFragment())
                .addToBackStack(null).commit();

    }

    private void attemptLogin() {
        if (isEmailValid(emailET.getText().toString()) &&
                isPasswordValid(passwordET.getText().toString())) {
            if (isEditTxtEmpty(emailET))
                if (isEditTxtEmpty(nameET))
                    if (isEditTxtEmpty(mobileET))
                        if (isEditTxtEmpty(passwordET))
                            if (isEditTxtEmpty(confirmPasswordET))
                                serverRegister();
                            else
                                Toast.makeText(mContext, "" + getString(R.string.enter_pass_confirm), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(mContext, "" + getString(R.string.enter_pass), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mContext, "" + getString(R.string.enter_mobile_num), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext, "" + getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "" + getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEditTxtEmpty(EditText editText) {
        return editText.getText().toString().trim().length() != 0;
    }

    private void serverRegister() {
        progressBar.setVisibility(View.VISIBLE);
        String mname = nameET.getText().toString();
        String memail = emailET.getText().toString();
        String mmobile = mobileET.getText().toString();
        String mpassword = passwordET.getText().toString();
        String mconfirmpassword = confirmPasswordET.getText().toString();
        Api api = MyRetrofitClient.getAuth().create(Api.class);
        final Call<RegisterModel> RegisterModelCall = api.register(mname, memail, mmobile, mpassword, mconfirmpassword);
        RegisterModelCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<RegisterModel> call, @NonNull Response<RegisterModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        RegisterModel.StatusBean statusBean = response.body().getStatus();
                        if (statusBean != null) {
                            if (statusBean.getType().equals("success")) {
                                Toast.makeText(mContext, "" + getString(R.string.registered_success), Toast.LENGTH_SHORT).show();
                                RegisterModel.DataBean dataBean = response.body().getData();
                                if (dataBean != null) {
                                    apitoken = dataBean.getApi_token();
                                    setSharedPreference.setIsLogged(true);
                                    setSharedPreference.settokenId(apitoken);
                                    serverUserInfo();
                                }
                            } else {
                                String msg = statusBean.getTitle();
                                if (mLanguage.equals("ar")) {
                                    Toast.makeText(mContext, "" + statusBean.getTitle(), Toast.LENGTH_SHORT).show();
                                } else {
                                    if (msg.equals("البريد الالكترونى مستخدم بالفعل")) {
                                        Toast.makeText(mContext, "" + getString(R.string.email_is_taken), Toast.LENGTH_SHORT).show();
                                    } else if (msg.equals("هذا الرقم مستخدم بالفعل")) {
                                        Toast.makeText(mContext, "" + getString(R.string.phone_is_taken), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });


    }

    private void serverUserInfo() {
        Api api = MyRetrofitClient.getAuth().create(Api.class);
        Call<UserInfModel> loginModelCall = api.inf(apitoken);
        loginModelCall.enqueue(new Callback<UserInfModel>() {
            @Override
            public void onResponse(@NonNull Call<UserInfModel> call, @NonNull Response<UserInfModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserInfModel.StatusBean statusBean = response.body().getStatus();
                    if (statusBean != null) {
                        if (statusBean.getTitle().equals("success")) {
                            UserInfModel.DataBean dataBean = response.body().getData();
                            if (dataBean != null) {
                                String id = String.valueOf(dataBean.getUser_info().getId());
                                String name = dataBean.getUser_info().getName();
                                String email = dataBean.getUser_info().getEmail();
                                String mobile = dataBean.getUser_info().getPhone();
                                String image = dataBean.getUser_info().getAvatar();

                                saveUserInfo(id, email, name, mobile, image);

                                Intent myIntent = new Intent();
                                myIntent.setClassName(MyApp.getContext().getPackageName(),
                                        MainActivity.class.getCanonicalName());
                                startActivity(myIntent);
                                getActivity().finish();
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfModel> call, @NonNull Throwable t) {
//              Toast.makeText(MainActivity.this, "error connection", Toast.LENGTH_SHORT).getShow();
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
            emailET.setError(getString(R.string.wrong_email_format));
            YoYo.with(Techniques.Shake).playOn(emailET);
            return false;
        }

    }

    private boolean isPasswordValid(String passwordd) {
        if (passwordd.length() > 2 || passwordd.length() == 0)
            return true;
        else {
            passwordET.setError(getString(R.string.error_invalid_password));
            YoYo.with(Techniques.Shake)
                    .playOn(passwordET);
            return false;
        }
    }

    private void fireBackButtonEvent() {
            ((MainActivity) mContext).setOnBackPressedListener(new BaseBackPressedListener(mContext) {
                @Override
                public void onBackPressed() {
                    moveToFragment.moveInLogin(new LoginFragment());
                }
            });
    }//end back pressed


}
