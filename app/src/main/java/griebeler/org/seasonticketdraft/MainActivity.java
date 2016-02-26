package griebeler.org.seasonticketdraft;

import android.app.Activity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        Firebase firebase = new Firebase("https://season-ticket-draft.firebaseio.com/");
        firebase.child("draft").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, new StartDraftFragment())
                            .commit();
                }else{
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, new DraftFragment())
                            .commit();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
