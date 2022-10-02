package com.e.cellpaycrypto.menus.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.databinding.FragmentPremiumMembershipBinding;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.ArrayList;
import java.util.List;

//import com.sellpay.android.menus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    /*   private ImageTextSlide imageTextSlidesobject;
       private Promotionadapter promotionadapter;
       private static int NUM_PAGES = 0;
       int currentPage = 0;
       Timer timer;*/
    RecyclerView recyclerView;
    List<ModelPojo> modelPojos = new ArrayList<>();
    private FragmentPremiumMembershipBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentPremiumMembershipBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modelPojos.clear();
        modelPojos.add(new ModelPojo("Level"));
        modelPojos.add(new ModelPojo("Rank"));
        modelPojos.add(new ModelPojo("Inactive"));
        modelPojos.add(new ModelPojo("Active"));
        modelPojos.add(new ModelPojo("Open Price"));
        AdapterPremiumDashboard adapterPremiumDashboard = new AdapterPremiumDashboard(getContext(), modelPojos);
        recyclerView.setAdapter(adapterPremiumDashboard);




        /*    *//* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*//*
        String response = "{\n" +
                "  \"resultCode\": \"200\",\n" +
                "  \"message\": \"Slider list fetched successfully\",\n" +
                "  \"slider_list\": [\n" +
                "    {\n" +
                "      \"image\": \"https:\\/\\/24kinteriors.com\\/crypto\\/admin\\/images\\/sliders\\/slide_1642598367.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"image\": \"https:\\/\\/24kinteriors.com\\/crypto\\/admin\\/images\\/sliders\\/slide_1642598367.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"image\": \"https:\\/\\/24kinteriors.com\\/crypto\\/admin\\/images\\/sliders\\/slide_1642599128.png\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String resultCode = jsonObject.getString("resultCode");
            String message = jsonObject.getString("message");
            JSONArray jsonArray = jsonObject.getJSONArray("slider_list");
            if (resultCode.equalsIgnoreCase("200")) {
                List<ImageTextSlide> imageTextSlides = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String offer_image = jsonObject1.getString("image");
                    imageTextSlidesobject = new ImageTextSlide(offer_image);
                    imageTextSlides.add(imageTextSlidesobject);

                }

                promotionadapter = new Promotionadapter(getActivity(), imageTextSlides) {
                    @Override
                    public void timer(int size) {
                        System.out.println("kljdslfdf" + size);
                        NUM_PAGES = size;
                    }
                };
                binding.viewPager.setAdapter(promotionadapter);

                binding.viewPager.setOnTouchListener((v, event) -> false);

                binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

                final Handler handler = new Handler();
                final Runnable Update = () -> {
                    if (currentPage == NUM_PAGES - 1) {
                        currentPage = 0;
                    }
                    if (binding != null) {
                        binding.viewPager.setCurrentItem(currentPage++, true);
                    }
                };

                timer = new Timer(); // This will create a new Thread
                timer.schedule(new TimerTask() { // task to be scheduled
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 2000, 3000);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        binding.hotTv.setBackground(getResources().getDrawable(R.drawable.rect2));
        binding.hotTv.setOnClickListener(v -> {
            disableAllColor();
            binding.hotTv.setBackground(getResources().getDrawable(R.drawable.rect2));
        });
        binding.gainersTv.setOnClickListener(v -> {
            disableAllColor();
            binding.gainersTv.setBackground(getResources().getDrawable(R.drawable.rect2));
        });
        binding.losersTv.setOnClickListener(v -> {
            disableAllColor();
            binding.losersTv.setBackground(getResources().getDrawable(R.drawable.rect2));
        });

        binding.vol24Tv.setOnClickListener(v -> {
            disableAllColor();
            binding.vol24Tv.setBackground(getResources().getDrawable(R.drawable.rect2));
        });

        binding.recycler1.setHasFixedSize(true);
        binding.recycler1.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterDashRecyc adapterDashRecyc = new AdapterDashRecyc();
        binding.recycler1.setAdapter(adapterDashRecyc);

        return root;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void disableAllColor() {
        binding.hotTv.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.gainersTv.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.losersTv.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.vol24Tv.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.progress1.setVisibility(View.VISIBLE);
        binding.recycler1.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                binding.progress1.setVisibility(View.GONE);
                binding.recycler1.setVisibility(View.VISIBLE);


            }
        },1000);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/


        return binding.getRoot();
    }
}