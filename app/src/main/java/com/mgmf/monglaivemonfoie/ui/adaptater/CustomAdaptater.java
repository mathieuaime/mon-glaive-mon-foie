package com.mgmf.monglaivemonfoie.ui.adaptater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * @author Mathieu
 */

public class CustomAdaptater<T> extends ArrayAdapter<T> {
    public CustomAdaptater(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        // Get the Layout Parameters for ListView Current Item View
        ViewGroup.LayoutParams params = view.getLayoutParams();

        // Set the height of the Item View
        params.height = 100;
        view.setLayoutParams(params);

        return view;
    }
}
