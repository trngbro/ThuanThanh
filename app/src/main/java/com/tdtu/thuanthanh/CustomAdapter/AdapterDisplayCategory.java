package com.tdtu.thuanthanh.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdtu.thuanthanh.DTO.LoaiSanPhamDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class AdapterDisplayCategory extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiSanPhamDTO> loaiSanPhamDTOList;
    ViewHolder viewHolder;

    //constructor
    public AdapterDisplayCategory(Context context, int layout, List<LoaiSanPhamDTO> loaiSanPhamDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiSanPhamDTOList = loaiSanPhamDTOList;
    }

    @Override
    public int getCount() {
        return loaiSanPhamDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSanPhamDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiSanPhamDTOList.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.img_customcategory_HinhLoai = (ImageView)view.findViewById(R.id.img_customcategory_HinhLoai);
            viewHolder.txt_customcategory_TenLoai = (TextView)view.findViewById(R.id.txt_customcategory_TenLoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSanPhamDTO loaiSanPhamDTO = loaiSanPhamDTOList.get(position);

        viewHolder.txt_customcategory_TenLoai.setText(loaiSanPhamDTO.getTenLoai());

        byte[] categoryimage = loaiSanPhamDTO.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.img_customcategory_HinhLoai.setImageBitmap(bitmap);

        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;
    }
}