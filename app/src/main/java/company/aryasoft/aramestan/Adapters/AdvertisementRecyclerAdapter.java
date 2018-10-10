package company.aryasoft.aramestan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import company.aryasoft.aramestan.Models.Advertisement;
import company.aryasoft.aramestan.R;

public class AdvertisementRecyclerAdapter extends RecyclerView.Adapter<AdvertisementRecyclerAdapter.AdvertisementRecyclerViewHolder> {
    private ArrayList<Advertisement> Advertisements;
    private Context context;

    public AdvertisementRecyclerAdapter(Context context) {
        this.Advertisements = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public AdvertisementRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View RecyclerItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advertisement_view_item, viewGroup, false);
        return new AdvertisementRecyclerViewHolder(RecyclerItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementRecyclerViewHolder holder, int position)
    {
        final String imageUrl = context.getString(R.string.ImageFolderName) + context.getString(R.string.AdImageFolder) + Advertisements.get(position).getImageName();

        Glide.with(context).load(imageUrl).into(holder.imgAdvertisements);
        holder.txtAdDescription.setText(Html.fromHtml( Advertisements.get(position).getDescription()).toString());
    }

    @Override
    public int getItemCount() {
        return Advertisements.size();
    }

    public void addAdvertisementsDataList(ArrayList<Advertisement> advertisements) {
        this.Advertisements.addAll(advertisements);
        this.notifyDataSetChanged();
    }

    class AdvertisementRecyclerViewHolder extends ViewHolder {
        private ImageView imgAdvertisements;
        private TextView txtAdDescription;

        AdvertisementRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAdvertisements = itemView.findViewById(R.id.img_ad);
            txtAdDescription=itemView.findViewById(R.id.txt_ad_description);
        }
    }
}
