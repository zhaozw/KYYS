package com.mm.kyys.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mm.kyys.R;
import com.mm.kyys.View.CircularImage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 27740 on 2017/1/9.
 */

public class DiseaseClassftyAdapter extends RecyclerView.Adapter<DiseaseClassftyAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<String> list_str = new ArrayList<>();


    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public DiseaseClassftyAdapter(Context context,List<String> list_str){
        this.context = context;
        this.list_str = list_str;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DiseaseClassftyAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_diseaseclassfty,viewGroup,false);
        DiseaseClassftyAdapter.MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DiseaseClassftyAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.tv.setText(list_str.get(i));
        myViewHolder.ci.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myViewHolder.linearLayout.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        params.width = width/2-80;
        params.height = height/10;
        if(i%2 == 0){
            params.setMargins(30,15,10,0);
        }else{
            params.setMargins(30,15,10,0);
        }

        myViewHolder.linearLayout.setLayoutParams(params);

        if (onItemClickListener != null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(i);
                }
            });
            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(i);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list_str.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        CircularImage ci;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.diseaseclasstfy_framelayout);
            ci = (CircularImage) view.findViewById(R.id.diseaseclasstfy_ci);
            tv = (TextView) view.findViewById(R.id.diseaseclasstfy_tv);

        }
    }

}
