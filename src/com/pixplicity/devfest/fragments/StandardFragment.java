package com.pixplicity.devfest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.mobdevcon.R;

/**
 * A fragment showcasing some standard components.
 */
public class StandardFragment extends Fragment {

	public StandardFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_standard, container,
				false);
		return rootView;
	}
}