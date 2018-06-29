package com.yarolegovich.discretescrollview.sample.Mv;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yarolegovich.discretescrollview.sample.R;
import com.yarolegovich.discretescrollview.sample.itemTouch;
import com.yarolegovich.discretescrollview.sample.shop.Item;

import java.util.List;


/**
 * Created by yarolegovich on 07.03.2017.
 */

public class MvAdapter extends RecyclerView.Adapter<MvAdapter.ViewHolder> {


    private int itemwidth;
    private itemTouch interaction;
    private List<Item> data;

    public MvAdapter(List<Item> data, itemTouch interact) {
        this.data = data;
        this.interaction = interact;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Activity context = (Activity) recyclerView.getContext();
        Point windowDimensions = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(windowDimensions);
        itemwidth = Math.round(windowDimensions.x * 0.85f);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.mv_card_2, parent, false);
     /*   ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                itemwidth,
                500);
        v.setLayoutParams(params);*/
        // return new GalleryAdapter.ViewHolder(v);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                .load(data.get(position).getImage())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interaction != null) interaction.touch_at(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
