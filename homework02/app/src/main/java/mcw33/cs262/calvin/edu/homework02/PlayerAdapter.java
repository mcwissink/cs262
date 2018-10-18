package mcw33.cs262.calvin.edu.homework02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<PlayerItem> {
    private Context mContext;

    public PlayerAdapter(Context context, ArrayList<PlayerItem> data) {
        super(context, R.layout.player_item, data);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlayerItem playerItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.player_item, parent, false);
        }

        if (playerItem != null) {
            TextView playerText = (convertView.findViewById(R.id.player_item_string));
            playerText.setText(playerItem.getPlayerString());
        }
        return convertView;
    }
}
