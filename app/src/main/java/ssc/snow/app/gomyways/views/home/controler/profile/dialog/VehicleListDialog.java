package ssc.snow.app.gomyways.views.home.controler.profile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.model.ModelVehicleAndTypes;

public class VehicleListDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "CityList";
    ArrayAdapter<String> adapter = null;
    private ListView list;
    private EditText filterText = null;


    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter.getFilter().filter(s);
        }
    };

    private List<String> itemsList = new ArrayList<>();

    public VehicleListDialog(Context context, List<ModelVehicleAndTypes.Data.Vehicle> items, final IDialogCallback mCallback) {
        super(context);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.vehiclelistview1);

        for (ModelVehicleAndTypes.Data.Vehicle mData : items) {
            itemsList.add(mData.getVehicle_name());

        }


        this.setTitle("Select Vehicle");

        filterText = findViewById(R.id.EditBox);
        filterText.addTextChangedListener(filterTextWatcher);
        list = findViewById(R.id.List);

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemsList.toArray(new String[itemsList.size()]));
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Log.d(TAG, "Selected Item is = " + list.getItemAtPosition(position));
                mCallback.getVehicle(list.getItemAtPosition(position).toString());

                cancel();

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStop() {
        filterText.removeTextChangedListener(filterTextWatcher);
    }
}