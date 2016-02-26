package griebeler.org.seasonticketdraft;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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
                firebase.child("draft/snake").addListenerForSingleValueEvent(new DraftButtonValueEventListener(firebase));

            }
        });

        return draftView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebase.child("draft").addListenerForSingleValueEvent(new PopulateSnakeDraftListener(getView()));
    }

    private class AttachToListViewListener implements ValueEventListener{
        private View view;

        public AttachToListViewListener(View view){
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            ListView draftOrderView = (ListView)getView().findViewById(R.id.draft_order_listview);
            ArrayAdapter<String> draftOrderAdapter = (ArrayAdapter<String>)draftOrderView.getAdapter();
            draftOrderAdapter.clear();

            List<DraftPosition> draftPositions = (List<DraftPosition>) dataSnapshot.getValue();

            draftOrderAdapter.addAll((List<String>)dataSnapshot.getValue());
            draftOrderAdapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    private class PopulateSnakeDraftListener implements ValueEventListener {
        private View view;

        public PopulateSnakeDraftListener(View view) {
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(!dataSnapshot.child("snake").exists()) {
                List<DraftPosition> snake = generateSnake((List<String>) dataSnapshot.child("draw").getValue());
                firebase.child("draft/snake").setValue(snake);
            }

            firebase.child("draft/snake").addValueEventListener(new AttachToListViewListener(view));
        }

        private List<DraftPosition> generateSnake(List<String> draw){
            List<DraftPosition> snake = new ArrayList<>();
            int drawIndex = 0;
            for(int k=0; k < 80; k++){
                if(drawIndex == draw.size()){
                    Collections.reverse(draw);
                    drawIndex = 0;
                }
                snake.add(new DraftPosition()
                        .setName(draw.get(drawIndex))
                        .setCompleted(false)
                        .setPosition(k));
                drawIndex++;
            }
            return snake;
        }


        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
