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

import java.util.List;

public class ImageResultAdapter extends RecyclerView.Adapter<ImageResultAdapter.ListImageViewHolder> {

    private List<String> detailInformationList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ImageResultAdapter(List<String> detailInformationList, Context context) {
        this.detailInformationList = detailInformationList;
        this.context = context;
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
        String imageURL = detailInformationList.get(position);
        holder.tvImageURL.setText(imageURL);
        Glide.with(context).load(imageURL).into(holder.ivImageResult);

    }

    @Override
    public int getItemCount() {
        return detailInformationList.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ListImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvImageName, tvImageURL;
        ImageView ivImageResult;
        OnItemClickListener onItemClickListener;

        ListImageViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivImageResult = itemView.findViewById(R.id.iv_result);
            tvImageName = itemView.findViewById(R.id.tv_image_name);
            tvImageURL = itemView.findViewById(R.id.tv_image_url);
            this.onItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view, getAdapterPosition(), false);

        }
    }
}