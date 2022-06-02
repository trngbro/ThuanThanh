package com.tdtu.thuanthanh.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.tdtu.thuanthanh.DAO.PhieuMuaDAO;
import com.tdtu.thuanthanh.R;

import java.util.Objects;

public class EditTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_edittable_tenban;
    Button BTN_edittable_SuaBan;
    PhieuMuaDAO phieuMuaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittable_layout);

        //thuộc tính view
        TXTL_edittable_tenban = (TextInputLayout)findViewById(R.id.txtl_edittable_tenban);
        BTN_edittable_SuaBan = (Button)findViewById(R.id.btn_edittable_SuaBan);

        //khởi tạo dao mở kết nối csdl
        phieuMuaDAO = new PhieuMuaDAO(this);
        int maban = getIntent().getIntExtra("maban",0); //lấy maban từ bàn ăn mua đc chọn

        BTN_edittable_SuaBan.setOnClickListener(v -> {
            String tenban = Objects.requireNonNull(TXTL_edittable_tenban.getEditText()).getText().toString();

            boolean ktra = phieuMuaDAO.CapNhatTenBan(maban,tenban);
            Intent intent = new Intent();
            intent.putExtra("ketquasua",ktra);
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}