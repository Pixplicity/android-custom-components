package com.pixplicity.mobdevcon.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class SimpleAnimatedViewSlow2 extends SimpleAnimatedView {

	public SimpleAnimatedViewSlow2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void nextFrame() {
		postDelayed(animator, FPS_DELAY);
	}

}
