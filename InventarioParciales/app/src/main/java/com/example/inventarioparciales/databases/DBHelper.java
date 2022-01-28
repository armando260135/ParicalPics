/*
package com.example.inventarioparciales.databases;

import static com.example.inventarioparciales.Utils.DB_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //creamos las dos bases de datos
        MyDB.execSQL("create Table usuarios(nombreusuario TEXT primary key, password TEXT, email TEXT, telefono TEXT)");
        MyDB.execSQL("create Table materias(codigomateria INTEGER primary key, nombremateria TEXT, codigoicono INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists usuarios");
        MyDB.execSQL("drop Table if exists materias");
    }

    // metodo inserta los datos en la tabla usuarios
    public boolean insertData(String nombreUsuario, String password,String email, String telefono){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombreusuario",nombreUsuario);
        cv.put("password",password);
        cv.put("email",email);
        cv.put("telefono",telefono);

        Long results = MyDB.insert("usuarios",null ,cv);
        if (results==-1){
            return false;
        }else{
            return true;
        }
    }
    // verifica que no haya usuarios duplicados
    public boolean checkNombreUsuario(String nombreusuario){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from usuarios where nombreusuario=?",new String[]{nombreusuario});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    // verifica que coincidan nombre de usuario y clave
    public boolean checkNombreUsuarioClave (String nombreusuario, String clave){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from usuarios where nombreusuario=? and password=?",new String[]{nombreusuario,clave});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    // metodo inserta los datos en la tabla materias  materias(codigomateria INTEGER primary key, nombremateria TEXT)
    public boolean insertDataMaterias(int codigo, String nombre,int id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("codigomateria",codigo);
        cv.put("nombremateria", nombre);
        cv.put("codigoicono",id);

        Long results = MyDB.insert("materias",null ,cv);
        if (results==-1){
            return false;
        }else{
            return true;
        }
    }
}
*/
