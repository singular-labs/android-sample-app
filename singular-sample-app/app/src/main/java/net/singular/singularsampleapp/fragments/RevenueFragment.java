package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.android.billingclient.api.Purchase;
import com.singular.sdk.Singular;

import net.singular.singularsampleapp.R;
import net.singular.singularsampleapp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class RevenueFragment extends Fragment {

    private EditText eventNameText;
    private Spinner currencyCodeText;
    private EditText priceText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);

        eventNameText = view.findViewById(R.id.revenue_event_name);
        currencyCodeText = view.findViewById(R.id.revenue_event_currency);
        priceText = view.findViewById(R.id.revenue_price);

        view.findViewById(R.id.report_revenue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRevenue(true);
            }
        });

        view.findViewById(R.id.report_revenue_with_validation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRevenue(false);
            }
        });

        return view;
    }

    private void sendRevenue(boolean isSimpleRevenue) {
        String eventName = eventNameText.getText().toString().trim();

        if (Utils.isNullOrEmpty(eventName)) {
            Utils.showToast(getContext(), "Please enter a valid event name");
            return;
        }

        String currency = currencyCodeText.getSelectedItem().toString().trim();

        if (Utils.isNullOrEmpty(currency)) {
            Utils.showToast(getContext(), "Please enter a valid currency");
            return;
        }

        String price = priceText.getText().toString().trim();

        if (Utils.isNullOrEmpty(price) || Double.parseDouble(price) == 0) {
            Utils.showToast(getContext(), "Revenue can't be zero or empty");
            return;
        }

        if (isSimpleRevenue) {

            // Reporting a simple revenue event to Singular
            Singular.customRevenue(eventName, currency, Double.parseDouble(price));

            Utils.showToast(getContext(), "Revenue event sent");
        } else {
            Purchase purchase = buildFakePurchase();

            if (purchase == null) {
                Utils.showToast(getContext(), "Failed to create a fake purchase");
                return;
            }

            // Instead of sending a real purchase we create a fake one for testing.
            // In your production environment, the Purchase object should be received from the Google Billing API.
            Singular.customRevenue(eventName, currency, Double.parseDouble(price), purchase);

            Utils.showToast(getContext(), "IAP sent");
        }
    }

    private Purchase buildFakePurchase() {
        Purchase fakePurchase;

        try {
            JSONObject json = new JSONObject();
            json.put("productId", "fake_product_id");

            fakePurchase = new Purchase(json.toString(), "test_signature");
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

        return fakePurchase;
    }
}