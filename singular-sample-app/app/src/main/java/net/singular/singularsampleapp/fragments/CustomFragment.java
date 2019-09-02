package net.singular.singularsampleapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.singular.sdk.Singular;

import net.singular.singularsampleapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomFragment extends Fragment {

    private EditText eventNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_custom, container, false);

        eventNameText = view.findViewById(R.id.event_name);

        view.findViewById(R.id.send_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = extractAndValidateEventName();

                if (eventName == null) {
                    return;
                }

                if (Singular.event(eventName)) {
                    toastEventConfirmation();
                }
            }
        });

        view.findViewById(R.id.send_event_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eventName = extractAndValidateEventName();

                    if (eventName == null) {
                        return;
                    }

                    JSONObject json = new JSONObject();
                    json.put("first_key", "first_value");
                    json.put("second_key", "second_value");
                    if (Singular.event(eventName, json)) {
                        toastEventConfirmation();
                    }

                } catch (JSONException e) {
                }
            }
        });

        view.findViewById(R.id.send_event_args).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = extractAndValidateEventName();

                if (eventName == null) {
                    return;
                }

                // The total amount of args needs to be even because we turn them into a HashMap
                if (Singular.event(eventName, "first_key", "first_value", "second_key", "second_value")) {
                    toastEventConfirmation();
                }
            }
        });

        return view;
    }

    private void toastEventConfirmation() {
        Toast.makeText(getContext(), "Event was sent successfully", Toast.LENGTH_SHORT).show();
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
