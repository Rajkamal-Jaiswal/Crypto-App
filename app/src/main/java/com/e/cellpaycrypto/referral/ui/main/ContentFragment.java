package com.e.cellpaycrypto.referral.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class ContentFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    LinearLayoutCompat myReferralPage, referPageCvr;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private TextView titleTxtView;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(String param1) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_content, container, false);
     /*   titleTxtView = (TextView) myView.findViewById(R.id.title_txtView);

        titleTxtView.setText(mParam1);*/
        myReferralPage = myView.findViewById(R.id.myReferralPage);
        referPageCvr = myView.findViewById(R.id.referPageCvr);
        recyclerView = myView.findViewById(R.id.recyclerView);
        if (mParam1.equalsIgnoreCase("Refer")) {
            myReferralPage.setVisibility(View.GONE);
            referPageCvr.setVisibility(View.VISIBLE);
        } else {
            myReferralPage.setVisibility(View.VISIBLE);
            referPageCvr.setVisibility(View.GONE);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterMyReferrals adapterMyReferrals = new AdapterMyReferrals();
        recyclerView.setAdapter(adapterMyReferrals);
        return myView;
    }

}
