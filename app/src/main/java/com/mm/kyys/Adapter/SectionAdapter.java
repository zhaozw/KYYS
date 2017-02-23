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
import com.mm.kyys.Model.Section;
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
    private List<Section> list_data = new ArrayList();
    private List<Boolean> list_ischeck = new ArrayList<Boolean>();


    public interface OnSectionItemClickListener{
        void onClick(int position,TextView tv);
        void onLongClick(int position);
    }
    public void setOnSectionItemClickListener(SectionAdapter.OnSectionItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SectionAdapter(Activity oThis, List<Section> list_data, List<Boolean> list_ischeck){
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        MyViewHolder holder1 = holder;
        holder1.setDate(position);

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_section;
        TextView tv_arrow;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_section = (TextView) itemView.findViewById(R.id.item_section_tv);
            tv_arrow = (TextView) itemView.findViewById(R.id.item_section_arrow);
        }

        public void setDate(final int position){

            tv_section.setText(list_data.get(position).getName());
            //点击Item改变颜色
            if (list_ischeck.size()!=0){
                if (list_ischeck.get(position)){
                    tv_section.setTextColor(oThis.getResources().getColor(R.color.colorGreen));
                    tv_arrow.setBackgroundResource(R.drawable.ic_keyboard_arrow_right_green_24dp);
                }else{
                    tv_section.setTextColor(oThis.getResources().getColor(R.color.black_deep));
                    tv_arrow.setBackgroundResource(R.drawable.ic_keyboard_arrow_right_white_24dp);
                }
                if (onItemClickListener != null){
                    //item点击事件
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onClick(position,tv_section);
                        }
                    });
                    //item长按事件
                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            onItemClickListener.onLongClick(position);
                            return false;
                        }
                    });
                }
            }
        }
    }


}
