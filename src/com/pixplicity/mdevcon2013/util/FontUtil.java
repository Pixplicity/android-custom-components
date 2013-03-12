package com.pixplicity.mdevcon2013.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontUtil {

	private static final Map<String, Typeface> FONTS = new HashMap<String, Typeface>();

	public static Typeface getTypeface(Context context, String typefaceName) {
		typefaceName = typefaceName.intern();
		Typeface typeface = FONTS.get(typefaceName);
		if (typeface == null) {
			typeface = Typeface.createFromAsset(
					context.getAssets(),
					"fonts/" + typefaceName);
			FONTS.put(typefaceName, typeface);
		}
		return typeface;
	}

	public static void setTypeface(TextView view, String typeface) {
		view.setTypeface(getTypeface(view.getContext(), typeface));
	}

	public static void setTypefaceBad(TextView view, String typefaceName) {
		Typeface typeface = Typeface.createFromAsset(
				view.getContext().getAssets(),
				"fonts/" + typefaceName);
		view.setTypeface(typeface);
	}

}
