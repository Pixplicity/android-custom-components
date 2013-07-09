package com.pixplicity.mobdevcon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.droidconfr.R;

/**
 * A fragment showcasing some custom components.
 */
public class Fonts1Fragment extends Fragment {

	public Fonts1Fragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_fonts, container,
				false);
		return rootView;
	}
}