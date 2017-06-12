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
import android.widget.Toast;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class PaintFragment extends Fragment {

  private ImageView mColorPickerButton;
  private int mColor;
  private DrawingView mDrawingView;

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
        .setOnColorSelectedListener(new OnColorSelectedListener() {
          @Override
          public void onColorSelected(int selectedColor) {
            Toast.makeText(getContext(), "onColorSelected: 0x" + Integer.toHexString(selectedColor),
                Toast.LENGTH_LONG).show();
          }
        })
        .setPositiveButton("ok", new ColorPickerClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
            updateColor(selectedColor);
          }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
          }
        })
        .build()
        .show();
  }

  private void updateColor(int newColor) {
    mColor = newColor;
    Drawable x = new ColorDrawable(mColor);
    mColorPickerButton.setBackground(x);
    mDrawingView.setColor(mColor);
  }
}
