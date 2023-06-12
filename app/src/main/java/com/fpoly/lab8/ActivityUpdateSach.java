package com.fpoly.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityUpdateSach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        EditText edtName = findViewById(R.id.edt_updateName);
        EditText edtYear = findViewById(R.id.edt_updateYear);
        EditText edtTacGia = findViewById(R.id.edt_updateTacGia);

        BookModels bookModels = (BookModels) getIntent().getSerializableExtra("sach");

        edtName.setText(bookModels.getTenSach());
        edtYear.setText(bookModels.getNamXuatBan()+"");
        edtTacGia.setText(bookModels.getTacGia());

        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtName.getText().toString();
                String year = edtYear.getText().toString();

                String tg = edtTacGia.getText().toString();
                // validate != null
                if (tenSach.length()==0 || tenSach.trim().equals("")){
                    Toast.makeText(ActivityUpdateSach.this, "Bạn chưa nhập tên sách", Toast.LENGTH_SHORT).show();
                }else if (year.length() == 0 || year.trim().equals("")){
                    Toast.makeText(ActivityUpdateSach.this, "Bạn chưa nhập năm xuất bản", Toast.LENGTH_SHORT).show();
                }else if (tg.length() == 0 || tg.trim().equals("")){
                    Toast.makeText(ActivityUpdateSach.this, "Bạn chưa nhập tên tác giả", Toast.LENGTH_SHORT).show();
                }else {
                    int namXuatBan = Integer.parseInt(year);
                    BookModels bookModels1 = new BookModels(tenSach,namXuatBan,tg);
                    Intent intent = new Intent();
                    intent.putExtra("datasach",bookModels1);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }
}