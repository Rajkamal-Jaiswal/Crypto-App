package com.e.cellpaycrypto.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.e.cellpaycrypto.Getset.ImageTextSlide;
import com.e.cellpaycrypto.R;

import java.util.List;

public abstract class Promotionadapter extends PagerAdapter {
    Context context;
    List<ImageTextSlide> imageTextSlides;
    LayoutInflater layoutInflater;

    public Promotionadapter(Context context, List<ImageTextSlide> imageTextSlides) {
        this.context = context;
        this.imageTextSlides = imageTextSlides;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return imageTextSlides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout) o);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        timer(imageTextSlides.size());
        View itemView = layoutInflater.inflate(R.layout.promotion, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        if (!imageTextSlides.get(position).getImageoffer().equalsIgnoreCase("")) {
            Glide.with(context)
                    .load(imageTextSlides.get(position).getImageoffer())
                    .error(R.drawable.ic_if_user)
                    .into(imageView);
            // imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            System.out.println("vgzsvghcavSG"+ imageTextSlides.get(position).getImageoffer());
            //    timer();
        }
        container.addView(itemView);
        return itemView;
    }

    public abstract void timer(int size);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
