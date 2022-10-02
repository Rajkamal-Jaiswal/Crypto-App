package com.e.cellpaycrypto.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Activity.PaymentGatewayActivity;
import com.e.cellpaycrypto.Adaptor.Promotionadapter;
import com.e.cellpaycrypto.Adaptor.SliderData;
import com.e.cellpaycrypto.Adaptor.TravelLocationAdapter;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Getset.ImageTextSlide;
import com.e.cellpaycrypto.databinding.FragmentMAinfragmentBinding;
import com.e.cellpaycrypto.menus.AdapterDashRecyc;
import com.e.cellpaycrypto.menusP2P.P2PDashboard;
import com.e.cellpaycrypto.mypack.DepositActivity;
import com.e.cellpaycrypto.referral.EarnActivity;
import com.e.cellpaycrypto.referral.ReferralActivity;
import com.e.cellpaycrypto.test1.ModelPojo;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mainfragment extends Fragment {
    private static int NUM_PAGES = 0;
    int toggle_btn = 1;
    TextView rank, Username, tvCoinList;
    CardView cvP2pMarker;
    SharedPreferences sharedpreferences;
    String userid, username, ranking;
    SharedPreferences.Editor prefEditor;
    ViewPager viewPager, viewPager1;
    int currentPage = 0;
    Timer timer;
    FragmentMAinfragmentBinding binding;
    SliderView sliderView;
    LinearLayout ll_referral;
    ArrayList<SliderData> sliderDataArrayList;
    ViewPager2 viewPager2;
    Handler sliderHandlerV2 = new Handler();
    ImageTextSlide imageTextSlidesobject;
//    TravelLocationAdapter travelLocationAdapter;
    Promotionadapter promotionadapter;
    private RequestQueue queue;
    private HashMap<String, String> mapTop = new HashMap<>();
    private HashMap<String, String> mapBottom = new HashMap<>();
    private Runnable sliderRunnableV2 = new Runnable() {
        @Override
        public void run() {

            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    public Mainfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMAinfragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvCoinList = binding.tvCoinList;
        cvP2pMarker = binding.cvP2pMarker;
        viewPager2 = binding.viewPager2;
        queue = Volley.newRequestQueue(getActivity());

        cvP2pMarker.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), P2PDashboard.class);
            startActivity(intent);
        });
        binding.paymentGateway.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PaymentGatewayActivity.class);
            startActivity(intent);
        });

        binding.llReferral.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ReferralActivity.class);
            startActivity(intent);
        });
        binding.llPrepaid.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), DepositActivity.class);
            startActivity(intent);
        });
        binding.llEarn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EarnActivity.class);
            startActivity(intent);
        });

        binding.moreBtn.setOnClickListener(view -> {

            if (toggle_btn == 1) {
                binding.cvrBelowLayerMenus.setVisibility(View.VISIBLE);
                toggle_btn = 0;
                binding.txtShowMore.setText("Show Less");
            } else {
                binding.cvrBelowLayerMenus.setVisibility(View.GONE);
                toggle_btn = 1;
                binding.txtShowMore.setText("Show More");

            }
        });


        binding.recycler1.setHasFixedSize(true);
        binding.recycler1.setLayoutManager(new

                LinearLayoutManager(getActivity()));
        AdapterDashRecyc adapterDashRecyc = new AdapterDashRecyc();
        binding.recycler1.setAdapter(adapterDashRecyc);

    /*    v.findViewById(R.id.selectpaymentmethod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectPamentMethodActivity.class);
                startActivity(intent);
            }
        });*/
        setOffer();

        sliderView = binding.imageSlider;

        sliderDataArrayList = new ArrayList<>();
        setOffer1();


        return root;
    }

    private void setOffer() {
        mapTop.put("module", "sliderList");
        readPostSignTop(GlobalConstants.BASE_URL, mapTop);
    }

    public void readPostSignTop(String url, final HashMap<String, String> map) {
        System.out.println("sakjjhd" + url);
        binding.progressBar1.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onResponse(String response) {
                        binding.progressBar1.setVisibility(View.GONE);
                        Helper.watchLog(""+response,"readPostSignTop" );
                        try {
                            //dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
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

                                viewPager = binding.viewPager;
                                promotionadapter = new Promotionadapter(getActivity(), imageTextSlides) {
                                    @Override
                                    public void timer(int size) {
                                        System.out.println("kljdslfdf" + size);
                                        NUM_PAGES = size;
                                    }
                                };
                                viewPager.setAdapter(promotionadapter);

                                viewPager.setOnTouchListener((v, event) -> false);

                                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                                final Runnable Update = new Runnable() {
                                    public void run() {
                                        if (currentPage == NUM_PAGES - 1) {
                                            currentPage = 0;
                                        }
                                        viewPager.setCurrentItem(currentPage++, true);
                                    }
                                };

                                timer = new Timer(); // This will create a new Thread
                                timer.schedule(new TimerTask() { // task to be scheduled
                                    @Override
                                    public void run() {
                                        handler.post(Update);
                                    }
                                }, 2000, 3000);

                                viewPager.setOffscreenPageLimit(3);
                                System.out.println("klsjdljfdf");

                            } else {
                                // dialog.dismiss();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // setErrorToast();
                        binding.progressBar1.setVisibility(View.GONE);
                        try {
                            // dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void setOffer1() {
        mapBottom.put("module", "sliderListBottom");
        readPostSignBottom(GlobalConstants.BASE_URL, mapBottom);
    }

    public void readPostSignBottom(String url, final HashMap<String, String> map) {
        System.out.println("sakjjhd" + url);
        binding.progressBar2.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onResponse(String response) {
                        binding.progressBar2.setVisibility(View.GONE);

                        Helper.watchLog("readPostSignBottom", "" + response);

                        try {
                            //dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("slider_list");
                            List<ModelPojo> modelPojos1 = new ArrayList<>();
                            modelPojos1.clear();
                            if (resultCode.equalsIgnoreCase("200")) {
                                List<ImageTextSlide> imageTextSlides = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String offer_image = jsonObject1.getString("image");
                                    imageTextSlidesobject = new ImageTextSlide(offer_image);
                                    imageTextSlides.add(imageTextSlidesobject);
                                    modelPojos1.add(new ModelPojo(offer_image));
                                }

                                TravelLocationAdapter travelLocationAdapter = new TravelLocationAdapter(modelPojos1, viewPager2);
                                viewPager2.setAdapter(travelLocationAdapter);
                                viewPager2.setOffscreenPageLimit(3);
                                viewPager2.setClipChildren(false);
                                viewPager2.setClipToPadding(false);
                                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                                    @Override
                                    public void transformPage(@NonNull View page, float position) {
                                        float r = 1 - Math.abs(position);
                                        page.setScaleY(0.85f + r * 0.14f);
                                    }
                                });

                                viewPager2.setPageTransformer(compositePageTransformer);
                                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        super.onPageSelected(position);
                                        sliderHandlerV2.removeCallbacks(sliderRunnableV2);
                                        sliderHandlerV2.postDelayed(sliderRunnableV2, 2000);

                                    }
                                });

                            } else {
                                // dialog.dismiss();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // setErrorToast();
                        binding.progressBar2.setVisibility(View.GONE);

                        try {
                            // dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandlerV2.removeCallbacks(sliderRunnableV2);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandlerV2.postDelayed(sliderRunnableV2, 2000);
    }
}