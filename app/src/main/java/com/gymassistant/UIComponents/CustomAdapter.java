package com.gymassistant.UIComponents;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-03-30.
 */
public class CustomAdapter extends PagerAdapter{

    private Context context;
    private List<String> names;

    public CustomAdapter(Context context, List<String> names){
        this.names = names;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.item_image, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        Glide.with(context)
                .load("file:///android_asset" + names.get(position))
                .fitCenter()
                .into(imageView);
        ((ViewPager)container).addView(viewItem);
        Log.i("TAG", "file:///android_asset/images" + names.get(position));
        return viewItem;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}