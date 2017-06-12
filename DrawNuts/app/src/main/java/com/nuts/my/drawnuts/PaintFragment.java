package com.nuts.my.drawnuts;

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
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class PaintFragment extends Fragment {

  public static final int SEEKBAR_MAX_VALUE = 100;
  public static final int MAX_STROKE = 40;
  public static final int MIN_STROKE = 1;

  private ImageView mColorPickerButton;
  private int mColor;
  private DrawingView mDrawingView;
  private SeekBar mStrokeWidthSeekbar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_paint, container, false);
    mColorPickerButton = (ImageView) inflate.findViewById(R.id.fragment_paint_color_picker_button);
    mDrawingView = (DrawingView) inflate.findViewById(R.id.fragment_paint_drawing_view);
    mStrokeWidthSeekbar = (SeekBar) inflate.findViewById(R.id.fragment_paint_stroke_width_seekbar);

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

    mStrokeWidthSeekbar.setProgress((int) (SEEKBAR_MAX_VALUE / 2.0));

    mColorPickerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openColorPicker();
      }
    });

    updateColor(ContextCompat.getColor(getContext(), R.color.starting_paint_color));
    return inflate;
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
    Drawable x = new ColorDrawable(mColor);
    mColorPickerButton.setBackground(x);
    mDrawingView.setColor(mColor);
  }

  private class ColorPickerOkButtonListener implements ColorPickerClickListener {
    @Override
    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
      updateColor(selectedColor);
    }
  }

  private static class DummyListener implements DialogInterface.OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
  }
}
