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

import com.tdtu.thuanthanh.Activities.AddStaffActivity;
import com.tdtu.thuanthanh.Activities.HomeActivity;
import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayStaff;
import com.tdtu.thuanthanh.DAO.NhanVienDAO;
import com.tdtu.thuanthanh.DTO.NhanVienDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;
import java.util.Objects;

public class DisplayStaffFragment extends Fragment {

    GridView gvStaff;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOS;
    AdapterDisplayStaff adapterDisplayStaff;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == Activity.RESULT_OK){
            Intent intent = result.getData();
            assert intent != null;
            long ktra = intent.getLongExtra("ketquaktra",0);
            String chucnang = intent.getStringExtra("chucnang");
            if(chucnang.equals("themnv"))
            {
                if(ktra != 0){
                    HienThiDSNV();
                    Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(ktra != 0){
                    HienThiDSNV();
                    Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                }
            }

        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystaff_layout,container,false);
        Objects.requireNonNull(((HomeActivity) requireActivity()).getSupportActionBar()).setTitle("Quản lý nhân viên");
        setHasOptionsMenu(true);

        gvStaff = (GridView)view.findViewById(R.id.gvStaff) ;

        nhanVienDAO = new NhanVienDAO(getActivity());
        HienThiDSNV();

        registerForContextMenu(gvStaff);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manv = nhanVienDTOS.get(vitri).getMANV();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(),AddStaffActivity.class);
                iEdit.putExtra("manv",manv);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = nhanVienDAO.XoaNV(manv);
                if(ktra){
                    HienThiDSNV();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1,R.id.itAddStaff,1,"Thêm nhân viên");
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itAddStaff) {
            Intent iDangky = new Intent(getActivity(), AddStaffActivity.class);
            resultLauncherAdd.launch(iDangky);
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSNV(){
        nhanVienDTOS = nhanVienDAO.LayDSNV();
        adapterDisplayStaff = new AdapterDisplayStaff(getActivity(),R.layout.custom_layout_displaystaff,nhanVienDTOS);
        gvStaff.setAdapter(adapterDisplayStaff);
        adapterDisplayStaff.notifyDataSetChanged();
    }
}