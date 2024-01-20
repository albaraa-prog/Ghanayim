
package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Data> dataList;

    public MyAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleproduct, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getDataimg()).into(holder.recimage);
        holder.recName.setText(dataList.get(position).getDataname());
        holder.recPrice.setText(dataList.get(position).getDataprice());
        holder.recDesc.setText(dataList.get(position).getDatadesc());
        holder.recSec.setText(dataList.get(position).getSection());
        holder.recProd.setOnClickListener(view -> {
            Intent intent = new Intent(context,Detail.class);
            intent.putExtra("image", dataList.get(holder.getAdapterPosition()).getDataimg());
            intent.putExtra("name", dataList.get(holder.getAdapterPosition()).getDataname());
            intent.putExtra("price", dataList.get(holder.getAdapterPosition()).getDataprice());
            intent.putExtra("description", dataList.get(holder.getAdapterPosition()).getDatadesc());
            intent.putExtra("section", dataList.get(holder.getAdapterPosition()).getSection());
            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }
    @Override
   public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Data> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recimage;
    TextView recName, recDesc, recPrice, recSec;
    CardView recProd;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recimage = itemView.findViewById(R.id.recimage);
        recProd = itemView.findViewById(R.id.recprod);
        recName = itemView.findViewById(R.id.recname);
        recPrice = itemView.findViewById(R.id.recprice);
        recDesc = itemView.findViewById(R.id.recdesc);
        recSec = itemView.findViewById(R.id.recsec);

    }
}


