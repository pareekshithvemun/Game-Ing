package com.example.game_ing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<ImageUploadInfo> mimageUploadInfo;
    Context context;

    public MyAdapter(Context c, List<ImageUploadInfo> p) {
        context = c;
        mimageUploadInfo = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.ad_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageUploadInfo ImageuploadInfoCurrent = mimageUploadInfo.get(position);

        /*ProductDetails productDetails = productDetailsList.get(position);*/
        holder.Description.setText(ImageuploadInfoCurrent.getDescription());
        holder.Price.setText(ImageuploadInfoCurrent.getPrice());
        holder.Address.setText(ImageuploadInfoCurrent.getAddress());
        holder.Phone.setText(ImageuploadInfoCurrent.getPhone());
        Picasso.get().load(ImageuploadInfoCurrent.getImageURL()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return mimageUploadInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Description, Price, Address, Phone;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            Description = itemView.findViewById(R.id.Description);
            Price = itemView.findViewById(R.id.Price);
            Address = itemView.findViewById(R.id.Address);
            Phone = itemView.findViewById(R.id.Phone);
            imageView = itemView.findViewById(R.id.imageview2);
        }

    }
}
