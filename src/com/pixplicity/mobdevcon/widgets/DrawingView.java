package com.pixplicity.mobdevcon.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	private static final boolean QUAD_LINES = false;
	private static final float STROKE_WIDTH = 6f;
	private static final boolean INVALIDATE_REGION = true;

	private final Paint paint = new Paint();
	private final Path path = new Path();
	private float lastX = -1, lastY;

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint.setAntiAlias(true);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			if (QUAD_LINES) {
				path.quadTo(x, y, (lastX + x) / 2, (lastY + y) / 2);
			} else {
				path.lineTo(x, y);
			}
			break;
		case MotionEvent.ACTION_UP:
			// Nothing to do
			break;
		}
		if (INVALIDATE_REGION) {
			int pad = (int) (STROKE_WIDTH / 2);
			int l, t, r, b;
			if (lastX < x) {
				l = (int) Math.floor(lastX);
				r = (int) Math.ceil(x);
			} else {
				l = (int) Math.floor(x);
				r = (int) Math.ceil(lastX);
			}
			if (lastY < y) {
				t = (int) Math.floor(lastY);
				b = (int) Math.ceil(y);
			} else {
				t = (int) Math.floor(y);
				b = (int) Math.ceil(lastY);
			}
			invalidate(l - pad, t - pad, r + pad, b + pad);
		} else {
			invalidate();
		}
		lastX = x;
		lastY = y;
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, paint);
		super.onDraw(canvas);
	}

}
