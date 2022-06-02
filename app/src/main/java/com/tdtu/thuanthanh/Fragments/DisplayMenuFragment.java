package com.tdtu.thuanthanh.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
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

import com.tdtu.thuanthanh.Activities.AddMenuActivity;
import com.tdtu.thuanthanh.Activities.AmountMenuActivity;
import com.tdtu.thuanthanh.Activities.HomeActivity;
import com.tdtu.thuanthanh.CustomAdapter.AdapterDisplayMenu;
import com.tdtu.thuanthanh.DAO.SanPhamDAO;
import com.tdtu.thuanthanh.DTO.SanPhamDTO;
import com.tdtu.thuanthanh.R;

import java.util.List;
import java.util.Objects;

public class DisplayMenuFragment extends Fragment {

    int maloai, maban;
    String tenloai, tinhtrang;
    GridView gvDisplayMenu;
    SanPhamDAO sanPhamDAO;
    List<SanPhamDTO> sanPhamDTOList;
    AdapterDisplayMenu adapterDisplayMenu;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == Activity.RESULT_OK){
            Intent intent = result.getData();
            assert intent != null;
            boolean ktra = intent.getBooleanExtra("ktra",false);
            String chucnang = intent.getStringExtra("chucnang");
            if(chucnang.equals("themmon"))
            {
                if(ktra){
                    HienThiDSMon();
                    Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(ktra){
                    HienThiDSMon();
                    Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                }
            }

        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaymenu_layout,container,false);
        Objects.requireNonNull(((HomeActivity) requireActivity()).getSupportActionBar()).setTitle("Quản lý thực đơn");
        sanPhamDAO = new SanPhamDAO(getActivity());

        gvDisplayMenu = (GridView)view.findViewById(R.id.gvDisplayMenu);

        Bundle bundle = getArguments();
        if(bundle !=null){
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvDisplayMenu.setOnItemClickListener((parent, view1, position, id) -> {
                //nếu lấy đc mã bàn ăn mua mới mở
                tinhtrang = sanPhamDTOList.get(position).getTinhTrang();
                if(maban != 0){
                    if(tinhtrang.equals("true")){
                        Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                        iAmount.putExtra("maban",maban);
                        iAmount.putExtra("mamon", sanPhamDTOList.get(position).getMaMon());
                        startActivity(iAmount);
                    }else{
                        Toast.makeText(getActivity(),"món ăn đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                 getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            return false;
        });

        return view;
    }

    //tạo 1 menu context show lựa chọn
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = sanPhamDTOList.get(vitri).getMaMon();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("mamon",mamon);
                iEdit.putExtra("maLoai",maloai);
                iEdit.putExtra("tenLoai",tenloai);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = sanPhamDAO.XoaMon(mamon);
                if(ktra){
                    HienThiDSMon();
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
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itAddMenu) {
            Intent intent = new Intent(getActivity(), AddMenuActivity.class);
            intent.putExtra("maLoai", maloai);
            intent.putExtra("tenLoai", tenloai);
            resultLauncherMenu.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void HienThiDSMon(){
        sanPhamDTOList = sanPhamDAO.LayDSMonTheoLoai(maloai);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(),R.layout.custom_layout_displaymenu, sanPhamDTOList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }
}