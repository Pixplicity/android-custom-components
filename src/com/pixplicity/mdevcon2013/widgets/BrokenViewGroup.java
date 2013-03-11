package com.pixplicity.mdevcon2013.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BrokenViewGroup extends LinearLayout {

	private TextView mViewToShowIfSpace;
	private final float mCX = 250;
	private final float mCY = 250;
	private final float mRadius = 250;
	private int mTextWidth;

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

	/*/
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (mViewToShowIfSpace != null
				&& b < ((ViewGroup) getParent()).getHeight()) {
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			addViewInLayout(mViewToShowIfSpace, -1, layoutParams);
			int widthSpec = MeasureSpec.makeMeasureSpec(layoutParams.width,
					MeasureSpec.EXACTLY);
			int heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height,
					MeasureSpec.EXACTLY);
			mViewToShowIfSpace.measure(widthSpec, heightSpec);
			mViewToShowIfSpace.layout(0, 0,
					mViewToShowIfSpace.getMeasuredWidth(),
					mViewToShowIfSpace.getMeasuredHeight());
			mViewToShowIfSpace = null;
		}
		super.onLayout(changed, l, t, r, b);
	}

	// */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		requestLayout();
		return super.onTouchEvent(event);
	}

}
