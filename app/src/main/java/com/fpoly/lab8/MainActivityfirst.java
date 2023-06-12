package com.fpoly.lab8;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivityfirst extends AppCompatActivity {
    private BookModels book;
    ListView listViewSach;
    ArrayList<BookModels> arrSach = new ArrayList<>();
    SachAdapter sachAdapter;
    ActivityResultLauncher<Intent> getData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        BookModels bookModels = (BookModels) result.getData().getSerializableExtra("datasach");

                        book.setTacGia(bookModels.getTacGia());
                        book.setTenSach(bookModels.getTenSach());
                        book.setNamXuatBan(bookModels.getNamXuatBan());
                        luuListDuLieu();
                        setDataChange();
                    }
                }
            }
    );
    String fileName = "book.txt";
    public void docDuLieu(){
        try {
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrSach = (ArrayList<BookModels>) ois.readObject();
            fis.close();
            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void luuListDuLieu() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(arrSach);
            oos.close();
            fileOutputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         listViewSach = findViewById(R.id.listview_sach);
        docDuLieu();
        if (arrSach.size() == 0 ){
            arrSach.add(new BookModels("Sach 1", 2022, "Tac Gia 1"));
            arrSach.add(new BookModels("Sach 2", 2023, "Tac Gia 2"));
            arrSach.add(new BookModels("Sach 3", 2020, "Tac Gia 3"));
        }
        luuListDuLieu();
       setDataChange();

    }
    private void setDataChange(){
        sachAdapter = new SachAdapter(this, arrSach);
        listViewSach.setAdapter(sachAdapter);
    }


    public void  suaDuLieu(int postion){
        Intent intent = new Intent(getApplicationContext(),ActivityUpdateSach.class);
        book = arrSach.get(postion);
        intent.putExtra("sach",book);
        getData.launch(intent);
    }

    private class SachAdapter extends BaseAdapter {

        Activity atv;
        ArrayList<BookModels> arrayList;

        public SachAdapter(Activity atv, ArrayList<BookModels> arrayList) {
            this.atv = atv;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = atv.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_sach, parent, false);
            BookModels book = arrayList.get(position);
            TextView txtSach = convertView.findViewById(R.id.txt_tensach);
            TextView txtNamXuatBan = convertView.findViewById(R.id.txt_namxuatban);
            TextView txtTacGia = convertView.findViewById(R.id.txt_tacgia);


            txtSach.setText(book.getTenSach());
            txtNamXuatBan.setText(book.getNamXuatBan() + "");
            txtTacGia.setText(book.getTacGia());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  ((MainActivityfirst)atv).suaDuLieu(position);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder confirm = new AlertDialog.Builder(atv);
                    confirm.setMessage("Bán có muốn xóa sinh viên này không ?");

                    confirm.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            luuListDuLieu();
                            Toast.makeText(atv, "Xoá thành công !!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    confirm.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    confirm.show();

                    notifyDataSetChanged();
                    return false;
                }
            });

            return convertView;
        }
    }
}