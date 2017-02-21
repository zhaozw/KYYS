package com.mm.kyys.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.mm.kyys.Model.Doctor;
import com.mm.kyys.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2017/1/16.
 */

public class SelectDoctorAdapter extends RecyclerView.Adapter<SelectDoctorAdapter.MyViewHolder>{

    private LayoutInflater inflater;

    private Activity oThis;
    private List<Doctor> list_data = new ArrayList();
    private OnDoctorClickListener onDoctorClickListener;

    public interface OnDoctorClickListener{
        void OnClick(int position);
    }

    public void setOnDoctorClickListener(OnDoctorClickListener onDoctorClickListener){
        this.onDoctorClickListener = onDoctorClickListener;
    }

    public SelectDoctorAdapter(Activity oThis,List list_data){
        this.oThis = oThis;
        this.list_data = list_data;
        inflater = LayoutInflater.from(oThis);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_selectdoctor,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SelectDoctorAdapter.MyViewHolder holder, final int position) {
        Doctor doctor = list_data.get(position);
        holder.tv_name.setText(doctor.getName());
        ImageLoader.getInstance().displayImage(doctor.getImg(),holder.iv_photo);
        holder.tv_reception.setText(doctor.getStars()+"");
        holder.tv_info.setText(doctor.getIntroduction()+"");
        holder.tv_job.setText(doctor.getType() );

        if (onDoctorClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDoctorClickListener.OnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_photo;
        TextView tv_name,tv_job,tv_reception,tv_info;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.selectdoctor_iv_photo);
            tv_name = (TextView) itemView.findViewById(R.id.selectdoctor_tv_name);
            tv_job = (TextView) itemView.findViewById(R.id.selectdoctor_tv_job);
            tv_reception = (TextView) itemView.findViewById(R.id.selectdoctor_tv_reception_count);
            tv_info = (TextView) itemView.findViewById(R.id.selectdoctor_tv_infotext);
        }
    }
}
