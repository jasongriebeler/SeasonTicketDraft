package griebeler.org.seasonticketdraft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartDraftFragment extends Fragment {

    private List<String> getParticipantList(){
        List participants = new ArrayList();
        participants.add("Griebeler");
        participants.add("Burrows");
        participants.add("Rosens");
        participants.add("Gibbs");
        Collections.shuffle(participants);
        return participants;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_draft_fragment_main, container, false);
        setHasOptionsMenu(true);

        List<String> participantList = getParticipantList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.draft_participant_view, R.id.draft_participants_textview, participantList);

        ListView listView = (ListView) rootView.findViewById(R.id.draft_participants);
        listView.setAdapter(adapter);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.shuffle_participants) {
            return shuffleParticipants();
        }

        return super.onOptionsItemSelected(item);
    }
}
