package griebeler.org.seasonticketdraft;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DraftFragment extends Fragment {
    private Firebase firebase = new Firebase("https://season-ticket-draft.firebaseio.com/");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View draftView = inflater.inflate(R.layout.draft_fragment, container, false);

        List<String> draftOrder = new ArrayList<>();
        DraftInstanceAdapter adapter = new DraftInstanceAdapter(getActivity(), R.layout.draft_instance_view, R.id.participant_textview, draftOrder);

        ListView listView = (ListView)draftView.findViewById(R.id.draft_order_listview);
        listView.setAdapter(adapter);

        draftView.findViewById(R.id.draft_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.clear();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        firebase.child("draft/schedule/" + dateFormat.format(cal.getTime())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot scheduleSnapshot) {
                                if (!scheduleSnapshot.exists())
                                    new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("No Game That Day").create().show();
                                else{
                                    final Game game = scheduleSnapshot.getValue(Game.class);

                                    if(game.isSelected()){
                                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("Game already selected by: " + game.getSelectedBy()).show();
                                    }else {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Verify")
                                                .setMessage(game.getOpponent() + " on " + game.getFormattedDate() + " at " + game.getFormattedTime())
                                                .setPositiveButton("Select Game", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        firebase.child("draft/snake").addListenerForSingleValueEvent(new DraftButtonValueEventListener(firebase, game));
                                                    }
                                                })
                                                .create()
                                                .show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
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
