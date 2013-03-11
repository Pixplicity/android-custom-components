package com.pixplicity.mdevcon2013.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BadSmellView1 extends View {

	private static final long FPS_DELAY = 1000 / 60;
	private float mAngle;
	private final float mSize = 200;

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

	public BadSmellView1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mAngle = 0;
		nextFrame();
	}

	private void nextFrame() {
		postDelayed(animator, FPS_DELAY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.RED);
		canvas.drawArc(new RectF(0, 0, mSize, mSize), 0, mAngle, true, paint);
	}

}
