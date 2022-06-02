package com.tdtu.thuanthanh.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tdtu.thuanthanh.DTO.LoaiSanPhamDTO;
import com.tdtu.thuanthanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {

    SQLiteDatabase database;
    public LoaiSanPhamDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaiSanPhamDTO loaiSanPhamDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAITHUCDON_TENLOAI, loaiSanPhamDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAITHUCDON_HINHANH, loaiSanPhamDTO.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAITHUCDON,null,contentValues);

        return ktra != 0;
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAITHUCDON,CreateDatabase.TBL_LOAITHUCDON_MALOAI+ " = " +maloai
                ,null);
        return ktra != 0;
    }

    public boolean SuaLoaiMon(LoaiSanPhamDTO loaiSanPhamDTO, int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAITHUCDON_TENLOAI, loaiSanPhamDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAITHUCDON_HINHANH, loaiSanPhamDTO.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAITHUCDON,contentValues
                ,CreateDatabase.TBL_LOAITHUCDON_MALOAI+" = "+maloai,null);
        return ktra != 0;
    }

    public List<LoaiSanPhamDTO> LayDSLoaiMon(){
        List<LoaiSanPhamDTO> loaiSanPhamDTOList = new ArrayList<>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAITHUCDON;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
            loaiSanPhamDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_MALOAI)));
            loaiSanPhamDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_TENLOAI)));
            loaiSanPhamDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_HINHANH)));
            loaiSanPhamDTOList.add(loaiSanPhamDTO);

            cursor.moveToNext();
        }
        return loaiSanPhamDTOList;
    }

    public LoaiSanPhamDTO LayLoaiMonTheoMa(int maloai){
        LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAITHUCDON+" WHERE "+CreateDatabase.TBL_LOAITHUCDON_MALOAI+" = "+maloai;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiSanPhamDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_MALOAI)));
            loaiSanPhamDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_TENLOAI)));
            loaiSanPhamDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAITHUCDON_HINHANH)));

            cursor.moveToNext();
        }
        return loaiSanPhamDTO;
    }
}
