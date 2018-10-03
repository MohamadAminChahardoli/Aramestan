package company.aryasoft.aramestan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.R;

public class AdvertisementRecyclerAdapter extends RecyclerView.Adapter<AdvertisementRecyclerAdapter.NotifiesRecyclerAdapterViewHolder>
{
    private ArrayList<Announcement> notifiesDataList;
    private Context context;

    public AdvertisementRecyclerAdapter(Context context)
    {
        this.notifiesDataList = new ArrayList<>();
        this.context=context;
    }

    @NonNull
    @Override
    public NotifiesRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View RecyclerItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advertisement_view_item, viewGroup, false);
        return new NotifiesRecyclerAdapterViewHolder(RecyclerItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifiesRecyclerAdapterViewHolder holder, int position)
    {
        Glide.with(context).load("URL"+notifiesDataList.get(position).ImageName).into(holder.imgNotifies);
        holder.imgNotifies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //open dialog for biggerImage
            }
        });
    }

    @Override
    public int getItemCount()
    {
       return notifiesDataList.size();
    }

    public void addNotifiesDataList(ArrayList<Announcement> notifiesDataList)
    {
        this.notifiesDataList.addAll(notifiesDataList);
        this.notifyDataSetChanged();
    }

    class NotifiesRecyclerAdapterViewHolder extends ViewHolder
    {
        private ImageView imgNotifies;

        NotifiesRecyclerAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imgNotifies=itemView.findViewById(R.id.img_notifies);
        }
    }
}
