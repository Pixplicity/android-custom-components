package com.pixplicity.add2013.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class SimpleAnimatedViewSlow1 extends SimpleAnimatedView {

	public SimpleAnimatedViewSlow1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		createPaints();
		super.onDraw(canvas);
	}

}
