package com.pixplicity.add2013.widgets;

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

	protected static final long FPS_DELAY = 1000 / 60;

	private float mStartAngle, mSweepAngle;
	private float mRadius = 200;
	private final RectF mRect = new RectF(0, 0, mRadius, mRadius);
	private final Paint mPaintFg = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintLn = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);

	protected final Runnable animator = new Runnable() {

		@Override
		public void run() {
			if (mSweepAngle <= 360) {
				mSweepAngle += 1f;
			} else if (mStartAngle <= 360) {
				mStartAngle += 1f;
			} else {
				reset();
			}
			nextFrame();
			invalidate();
		}
	};

	public SimpleAnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		createPaints();
	}

	protected void createPaints() {
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
		reset();
		nextFrame();
	}

	protected void reset() {
		mStartAngle = mSweepAngle = 0;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		removeCallbacks(animator);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Try for a width based on our minimum including horizontal padding
		int paddingX = getPaddingLeft() + getPaddingRight();
		int minW = getSuggestedMinimumWidth() + paddingX;
		int w = resolveSizeAndState(minW, widthMeasureSpec, 0);

		// Whatever the width ends up being, ask for a height that would let the
		// view get as big as it can, again compensating for padding
		int paddingY = getPaddingBottom() + getPaddingTop();
		int minH = MeasureSpec.getSize(w) - paddingX + paddingY;
		int h = resolveSizeAndState(minH, heightMeasureSpec, 0);

		setMeasuredDimension(w, h);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	protected void nextFrame() {
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
		canvas.drawArc(
				mRect,
				mStartAngle,
				mSweepAngle - mStartAngle,
				true,
				mPaintFg);
	}

}
