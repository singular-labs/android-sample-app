package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.singular.sdk.Singular;

import net.singular.singularsampleapp.R;
import net.singular.singularsampleapp.Utils;

public class IdentityFragment extends Fragment {

    private EditText customUserIdText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity, container, false);

        customUserIdText = view.findViewById(R.id.custom_user_id);

        view.findViewById(R.id.set_custom_user_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customUserId = customUserIdText.getText().toString().trim();

                if (Utils.isNullOrEmpty(customUserId)) {
                    Utils.showToast(getContext(), "Please enter a valid Custom User Id");
                    return;
                }

                // Once set, the Custom User Id will persist between runs until `Singular.unsetCustomUserId()` is called.
                // You can also use SingularConfig.withCustomUserId() to include it in the first session.
                Singular.setCustomUserId(customUserId);

                Utils.showToast(getContext(), String.format("Custom User id set to: %s", customUserId));
            }
        });

        view.findViewById(R.id.unset_custom_user_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singular.unsetCustomUserId();

                Utils.showToast(getContext(), "Custom User id unset");
            }
        });

        return view;
    }
}
