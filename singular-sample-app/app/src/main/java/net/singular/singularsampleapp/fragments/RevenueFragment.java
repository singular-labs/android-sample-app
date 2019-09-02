package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.singular.sdk.Singular;

import net.singular.singularsampleapp.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;

public class RevenueFragment extends Fragment implements BillingClientStateListener, PurchasesUpdatedListener {

    private BillingClient billingClient;
    private SkuDetails product;

    private EditText eventNameText;
    private EditText currencyCodeText;
    private EditText priceText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_revenue, container, false);

        if(billingClient == null){
            billingClient = BillingClient.newBuilder(getContext()).setListener(this).enablePendingPurchases().build();
            billingClient.startConnection(this);
        }

        eventNameText = view.findViewById(R.id.revenue_event_name);
        currencyCodeText = view.findViewById(R.id.revenue_event_currency);
        priceText = view.findViewById(R.id.revenue_price);

        view.findViewById(R.id.report_revenue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = extractAndValidateEventName();

                if (eventName == null) {
                    return;
                }

                String currency = currencyCodeText.getText().toString().trim();

                if (currency.equals("")) {
                    Toast.makeText(getContext(), "Currency Code can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String price = priceText.getText().toString().trim();

                if (price.equals("")) {
                    Toast.makeText(getContext(), "Price can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Singular.customRevenue(eventName, currency, Double.parseDouble(price));
            }
        });

        view.findViewById(R.id.report_revenue_with_validation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = extractAndValidateEventName();

                if (eventName == null) {
                    return;
                }

                if (product == null) {
                    Toast.makeText(getContext(), "Product is not ready to be purchased", Toast.LENGTH_LONG).show();
                    return;
                }

                // This will trigger the onPurchasesUpdated method
                BillingFlowParams billingFlowParams = BillingFlowParams
                        .newBuilder()
                        .setSkuDetails(product)
                        .build();
                billingClient.launchBillingFlow(getActivity(), billingFlowParams);
            }
        });

        return view;
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == OK) {
            for (Purchase item : billingClient.queryPurchases(BillingClient.SkuType.INAPP).getPurchasesList()) {

                Singular.customRevenue("Test Purchase", product.getPriceCurrencyCode(), 4.0, item);

                // This code is here only to consume the product thus allowing us to purchase the same product again
                ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(item.getPurchaseToken()).build();
                billingClient.consumeAsync(params, new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {

                    }
                });
            }
        }
    }

    @Override
    public void onBillingSetupFinished(BillingResult billingResult) {
        if (billingResult.getResponseCode() == OK) {
            List<String> skuList = new ArrayList<>();
            skuList.add("test.product");

            SkuDetailsParams params = SkuDetailsParams
                    .newBuilder()
                    .setSkusList(skuList)
                    .setType(BillingClient.SkuType.INAPP)
                    .build();

            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                    // The product is now ready to be purchased
                    product = skuDetailsList.get(0);
                }
            });
        }
    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    private String extractAndValidateEventName() {
        String eventName = eventNameText.getText().toString().trim();

        if (eventName.equals("")) {
            Toast.makeText(getContext(), "Event name can't be empty", Toast.LENGTH_SHORT).show();
            return null;
        }

        return eventName;
    }
}