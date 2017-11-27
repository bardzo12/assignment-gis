package sk.pixwell.therun.presentation.Dashboard.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import sk.pixwell.therun.presentation.R;
import sk.pixwell.therun.presentation.shared.UIUtils;

/**
 * Created by Tomáš Baránek on 27.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class DrawerItemAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public DrawerItemAdapter(Context context, int layoutResourceID,
                               List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    public void updateItems(int possiton){
        for(int i = 0; i < drawerItemList.size(); i++) {
            if(i == possiton)
                drawerItemList.get(i).setSelected(true);
            else
                drawerItemList.get(i).setSelected(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.itemText);
            drawerHolder.selected = view
                    .findViewById(R.id.selected);
            drawerHolder.background = (RelativeLayout) view
                    .findViewById(R.id.background);
            //drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if(dItem.getSelected()) {
            drawerHolder.selected.setVisibility(View.VISIBLE);
            drawerHolder.background.setBackgroundColor(UIUtils.getColorWrapper(context,R.color.backgroundColor));
        }else {
            drawerHolder.selected.setVisibility(View.INVISIBLE);
            drawerHolder.background.setBackgroundColor(UIUtils.getColorWrapper(context,R.color.backgroundColor1));
        }
        /*drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResID()));*/
        drawerHolder.ItemName.setText(dItem.getItemName());

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        View selected;
        RelativeLayout background;
        //ImageView icon;
    }
}