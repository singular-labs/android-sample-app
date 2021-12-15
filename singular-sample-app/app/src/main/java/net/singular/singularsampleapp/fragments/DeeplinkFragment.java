package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import net.singular.singularsampleapp.Constants;
import net.singular.singularsampleapp.MainActivity;
import net.singular.singularsampleapp.R;

public class DeeplinkFragment extends Fragment {

    private View fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_deeplink, container, false);
        }

        Bundle deeplinkData = ((MainActivity) getActivity()).getDeeplinkData();

        if (deeplinkData == null) {
            return fragment;
        }

        ((TextView) fragment.findViewById(R.id.resolved_deeplink)).setText(deeplinkData.getString(Constants.DEEPLINK_KEY));
        ((TextView) fragment.findViewById(R.id.resolved_passthrough)).setText(deeplinkData.getString(Constants.PASSTHROUGH_KEY));
        ((TextView) fragment.findViewById(R.id.is_deferred)).setText(String.valueOf(deeplinkData.getBoolean(Constants.IS_DEFERRED_KEY)));

        return fragment;
    }
}
