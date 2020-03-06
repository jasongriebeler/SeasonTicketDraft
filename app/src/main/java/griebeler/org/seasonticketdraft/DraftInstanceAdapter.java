package griebeler.org.seasonticketdraft;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DraftInstanceAdapter extends ArrayAdapter<String> {

    public DraftInstanceAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linear = (LinearLayout)super.getView(position, convertView, parent);

        CircleImageView imageView = (CircleImageView)linear.getChildAt(0);
        TextView textView = (TextView)linear.getChildAt(1);
        TextView draftPositionView = (TextView)linear.getChildAt(2);

        if(StringUtils.containsIgnoreCase(textView.getText(), "griebeler"))
            imageView.setImageResource(R.drawable.griebeler);
        if(StringUtils.containsIgnoreCase(textView.getText(), "dileonardi"))
            imageView.setImageResource(R.drawable.dileonardi);
        if(StringUtils.containsIgnoreCase(textView.getText(), "gibbs"))
            imageView.setImageResource(R.drawable.gibbs);
        if(StringUtils.containsIgnoreCase(textView.getText(), "rosen"))
            imageView.setImageResource(R.drawable.rosens);

        int draftPick = position + 1;
        int round = (draftPick / 4) + 1;

        draftPositionView.setText("Round: " + round + ", Pick: " + new DecimalFormat("00").format(position + 1));

        return linear;
    }
}
