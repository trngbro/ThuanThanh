package com.tdtu.thuanthanh.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tdtu.thuanthanh.DTO.PhieuMuaDTO;
import com.tdtu.thuanthanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuaDAO {
    SQLiteDatabase database;
    public PhieuMuaDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm bàn ăn mua ăn mới
    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BANAN_TENBAN,tenban);
        contentValues.put(CreateDatabase.TBL_BANAN_TINHTRANG,"false");

        long ktra = database.insert(CreateDatabase.TBL_BANAN,null,contentValues);
        return ktra != 0;
    }

    //Hàm xóa bàn ăn mua ăn theo mã
    public boolean XoaBanTheoMa(int maban){
        long ktra =database.delete(CreateDatabase.TBL_BANAN,CreateDatabase.TBL_BANAN_MABAN+" = "+maban,null);
        return ktra != 0;
    }

    //Sửa tên bàn ăn mua
    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BANAN_TENBAN,tenban);

        long ktra = database.update(CreateDatabase.TBL_BANAN,contentValues,CreateDatabase.TBL_BANAN_MABAN+ " = '"+maban+"' ",null);
        return ktra != 0;
    }

    //Hàm lấy ds các bàn ăn mua ăn đổ vào gridview
    public List<PhieuMuaDTO> LayTatCaBanAn(){
        List<PhieuMuaDTO> phieuMuaDTOList = new ArrayList<>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_BANAN;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhieuMuaDTO phieuMuaDTO = new PhieuMuaDTO();
            phieuMuaDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BANAN_MABAN)));
            phieuMuaDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BANAN_TENBAN)));

            phieuMuaDTOList.add(phieuMuaDTO);
            cursor.moveToNext();
        }
        return phieuMuaDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BANAN + " WHERE " +CreateDatabase.TBL_BANAN_MABAN+ " = '" +maban+ "' ";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BANAN_TINHTRANG,tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_BANAN,contentValues,CreateDatabase.TBL_BANAN_MABAN+ " = '"+maban+"' ",null);
        return ktra != 0;
    }

    public String LayTenBanTheoMa(int maban){
        String tenban="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BANAN + " WHERE " +CreateDatabase.TBL_BANAN_MABAN+ " = '" +maban+ "' ";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BANAN_TENBAN));
            cursor.moveToNext();
        }
        return tenban;
    }
}
