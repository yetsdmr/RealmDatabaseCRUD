package com.example.realminserview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class adapter extends BaseAdapter {
    List<KisiBilgileri> list;
    Context context;

    public adapter(List<KisiBilgileri> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.layout,viewGroup,false);
        TextView isim = view.findViewById(R.id.kullaniciIsimText);
        TextView kAd = view.findViewById(R.id.kullaniciAdiText);
        TextView sifre = view.findViewById(R.id.kullaniciSifreText);
        TextView cinsiyet = view.findViewById(R.id.kullaniciCinsiyetText);
        isim.setText(list.get(i).getIsim());
        kAd.setText(list.get(i).getKullanici());
        sifre.setText(list.get(i).getSifre());
        cinsiyet.setText(list.get(i).getCinsiyet());
        return view;
    }
}
