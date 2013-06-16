package com.pixplicity.add2013.widgets;

import java.util.Iterator;
import java.util.Stack;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SimpleAnimatedView extends View {
	
	public static interface FpsListener {
		
		public abstract void onFpsChange(float fps);
		
	}

	protected static final long FPS_DELAY = 1000 / 60;

	protected float mRadius = 200;
	protected float mStartAngle, mSweepAngle;
	protected RectF mRect = new RectF(0, 0, mRadius, mRadius);
	protected Paint mPaintFg = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint mPaintLn = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);

	protected final Runnable animator = new Runnable() {

		@Override
		public void run() {
			if (mSweepAngle <= 360) {
				mSweepAngle += 1f;
			} else if (mStartAngle <= 360) {
				mStartAngle += 1f;
			} else {
				resetAnimation();
			}
			nextFrame();
			invalidate();
		}
	};

	private long lastTime;
	private Stack<Float> fpsList = new Stack<Float>();
	private FpsListener fpsListener;

	public SimpleAnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		createPaints();
		resetAnimation();
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
		startAnimation();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopAnimation();
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		switch (visibility) {
		case View.VISIBLE:
			startAnimation();
			break;
		default:
			stopAnimation();
			break;
		}
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
		float diameter = Math.min(w - mRect.left - getPaddingRight(), h
				- mRect.top - getPaddingBottom());
		mRect.right = mRect.left + diameter;
		mRect.bottom = mRect.top + diameter;
		mRadius = diameter / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(mRect.left + mRadius, mRect.top + mRadius, mRadius,
				mPaintBg);
		canvas.drawCircle(mRect.left + mRadius, mRect.top + mRadius, mRadius,
				mPaintLn);
		canvas.drawArc(mRect, mStartAngle, mSweepAngle - mStartAngle, true,
				mPaintFg);
		if (fpsListener != null) {
			long duration = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			float fps = 1000f / duration;
			while (fpsList.size() >= 10) {
				fpsList.pop();
			}
			Log.d("SimpleAnim", "size: " + fpsList.size());
			fpsList.push(fps);
			Iterator<Float> iter = fpsList.iterator();
			fps = 0;
			while (iter.hasNext()) {
				fps += iter.next();
			}
			fps /= fpsList.size();
			fpsListener.onFpsChange(fps);
		}
	}

	protected void resetAnimation() {
		mStartAngle = mSweepAngle = 0;
	}

	private void startAnimation() {
		if (isShown()) {
			nextFrame();
		}
	}

	private void stopAnimation() {
		removeCallbacks(animator);
	}
	
	public void setFpsListener(FpsListener listener) {
		fpsListener = listener;
	}

}
