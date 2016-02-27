package griebeler.org.seasonticketdraft;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DraftButtonValueEventListener implements ValueEventListener{
    private final Firebase firebase;
    private final Game game;

    public DraftButtonValueEventListener(Firebase firebase, Game game){
        this.firebase = firebase;
        this.game = game;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<DraftPosition> draftPositions = new ArrayList<>();

        boolean draftMarked = false;

        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
            DraftPosition draftPosition = snapshot.getValue(DraftPosition.class);
            if(!draftMarked && !draftPosition.isCompleted()){
                draftPosition.setCompleted(true);
                draftPosition.setDateTimeDate(new Date());
                draftPosition.setGame(game);
                draftMarked = true;
            }
            draftPositions.add(draftPosition);
        }
        firebase.child("draft/snake").setValue(draftPositions);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
