package com.example.realminserview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    EditText kAdi,kSifre,kIsim;
    RadioGroup cinsiyetGrp;
    Button btnKayit,btnGuncelle;
    ListView listView;
    Integer positionT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmTanimla();
        tanimla();
        ekle();
        goster();
        pozisyonBul();
    }

    private void tanimla() {
        kAdi = findViewById(R.id.eTextKullaniciAdi);
        kIsim = findViewById(R.id.eTextIsım);
        kSifre = findViewById(R.id.eTextSifre);
        cinsiyetGrp = findViewById(R.id.cinsiyetRadiGroup);
        btnKayit = findViewById(R.id.kayitOlButon);
        listView = findViewById(R.id.listview);
        btnGuncelle = findViewById(R.id.guncelleButon);
    }

    private void realmTanimla() {
        realm = Realm.getDefaultInstance(); // opens "myrealm2.realm"
    }
    private void ekle(){
        btnKayit.setOnClickListener(new View.OnClickListener() {

            final String kAdiText = kAdi.getText().toString();
            final String kIsimText = kIsim.getText().toString();
            final String kSifreText = kSifre.getText().toString();
            Integer id = cinsiyetGrp.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(id);
            final String cinsiyetText = radioButton.getText().toString();

            @Override
            public void onClick(View view) {
                yaz(kIsimText,kAdiText,kSifreText,cinsiyetText);

                goster();
            }
        });

        btnGuncelle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String kAdiText = kAdi.getText().toString();
                final String kIsimText = kIsim.getText().toString();
                final String kSifreText = kSifre.getText().toString();
                Integer id = cinsiyetGrp.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                final String cinsiyetText = radioButton.getText().toString();

                final RealmResults<KisiBilgileri> list = realm.where(KisiBilgileri.class).findAll();
                final KisiBilgileri kisi = list.get(positionT);


                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        kisi.setCinsiyet(cinsiyetText);
                        kisi.setSifre(kSifreText);
                        kisi.setIsim(kIsimText);
                        kisi.setKullanici(kAdiText);
                    }
                });
                goster();
            }
        });
    }

    private void yaz(final String isim,final String kAd,final String sifre,final String cinsiyet){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                KisiBilgileri kisiBilgileri = realm.createObject(KisiBilgileri.class);
                kisiBilgileri.setKullanici(kAd);
                kisiBilgileri.setIsim(isim);
                kisiBilgileri.setSifre(sifre);
                kisiBilgileri.setCinsiyet(cinsiyet);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Başarılı", Toast.LENGTH_LONG).show();
                goster();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, "Başarısız", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void goster(){
        RealmResults<KisiBilgileri> sonuc = realm.where(KisiBilgileri.class).findAll();
        //for (KisiBilgileri k : sonuc){
         //   Log.i("Gelenler",k.toString());
       // }

        if (sonuc.size() > 0){
            adapter adp = new adapter(sonuc,this);
            listView.setAdapter(adp);
        }
    }

    private void pozisyonBul(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            RealmResults<KisiBilgileri> list= realm.where(KisiBilgileri.class).findAll();

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //sil(i);
                ac(i);
                //kAdi,kSifre,kIsim
                kAdi.setText(list.get(i).getKullanici());
                kSifre.setText(list.get(i).getSifre());
                kIsim.setText(list.get(i).getIsim());
                if (list.get(i).getCinsiyet().equals("Erkek")){
                    ((RadioButton)cinsiyetGrp.getChildAt(0)).setChecked(true);
                }else {
                    ((RadioButton)cinsiyetGrp.getChildAt(1)).setChecked(true);
                }
                positionT = i;
            }
        });
    }
    private void sil(final int position){
        final RealmResults<KisiBilgileri> gelenList = realm.where(KisiBilgileri.class).findAll();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                KisiBilgileri kisi=gelenList.get(position);
                kisi.deleteFromRealm();
                goster();
            }
        });
    }
    private void ac(final int position){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alertlayout,null);

        Button evetButon = view.findViewById(R.id.evetButon);
        Button hayirButon = view.findViewById(R.id.hayirButon);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(view);
        alert.setCancelable(false);

        final AlertDialog dialog = alert.create();
        evetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sil(position);
                dialog.cancel();
            }
        });
        hayirButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

}