package cloudist.cc.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cloudist.cc.library.R;
import cloudist.cc.library.widget.KeyBoardDialog;

/**
 * Created by cloudist on 2017/9/13.
 */

public class PasswordInputView extends EditText {

    private Context mContext;
    private KeyBoardDialog mKeyBoardDialog;

    private int borderColor;
    private int borderRespondingColor;
    private float borderWidth;
    private float borderRadius;

    private int passwordLength;
    private int passwordColor;
    private float passwordWidth;

    private float itemPadding;
    private float itemHeight;
    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textLength = 0;
    private float paddingVertical;
    private RectF rect = new RectF();


    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        borderColor = a.getColor(R.styleable.PasswordInputView_borderColor, ContextCompat.getColor(mContext, R.color.textMedium));
        borderRespondingColor = a.getColor(R.styleable.PasswordInputView_borderRespondingColor, ContextCompat.getColor(mContext, R.color.colorBlue));
        borderWidth = a.getDimension(R.styleable.PasswordInputView_borderWidth, 0.5f);
        borderRadius = a.getDimension(R.styleable.PasswordInputView_borderRadius, 2f);

        passwordColor = a.getColor(R.styleable.PasswordInputView_passwordColor, ContextCompat.getColor(mContext, R.color.textDark));
        passwordWidth = a.getDimension(R.styleable.PasswordInputView_passwordWidth, 6f);
        passwordLength = a.getInteger(R.styleable.PasswordInputView_passwordLength, 6);

        itemPadding = a.getDimension(R.styleable.PasswordInputView_itemPadding, 8f);
        itemHeight = a.getDimension(R.styleable.PasswordInputView_itemHeight, 36f);

        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        a.recycle();

        setCursorVisible(false);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(passwordLength)});
        setSingleLine(true);
        setInputType(InputType.TYPE_CLASS_NUMBER); // restore input type

        initListener();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制边框 如果不想要圆边的话其实可以使用Path
        for (int i = 0; i < passwordLength; i++) {
            rect.set((itemHeight + itemPadding) * i + itemPadding, paddingVertical,
                    (itemHeight + itemPadding) * (i + 1), paddingVertical + itemHeight);
            if (i == textLength && hasFocus()) {
                borderPaint.setColor(borderRespondingColor);
            } else {
                borderPaint.setColor(borderColor);
            }
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);
        }

        // 密码
        float cx;
        float cy = itemHeight / 2 + paddingVertical;
        float half = (itemHeight) / 2 + itemPadding;
        for (int i = 0; i < textLength; i++) {
            cx = (itemHeight + itemPadding) * i + half;
            canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = (int) itemHeight * passwordLength + (int) itemPadding * (passwordLength + 1) + getPaddingLeft() + getPaddingRight();
        int height = (int) (itemHeight + 2 * itemPadding) + getPaddingTop() + getPaddingBottom();

        int mHeight, mWidth;

        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(width, widthSpecSize);
        } else {
            mWidth = widthSpecSize;
        }

        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(height, heightSpecSize);
        } else {
            mHeight = heightSpecSize;
        }

        paddingVertical = (mHeight - itemHeight) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.length();
        invalidate();
    }

    public void bindKeyBoard(final FragmentManager manager, final String tag) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(PasswordInputView.this.getWindowToken(), 0);
                    }
                });
                showKeyBoard(view, manager, tag);
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(PasswordInputView.this.getWindowToken(), 0);
                        }
                    });
                    showKeyBoard(view, manager, tag);
                }
            }
        });
    }

    private void showKeyBoard(View view, final FragmentManager manager, final String tag) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mKeyBoardDialog = KeyBoardDialog.newInstance();
                mKeyBoardDialog.bindTextView(PasswordInputView.this)
                        .show(manager, tag);
            }
        }, 100);
    }

    public void unbindKeyBoard() {
        mKeyBoardDialog = null;
        initListener();
    }

    private void initListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(PasswordInputView.this.getWindowToken(), 0);
                    }
                });
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(PasswordInputView.this.getWindowToken(), 0);
                        }
                    });
                }
            }
        });
    }

}
