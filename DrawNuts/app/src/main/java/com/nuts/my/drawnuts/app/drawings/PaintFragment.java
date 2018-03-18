package com.nuts.my.drawnuts.app.drawings;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.nuts.my.drawnuts.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class PaintFragment extends Fragment {
	
	private Socket mSocket;
	
	{
		try {
			mSocket = IO.socket("http://10.0.0.4:1234");
		} catch (URISyntaxException e) {
		}
	}
	
	public static final int SEEKBAR_MAX_VALUE = 100;
	public static final int MAX_STROKE = 40;
	public static final int MIN_STROKE = 1;
	
	@BindView(R.id.fragment_paint_eraser_button)
	ImageView mEraserButton;
	@BindView(R.id.fragment_pencil_button)
	ImageView mPencilButton;
	@BindView(R.id.fragment_paint_color_picker_button)
	ImageView mColorPickerButton;
	@BindView(R.id.fragment_paint_drawing_view)
	DrawingView mDrawingView;
	
	@BindView(R.id.fragment_paint_stroke_width_seekbar)
	SeekBar mStrokeWidthSeekbar;
	
	private int mColor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mSocket.connect();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_paint, container, false);
		ButterKnife.bind(this, view);
		
		bindSeekbarValue();
		initSeekbarValue();
		initColor();
		
		return view;
	}
	
	@OnClick(R.id.fragment_pencil_button)
	public void onPencilClicked() {
		mDrawingView.setColor(mColor);
	}
	
	@OnClick(R.id.fragment_paint_color_picker_button)
	public void onColorPickerClicked() {
		openColorPicker();
	}
	
	@OnClick(R.id.fragment_paint_eraser_button)
	public void onEraserClicked() {
		mDrawingView.setColor(ContextCompat.getColor(getContext(), R.color.white));
	}
	
	private void initColor() {
		updateColor(ContextCompat.getColor(getContext(), R.color.starting_paint_color));
	}
	
	private void initSeekbarValue() {
		mStrokeWidthSeekbar.setProgress((int) (SEEKBAR_MAX_VALUE / 2.0));
	}
	
	private void bindSeekbarValue() {
		mStrokeWidthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int currentValue, boolean fromUser) {
				float proportionalValue = currentValue * 1.0f / SEEKBAR_MAX_VALUE;
				float strokeValue = proportionalValue * (MAX_STROKE - MIN_STROKE) + MIN_STROKE;
				mDrawingView.setStrokeWidth(strokeValue);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});
	}
	
	private void openColorPicker() {
		ColorPickerDialogBuilder.with(getContext())
				.setTitle("Choose color")
				.initialColor(mColor)
				.wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
				.density(12)
				.setPositiveButton("ok", new ColorPickerOkButtonListener())
				.setNegativeButton("cancel", new DummyListener())
				.build()
				.show();
	}
	
	private void updateColor(int newColor) {
		
		mColor = newColor;
		mSocket.emit("updated color","" + mColor);
		Drawable x = new ColorDrawable(mColor);
		mColorPickerButton.setBackground(x);
		mDrawingView.setColor(mColor);
		
	}
	
	private Emitter.Listener onNewMessage = new Emitter.Listener() {
		@Override
		public void call(final Object[] args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					String message;
					try {
						username = data.getString("username");
						message = data.getString("message");
					} catch (JSONException e) {
						return;
					}
					
					// add the message to view
					Toast.makeText(getContext(), "abc", Toast.LENGTH_LONG).show();
					
				}
			});
		}
	};
	
	private static class DummyListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	}
	
	private class ColorPickerOkButtonListener implements ColorPickerClickListener {
		@Override
		public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
			updateColor(selectedColor);
		}
	}
}
