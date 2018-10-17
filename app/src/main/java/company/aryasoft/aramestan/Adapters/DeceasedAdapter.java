package company.aryasoft.aramestan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import company.aryasoft.aramestan.Activities.DetailActivity;
import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeceasedAdapter extends RecyclerView.Adapter<DeceasedAdapter.DeceasedViewHolder> {

    private List<Deceased> DeceasedList;
    private Context ContextInstance;
    public DeceasedAdapter(Context context) {
        DeceasedList = new ArrayList<>();
        ContextInstance = context;
    }
    public DeceasedAdapter(List<Deceased> deceasedList, Context context) {
        DeceasedList = deceasedList;
        ContextInstance = context;
    }

    @NonNull
    @Override
    public DeceasedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.deceased_search_result_item_layout, viewGroup, false);

        return new DeceasedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeceasedViewHolder deceasedViewHolder, final int position) {
        Deceased deceased = DeceasedList.get(position);
        String imageUrl = ContextInstance.getString(R.string.ImageFolderName) + ContextInstance.getString(R.string.DeadImageFolder) + deceased.getImageName();
        Glide.with(ContextInstance).load(imageUrl).into(deceasedViewHolder.ImageDeceasedPhoto);
        if (deceased.getDefunctTitle().contains("عادی"))
        {
            deceased.setDefunctTitle("شادروان");
        }
        String titleAndFullName = deceased.getDefunctTitle() + " " + deceased.getFullName();
        deceasedViewHolder.TextTitleFullName.setText(titleAndFullName);
        String fatherName = "فرزند " + deceased.getFatherName();
        deceasedViewHolder.TextFatherName.setText(fatherName);
        deceasedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeceasedDetails(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DeceasedList.size();
    }

    public void addDeceasedListData(List<Deceased> DeceasedList) {
        this.DeceasedList.addAll(DeceasedList);
        this.notifyDataSetChanged();
    }

    public void clearAllItems()
    {
        DeceasedList.clear();
        this.notifyDataSetChanged();
    }

    public class DeceasedViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ImageDeceasedPhoto;
        public TextView TextTitleFullName;
        public TextView TextFatherName;

        public DeceasedViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews(itemView);
        }

        private void initializeViews(View view) {
            ImageDeceasedPhoto = view.findViewById(R.id.img_deceased_photo);
            TextTitleFullName = view.findViewById(R.id.txt_title_full_name);
            TextFatherName = view.findViewById(R.id.txt_father_name);
        }
    }

    private void openDeceasedDetails(int position)
    {
        Intent detailActivityIntent = new Intent(ContextInstance, DetailActivity.class);
        Deceased selectedDeceased = DeceasedList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("dead_id", selectedDeceased.getDeadId());
        bundle.putString("full_name", selectedDeceased.getFullName());
        bundle.putString("father_name", selectedDeceased.getFatherName());
        bundle.putString("image_name", selectedDeceased.getImageName());
        bundle.putString("defunct_title", selectedDeceased.getDefunctTitle());
        detailActivityIntent.putExtra("selected_deceased", bundle);
        ContextInstance.startActivity(detailActivityIntent);
    }

}
