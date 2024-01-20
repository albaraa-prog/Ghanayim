
package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


class DelAdapter extends RecyclerView.Adapter<DelAdapter.DelViewHolder> {
    private List<Data> delList;
    private Context context;

    public DelAdapter(Context context, ArrayList<Data> delList) {
        this.context = context;
        this.delList = delList;
    }

    @NonNull
    @Override
    public DelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleproduct, parent, false);
        return new DelViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DelViewHolder holder, int position) {

        Glide.with(context).load(delList.get(position).getDataimg()).into(holder.recimage);
        holder.recName.setText(delList.get(position).getDataname());
        holder.recPrice.setText(delList.get(position).getDataprice());
        holder.recDesc.setText(delList.get(position).getDatadesc());
        holder.recSec.setText(delList.get(position).getSection());
        holder.recProd.setOnClickListener(view -> {
            Intent intent = new Intent(context, delete.class);
            intent.putExtra("image", delList.get(holder.getAdapterPosition()).getDataimg());
            intent.putExtra("name", delList.get(holder.getAdapterPosition()).getDataname());
            intent.putExtra("price", delList.get(holder.getAdapterPosition()).getDataprice());
            intent.putExtra("description", delList.get(holder.getAdapterPosition()).getDatadesc());
            intent.putExtra("section", delList.get(holder.getAdapterPosition()).getSection());
            intent.putExtra("key",delList.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return delList.size();
    }
    static class DelViewHolder extends RecyclerView.ViewHolder {
        ImageView recimage;
        TextView recName, recDesc, recPrice, recSec;
        CardView recProd;
        public DelViewHolder(@NonNull View itemView) {
            super(itemView);
            recimage = itemView.findViewById(R.id.recimage);
            recProd = itemView.findViewById(R.id.recprod);
            recName = itemView.findViewById(R.id.recname);
            recPrice = itemView.findViewById(R.id.recprice);
            recDesc = itemView.findViewById(R.id.recdesc);
            recSec = itemView.findViewById(R.id.recsec);
        }
    }
}




