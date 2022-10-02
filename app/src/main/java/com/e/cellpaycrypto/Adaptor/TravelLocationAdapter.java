package com.e.cellpaycrypto.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.List;

public class TravelLocationAdapter extends RecyclerView.Adapter<TravelLocationAdapter.EXViewHolder> {

    List<ModelPojo> modelPojos;
    ViewPager2 viewPager2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            modelPojos.addAll(modelPojos);
            notifyDataSetChanged();
        }
    };

    public TravelLocationAdapter(List<ModelPojo> modelPojos, ViewPager2 viewPager2) {
        this.modelPojos = modelPojos;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public EXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_banner, parent, false);
        return new EXViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EXViewHolder viewHolder, int position) {
        final ModelPojo sliderItem = modelPojos.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getTtl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        if (position == modelPojos.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return modelPojos.size();
    }

    public class EXViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public EXViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
        }
    }
}
