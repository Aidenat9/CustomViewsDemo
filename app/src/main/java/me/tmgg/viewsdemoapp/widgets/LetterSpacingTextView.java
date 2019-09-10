package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/9/5 14:39
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：   横向设置文字间隔           </p>
 */
public class LetterSpacingTextView extends AppCompatTextView {
    private float spacing = Spacing.NORMAL;
    private CharSequence originalText = "";

    public LetterSpacingTextView(Context context) {
        super(context, null, 0);
    }

    public LetterSpacingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSpacingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSpacingTextView);
        int space = typedArray.getInt(R.styleable.LetterSpacingTextView_spacing, 0);
        typedArray.recycle();
        setOriginalText(getText());
        setSpacing(space);
    }


    private class Spacing {
        public static final int NORMAL = 0;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
        applySpaceSize();
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    /**
     * 设置文字，优先展示布局里文字
     *
     * @param originalText
     */
    public void setOriginalText(CharSequence originalText) {
        CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            this.originalText = text;
        } else {
            this.originalText = originalText;
        }
        applySpaceSize();
    }

    /**
     * 设置字间隔
     */
    private void applySpaceSize() {
        if (null == this || TextUtils.isEmpty(originalText)) {
            return;
        }
        //把文字分散，每个文字后增加一个空格
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            char c = originalText.charAt(i);
            sb.append(c);
            sb.append("\u00A0");
        }
        //通过SpannableString设置 空格的横向缩放达到间距增加缩小视觉效果
        SpannableString spannableString = new SpannableString(sb.toString());
        if (sb.toString().length() > 1) {
            //由于现在的文字是 每个字后有一个空格，那么空格的坐标就是 1 3 5 ，步长2
            for (int i = 1; i < sb.toString().length(); i += 2) {
                spannableString.setSpan(new ScaleXSpan((spacing + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(spannableString, BufferType.NORMAL);
    }


}
