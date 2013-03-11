package com.pixplicity.mdevcon2013.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class SimpleAnimatedView extends View {

	private static final long FPS_DELAY = 1000 / 60;
	private float mAngle;
	private final float mSize = 200;
	private final RectF mRect = new RectF(0, 0, mSize, mSize);
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private final Runnable animator = new Runnable() {

		@Override
		public void run() {
			if (mAngle <= 360) {
				mAngle += 1f;
				nextFrame();
				invalidate();
			}
		}
	};

	public SimpleAnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.RED);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mAngle = 0;
		nextFrame();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void nextFrame() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			postDelayed(animator, FPS_DELAY);
		} else {
			postOnAnimationDelayed(animator, FPS_DELAY);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawArc(mRect, 0, mAngle, true, mPaint);
	}

}
