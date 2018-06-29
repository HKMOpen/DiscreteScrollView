package com.yarolegovich.discretescrollview.sample.Mv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.sample.ClearActivityTest;
import com.yarolegovich.discretescrollview.sample.DiscreteScrollViewOptions;
import com.yarolegovich.discretescrollview.sample.R;
import com.yarolegovich.discretescrollview.sample.itemTouch;
import com.yarolegovich.discretescrollview.sample.shop.Item;
import com.yarolegovich.discretescrollview.sample.shop.Shop;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class MoviePoster extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener, itemTouch {

    private List<Item> data;
    private Shop shop;

    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;

    Handler ehandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mv);

        rateItemButton = (ImageView) findViewById(R.id.item_btn_rate);

        shop = Shop.get();
        data = shop.getData();
        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        MvAdapter shopAdapter = new MvAdapter(data, this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(shopAdapter);
        itemPicker.setAdapter(shopAdapter);
        itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.892f)
                .build());

        onItemChanged(data.get(0));

        findViewById(R.id.item_btn_rate).setOnClickListener(this);
        findViewById(R.id.item_btn_buy).setOnClickListener(this);
        findViewById(R.id.item_btn_comment).setOnClickListener(this);

        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
        findViewById(R.id.btn_transition_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_btn_rate:
                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
                Item current = data.get(realPosition);
                shop.setRated(current.getId(), !shop.isRated(current.getId()));
                changeRateButtonState(current);
                break;
            case R.id.home:
                finish();
                break;
            case R.id.btn_transition_time:
                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
                break;
            case R.id.btn_smooth_scroll:
                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    @Override
    public void touch_at(int item_num) {
        ClearActivityTest.init_pos(MoviePoster.this, item_num);
    }

    private void onItemChanged(Item item) {
        //  currentItemName.setText(item.getName());
        //   currentItemPrice.setText(item.getPrice());
        changeRateButtonState(item);
    }

    private void changeRateButtonState(Item item) {
        if (shop.isRated(item.getId())) {
            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
        } else {
            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }


}
