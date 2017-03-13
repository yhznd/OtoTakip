package com.example.yunus.ototakip;

import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created by Yunus on 10.03.2017.
 */

public class PdfRendererBasicFragment extends Fragment implements View.OnClickListener
{
    private static final String O_ANKI_SAYFA_DURUMU = "guncel_sayfa_index";
    private static final String DOSYAADI = "sample.pdf";
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mGuncelSayfa;
    private ImageView mImageView;
    private ImageButton mOncekiButon;
    private ImageButton mSonrakiButon;
    //kurucu
    public PdfRendererBasicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_pdf_renderer_basic, container, false);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.onceki: {
                //onceki sayfaya geç
                showPage(mGuncelSayfa.getIndex() - 1);
                break;
            }
            case R.id.sonraki: {
                // sonraki sayfaya geç
                showPage(mGuncelSayfa.getIndex() + 1);
                break;
            }
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.pdf_goruntusu);
        mOncekiButon = (ImageButton) view.findViewById(R.id.onceki);
        mSonrakiButon = (ImageButton) view.findViewById(R.id.sonraki);
        mOncekiButon.setOnClickListener(this);
        mSonrakiButon.setOnClickListener(this);
        int index = 0;
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt(O_ANKI_SAYFA_DURUMU, 0);
        }
        showPage(index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            openRenderer(activity);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Beklenmedik hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    @Override
    public void onDetach() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mGuncelSayfa) {
            outState.putInt(O_ANKI_SAYFA_DURUMU, mGuncelSayfa.getIndex());
        }
    }


    private void openRenderer(Context context) throws IOException {
        // bu ornekte, asset klasöründeki PDF'i okuyoruz.
        File file = new File(context.getCacheDir(), DOSYAADI);
        if (!file.exists()) {
            InputStream asset = context.getAssets().open(DOSYAADI);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        mPdfRenderer = new PdfRenderer(mFileDescriptor);
    }

    private void closeRenderer() throws IOException {
        if (null != mGuncelSayfa) {
            mGuncelSayfa.close();
        }
        mPdfRenderer.close();
        mFileDescriptor.close();
    }


    private void showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }

        if (null != mGuncelSayfa) {
            mGuncelSayfa.close();
        }
        mGuncelSayfa = mPdfRenderer.openPage(index);
        // ÖNEMLİ: Hedef bitmap ARGB olmalı, RGB olmamalı.
        Bitmap bitmap = Bitmap.createBitmap(mGuncelSayfa.getWidth(), mGuncelSayfa.getHeight(),
                Bitmap.Config.ARGB_8888);
        mGuncelSayfa.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mImageView.setImageBitmap(bitmap);
        sayfayıGuncelle();
    }


    private void sayfayıGuncelle() {
        int index = mGuncelSayfa.getIndex();
        int pageCount = mPdfRenderer.getPageCount();
        mOncekiButon.setEnabled(0 != index);
        mSonrakiButon.setEnabled(index + 1 < pageCount);
        getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
    }


    public int sayfaSayisiniGetir()
    {
        return mPdfRenderer.getPageCount();
    }

}
