package vn.edu.tnut.mysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DataDAO dataDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataDAO = new DataDAO(this);
        dataDAO.open();

        //nếu lần đầu chạy app: thì bổ xung vài row mặc định
        if(dataDAO.getDataCount()==0){
            final String hotens[]={"Hùng","Dũng","Tuấn","Tú"};
            for (String hoten : hotens)
                dataDAO.insertData(hoten);
        }

        listAllrow(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataDAO.close();
    }

    public void insert1row(View view) {
        //lấy họ tên nhập trên form
        EditText txtHoTen = findViewById(R.id.txtHoTen);
        String hoten = txtHoTen.getText().toString();
        //gọi dataDAO để insert

        dataDAO.insertData(hoten);
        listAllrow(view);
    }

    public void listAllrow(View view) {
        //gọi dataDAO để lấy mọi row -> string -> GUI
        String strAllRow = dataDAO.getAllDataAsString();
        TextView txtKQ = findViewById(R.id.txtKQ);
        txtKQ.setText(strAllRow);
    }
}