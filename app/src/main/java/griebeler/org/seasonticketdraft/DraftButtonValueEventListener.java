package griebeler.org.seasonticketdraft;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class DraftButtonValueEventListener implements ValueEventListener{
    private final Firebase firebase;

    public DraftButtonValueEventListener(Firebase firebase){
        this.firebase = firebase;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

//        firebase.child("draft/snake").setValue(updatedDraftOrder);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
