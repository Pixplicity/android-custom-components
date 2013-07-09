package com.pixplicity.mobdevcon.fragments;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.pixplicity.mobdevcon.R;
import com.pixplicity.mobdevcon.widgets.SimpleAnimatedView;

/**
 * A fragment showcasing some custom components.
 */
public class CodeSmellsFragment extends Fragment implements SimpleAnimatedView.FpsListener {

	private SimpleAnimatedView mAnim1;
	private SimpleAnimatedView mAnim2;
	private SimpleAnimatedView mAnim3;
	
	private TextView mFpsText;
	private float mFps;
	
	private final Handler mHandler = new Handler();
	private final Runnable mFpsRunnable = new Runnable() {
		@Override
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