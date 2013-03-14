package com.pixplicity.mdevcon2013.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BrokenViewGroup extends LinearLayout {

	private TextView mViewToShowIfSpace;

	public BrokenViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mViewToShowIfSpace = new TextView(context);
		mViewToShowIfSpace.setText("Hello world!");
	}

	@Override
	// *
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mViewToShowIfSpace != null
				&& b < ((ViewGroup) getParent()).getHeight()) {
			addView(mViewToShowIfSpace);
			mViewToShowIfSpace = null;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		requestLayout();
		return super.onTouchEvent(event);
	}

}
