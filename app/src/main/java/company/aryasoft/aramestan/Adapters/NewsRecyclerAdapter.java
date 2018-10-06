package company.aryasoft.aramestan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import company.aryasoft.aramestan.Activities.NewsDetailsActivity;
import company.aryasoft.aramestan.Models.NewsModel;
import company.aryasoft.aramestan.R;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsRecyclerAdapterViewHolder>
{
    private Context context;
    private ArrayList<NewsModel> newsDataList;

    public NewsRecyclerAdapter(Context context)
    {
        this.context = context;
        this.newsDataList=new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new NewsRecyclerAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_view_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerAdapterViewHolder newsRecyclerAdapterViewHolder, final int position)
    {
        newsRecyclerAdapterViewHolder.txt_newsTitle.setText(newsDataList.get(position).NewsTitle);
        newsRecyclerAdapterViewHolder.txt_newsDate.setText(newsDataList.get(position).NewsDate);
        String imageUrl = context.getString(R.string.ImageFolderName) +
                context.getString(R.string.NewsImageFolder) +
                newsDataList.get(position).ImageName;
        Glide.with(context).load(imageUrl).into(newsRecyclerAdapterViewHolder.img_newsImg);
        newsRecyclerAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openNewsDetailActivity(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return newsDataList.size();
    }

    public void AddNewsData(ArrayList<NewsModel> newsDataList)
    {
        this.newsDataList.addAll(newsDataList);
        this.notifyDataSetChanged();
    }

    private void openNewsDetailActivity(int position)
    {
        Intent newsIntent = new Intent(context, NewsDetailsActivity.class);
        NewsModel selectedNews = newsDataList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("news_id", selectedNews.NewsID);
        bundle.putString("news_title", selectedNews.NewsTitle);
        bundle.putString("news_image_name", selectedNews.ImageName);
        bundle.putString("news_date", selectedNews.NewsDate);
        newsIntent.putExtra("selected_news", bundle);
        context.startActivity(newsIntent);
    }

    class NewsRecyclerAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_newsImg;
        private TextView txt_newsTitle;
        private TextView txt_newsDate;

        NewsRecyclerAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img_newsImg = itemView.findViewById(R.id.img_newsImg);
            txt_newsTitle = itemView.findViewById(R.id.txt_newsTitle);
            txt_newsDate = itemView.findViewById(R.id.txt_newsDate);
        }
    }
}
