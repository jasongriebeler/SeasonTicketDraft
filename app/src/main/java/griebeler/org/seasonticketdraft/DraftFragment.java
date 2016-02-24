package griebeler.org.seasonticketdraft;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

        return draftView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebase.child("original-draft-order").addValueEventListener(new OriginalDraftOrderValueEventListener(getView()));
    }

    private class OriginalDraftOrderValueEventListener implements ValueEventListener{
        private View view;

        public OriginalDraftOrderValueEventListener(View view){
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i("data changed", dataSnapshot.getValue().toString());
            ListView draftOrderView = (ListView)view.findViewById(R.id.draft_order_listview);
            ArrayAdapter<String> draftOrderAdapter = (ArrayAdapter<String>)draftOrderView.getAdapter();
            draftOrderAdapter.clear();
            draftOrderAdapter.addAll((List<String>)dataSnapshot.getValue());
            draftOrderAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.i("cancelled", firebaseError.toString());
        }
    }

}
