package griebeler.org.seasonticketdraft;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartDraftFragment extends Fragment {

    private Firebase firebase = new Firebase("https://season-ticket-draft.firebaseio.com/");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_draft_fragment_main, container, false);
        setHasOptionsMenu(true);

        final List<String> participantList = getParticipantList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.draft_participant_view, R.id.draft_participants_textview, participantList);

        ListView listView = (ListView) rootView.findViewById(R.id.draft_participants);
        listView.setAdapter(adapter);

        (rootView.findViewById(R.id.start_draft_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.child("original-draft-order").setValue(participantList);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new DraftFragment())
                        .commit();
            }
        });

        return rootView;
    }

    private boolean shuffleParticipants() {
        ListView draftParticipantsView = (ListView)getView().findViewById(R.id.draft_participants);
        ArrayAdapter arrayAdapter = (ArrayAdapter) draftParticipantsView.getAdapter();
        arrayAdapter.clear();
        arrayAdapter.addAll(getParticipantList());
        arrayAdapter.notifyDataSetChanged();
        return true;
    }

    private List<String> getParticipantList(){
        List participants = new ArrayList();
        participants.add("Griebeler");
        participants.add("Burrows");
        participants.add("Rosens");
        participants.add("Gibbs");
        Collections.shuffle(participants);
        return participants;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shuffle_participants) {
            return shuffleParticipants();
        }
        return super.onOptionsItemSelected(item);
    }
}
