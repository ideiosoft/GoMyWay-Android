package ssc.snow.app.gomyways.views.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.model.ModelStates;

public class AdapterAutoCompleteStates extends ArrayAdapter<ModelStates.Data> {

    Context context;
    int resource, textViewResourceId;
    List<ModelStates.Data> items, tempItems, suggestions;

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ModelStates.Data) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ModelStates.Data people : tempItems) {
                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ModelStates.Data> filterList = (ArrayList<ModelStates.Data>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ModelStates.Data people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public AdapterAutoCompleteStates(Context context, int resource, int textViewResourceId, @NonNull List<ModelStates.Data> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;

        tempItems = new ArrayList<>(items); // This makes the difference.
        suggestions = new ArrayList<>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_row_auto_complete, parent, false);
        }
        ModelStates.Data people = items.get(position);
        if (people != null) {
            TextView lblName = view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getName());
        }
        return view;
    }

    @NotNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
