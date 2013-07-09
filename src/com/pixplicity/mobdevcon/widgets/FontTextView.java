package com.pixplicity.mobdevcon.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pixplicity.droidconfr.R;
import com.pixplicity.mobdevcon.util.FontUtil;

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		super(context);
		FontTextView.init(this, context, null, 0);
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontTextView.init(this, context, attrs, 0);
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		FontTextView.init(this, context, attrs, defStyle);
	}

	protected static void init(TextView view, Context context,
			AttributeSet attrs, int defStyle) {
		if (attrs != null) {
			final TypedArray a = context.obtainStyledAttributes(
					attrs, R.styleable.FontTextView, defStyle, 0);
			final String typeface =
					a.getString(R.styleable.FontTextView_typeface);
			a.recycle();

			FontUtil.setTypeface(view, typeface);
		}
	}

	public void setTypeface(String typeface) {
		FontUtil.setTypeface(this, typeface);
	}

}
