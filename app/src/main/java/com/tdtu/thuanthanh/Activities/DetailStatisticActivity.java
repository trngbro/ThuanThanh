package com.tdtu.thuanthanh.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayPayment;
import com.tdtu.thuanthanh.DAO.NhanVienDAO;
import com.tdtu.thuanthanh.DAO.PhieuMuaDAO;
import com.tdtu.thuanthanh.DAO.ThanhToanDAO;
import com.tdtu.thuanthanh.DTO.NhanVienDTO;
import com.tdtu.thuanthanh.DTO.ThanhToanDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon, txt_detailstatistic_NgayDat, txt_detailstatistic_TenBan , txt_detailstatistic_TenNV, txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, maban;
    String ngaydat, tongtien;
    NhanVienDAO nhanVienDAO;
    PhieuMuaDAO phieuMuaDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    ThanhToanDAO thanhToanDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstatistic_layout);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        manv = intent.getIntExtra("manv",0);
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayDat = (TextView)findViewById(R.id.txt_detailstatistic_NgayDat);
        txt_detailstatistic_TenBan = (TextView)findViewById(R.id.txt_detailstatistic_TenBan);
        txt_detailstatistic_TenNV = (TextView)findViewById(R.id.txt_detailstatistic_TenNV);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //end

        //khởi tạo lớp dao mở kết nối csdl
        nhanVienDAO = new NhanVienDAO(this);
        phieuMuaDAO = new PhieuMuaDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayDat.setText(ngaydat);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(manv);
            txt_detailstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
            txt_detailstatistic_TenBan.setText(phieuMuaDAO.LayTenBanTheoMa(maban));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        });
    }

    private void HienThiDSCTDD(){
        thanhToanDTOList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}