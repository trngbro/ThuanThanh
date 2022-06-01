package com.tdtu.thuanthanh.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tdtu.thuanthanh.DTO.SanPhamDTO;
import com.tdtu.thuanthanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    SQLiteDatabase database;
    public SanPhamDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(SanPhamDTO sanPhamDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MONAN_MALOAI, sanPhamDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MONAN_TENSANPHAM, sanPhamDTO.getTenMon());
        contentValues.put(CreateDatabase.TBL_MONAN_GIATIEN, sanPhamDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MONAN_HINHANH, sanPhamDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MONAN_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_MONAN,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_MONAN,CreateDatabase.TBL_MONAN_MASANPHAM+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(SanPhamDTO sanPhamDTO, int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MONAN_MALOAI, sanPhamDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MONAN_TENSANPHAM, sanPhamDTO.getTenMon());
        contentValues.put(CreateDatabase.TBL_MONAN_GIATIEN, sanPhamDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MONAN_HINHANH, sanPhamDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MONAN_TINHTRANG, sanPhamDTO.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_MONAN,contentValues,
                CreateDatabase.TBL_MONAN_MASANPHAM+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<SanPhamDTO> LayDSMonTheoLoai(int maloai){
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<SanPhamDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_MONAN+ " WHERE " +CreateDatabase.TBL_MONAN_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_HINHANH)));
            sanPhamDTO.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_TENSANPHAM)));
            sanPhamDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_MALOAI)));
            sanPhamDTO.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_MASANPHAM)));
            sanPhamDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_GIATIEN)));
            sanPhamDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_TINHTRANG)));
            sanPhamDTOList.add(sanPhamDTO);

            cursor.moveToNext();
        }
        return sanPhamDTOList;
    }

    public SanPhamDTO LayMonTheoMa(int mamon){
        SanPhamDTO sanPhamDTO = new SanPhamDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_MONAN+" WHERE "+CreateDatabase.TBL_MONAN_MASANPHAM+" = "+mamon;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            sanPhamDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_HINHANH)));
            sanPhamDTO.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_TENSANPHAM)));
            sanPhamDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_MALOAI)));
            sanPhamDTO.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_MASANPHAM)));
            sanPhamDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_GIATIEN)));
            sanPhamDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MONAN_TINHTRANG)));

            cursor.moveToNext();
        }
        return sanPhamDTO;
    }

}
