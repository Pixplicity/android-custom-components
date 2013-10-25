package com.pixplicity.devfest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
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
		Rect dirty = null;
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			dirty = new Rect(
					(int) Math.floor(lastX), (int) Math.floor(lastY),
					(int) Math.floor(lastX), (int) Math.floor(lastY));
			// Android batches touch events; see
			// http://corner.squareup.com/2010/07/smooth-signatures.html
			int historySize = event.getHistorySize();
			for (int i = 0; i < historySize; i++) {
				float historicalX = event.getHistoricalX(i);
				float historicalY = event.getHistoricalY(i);
				lineTo(historicalX, historicalY, dirty);
			}
			lineTo(x, y, dirty);
			break;
		case MotionEvent.ACTION_UP:
			// Nothing to do
			break;
		}
		if (INVALIDATE_REGION && dirty != null) {
			int pad = (int) (STROKE_WIDTH / 2);
			invalidate(dirty.left - pad, dirty.top - pad, dirty.right + pad, dirty.bottom + pad);
		} else {
			invalidate();
		}
		lastX = x;
		lastY = y;
		return true;
	}

	private void lineTo(float x, float y, Rect dirty) {
		if (INVALIDATE_REGION) {
			int pad = (int) (STROKE_WIDTH / 2);
			if (x < dirty.left) {
				dirty.left = (int) Math.floor(x);
			} else {
				dirty.right = (int) Math.ceil(x);
			}
			if (y < dirty.top) {
				dirty.top = (int) Math.floor(y);
			} else {
				dirty.bottom = (int) Math.ceil(y);
			}
		}
		if (QUAD_LINES) {
			path.quadTo(x, y, (lastX + x) / 2, (lastY + y) / 2);
		} else {
			path.lineTo(x, y);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, paint);
		super.onDraw(canvas);
	}

}
