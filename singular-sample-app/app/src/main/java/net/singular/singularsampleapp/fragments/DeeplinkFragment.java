package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.singular.singularsampleapp.Constants;
import net.singular.singularsampleapp.MainActivity;
import net.singular.singularsampleapp.R;

public class DeeplinkFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deeplink, container, false);

        Bundle deeplinkData = ((MainActivity) getActivity()).getDeeplinkData();

        if (deeplinkData == null) {
            return view;
        }

        ((TextView) view.findViewById(R.id.resolved_deeplink)).setText(deeplinkData.getString(Constants.DEEPLINK_KEY));
        ((TextView) view.findViewById(R.id.resolved_passthrough)).setText(deeplinkData.getString(Constants.PASSTHROUGH_KEY));
        ((TextView) view.findViewById(R.id.is_deferred)).setText(String.valueOf(deeplinkData.getBoolean(Constants.IS_DEFERRED_KEY)));

        return view;
    }
}
