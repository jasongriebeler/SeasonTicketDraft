package griebeler.org.seasonticketdraft;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttachToListViewListener implements ValueEventListener {
    private View view;

    public AttachToListViewListener(View view){
        this.view = view;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        final ListView draftOrderView = (ListView)view.findViewById(R.id.draft_order_listview);
        ArrayAdapter<String> draftOrderAdapter = (ArrayAdapter<String>)draftOrderView.getAdapter();
        draftOrderAdapter.clear();

        List<String> items = new ArrayList<>();

        int position = Integer.MAX_VALUE;

        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
            DraftPosition draftPosition = snapshot.getValue(DraftPosition.class);
            if(draftPosition.isCompleted()) {
                items.add(draftPosition.getName() + " - drafted");
            } else {
                items.add(draftPosition.getName());
                if(draftPosition.getPosition() < position)
                    position = draftPosition.getPosition();
            }
        }

        draftOrderAdapter.addAll(items);
        draftOrderAdapter.notifyDataSetChanged();

        draftOrderView.smoothScrollToPositionFromTop(position, 128  * 3);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}