package com.yarolegovich.discretescrollview.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ClearActivityTest extends AppCompatActivity implements View.OnClickListener {

    private static final Uri URL_APP_REPO = Uri.parse("https://github.com/yarolegovich/DiscreteScrollView");
    private final static int requestCode = 39203;
    private TextView display_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_num);
        display_num = findViewById(R.id.noc_nm);
        try {
            int ds = getIntent().getExtras().getInt("strnum");
            display_num.setText(String.valueOf(ds));
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {

    }


    public static void init_pos(AppCompatActivity base, int position_int) {

        Intent in = new Intent(base, ClearActivityTest.class);
        //in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle work = new Bundle();

        work.putInt("strnum", position_int);
        /*
           if (post_raw instanceof movieOBase) {
               movieOBase pop_bar = (movieOBase) post_raw;
               work.putParcelable(intent_object_parcel, Parcels.wrap(pop_bar));
           }
        */

        in.putExtras(work);
        base.startActivityForResult(in, requestCode);
    }


}
