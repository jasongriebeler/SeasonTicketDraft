package griebeler.org.seasonticketdraft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartDraftFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_draft_fragment_main, container, false);

        List<String> participantList = Arrays.asList(
            "Griebeler",
            "Burrows",
            "Rosens",
            "Gibbs"
        );

        Collections.shuffle(participantList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.draft_participant_view, R.id.draft_participants_textview, participantList);

        ListView listView = (ListView) rootView.findViewById(R.id.draft_participants);
        listView.setAdapter(adapter);

        return rootView;
    }
}
