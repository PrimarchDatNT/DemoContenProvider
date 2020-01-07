package com.esasyassistivetouch.democontentprovider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ImageResultAdapter extends RecyclerView.Adapter<ImageResultAdapter.ListImageViewHolder> {

    private List<ImageResult> listResult;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ImageResultAdapter(Context context, List<ImageResult> listResult) {
        this.context = context;
        this.listResult = listResult;
    }

    @NonNull
    @Override
    public ListImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_view, parent, false);
        return new ListImageViewHolder(view, onItemClickListener);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ListImageViewHolder holder, int position) {
        ImageResult result = listResult.get(position);
        holder.tvImageSize.setText(formatSize(Long.parseLong(result.getSize())));
        holder.tvImageID.setText(result.getId());
        holder.tvImageName.setText(result.getName());
        holder.tvImageURL.setText(result.getPath());
        Glide.with(context).load(result.getPath()).into(holder.ivImageResult);

    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private String formatSize(long size) {

        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    public class ListImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvImageName, tvImageURL, tvImageID, tvImageSize;
        ImageView ivImageResult;
        OnItemClickListener onItemClickListener;

        ListImageViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivImageResult = itemView.findViewById(R.id.iv_result);
            tvImageName = itemView.findViewById(R.id.tv_image_name);
            tvImageURL = itemView.findViewById(R.id.tv_image_url);
            tvImageID = itemView.findViewById(R.id.tv_image_id);
            tvImageSize = itemView.findViewById(R.id.tv_image_size);
            this.onItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view, getAdapterPosition(), false);

        }
    }
}