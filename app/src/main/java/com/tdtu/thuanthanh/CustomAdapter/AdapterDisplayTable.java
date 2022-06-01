package com.tdtu.thuanthanh.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tdtu.thuanthanh.Activities.HomeActivity;
import com.tdtu.thuanthanh.Activities.PaymentActivity;
import com.tdtu.thuanthanh.DAO.PhieuMuaDAO;
import com.tdtu.thuanthanh.DAO.DonDatDAO;
import com.tdtu.thuanthanh.DTO.PhieuMuaDTO;
import com.tdtu.thuanthanh.DTO.DonDatDTO;
import com.tdtu.thuanthanh.Fragments.DisplayCategoryFragment;
import com.tdtu.thuanthanh.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<PhieuMuaDTO> phieuMuaDTOList;
    ViewHolder viewHolder;
    PhieuMuaDAO phieuMuaDAO;
    DonDatDAO donDatDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<PhieuMuaDTO> phieuMuaDTOList){
        this.context = context;
        this.layout = layout;
        this.phieuMuaDTOList = phieuMuaDTOList;
        phieuMuaDAO = new PhieuMuaDAO(context);
        donDatDAO = new DonDatDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return phieuMuaDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return phieuMuaDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return phieuMuaDTOList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgBanAn = (ImageView) view.findViewById(R.id.img_customtable_BanAn);
            viewHolder.imgGoiMon = (ImageView) view.findViewById(R.id.img_customtable_GoiMon);
            viewHolder.imgThanhToan = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txtTenBanAn = (TextView)view.findViewById(R.id.txt_customtable_TenBanAn);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(phieuMuaDTOList.get(position).isDuocChon()){
            HienThiButton();
        }else{
            AnButton();
        }

        PhieuMuaDTO phieuMuaDTO = phieuMuaDTOList.get(position);

        String kttinhtrang = phieuMuaDAO.LayTinhTrangBanTheoMa(phieuMuaDTO.getMaBan());
        //đổi hình theo tình trạng
        if(kttinhtrang.equals("true")){
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_radio_button_unchecked);
        }else{
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_weekend_24);
        }

        viewHolder.txtTenBanAn.setText(phieuMuaDTO.getTenBan());
        viewHolder.imgBanAn.setTag(position);

        //sự kiện click
        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int vitri1 = (int) viewHolder.imgBanAn.getTag();

        int maban = phieuMuaDTOList.get(vitri1).getMaBan();
        String tenban = phieuMuaDTOList.get(vitri1).getTenBan();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customtable_BanAn:
                int vitri = (int)v.getTag();
                phieuMuaDTOList.get(vitri).setDuocChon(true);
                HienThiButton();
                break;

            case R.id.img_customtable_AnNut:
                AnButton();
                break;

            case R.id.img_customtable_GoiMon:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int manv = getIHome.getIntExtra("manv",0);
                String tinhtrang = phieuMuaDAO.LayTinhTrangBanTheoMa(maban);

                if(tinhtrang.equals("false")){
                    //Thêm bảng gọi món ăn và update tình trạng bàn ăn mua
                    DonDatDTO donDatDTO = new DonDatDTO();
                    donDatDTO.setMaBan(maban);
                    donDatDTO.setMaNV(manv);
                    donDatDTO.setNgayDat(ngaydat);
                    donDatDTO.setTinhTrang("false");
                    donDatDTO.setTongTien("0");

                    long ktra = donDatDAO.ThemDonDat(donDatDTO);
                    phieuMuaDAO.CapNhatTinhTrangBan(maban,"true");
                    if(ktra == 0){ Toast.makeText(context,context.getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban",maban);
                displayCategoryFragment.setArguments(bDataCategory);

                transaction.replace(R.id.contentView,displayCategoryFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;

            case R.id.img_customtable_ThanhToan:
                //chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("maban",maban);
                iThanhToan.putExtra("tenban",tenban);
                iThanhToan.putExtra("ngaydat",ngaydat);
                context.startActivity(iThanhToan);
                break;
        }
    }

    private void HienThiButton(){
        viewHolder.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnNut;
        TextView txtTenBanAn;
    }
}