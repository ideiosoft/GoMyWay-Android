package ssc.snow.app.gomyways.views.home.controler.search;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostARequest;
import ssc.snow.app.gomyways.views.home.controler.search.adapter.AdapterViewpagerSearchResults;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenSearchResult extends CommonActivity implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_search_result);
        ButterKnife.inject(this);

        // initial implementation of views
        init();

    }


    private void init() {

        // Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setText("TRIPS"));
        tabLayout.addTab(tabLayout.newTab().setText("REQUESTS"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //  Creating our pager adapter
        AdapterViewpagerSearchResults
                adapter = new AdapterViewpagerSearchResults(getSupportFragmentManager(), tabLayout.getTabCount());

        //  Sync with view pager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //  Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @OnClick({R.id.img_back, R.id.ll_post_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.ll_post_request:
                goForNextScreenWithoutFinish(ScreenPostARequest.class);
                break;

        }
    }
}
