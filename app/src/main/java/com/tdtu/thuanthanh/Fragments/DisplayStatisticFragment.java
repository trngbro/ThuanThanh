package com.tdtu.thuanthanh.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.tdtu.thuanthanh.Activities.DetailStatisticActivity;
import com.tdtu.thuanthanh.Activities.HomeActivity;
import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayStatistic;
import com.tdtu.thuanthanh.DAO.DonDatDAO;
import com.tdtu.thuanthanh.DTO.DonDatDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;
import java.util.Objects;

public class DisplayStatisticFragment extends Fragment {

    ListView lvStatistic;
    List<DonDatDTO> donDatDTOS;
    DonDatDAO donDatDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    int madon, manv, maban;
    String ngaydat, tongtien;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_layout,container,false);
        Objects.requireNonNull(((HomeActivity) requireActivity()).getSupportActionBar()).setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        donDatDAO = new DonDatDAO(getActivity());

        donDatDTOS = donDatDAO.LayDSDonDat();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donDatDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener((parent, view1, position, id) -> {
            madon = donDatDTOS.get(position).getMaDonDat();
            manv = donDatDTOS.get(position).getMaNV();
            maban = donDatDTOS.get(position).getMaBan();
            ngaydat = donDatDTOS.get(position).getNgayDat();
            tongtien = donDatDTOS.get(position).getTongTien();

            Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
            intent.putExtra("madon",madon);
            intent.putExtra("manv",manv);
            intent.putExtra("maban",maban);
            intent.putExtra("ngaydat",ngaydat);
            intent.putExtra("tongtien",tongtien);
            startActivity(intent);
        });
        return view;
    }
}