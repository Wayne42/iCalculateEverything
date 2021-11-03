package tf.ferhat.icalculateeverything;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import tf.ferhat.icalculateeverything.ToDoEntryDB.ToDoEntry;

public class ToDoEntryAdapter extends RecyclerView.Adapter<ToDoEntryAdapter.ToDoEntryViewHolder> {

    class ToDoEntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView toDoEntryItemView;
        private final TextView toDoEntryItemView2;
        private final ImageView toDoEntryImageView;
        private final TextView toDoEntryCategory;

        private final ConstraintLayout recyclerviewConstraintLayout;

        private ToDoEntryViewHolder(View itemView) {
            super(itemView);
            toDoEntryItemView = itemView.findViewById(R.id.textView);
            toDoEntryItemView2 = itemView.findViewById(R.id.textView2);
            toDoEntryImageView = itemView.findViewById(R.id.imageView);
            toDoEntryCategory = itemView.findViewById(R.id.textViewCategory);
            recyclerviewConstraintLayout = itemView.findViewById(R.id.recyclerviewConstraintLayout);
        }
    }

    private final LayoutInflater mInflater;
    private List<ToDoEntry> mToDoEntries; // Cached copy of toDoEntries
    private Context mContext;



    ToDoEntryAdapter(Context context) { mInflater = LayoutInflater.from(context); mContext = context;}

    @Override
    public ToDoEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ToDoEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoEntryViewHolder holder, final int position) {
        if (mToDoEntries != null) {
            final ToDoEntry current = mToDoEntries.get(position);
            holder.toDoEntryItemView.setText(current.getTitle());

            Date date = new Date(Long.parseLong(current.getDuetodate()));

            holder.toDoEntryItemView2.setText(date.toLocaleString());

            if(TextUtils.isEmpty(current.getBase64pictureThumbnail())){
                holder.toDoEntryImageView.setImageResource(R.drawable.photos_stack);
            }else{
                holder.toDoEntryImageView.setImageBitmap(getBitmapFromEncodedString(current.getBase64pictureThumbnail()));
            }


                    //ToDoEntryViewModel mToDoEntryViewModel = ViewModelProviders.of((UnrealMainActivity)mContext).get(ToDoEntryViewModel.class);
                    //mToDoEntryViewModel.deleteById(current.getId());

            holder.toDoEntryCategory.setText(current.getCategory());

            holder.recyclerviewConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ToDoEntryViewerActivity.class);
                    intent.putExtra("ID", current.getId());
                    mContext.startActivity(intent);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.toDoEntryItemView.setText("No ToDoEntry");
        }
    }


    void setToDoEntries(List<ToDoEntry> toDoEntries){
        mToDoEntries = toDoEntries;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mToDoEntries has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mToDoEntries != null)
            return mToDoEntries.size();
        else return 0;
    }

    private Bitmap getBitmapFromEncodedString(String encodedString){
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }
}