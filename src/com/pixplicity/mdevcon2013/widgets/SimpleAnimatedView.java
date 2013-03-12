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
	private float mRadius = 200;
	private final RectF mRect = new RectF(0, 0, mRadius, mRadius);
	private final Paint mPaintFg = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintLn = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);

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
		mPaintBg.setStyle(Paint.Style.FILL);
		mPaintBg.setColor(Color.argb(128, 233, 233, 233));
		mPaintLn.setStyle(Paint.Style.STROKE);
		mPaintLn.setColor(Color.argb(128, 187, 187, 187));
		mPaintLn.setStrokeWidth(2);
		mPaintFg.setStyle(Paint.Style.FILL);
		mPaintFg.setColor(Color.argb(255, 0, 153, 204));
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mAngle = 0;
		nextFrame();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Try for a width based on our minimum
		int minW = getSuggestedMinimumWidth()
				+ getPaddingLeft() + getPaddingRight();
		int w = resolveSizeAndState(minW, widthMeasureSpec, 1);

		// Whatever the width ends up being, ask for a height that would let the
		// view get as big as it can
		int minH = MeasureSpec.getSize(w)
				+ getPaddingBottom() + getPaddingTop();
		int h = resolveSizeAndState(minH, heightMeasureSpec, 0);

		setMeasuredDimension(w, h);
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
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// Account for padding
		mRect.left = getPaddingLeft();
		mRect.top = getPaddingTop();
		float diameter = Math.min(
				w - mRect.left - getPaddingRight(),
				h - mRect.top - getPaddingBottom());
		mRect.right = mRect.left + diameter;
		mRect.bottom = mRect.top + diameter;
		mRadius = diameter / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(
				mRect.left + mRadius,
				mRect.top + mRadius,
				mRadius,
				mPaintBg);
		canvas.drawCircle(
				mRect.left + mRadius,
				mRect.top + mRadius,
				mRadius,
				mPaintLn);
		canvas.drawArc(mRect, 0, mAngle, true, mPaintFg);
	}

}
