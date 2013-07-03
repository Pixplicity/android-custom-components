package com.pixplicity.mobdevcon.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class SimpleAnimatedViewSlow1 extends SimpleAnimatedView {

	public SimpleAnimatedViewSlow1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void createPaints() {
		mPaintFg = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintLn = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
		super.createPaints();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		createPaints();
		super.onDraw(canvas);
	}

}
