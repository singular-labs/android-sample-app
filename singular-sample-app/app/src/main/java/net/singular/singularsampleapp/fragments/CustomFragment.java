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

import org.json.JSONException;
import org.json.JSONObject;

public class CustomFragment extends Fragment {

    private EditText eventNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom, container, false);

        eventNameText = view.findViewById(R.id.event_name);

        view.findViewById(R.id.send_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameText.getText().toString().trim();

                if (Utils.isNullOrEmpty(eventName)) {
                    Utils.showToast(getContext(), "Please enter a valid event name");
                    return;
                }

                // Reporting a simple event to Singular
                Singular.event(eventName);

                Utils.showToast(getContext(), "Event sent");
            }
        });

        view.findViewById(R.id.send_event_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eventName = eventNameText.getText().toString().trim();

                    if (Utils.isNullOrEmpty(eventName)) {
                        Utils.showToast(getContext(), "Please enter a valid event name");
                        return;
                    }

                    JSONObject json = new JSONObject();
                    json.put("first_key", "first_value");
                    json.put("second_key", "second_value");

                    // Reporting a simple event with json as your custom attributes to pass with the event
                    Singular.event(eventName, json);

                    Utils.showToast(getContext(), "Event with JSON sent");
                } catch (JSONException e) {
                }
            }
        });

        view.findViewById(R.id.send_event_args).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameText.getText().toString().trim();

                if (Utils.isNullOrEmpty(eventName)) {
                    Utils.showToast(getContext(), "Please enter a valid event name");
                    return;
                }

                // Reporting a simple event with your custom attributes to pass with the event
                // The total amount of args needs to be even, we save them as key-value pairs
                Singular.event(eventName, "first_key", "first_value", "second_key", "second_value");

                Utils.showToast(getContext(), "Event with args sent");
            }
        });

        return view;
    }
}
