package com.pixplicity.mdevcon2013.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pixplicity.mdevcon2013.R;
import com.pixplicity.mdevcon2013.util.FontUtil;

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		if (attrs != null) {
			final TypedArray a = context.obtainStyledAttributes(
					attrs, R.styleable.FontTextView, defStyle, 0);
			final String typeface =
					a.getString(R.styleable.FontTextView_typeface);
			a.recycle();

			if (typeface != null) {
				setTypeface(typeface);
			}
		}
	}

	public void setTypeface(String typeface) {
		FontUtil.setTypeface(this, typeface);
	}

}
