package vn.edu.tnut.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm dữ liệu
    public long insertData(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    // Truy vấn dữ liệu
    public Cursor getAllData() {
        String[] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME};
        return database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
    }
    public int getDataCount() {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        // Đóng Cursor sau khi sử dụng xong
        cursor.close();

        return count;
    }
    public String getAllDataAsString() {
        StringBuilder result = new StringBuilder();

        String[] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        // Di chuyển con trỏ đến vị trí đầu tiên của Cursor
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ Cursor cho mỗi cột
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));

                // Thêm dòng vào chuỗi kết quả
                result.append("ID: ").append(id).append(", Name: ").append(name).append("\n");
            } while (cursor.moveToNext());
        }

        // Đóng Cursor sau khi sử dụng xong
        cursor.close();

        return result.toString();
    }

    // Cập nhật dữ liệu
    public int updateData(long id, String newName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, newName);
        return database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    // Xoá dữ liệu
    public int deleteData(long id) {
        return database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + " = " + id, null);
    }
}
