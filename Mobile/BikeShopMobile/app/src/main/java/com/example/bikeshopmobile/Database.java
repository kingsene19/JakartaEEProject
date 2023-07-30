package com.example.bikeshopmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.entites.Produit;
import com.example.bikeshopmobile.entites.Stock;
import com.example.bikeshopmobile.entites.StockPK;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.RetrofitClient;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table stocks(quantite integer, produit_id integer, magasin_id integer, nom_produit text, nom_magasin text)";
        String qry2 = "create table stocks_update_add(quantite integer, produit_id integer, magasin_id integer, nom_produit text,nom_magasin text)";
        String qry3 = "create table stocks_delete(produit_id integer, magasin_id integer)";
        sqLiteDatabase.execSQL(qry1);
        sqLiteDatabase.execSQL(qry2);
        sqLiteDatabase.execSQL(qry3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Stock> getAllEntries() {
        List<Stock> stocks = new ArrayList<>();
        String[] columns={"quantite","produit_id","magasin_id","nom_produit","nom_magasin"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("stocks", columns,null,null,null,null,null);
        if (c!=null) {
            while (c.moveToNext()) {
                Integer qte = c.getInt(c.getColumnIndexOrThrow("quantite"));
                Integer p_id = c.getInt(c.getColumnIndexOrThrow("produit_id"));
                Integer m_id = c.getInt(c.getColumnIndexOrThrow("magasin_id"));
                String n_p = c.getString(c.getColumnIndexOrThrow("nom_produit"));
                String n_m = c.getString(c.getColumnIndexOrThrow("nom_magasin"));
                Stock stock =new Stock(new StockPK(p_id, m_id));
                stock.setQuantite(qte);
                Magasin mag = new Magasin(m_id);
                mag.setNom(n_m);
                Produit prod = new Produit(p_id);
                prod.setNom(n_p);
                stock.setMagasinId(mag);
                stock.setProduitId(prod);
                stocks.add(stock);
            }
            c.close();
        }
        db.close();
        return stocks;
    }

    public List<Stock> getDeletedStocks() {
        List<Stock> stocks = new ArrayList<>();
        String[] columns={"produit_id","magasin_id"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("stocks_delete", columns,null,null,null,null,null);
        if (c!=null) {
            while (c.moveToNext()) {
                Integer p_id = c.getInt(c.getColumnIndexOrThrow("produit_id"));
                Integer m_id = c.getInt(c.getColumnIndexOrThrow("magasin_id"));
                Stock stock =new Stock(new StockPK(p_id, m_id));
                stocks.add(stock);
            }
            c.close();
        }
        db.close();
        return stocks;
    }

    public List<Stock> getUpdatedOrAddedStocks() {
        List<Stock> stocks = new ArrayList<>();
        String[] columns={"quantite","produit_id","magasin_id","nom_produit","nom_magasin"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("stocks_update_add", columns,null,null,null,null,null);
        if (c!=null) {
            while (c.moveToNext()) {
                Integer qte = c.getInt(c.getColumnIndexOrThrow("quantite"));
                Integer p_id = c.getInt(c.getColumnIndexOrThrow("produit_id"));
                Integer m_id = c.getInt(c.getColumnIndexOrThrow("magasin_id"));
                String n_p = c.getString(c.getColumnIndexOrThrow("nom_produit"));
                String n_m = c.getString(c.getColumnIndexOrThrow("nom_magasin"));
                Stock stock =new Stock(new StockPK(p_id, m_id));
                stock.setQuantite(qte);
                Magasin mag = new Magasin(m_id);
                mag.setNom(n_m);
                Produit prod = new Produit(p_id);
                prod.setNom(n_p);
                stock.setMagasinId(mag);
                stock.setProduitId(prod);
                stocks.add(stock);
            }
            c.close();
        }
        db.close();
        return stocks;
    }

    public void removeAfterSyncDelete(Integer produitId, Integer magasinId) {
        String str[] = new String[2];
        str[0] = produitId.toString();
        str[1] = magasinId.toString();
        SQLiteDatabase db = getWritableDatabase();
        db.delete("stocks_delete", "produit_id=? and magasin_id=?", str);
        db.close();
    }

    public void removeAfterSyncAddOrUpdate(Integer produitId, Integer magasinId) {
        String str[] = new String[2];
        str[0] = produitId.toString();
        str[1] = magasinId.toString();
        SQLiteDatabase db = getWritableDatabase();
        db.delete("stocks_update_add", "produit_id=? and magasin_id=?", str);
        db.close();
    }

    public void save(Stock s) {
        ContentValues cv = new ContentValues();
        cv.put("quantite", s.getQuantite());
        cv.put("produit_id", s.getStockPK().getProduitId());
        cv.put("magasin_id",s.getStockPK().getMagasinId());
        cv.put("nom_produit", s.getProduitId().getNom());
        cv.put("nom_magasin", s.getMagasinId().getNom());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("stocks",null, cv);
        db.close();
    }

    public void addStock(Stock s) {
        ContentValues cv = new ContentValues();
        cv.put("quantite", s.getQuantite());
        cv.put("produit_id", s.getStockPK().getProduitId());
        cv.put("magasin_id",s.getStockPK().getMagasinId());
        cv.put("nom_produit", s.getProduitId().getNom());
        cv.put("nom_magasin", s.getMagasinId().getNom());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("stocks",null, cv);
        cv.put("quantite", s.getQuantite());
        cv.put("produit_id", s.getStockPK().getProduitId());
        cv.put("magasin_id", s.getStockPK().getMagasinId());
        cv.put("nom_produit", s.getProduitId().getNom());
        cv.put("nom_magasin", s.getMagasinId().getNom());
        db.insert("stocks_update_add",null, cv);
        db.close();
    }

    public void delete(Integer produitId, Integer magasinId) {
        String str[] = new String[2];
        str[0] = produitId.toString();
        str[1] = magasinId.toString();
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete("stocks", "produit_id=? and magasin_id=?", str);
        boolean isDeleted = rowsDeleted > 0;
        if (isDeleted) {
            ContentValues cv = new ContentValues();
            cv.put("produit_id", produitId);
            cv.put("magasin_id", magasinId);
            db.insert("stocks_delete", null, cv);
        }
        db.close();
    }

    public void update(Stock s) {
        ContentValues cv = new ContentValues();
        cv.put("quantite", s.getQuantite());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "produit_id = ? AND magasin_id = ?";
        String[] whereArgs = {String.valueOf(s.getStockPK().getProduitId()), String.valueOf(s.getStockPK().getMagasinId())};
        int rowsUpdated = db.update("stocks", cv, whereClause, whereArgs);
        if (rowsUpdated > 0) {
            cv.put("quantite", s.getQuantite());
            cv.put("produit_id", s.getStockPK().getProduitId());
            cv.put("magasin_id", s.getStockPK().getMagasinId());
            cv.put("nom_produit", s.getProduitId().getNom());
            cv.put("nom_magasin", s.getMagasinId().getNom());
            db.insert("stocks_update_add", null, cv);
        }
        db.close();
    }

    public String checkStockQuantities() {
        String message="";
        String[] columns={"quantite","nom_produit","nom_magasin"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("stocks", columns,null,null,null,null,null);
        if (c!=null) {
            while (c.moveToNext()) {
                Integer qte = c.getInt(c.getColumnIndexOrThrow("quantite"));
                String n_p = c.getString(c.getColumnIndexOrThrow("nom_produit"));
                String n_m = c.getString(c.getColumnIndexOrThrow("nom_magasin"));
                if (qte < 2) {
                    message += "Le stock pour " + n_p + " à la boutique " + n_m + "\n";
                }
            }
            c.close();
        }
        db.close();
        return message;
    }
}