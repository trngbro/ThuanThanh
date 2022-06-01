package com.tdtu.thuanthanh.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.thuanthanh.DTO.LoaiSanPhamDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class AdapterRecycleViewCategory extends RecyclerView.Adapter<AdapterRecycleViewCategory.ViewHolder>{

    Context context;
    int layout;
    List<LoaiSanPhamDTO> loaiSanPhamDTOList;

    public AdapterRecycleViewCategory(Context context,int layout, List<LoaiSanPhamDTO> loaiSanPhamDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiSanPhamDTOList = loaiSanPhamDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewCategory.ViewHolder holder, int position) {
        LoaiSanPhamDTO loaiSanPhamDTO = loaiSanPhamDTOList.get(position);
        holder.txt_customcategory_TenLoai.setText(loaiSanPhamDTO.getTenLoai());
        byte[] categoryimage = loaiSanPhamDTO.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        holder.img_customcategory_HinhLoai.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return loaiSanPhamDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customcategory_TenLoai = itemView.findViewById(R.id.txt_customcategory_TenLoai);
            img_customcategory_HinhLoai = itemView.findViewById(R.id.img_customcategory_HinhLoai);
        }
    }

}