package com.tdtu.thuanthanh.CustomAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdtu.thuanthanh.DAO.PhieuMuaDAO;
import com.tdtu.thuanthanh.DAO.NhanVienDAO;
import com.tdtu.thuanthanh.DTO.DonDatDTO;
import com.tdtu.thuanthanh.DTO.NhanVienDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<DonDatDTO> donDatDTOS;
    ViewHolder viewHolder;
    NhanVienDAO nhanVienDAO;
    PhieuMuaDAO phieuMuaDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<DonDatDTO> donDatDTOS){
        this.context = context;
        this.layout = layout;
        this.donDatDTOS = donDatDTOS;
        nhanVienDAO = new NhanVienDAO(context);
        phieuMuaDAO = new PhieuMuaDAO(context);
    }

    @Override
    public int getCount() {
        return donDatDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatDTOS.get(position).getMaDonDat();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDatDTO donDatDTO = donDatDTOS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("M?? ????n: "+donDatDTO.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTO.getTongTien()+" VN??");
        if (donDatDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("???? thanh to??n");
        }else{
            viewHolder.txt_customstatistic_TrangThai.setText("Ch??a thanh to??n");
        }
        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        viewHolder.txt_customstatistic_BanDat.setText(phieuMuaDAO.LayTenBanTheoMa(donDatDTO.getMaBan()));

        return view;
    }
    public static class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}