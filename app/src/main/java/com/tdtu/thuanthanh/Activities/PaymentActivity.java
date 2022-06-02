package com.tdtu.thuanthanh.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayPayment;
import com.tdtu.thuanthanh.DAO.PhieuMuaDAO;
import com.tdtu.thuanthanh.DAO.DonDatDAO;
import com.tdtu.thuanthanh.DAO.ThanhToanDAO;
import com.tdtu.thuanthanh.DTO.ThanhToanDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonDatDAO donDatDAO;
    PhieuMuaDAO phieuMuaDAO;
    ThanhToanDAO thanhToanDAO;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int maban, madondat;
    FragmentManager fragmentManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        //region thuộc tính view
        gvDisplayPayment= findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayDat = findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        phieuMuaDAO = new PhieuMuaDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn ăn mua đc chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        TXT_payment_TenBan.setText(tenban);
        TXT_payment_NgayDat.setText(ngaydat);

        //ktra mã bàn ăn mua tồn tại thì hiển thị
        if(maban !=0 ){
            HienThiThanhToan();

            for (int i=0;i<thanhToanDTOS.size();i++){
                int soluong = thanhToanDTOS.get(i).getSoLuong();
                int giatien = thanhToanDTOS.get(i).getGiaTien();

                tongtien += ((long) soluong * giatien);
            }
            TXT_payment_TongTien.setText(tongtien +" VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                boolean ktraban = phieuMuaDAO.CapNhatTinhTrangBan(maban,"false");
                boolean ktradondat = donDatDAO.UpdateTThaiDonTheoMaBan(maban,"true");
                boolean ktratongtien = donDatDAO.UpdateTongTienDonDat(madondat,String.valueOf(tongtien));
                if(ktraban && ktradondat && ktratongtien){
                    HienThiThanhToan();
                    TXT_payment_TongTien.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan(){
        madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban,"false");
        thanhToanDTOS = thanhToanDAO.LayDSMonTheoMaDon(madondat);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}