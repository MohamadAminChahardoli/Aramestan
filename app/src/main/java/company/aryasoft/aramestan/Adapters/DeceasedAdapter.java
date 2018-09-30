package company.aryasoft.aramestan.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import company.aryasoft.aramestan.Models.Deceased;
import company.aryasoft.aramestan.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeceasedAdapter extends RecyclerView.Adapter<DeceasedAdapter.DeceasedViewHolder> {

    private List<Deceased> DeceasedList;

    public DeceasedAdapter(List<Deceased> deceasedList) {
        DeceasedList = deceasedList;
    }

    @NonNull
    @Override
    public DeceasedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.deceased_seratch_result_item_layout, viewGroup, false);

        return new DeceasedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeceasedViewHolder deceasedViewHolder, int position) {
        Deceased deceased = DeceasedList.get(position);
        //deceasedViewHolder.ImageDeceasedPhoto.setBackgroundResource(deceased.getImageName());
        String titleAndFullName = deceased.getDefunctTitle()+" "+deceased.getFullName();
        deceasedViewHolder.TextTitleFullName.setText(titleAndFullName);
        String fatherName = "فرزند " + deceased.getFatherName();
        deceasedViewHolder.TextFatherName.setText(fatherName);
    }

    @Override
    public int getItemCount() {
        return DeceasedList.size();
    }

    public class DeceasedViewHolder extends RecyclerView.ViewHolder
    {
        public CircleImageView ImageDeceasedPhoto;
        public TextView TextTitleFullName;
        public TextView TextFatherName;

        public DeceasedViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews(itemView);
        }

        private void initializeViews(View view)
        {
            ImageDeceasedPhoto = view.findViewById(R.id.img_deceased_photo);
            TextTitleFullName = view.findViewById(R.id.txt_title_full_name);
            TextFatherName = view.findViewById(R.id.txt_father_name);
        }
    }
}
