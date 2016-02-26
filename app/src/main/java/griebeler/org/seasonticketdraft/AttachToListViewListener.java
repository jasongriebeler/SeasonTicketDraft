package griebeler.org.seasonticketdraft;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

public class AttachToListViewListener implements ValueEventListener {
    private View view;

    public AttachToListViewListener(View view){
        this.view = view;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        ListView draftOrderView = (ListView)view.findViewById(R.id.draft_order_listview);
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