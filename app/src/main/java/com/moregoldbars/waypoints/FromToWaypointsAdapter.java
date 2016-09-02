package com.moregoldbars.waypoints;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uidr3144 on 02/09/2016.
 */
public class FromToWaypointsAdapter extends BaseAdapter {

    public static final int TYPE_FROM = 0;
    public static final int TYPE_TO = 1;
    public static final int TYPE_WAYPOINT = 2;
    private static final int TYPE_MAX_COUNT = TYPE_WAYPOINT + 1;

    private Context context;
    private WaypointInterface listenerInterface;
    private LayoutInflater layoutInflater;
    private List<String> points;

    public FromToWaypointsAdapter(Context context, List<String> points) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.points = points;
    }

    public void setListenerInterface(WaypointInterface listenerInterface) {
        this.listenerInterface = listenerInterface;
    }

    @Override
    public int getCount() {
        return points.size();
    }

    @Override
    public Object getItem(int i) {
        return points.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_FROM;
        if (position > 0 && position < points.size() - 1) return TYPE_WAYPOINT;
        if (position == points.size() - 1) return TYPE_TO;
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    private List<String> cloneList(List<String> list) {
        List<String> clone = new ArrayList<>();

        for (String s : list) {
            clone.add(s);
        }

        return clone;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        String point = (String) getItem(position);
        final int type = getItemViewType(position);

        System.out.println("position: " + position + "\tpoint: " + point + "\ttype: " + type);

        if (convertView == null) {
            switch (type) {
                case TYPE_FROM:
                    convertView = layoutInflater.inflate(R.layout.from_layout, null);
                    break;
                case TYPE_WAYPOINT:
                    convertView = layoutInflater.inflate(R.layout.waypoint_layout, null);
                    break;
                case TYPE_TO:
                    convertView = layoutInflater.inflate(R.layout.to_layout, null);
                    break;
                default:
                    System.out.println("ERROR: Unknown TYPE");
            }
        }

        EditText editText = (EditText) convertView.findViewById(R.id.edit_text);
        editText.setText(point);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString();
                    System.out.println("FromToWaypointsAdapter.onFocusChange: false: " + text);

                    if (!text.isEmpty()) {
                        listenerInterface.onTextInput(position, text);
                    }
                }
            }
        });

        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerInterface.onButtonClicked(position, type);
            }
        });

        return convertView;
    }
}
