package griebeler.org.seasonticketdraft;

import android.app.Activity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.opencsv.CSVReader;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        final Firebase firebase = new Firebase("https://season-ticket-draft.firebaseio.com/");
        firebase.child("draft/schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    InputStream scheduleStream = getResources().openRawResource(R.raw.schedule);
                    CSVReader csv = new CSVReader(new InputStreamReader(scheduleStream));
                    Map<String, Game> schedule = new HashMap<>();
                    try {
                        for(String[] row : csv.readAll()){
                            SimpleDateFormat dateInputFormat = new SimpleDateFormat("MM/dd/yy");
                            SimpleDateFormat dateOutputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Game game = new Game();
                            game.setDate(dateInputFormat.parse(row[0]));
                            if(StringUtils.isNotBlank(row[1]))
                                game.setTime(timeFormat.parse(row[1]));
                            game.setOpponent(row[2]);
                            schedule.put(dateOutputFormat.format(game.getDate()), game);
                            firebase.child("draft/schedule").setValue(schedule);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
