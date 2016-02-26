package griebeler.org.seasonticketdraft;

import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopulateSnakeDraftListener implements ValueEventListener {
    private final Firebase firebase;
    private final View view;

    public PopulateSnakeDraftListener(Firebase firebase, View view) {
        this.view = view;
        this.firebase = firebase;
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
        for(int k=0; k <= 81; k++){
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
