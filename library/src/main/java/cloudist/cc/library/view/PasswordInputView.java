package cloudist.cc.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import cloudist.cc.library.R;

import static android.text.InputFilter.*;

/**
 * Created by cloudist on 2017/9/13.
 */

public class PasswordInputView extends EditText {

    private int borderColor;
    private int borderRespondingColor;
    private float borderWidth;
    private float borderRadius;

    private int passwordLength;
    private int passwordColor;
    private float passwordWidth;

    private float itemPadding;
    private float itemHeight;
    private boolean normalInput;

    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textLength = 0;
    private RectF rect = new RectF();
    private RectF rectIn = new RectF();


    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        borderColor = a.getColor(R.styleable.PasswordInputView_borderColor, ContextCompat.getColor(context, R.color.textMedium));
        borderRespondingColor = a.getColor(R.styleable.PasswordInputView_borderRespondingColor, ContextCompat.getColor(context, R.color.colorBlue));
        borderWidth = a.getDimension(R.styleable.PasswordInputView_borderWidth, 0.5f);
        borderRadius = a.getDimension(R.styleable.PasswordInputView_borderRadius, 2f);

        passwordColor = a.getColor(R.styleable.PasswordInputView_passwordColor, ContextCompat.getColor(context, R.color.textDark));
        passwordWidth = a.getDimension(R.styleable.PasswordInputView_passwordWidth, 6f);
        passwordLength = a.getInteger(R.styleable.PasswordInputView_passwordLength, 6);

        itemPadding = a.getDimension(R.styleable.PasswordInputView_itemPadding, 8f);
        itemHeight = a.getDimension(R.styleable.PasswordInputView_itemHeight, 36f);

        normalInput = a.getBoolean(R.styleable.PasswordInputView_normalInput, true);

        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        setFocusable(normalInput);
        setCursorVisible(false);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(passwordLength)});
        setSingleLine(true);
        setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 外边框
        float paddingVertical = (getHeight() - itemHeight) / 2;
        for (int i = 0; i < passwordLength; i++) {
            rect.set((itemHeight + itemPadding) * i + itemPadding, paddingVertical,
                    (itemHeight + itemPadding) * (i + 1), paddingVertical + itemHeight);
            if (i == textLength && (!normalInput || hasFocus())) {
                borderPaint.setColor(borderRespondingColor);
            } else {
                borderPaint.setColor(borderColor);
            }
            borderPaint.setStrokeWidth(1f);
            canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);

            // 内容区
            rectIn.set(rect.left + borderWidth, rect.top + borderWidth,
                    rect.right - borderWidth, rect.bottom - borderWidth);
            borderPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
            canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);
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
        setMeasuredDimension((int) itemHeight * passwordLength + (int) itemPadding * (passwordLength + 1)
                , (int) (itemHeight + 2 * itemPadding));
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.length();
        invalidate();
    }

}
