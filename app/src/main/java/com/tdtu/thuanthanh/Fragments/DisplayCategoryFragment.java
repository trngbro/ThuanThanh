package com.tdtu.thuanthanh.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tdtu.thuanthanh.Activities.AddCategoryActivity;
import com.tdtu.thuanthanh.Activities.HomeActivity;
import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayCategory;
import com.tdtu.thuanthanh.DAO.LoaiSanPhamDAO;
import com.tdtu.thuanthanh.DTO.LoaiSanPhamDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;
import java.util.Objects;

public class DisplayCategoryFragment extends Fragment {

    GridView gvCategory;
    List<LoaiSanPhamDTO> loaiSanPhamDTOList;
    LoaiSanPhamDAO loaiSanPhamDAO;
    AdapterDisplayCategory adapter;
    FragmentManager fragmentManager;
    int maban;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == Activity.RESULT_OK){
            Intent intent = result.getData();
            assert intent != null;
            boolean ktra = intent.getBooleanExtra("ktra",false);
            String chucnang = intent.getStringExtra("chucnang");
            if(chucnang.equals("themloai"))
            {
                if(ktra){
                    HienThiDSLoai();
                    Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(ktra){
                    HienThiDSLoai();
                    Toast.makeText(getActivity(),"Sủa thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"sửa thất bại",Toast.LENGTH_SHORT).show();
                }
            }

        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaycategory_layout,container,false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) requireActivity()).getSupportActionBar()).setTitle("Quản lý thực đơn");

        gvCategory = (GridView)view.findViewById(R.id.gvCategory);

        fragmentManager = requireActivity().getSupportFragmentManager();

        loaiSanPhamDAO = new LoaiSanPhamDAO(getActivity());
        HienThiDSLoai();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            maban = bDataCategory.getInt("maban");
        }

        gvCategory.setOnItemClickListener((parent, view1, position, id) -> {
            int maloai = loaiSanPhamDTOList.get(position).getMaLoai();
            String tenloai = loaiSanPhamDTOList.get(position).getTenLoai();
            DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("maloai",maloai);
            bundle.putString("tenloai",tenloai);
            bundle.putInt("maban",maban);
            displayMenuFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.contentView,displayMenuFragment).addToBackStack("hienthiloai");
            transaction.commit();
        });

        registerForContextMenu(gvCategory);

        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiSanPhamDTOList.get(vitri).getMaLoai();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                iEdit.putExtra("maloai",maloai);
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = loaiSanPhamDAO.XoaLoaiMon(maloai);
                if(ktra){
                    HienThiDSLoai();
                    Toast.makeText(getActivity(), requireActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), requireActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    //khởi tạo nút thêm Thực đơn
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //xử lý nút thêm Thực đơn
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itAddCategory) {
            Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
            resultLauncherCategory.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //hiển thị dữ liệu trên gridview
    private void HienThiDSLoai(){
        loaiSanPhamDTOList = loaiSanPhamDAO.LayDSLoaiMon();
        adapter = new AdapterDisplayCategory(getActivity(),R.layout.custom_layout_displaycategory, loaiSanPhamDTOList);
        gvCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}