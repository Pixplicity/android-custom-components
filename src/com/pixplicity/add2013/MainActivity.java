package com.pixplicity.add2013;

import java.text.NumberFormat;

import com.pixplicity.add2013.R;
import com.pixplicity.add2013.widgets.SimpleAnimatedView;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3),
								getString(R.string.title_section4),
								getString(R.string.title_section5), }), this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (position) {
		case 0:
			fragment = new StandardFragment();
			break;
		case 1:
			fragment = new Fonts1Fragment();
			break;
		case 2:
			fragment = new Fonts2Fragment();
			break;
		case 3:
			fragment = new SimpleAnimatedViewFragment();
			break;
		case 4:
			fragment = new CodeSmellsFragment();
			break;
		}
		if (fragment == null) {
			return false;
		}
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

	/**
	 * A fragment showcasing some standard components.
	 */
	public static class StandardFragment extends Fragment {

		public StandardFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_standard, container,
					false);
			return rootView;
		}
	}

	/**
	 * A fragment showcasing some custom components.
	 */
	public static class Fonts1Fragment extends Fragment {

		public Fonts1Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_fonts, container,
					false);
			return rootView;
		}
	}

	/**
	 * A fragment showcasing some custom components.
	 */
	public static class Fonts2Fragment extends Fragment {

		public Fonts2Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_fonts_styled,
					container, false);
			return rootView;
		}
	}

	/**
	 * A fragment showcasing some custom components.
	 */
	public static class SimpleAnimatedViewFragment extends Fragment {

		public SimpleAnimatedViewFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_animation, container,
					false);
			return rootView;
		}
	}

	/**
	 * A fragment showcasing some custom components.
	 */
	public static class CodeSmellsFragment extends Fragment implements SimpleAnimatedView.FpsListener {

		private SimpleAnimatedView mAnim1;
		private SimpleAnimatedView mAnim2;
		private SimpleAnimatedView mAnim3;
		private Button mBtn;
		
		private int curIndex;
		private TextView mFpsText;
		private float mFps;
		
		private Handler mHandler = new Handler();
		private Runnable mFpsRunnable = new Runnable() {
			public void run() {
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(1);
				mFpsText.setText(nf.format(mFps) + " fps");
				mHandler.postDelayed(this, 200);
			}
		};
		private Spinner mAnimSpinner;

		public CodeSmellsFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_code_smells,
					container, false);
			mAnimSpinner = (Spinner) rootView.findViewById(R.id.sp_switch);
			String[] animList = new String[]{"Normal", "Code smell 1", "Code smell 2"};
			mAnimSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, animList));
			mAnimSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					nextSmell(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			mFpsText = (TextView) rootView.findViewById(R.id.tv_fps);
			mAnim1 = (SimpleAnimatedView) rootView.findViewById(R.id.sav1);
			mAnim1.setFpsListener(this);
			mAnim2 = (SimpleAnimatedView) rootView.findViewById(R.id.sav2);
			mAnim2.setFpsListener(this);
			mAnim3 = (SimpleAnimatedView) rootView.findViewById(R.id.sav3);
			mAnim3.setFpsListener(this);
			nextSmell(0);
			return rootView;
		}

		private void nextSmell(int index) {
			mAnim1.setVisibility(View.GONE);
			mAnim2.setVisibility(View.GONE);
			mAnim3.setVisibility(View.GONE);
			View anim;
			switch (index) {
			case 1:
				anim = mAnim1;
				break;
			case 2:
				anim = mAnim2;
				break;
			case 3:
			default:
				anim = mAnim3;
				break;
			}
			anim.setVisibility(View.VISIBLE);
		}

		@Override
		public void onFpsChange(float fps) {
			mFps = fps;
		}
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			mHandler.post(mFpsRunnable);
		}
		
		@Override
		public void onDetach() {
			super.onDetach();
			mHandler.removeCallbacks(mFpsRunnable);
		}
		
	}

}
