package com.mm.kyys.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2017/1/16.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Activity oThis;
    private SectionAdapter.OnSectionItemClickListener onItemClickListener;
    private List<String> list_data = new ArrayList();

    private List<Boolean> list_ischeck = new ArrayList<Boolean>();


    public interface OnSectionItemClickListener{
        void onClick(int position,TextView tv);
        void onLongClick(int position);
    }
    public void setOnSectionItemClickListener(SectionAdapter.OnSectionItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SectionAdapter(Activity oThis,List<String> list_data,List<Boolean> list_ischeck){
        this.oThis = oThis;
        this.list_data = list_data;
        this.list_ischeck = list_ischeck;
        inflater = LayoutInflater.from(oThis);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_section,parent,false);
        SectionAdapter.MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_section.getLayoutParams();
        float[] arr = MyUtil.getIntance().screenPix(oThis);
        //params.width = (int) arr[0];
        params.height = (int) arr[1]/10;
        holder.tv_section.setLayoutParams(params);*/

        holder.tv_section.setText(list_data.get(position));
        if (list_ischeck.get(position)){
            holder.tv_section.setTextColor(oThis.getResources().getColor(R.color.colorGreen));
        }else{
            holder.tv_section.setTextColor(oThis.getResources().getColor(R.color.black_deep));
        }
        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onClick(position,holder.tv_section);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_section;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_section = (TextView) itemView.findViewById(R.id.item_section_tv);

        }
    }
}
