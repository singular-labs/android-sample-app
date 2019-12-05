package net.singular.singularsampleapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.singular.sdk.Singular;
import com.singular.sdk.SingularConfig;
import com.singular.sdk.SingularLinkHandler;
import com.singular.sdk.SingularLinkParams;
import net.singular.singularsampleapp.adapters.TabAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle deeplinkData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        initSingularSDK();
    }

    private void initSingularSDK() {
        SingularConfig config = new SingularConfig(Constants.API_KEY, Constants.SECRET).withSingularLink(getIntent(), new SingularLinkHandler() {
            @Override
            public void onResolved(SingularLinkParams singularLinkParams) {
                deeplinkData = new Bundle();
                deeplinkData.putString(Constants.DEEPLINK_KEY, singularLinkParams.getDeeplink());
                deeplinkData.putString(Constants.PASSTHROUGH_KEY, singularLinkParams.getPassthrough());
                deeplinkData.putBoolean(Constants.IS_DEFERRED_KEY, singularLinkParams.isDeferred());

                // When the is opened using a deeplink, we will open the deeplink_blue tab
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.getTabAt(3).select();
                    }
                });
            }
        });

        Singular.init(this, config);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
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
    };

    public Bundle getDeeplinkData() {

        // Clearing the deeplink data so it won't be used twice
        Bundle bundle = deeplinkData;
        deeplinkData = null;

        return bundle;
    }
}
