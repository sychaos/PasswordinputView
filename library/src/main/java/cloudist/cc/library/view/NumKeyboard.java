package cloudist.cc.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cloudist.cc.library.R;
import cloudist.cc.library.adapter.KeyboardAdapter;
import cloudist.cc.library.model.KeyBoardItem;

/**
 * Created by cloudist on 2017/9/13.
 */

public class NumKeyboard extends LinearLayout {

    private Context mContext;
    private GridView gvKeyboard;
    private TextView mTextView;
    private KeyboardAdapter mAdapter;
    private int mHeight;

    public NumKeyboard(Context context) {
        super(context);
        mContext = context;
        initKeyboardView();
    }

    public NumKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initKeyboardView();
    }

    public NumKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initKeyboardView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initKeyboardView() {
        View view = View.inflate(mContext, R.layout.view_keyboard, this);
        gvKeyboard = view.findViewById(R.id.gv_keyboard);
        mAdapter = new KeyboardAdapter(mContext);
        mAdapter.setmSelectedData(convertKeyBoardItem());
        gvKeyboard.setAdapter(mAdapter);
        gvKeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mTextView == null) {
                    return;
                }
                KeyBoardItem item = (KeyBoardItem) mAdapter.getItem(position);
                switch (item.getItemType()) {
                    case KeyboardAdapter.TYPE_NUM:
                        mTextView.append(item.getValue() + "");
                        break;
                    case KeyboardAdapter.TYPE_DELETE:
                        if (mTextView.length() == 0) {
                            return;
                        }
                        mTextView.setText(mTextView.getText().subSequence(0, mTextView.length() - 1));
                        break;
                }
            }
        });
        mHeight = view.getHeight();
    }

    public void bindTextView(TextView textView) {
        mTextView = textView;
    }

    private List<KeyBoardItem> convertKeyBoardItem() {
        List<KeyBoardItem> keyBoardItems = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            keyBoardItems.add(new KeyBoardItem(KeyboardAdapter.TYPE_NUM, i));
        }
        keyBoardItems.add(new KeyBoardItem(KeyboardAdapter.TYPE_EMPTY));
        keyBoardItems.add(new KeyBoardItem(KeyboardAdapter.TYPE_NUM, 0));
        keyBoardItems.add(new KeyBoardItem(KeyboardAdapter.TYPE_DELETE));
        return keyBoardItems;
    }
}
