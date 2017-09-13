package cloudist.cc.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cloudist.cc.library.R;
import cloudist.cc.library.model.KeyBoardItem;

/**
 * Created by cloudist on 2017/9/13.
 */

public class KeyboardAdapter extends BaseAdapter {

    public static final int TYPE_NUM = 1001;
    public static final int TYPE_DELETE = 1002;
    public static final int TYPE_EMPTY = 1003;

    private List<KeyBoardItem> mSelectedData = new ArrayList<>();
    private Context mContext;

    public KeyboardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mSelectedData.size();
    }

    @Override
    public Object getItem(int position) {
        return mSelectedData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            View mConvertView = null;
            switch (mSelectedData.get(position).getItemType()) {
                case TYPE_NUM:
                    mConvertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_keyboard, parent, false);
                    viewHolder = new ViewHolder(mConvertView);
                    viewHolder.tvKey.setText(mSelectedData.get(position).getValue() + "");
                    break;
                case TYPE_DELETE:
                    mConvertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_keyboard_delete, parent, false);
                    break;
                case TYPE_EMPTY:
                    mConvertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_keyboard_empty, parent, false);
                    break;
            }
            return mConvertView;
        } else {
            return convertView;
        }
    }

    public void setmSelectedData(List mSelectedData) {
        this.mSelectedData = mSelectedData;
    }

    public List<KeyBoardItem> getmSelectedData() {
        return mSelectedData;
    }

    /**
     * ViewHolder,view缓存
     */
    class ViewHolder {
        View view;
        TextView tvKey;

        public ViewHolder(View view) {
            view.setTag(this);
            this.view = view;
            tvKey = view.findViewById(R.id.tv_keyboard_keys);
        }

    }
}
