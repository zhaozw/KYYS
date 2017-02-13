package com.mm.kyys.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mm.kyys.Model.Section;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2017/1/23.
 */

public class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.MyViewHolder> {

    private Activity oThis;
    private LayoutInflater inflater;
    private List<Section> list_section = new ArrayList<Section>();
    private OnSectorItemClickListener onSectorItemClickListener;

    public interface OnSectorItemClickListener{
        void OnClick(int position);
    }

    public void setOnSectorItemClickListener(OnSectorItemClickListener onSectorItemClickListener){
        this.onSectorItemClickListener = onSectorItemClickListener;
    }

    public SectorAdapter(Activity oThis,List list_section){
        this.oThis = oThis;
        this.list_section = list_section;
        inflater = LayoutInflater.from(oThis);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sector,parent,false);
        SectorAdapter.MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Section section = list_section.get(position);
        Log.e("xl", "Section=="+section.toString());
        //ImageLoader.getInstance().displayImage(section.getPic().toString().trim(),holder.iv);
        Log.e("xl", "pic------"+AllData.getInstance().getSectionPic(section.getPic_no()));
        holder.iv.setBackgroundResource(AllData.getInstance().getSectionPic(section.getPic_no()));
        holder.tv.setText(section.getName());
        if (onSectorItemClickListener!=null){
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSectorItemClickListener.OnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list_section.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        ImageView iv;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_sector_layout);
            iv = (ImageView) itemView.findViewById(R.id.item_sector_iv);
            tv = (TextView) itemView.findViewById(R.id.item_sector_tv);
        }
    }
}
