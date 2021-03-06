package company.aryasoft.aramestan.Adapters;

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

import company.aryasoft.aramestan.Models.SliderDataModel;
import company.aryasoft.aramestan.R;

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
        String imageUrl = context.getString(R.string.ImageFolderName) +
                context.getString(R.string.SliderImageFolder) +
                sliderList.get(position).ImageName;
        Glide.with(context).load(imageUrl).into(imgSlider);
        sliderTitle.setText(sliderList.get(position).SliderTitle1);
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
