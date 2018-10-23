package com.tkmsoft.akarat.navfragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.tkmsoft.akarat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        Toast.makeText(getActivity(), "loading...", Toast.LENGTH_SHORT).show();
        WebView webView = rootView.findViewById(R.id.webView);
        webView.loadUrl("http://3qaronline.net/contact-us");
        return rootView;
    }

}
