package com.example.recyclelistcardview;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

public class CardListAdapter extends  RecyclerView.Adapter {
    private List<String> titles;
    private  List<Integer>  icons;
    private  Context ctx;
    private  LayoutType  layoutType;

    private RecyclerView  recyclerView;
    enum LayoutType{
        LINEAR_LAYOUT,
        GRID_LAYOUT
    }

    public CardListAdapter(Context ctx,RecyclerView  recyclerView,LayoutType  layoutType, List<String>  titles, List<Integer>  icons){
        this.ctx = ctx;
        this.recyclerView = recyclerView;
        this.layoutType = layoutType;
        this.titles = titles;
        this.icons = icons;

        if(layoutType == LayoutType.LINEAR_LAYOUT){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(linearLayoutManager);
        }else  if(layoutType == LayoutType.GRID_LAYOUT){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx,2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    public void removeItem(int position) {
        if(recyclerView == null){
             return;
        }
        if(titles == null){
            return;
        }
        if(icons == null){
            return;
        }

        recyclerView.setItemAnimator(null);
        titles.remove(position);
        icons.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    View view ;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(this.layoutType ==  LayoutType.GRID_LAYOUT){
            view = LayoutInflater.from(ctx).inflate(R.layout.grid_item,viewGroup,false);
            return new  GridViewViewHolder(view);
        }else if (this.layoutType ==  LayoutType.LINEAR_LAYOUT){
            view = LayoutInflater.from(ctx).inflate(R.layout.list_item,viewGroup,false);
            return new LinearLayoutViewHolder(view);
        }else{
            return   null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView  imageView = null;
        RelativeLayout  listItem = null;
        ImageView  arrowImageView;
        TextView  textView = null;
        if(holder == null){
            if(this.layoutType ==  LayoutType.GRID_LAYOUT){
                holder = new  GridViewViewHolder(view);
            }else if(this.layoutType ==  LayoutType.LINEAR_LAYOUT){
                holder = new  LinearLayoutViewHolder(view);
            }
        }

        if(holder instanceof LinearLayoutViewHolder){
            imageView = ((LinearLayoutViewHolder)holder).imageView;
            arrowImageView = ((LinearLayoutViewHolder)holder).arrowImageView;
            textView = ((LinearLayoutViewHolder)holder).textView;
            listItem = ((LinearLayoutViewHolder)holder).listItem;
            textView.setText(titles.get(position));
            imageView.setImageResource(icons.get(position));
            arrowImageView.setImageResource(R.drawable.selector_arrow);
        }else if(holder instanceof GridViewViewHolder){
            imageView = ((GridViewViewHolder)holder).imageView;
            textView = ((GridViewViewHolder)holder).textView;
            listItem = ((GridViewViewHolder)holder).listItem;
        }

        textView.setText(titles.get(position));
        imageView.setImageResource(icons.get(position));

        listItem.setOnClickListener(v -> {
            EventBus.getDefault().post(position);
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    private class GridViewViewHolder extends  RecyclerView.ViewHolder{
        private  ImageView  imageView;
        private   TextView  textView;
        private RelativeLayout listItem;
        public GridViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = itemView.findViewById(R.id.icon);
            textView  = itemView.findViewById(R.id.text);
            listItem  = itemView.findViewById(R.id.list_item_rl);
        }
    }


    private   class LinearLayoutViewHolder extends  RecyclerView.ViewHolder{
        private  ImageView  imageView;
        private  ImageView  arrowImageView;
        private   TextView  textView;
        private    RelativeLayout  listItem;
        public LinearLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = itemView.findViewById(R.id.icon);
            textView  = itemView.findViewById(R.id.text);
            arrowImageView  = itemView.findViewById(R.id.arrow_right);
            listItem  = itemView.findViewById(R.id.list_item_rl);
        }
    }
}