package company.aryasoft.aramestan.Adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import company.aryasoft.aramestan.Models.Announcement;
import company.aryasoft.aramestan.R;

public class NotifiesRecyclerAdapter extends RecyclerView.Adapter<NotifiesRecyclerAdapter.NotifiesRecyclerAdapterViewHolder>
{
    private ArrayList<Announcement> notifiesDataList;
    private Context context;

    public NotifiesRecyclerAdapter(Context context)
    {
        this.notifiesDataList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public NotifiesRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View RecyclerItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifies_recycler_view_item, viewGroup, false);
        return new NotifiesRecyclerAdapterViewHolder(RecyclerItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotifiesRecyclerAdapterViewHolder holder, int position)
    {
        final String imageUrl = context.getString(R.string.ImageFolderName) +
                context.getString(R.string.AnnouncementImageFolder) +
                notifiesDataList.get(position).ImageName;
        Glide.with(context).load(imageUrl).into(holder.imgNotifies);
        holder.imgNotifies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowNotifyImageDialog(imageUrl);
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

    private void ShowNotifyImageDialog(String ImageName)
    {
        final AppCompatDialog dlg = new AppCompatDialog(context);
        dlg.setCancelable(false);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View AlertView = View.inflate(context, R.layout.notify_image_dialog_layout, null);
        ImageView imgNotifiesDialog = AlertView.findViewById(R.id.img_notifies_dialog);
        ImageButton btn_close_notifies_dialog = AlertView.findViewById(R.id.btn_close_notifies_dialog);
        btn_close_notifies_dialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
            }
        });
        Glide.with(context).load(ImageName).into(imgNotifiesDialog);
        dlg.setContentView(AlertView);
        dlg.getWindow().getAttributes().windowAnimations = R.style.NotifiesImageDialogAnimation;
        dlg.show();
    }

    class NotifiesRecyclerAdapterViewHolder extends ViewHolder
    {
        private ImageView imgNotifies;

        NotifiesRecyclerAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imgNotifies = itemView.findViewById(R.id.img_notifies);
        }
    }
}
