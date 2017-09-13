package cloudist.cc.library.widget;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cloudist.cc.library.R;
import cloudist.cc.library.TextChangeListener;
import cloudist.cc.library.view.NumKeyboard;

/**
 * Created by cloudist on 2017/9/13.
 */

public class KeyBoardDialog extends DialogFragment {

    public static KeyBoardDialog newInstance() {
        return new KeyBoardDialog();
    }

    TextView mTextView;
    TextChangeListener textChangeListener = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.AnimBottomPushWindow;
        View rootView = inflater.inflate(R.layout.dialog_keyboard, container);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(true);
        NumKeyboard keyboardView = view.findViewById(R.id.keyboardView);
        keyboardView.bindTextView(mTextView);
        mTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textChangeListener == null) {
                    return;
                }
                textChangeListener.textChange(editable.toString());
            }
        });

    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setDimAmount(0f);
        window.setGravity(Gravity.BOTTOM);
        super.onResume();
    }

    public KeyBoardDialog bindTextView(TextView textView) {
        mTextView = textView;
        return this;
    }

    public KeyBoardDialog setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
