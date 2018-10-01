package soft.mohamadamin.aramestan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import soft.mohamadamin.aramestan.DataModels.SliderDataModel;
import soft.mohamadamin.aramestan.R;

public class NotifySliderAdapter extends PagerAdapter
{
    private ArrayList<SliderDataModel> sliderList;
    private LayoutInflater inflater;
    private Context context;

    public NotifySliderAdapter(Context context, ArrayList<SliderDataModel> sliderList)
    {
        this.context = context;
        this.sliderList = sliderList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position)
    {
        View imageLayout = inflater.inflate(R.layout.slider_item_layout, view, false);
        ImageView imgSlider = imageLayout.findViewById(R.id.img_slider);
        TextView sliderTitle=imageLayout.findViewById(R.id.slider_title);
        Glide.with(context).load(sliderList.get(position).ImageName).into(imgSlider);
        sliderTitle.setText(sliderList.get(position).ImageTitle);
        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public int getCount()
    {
        return sliderList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view == o;
    }
}
