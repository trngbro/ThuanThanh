package com.tdtu.thuanthanh.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class    CreateDatabase extends SQLiteOpenHelper {

    public static String TBL_NHANVIEN = "NHANVIEN";
    public static String TBL_MONAN = "MON";
    public static String TBL_LOAITHUCDON = "LOAIMON";
    public static String TBL_BANAN = "BAN";
    public static String TBL_HOADON = "DONDAT";
    public static String TBL_CHITIETDONDAT = "CHITIETDONDAT";
    public static String TBL_QUYEN = "QUYEN";

    //Bảng nhân viên
    public static String TBL_NHANVIEN_MANV = "MANV";
    public static String TBL_NHANVIEN_HOTENNV = "HOTENNV";
    public static String TBL_NHANVIEN_TENDN = "TENDN";
    public static String TBL_NHANVIEN_MATKHAU = "MATKHAU";
    public static String TBL_NHANVIEN_EMAIL = "EMAIL";
    public static String TBL_NHANVIEN_SDT = "SDT";
    public static String TBL_NHANVIEN_GIOITINH = "GIOITINH";
    public static String TBL_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static String TBL_NHANVIEN_MAQUYEN= "MAQUYEN";

    //Bảng quyền
    public static String TBL_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TBL_QUYEN_TENQUYEN = "TENQUYEN";

    //Bảng món ăn
    public static String TBL_MONAN_MASANPHAM = "MAMON";
    public static String TBL_MONAN_TENSANPHAM = "TENMON";
    public static String TBL_MONAN_GIATIEN = "GIATIEN";
    public static String TBL_MONAN_TINHTRANG = "TINHTRANG";
    public static String TBL_MONAN_HINHANH = "HINHANH";
    public static String TBL_MONAN_MALOAI = "MALOAI";

    //Bảng Thực đơn
    public static String TBL_LOAITHUCDON_MALOAI = "MALOAI";
    public static String TBL_LOAITHUCDON_TENLOAI = "TENLOAI";
    public static String TBL_LOAITHUCDON_HINHANH = "HINHANH";

    //Bảng bàn ăn
    public static String TBL_BANAN_MABAN = "MABAN";
    public static String TBL_BANAN_TENBAN = "TENBAN";
    public static String TBL_BANAN_TINHTRANG = "TINHTRANG";

    //Bảng hoá đơn (đơn đặt)
    public static String TBL_HOADON_MADONDAT = "MADONDAT";
    public static String TBL_HOADON_MANV = "MANV";
    public static String TBL_HOADON_NGAYDAT = "NGAYDAT";
    public static String TBL_HOADON_TINHTRANG = "TINHTRANG";
    public static String TBL_HOADON_TONGTIEN = "TONGTIEN";
    public static String TBL_HOADON_MABAN = "MABAN";

    //Bảng chi tiết hoá đơn
    public static String TBL_CHITIETDONDAT_MADONDAT = "MADONDAT";
    public static String TBL_CHITIETDONDAT_MAMON = "MAMON";
    public static String TBL_CHITIETDONDAT_SOLUONG = "SOLUONG";


    public CreateDatabase(Context context) {
        super(context, "ThuanThanh", null, 1);
    }

    //thực hiện tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblNHANVIEN = "CREATE TABLE " +TBL_NHANVIEN+ " ( " +TBL_NHANVIEN_MANV+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_NHANVIEN_HOTENNV+ " TEXT, " +TBL_NHANVIEN_TENDN+ " TEXT, " +TBL_NHANVIEN_MATKHAU+ " TEXT, " +TBL_NHANVIEN_EMAIL+ " TEXT, "
                +TBL_NHANVIEN_SDT+ " TEXT, " +TBL_NHANVIEN_GIOITINH+ " TEXT, " +TBL_NHANVIEN_NGAYSINH+ " TEXT , "+TBL_NHANVIEN_MAQUYEN+" INTEGER)";

        String tblQUYEN = "CREATE TABLE " +TBL_QUYEN+ " ( " +TBL_QUYEN_MAQUYEN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_QUYEN_TENQUYEN+ " TEXT)" ;

        String tblBAN = "CREATE TABLE " +TBL_BANAN+ " ( " +TBL_BANAN_MABAN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_BANAN_TENBAN+ " TEXT, " +TBL_BANAN_TINHTRANG+ " TEXT )";

        String tblMON = "CREATE TABLE " +TBL_MONAN+ " ( " +TBL_MONAN_MASANPHAM+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_MONAN_TENSANPHAM+ " TEXT, " +TBL_MONAN_GIATIEN+ " TEXT, " +TBL_MONAN_TINHTRANG+ " TEXT, "
                +TBL_MONAN_HINHANH+ " BLOB, "+TBL_MONAN_MALOAI+ " INTEGER )";

        String tblLOAIMON = "CREATE TABLE " +TBL_LOAITHUCDON+ " ( " +TBL_LOAITHUCDON_MALOAI+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_LOAITHUCDON_HINHANH+ " BLOB, " +TBL_LOAITHUCDON_TENLOAI+ " TEXT)" ;

        String tblDONDAT = "CREATE TABLE " +TBL_HOADON+ " ( " +TBL_HOADON_MADONDAT+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_HOADON_MABAN+ " INTEGER, " +TBL_HOADON_MANV+ " INTEGER, " +TBL_HOADON_NGAYDAT+ " TEXT, "+TBL_HOADON_TONGTIEN+" TEXT,"
                +TBL_HOADON_TINHTRANG+ " TEXT )" ;

        String tblCHITIETDONDAT = "CREATE TABLE " +TBL_CHITIETDONDAT+ " ( " +TBL_CHITIETDONDAT_MADONDAT+ " INTEGER, "
                +TBL_CHITIETDONDAT_MAMON+ " INTEGER, " +TBL_CHITIETDONDAT_SOLUONG+ " INTEGER, "
                + " PRIMARY KEY ( " +TBL_CHITIETDONDAT_MADONDAT+ "," +TBL_CHITIETDONDAT_MAMON+ "))";

        db.execSQL(tblNHANVIEN);
        db.execSQL(tblQUYEN);
        db.execSQL(tblBAN);
        db.execSQL(tblMON);
        db.execSQL(tblLOAIMON);
        db.execSQL(tblDONDAT);
        db.execSQL(tblCHITIETDONDAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}