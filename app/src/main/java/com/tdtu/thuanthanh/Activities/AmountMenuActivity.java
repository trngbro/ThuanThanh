package com.tdtu.thuanthanh.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.tdtu.thuanthanh.DAO.ChiTietDonDatDAO;
import com.tdtu.thuanthanh.DAO.DonDatDAO;
import com.tdtu.thuanthanh.DTO.ChiTietDonDatDTO;
import com.tdtu.thuanthanh.R;
import java.util.Objects;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    int maban, mamon;
    DonDatDAO donDatDAO;
    ChiTietDonDatDAO chiTietDonDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = (TextInputLayout)findViewById(R.id.txtl_amountmenu_SoLuong);
        BTN_amountmenu_DongY = (Button)findViewById(R.id.btn_amountmenu_DongY);

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        //Lấy thông tin từ bàn ăn mua được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamon = intent.getIntExtra("mamon",0);

        BTN_amountmenu_DongY.setOnClickListener(v -> {
            if(!validateAmount()){
                return;
            }

            int madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban,"false");
            boolean ktra = chiTietDonDatDAO.KiemTraMonTonTai(madondat,mamon);
            if(ktra){
                //update số lượng món ăn đã chọn
                int sluongcu = chiTietDonDatDAO.LaySLMonTheoMaDon(madondat,mamon);
                int sluongmoi = Integer.parseInt(Objects.requireNonNull(TXTL_amountmenu_SoLuong.getEditText()).getText().toString());
                int tongsl = sluongcu + sluongmoi;

                ChiTietDonDatDTO chiTietDonDatDTO = new ChiTietDonDatDTO();
                chiTietDonDatDTO.setMaMon(mamon);
                chiTietDonDatDTO.setMaDonDat(madondat);
                chiTietDonDatDTO.setSoLuong(tongsl);

                boolean ktracapnhat = chiTietDonDatDAO.CapNhatSL(chiTietDonDatDTO);
                if(ktracapnhat){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }else{
                //thêm số lượng món ăn nếu chưa chọn món ăn này
                int sluong = Integer.parseInt(Objects.requireNonNull(TXTL_amountmenu_SoLuong.getEditText()).getText().toString());
                ChiTietDonDatDTO chiTietDonDatDTO = new ChiTietDonDatDTO();
                chiTietDonDatDTO.setMaMon(mamon);
                chiTietDonDatDTO.setMaDonDat(madondat);
                chiTietDonDatDTO.setSoLuong(sluong);

                boolean ktracapnhat = chiTietDonDatDAO.ThemChiTietDonDat(chiTietDonDatDTO);
                if(ktracapnhat){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount(){
        String val = Objects.requireNonNull(TXTL_amountmenu_SoLuong.getEditText()).getText().toString().trim();
        if(val.isEmpty()){
            TXTL_amountmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else if(Integer.parseInt(val)==0) {
            TXTL_amountmenu_SoLuong.setError("Số lượng phải lớn hơn 0");
            return false;
        }else{
            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}