package com.tkmsoft.akarat.fragment.main;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.model.ProfileModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.ListSharePreference;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final int REQUEST_GALLERY_CODE = 7778;
    String mId, mName, mEmail, mMobile, mImage;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    CircleImageView imageView;
    EditText NameTextView, EmailTextView, number, oldpasswordET, newpassET;
    Button update;
    MainViewCallBack mMainViewsCallBack;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.GET_ACCOUNTS
    };
    String image_path = "";
    private ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        iniui(root);
        return root;
    }

    private void iniui(View root) {
        setSharedPreference = new ListSharePreference.Set(getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(getActivity().getApplicationContext());
        imageView = root.findViewById(R.id.imageView);
        NameTextView = root.findViewById(R.id.name);
        EmailTextView = root.findViewById(R.id.email);
        number = root.findViewById(R.id.number);
        oldpasswordET = root.findViewById(R.id.password1);
        newpassET = root.findViewById(R.id.password2);
        update = root.findViewById(R.id.updatepf);
        progressBar = root.findViewById(R.id.progerss3);
        getUserInfo();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });
    }

    private void serverUpdateNoImage() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            String api1 = getSharedPreference.gettokenId();
            Api api = MyRetrofitClient.getAuth().create(Api.class);
            Call<ProfileModel> loginModelCall = api.profileNoImage(NameTextView.getText().toString(), EmailTextView.getText().toString(), number.getText().toString(), oldpasswordET.getText().toString(), newpassET.getText().toString(), api1);
            loginModelCall.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus().getType().equals("success")) {
                                Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                                String id = String.valueOf(response.body().getData().getUser_info().getId());
                                String name = response.body().getData().getUser_info().getName();
                                String email = response.body().getData().getUser_info().getEmail();
                                String mobile = response.body().getData().getUser_info().getPhone();
                                String image = response.body().getData().getUser_info().getAvatar();

                                saveUserInfo(id, email, name, mobile, image);

                                Intent myIntent = new Intent();
                                myIntent.setClassName("com.tkmsoft.akarat", "com.tkmsoft.akarat.activities.MainActivity");
                                startActivity(myIntent);
                                getActivity().finish();
                            } else
                                Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getActivity(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we getShow an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            112);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                if (!image_path.equals(""))
                    serverUpdateProfile();
                else
                    serverUpdateNoImage();
            }
        } else {
            if (!image_path.equals(""))
                serverUpdateProfile();
            else
                serverUpdateNoImage();
        }
    }

    private void pickImage() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            image_path = getRealPathFromUri(uri, getActivity());
            Picasso.get()
                    .load(uri)
                    .error(R.drawable.profile_placeholder)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(imageView);
        }
    }


    public static String getRealPathFromUri(Uri contentURI, Context context) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentURI, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        } catch (Exception ignored) {
            return null;
        }
    }


    private void getUserInfo() {

        mId = getSharedPreference.getUId();
        mName = getSharedPreference.getname();
        mEmail = getSharedPreference.getemail();
        mMobile = getSharedPreference.getmobil();
        mImage = getSharedPreference.getimage();

        NameTextView.setText(mName);
        EmailTextView.setText(mEmail);
        number.setText(mMobile);
        Picasso.get()
                .load(mImage)
                .error(R.drawable.profile_placeholder)
                .placeholder(R.drawable.profile_placeholder)
                .into(imageView);

    }

    private void serverUpdateProfile() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            File file1;
            file1 = new File(image_path);
            RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
            MultipartBody.Part image1 = MultipartBody.Part.createFormData("avatar", file1.getName(), mFile1);
            String api1 = getSharedPreference.gettokenId();
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), NameTextView.getText().toString());
            RequestBody api2 = RequestBody.create(MediaType.parse("text/plain"), api1);
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), EmailTextView.getText().toString());
            RequestBody number1 = RequestBody.create(MediaType.parse("text/plain"), number.getText().toString());
            RequestBody old_password = RequestBody.create(MediaType.parse("text/plain"), oldpasswordET.getText().toString());
            RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), newpassET.getText().toString());
            Api api = MyRetrofitClient.getAuth().create(Api.class);
            Call<ProfileModel> loginModelCall = api.profile(
                    name,
                    email,
                    number1,
                    old_password,
                    password1,
                    api2,
                    image1);
            loginModelCall.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            if (response.body().getStatus().getType().equals("success")) {
                                Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                                String id = String.valueOf(response.body().getData().getUser_info().getId());
                                String name = response.body().getData().getUser_info().getName();
                                String email = response.body().getData().getUser_info().getEmail();
                                String mobile = response.body().getData().getUser_info().getPhone();
                                String image = response.body().getData().getUser_info().getAvatar();

                                saveUserInfo(id, email, name, mobile, image);

                                getActivity().finish();

                                Intent myIntent = new Intent();
                                myIntent.setClassName("com.tkmsoft.akarat", MainActivity.class.getCanonicalName());
                                startActivity(myIntent);
                            } else {
                                Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), "asdaszxc", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();

                }
            });
        } catch (Exception exc) {
            Toast.makeText(getActivity(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserInfo(String id, String email, String name, String mobile, String image) {
        setSharedPreference.setUId(id);
        setSharedPreference.setname(name);
        setSharedPreference.setemail(email);
        setSharedPreference.setimage(image);
        setSharedPreference.setmobil(mobile);
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mMainViewsCallBack = (MainViewCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }

//        fireBackButtonEvent();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainViewsCallBack = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewsCallBack.setFilterBtn(false);
        mMainViewsCallBack.setCallBackTitle(getString(R.string.profilee));
    }


}
