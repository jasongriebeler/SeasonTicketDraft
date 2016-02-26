package griebeler.org.seasonticketdraft;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class DraftFragment extends Fragment {
    private Firebase firebase = new Firebase("https://season-ticket-draft.firebaseio.com/");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View draftView = inflater.inflate(R.layout.draft_fragment, container, false);

        List<String> draftOrder = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.draft_order_view, R.id.draft_order_participant_textview, draftOrder);
        ListView listView = (ListView)draftView.findViewById(R.id.draft_order_listview);
        listView.setAdapter(adapter);

        draftView.findViewById(R.id.draft_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.i("dialog date set", "YOU PICKED: " + year + " | " + monthOfYear + " | " + dayOfMonth);
                        new AlertDialog.Builder(getActivity()).setTitle("ERROR").setMessage("GAME TAKEN").create().show();
                    }
                }, 2016, 03, 01);

                dialog.show();
//                firebase.child("draft/snake").addListenerForSingleValueEvent(new DraftButtonValueEventListener(firebase));

            }
        });

        return draftView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebase.child("draft").addListenerForSingleValueEvent(new PopulateSnakeDraftListener(firebase, getView()));
    }

}
