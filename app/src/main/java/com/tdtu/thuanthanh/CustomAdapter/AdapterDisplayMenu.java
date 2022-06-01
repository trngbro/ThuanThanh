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

import com.tdtu.thuanthanh.DTO.SanPhamDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    Context context;
    int layout;
    List<SanPhamDTO> sanPhamDTOList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<SanPhamDTO> sanPhamDTOList){
        this.context = context;
        this.layout = layout;
        this.sanPhamDTOList = sanPhamDTOList;
    }

    @Override
    public int getCount() {
        return sanPhamDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhamDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sanPhamDTOList.get(position).getMaMon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhMon = (ImageView)view.findViewById(R.id.img_custommenu_HinhMon);
            viewholder.txt_custommenu_TenMon = (TextView) view.findViewById(R.id.txt_custommenu_TenMon);
            viewholder.txt_custommenu_TinhTrang = (TextView)view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        SanPhamDTO sanPhamDTO = sanPhamDTOList.get(position);
        viewholder.txt_custommenu_TenMon.setText(sanPhamDTO.getTenMon());
        viewholder.txt_custommenu_GiaTien.setText(sanPhamDTO.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của món ăn
        if(sanPhamDTO.getTinhTrang().equals("true")){
            viewholder.txt_custommenu_TinhTrang.setText("Còn món ăn");
        }else{
            viewholder.txt_custommenu_TinhTrang.setText("Hết món ăn");
        }

        //lấy hình ảnh
        if(sanPhamDTO.getHinhAnh() != null){
            byte[] menuimage = sanPhamDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhMon.setImageResource(R.drawable.pic_item);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_HinhMon;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien,txt_custommenu_TinhTrang;
    }
}