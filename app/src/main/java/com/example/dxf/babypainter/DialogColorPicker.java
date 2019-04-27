package com.example.dxf.babypainter;

import android.app.Dialog;
import android.animation.*;
import android.content.*;
import android.text.*;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
public class DialogColorPicker extends DialogFragment implements View.OnLongClickListener
{
    private GSeekBar sbA, sbR, sbG, sbB;
    private View color_view;
    private TextView hexA, hexR, hexG, hexB;
    private ColorSelectListener colorSelectListener;
    public DialogColorPicker()
    {
    }
    //show dialog
    public static void showDialogColorPicker(AppCompatActivity activity, int tag)
    {
        DialogColorPicker dialog = new DialogColorPicker();
        dialog.show(activity.getSupportFragmentManager(), String.valueOf(tag));
    }
    //build dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        colorSelectListener = (ColorSelectListener)getActivity();
//tag DialogNumber
        final int dialogNumber = Integer.valueOf(getTag());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view, null);
//color view
        color_view = (View)view.findViewById(R.id.color_view);
        color_view.setOnLongClickListener(this);
//SeekBars
        sbA = (GSeekBar)view.findViewById(R.id.sbA);
        sbR = (GSeekBar)view.findViewById(R.id.sbR);
        sbG = (GSeekBar)view.findViewById(R.id.sbG);
        sbB = (GSeekBar)view.findViewById(R.id.sbB);
//TextViews
        hexA = (TextView)view.findViewById(R.id.hexA);
        hexR = (TextView)view.findViewById(R.id.hexR);
        hexG = (TextView)view.findViewById(R.id.hexG);
        hexB = (TextView)view.findViewById(R.id.hexB);
        getStateColor(getActivity(), dialogNumber, sbA, sbR, sbG, sbB, hexA, hexR, hexG, hexB, color_view);
        SeekBar.OnSeekBarChangeListener listener  = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int porgress, boolean fromUser)
            {
                color_view.setBackgroundColor(Color.argb(sbA.getProgress(), sbR.getProgress(), sbG.getProgress(), sbB.getProgress()));
                String A = String.valueOf(Integer.toHexString(sbA.getProgress()));
                String R = String.valueOf(Integer.toHexString(sbR.getProgress()));
                String G = String.valueOf(Integer.toHexString(sbG.getProgress()));
                String B = String.valueOf(Integer.toHexString(sbB.getProgress()));
                hexA.setText(A);
                hexR.setText(R);
                hexG.setText(G);
                hexB.setText(B);
            }
            public void onStartTrackingTouch(SeekBar sb)
            {
            }
            public void onStopTrackingTouch(SeekBar sb)
            {
            }};
        sbA.setOnSeekBarChangeListener(listener);
        sbR.setOnSeekBarChangeListener(listener);
        sbG.setOnSeekBarChangeListener(listener);
        sbB.setOnSeekBarChangeListener(listener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
//Positive Button
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                int A = sbA.getProgress();
                int R = sbR.getProgress();
                int G = sbG.getProgress();
                int B = sbB.getProgress();
                int color = Color.argb(A, R, G, B);
                setDialogColor(getContext(), dialogNumber, color, A, R, G, B);
                colorSelectListener.onColorSelected(DialogColorPicker.this, color);
                dismiss();
            }
        });
//NegativeButton
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }
    //get DialogColor / get value of color
    public static int getDialogColor(final Context c, final int dialogNumber, int color)
    {
        SharedPreferences sp = c.getSharedPreferences(String.valueOf(dialogNumber), Context.MODE_PRIVATE);
        final int color_value = sp.getInt("color_value", color);
        return color_value;
    }
    //set DialogColor / put value of color
    public static void setDialogColor(Context c, int dialogNumber, int colorValue, int valueA, int valueR, int valueG, int valueB)
    {
        SharedPreferences sp = c.getSharedPreferences(String.valueOf(dialogNumber), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
        edit.putInt("seekbar_a", valueA);
        edit.putInt("seekbar_r", valueR);
        edit.putInt("seekbar_g", valueG);
        edit.putInt("seekbar_b", valueB);
        edit.putInt("color_value", colorValue);
        edit.putString("hex_a", String.valueOf(Integer.toHexString(valueA)));
        edit.putString("hex_r", String.valueOf(Integer.toHexString(valueR)));
        edit.putString("hex_g", String.valueOf(Integer.toHexString(valueG)));
        edit.putString("hex_b", String.valueOf(Integer.toHexString(valueB)));
        edit.apply();
    }
    //get State Color
    public void getStateColor(Context c, int dialogNumber, SeekBar sbA, SeekBar sbR, SeekBar sbG, SeekBar sbB, TextView hexA, TextView hexR, TextView hexG, TextView hexB, View view)
    {
        SharedPreferences sp = c.getSharedPreferences(String.valueOf(dialogNumber), Context.MODE_PRIVATE);
        int A = sp.getInt("seekbar_a", 255);
        int R = sp.getInt("seekbar_r", 255);
        int G = sp.getInt("seekbar_g", 255);
        int B = sp.getInt("seekbar_b", 255);
        String hex_a = sp.getString("hex_a", "ff");
        String hex_r = sp.getString("hex_r", "ff");
        String hex_g = sp.getString("hex_g", "ff");
        String hex_b = sp.getString("hex_b", "ff");
        int colorValue = sp.getInt("color_value", -1);
        sbA.setProgress(A);
        sbR.setProgress(R);
        sbG.setProgress(G);
        sbB.setProgress(B);
        hexA.setText(hex_a);
        hexR.setText(hex_r);
        hexG.setText(hex_g);
        hexB.setText(hex_b);
        view.setBackgroundColor(colorValue);
    }
    //onLongClick
    @Override
    public boolean onLongClick(View v)
    {
        int x = color_view.getWidth() /2;
        int y = color_view.getHeight() /2;
        int endRadius = Math.max(color_view.getWidth(), color_view.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(color_view, x, y, 0, endRadius);
        circularReveal.start();
        int color = Color.argb(sbA.getProgress(), sbR.getProgress(), sbG.getProgress(), sbB.getProgress());
        setClipboard(getActivity(), String.valueOf(Integer.toHexString(color)));
        return true;
    }
    //setClipboard
    public void setClipboard(Context c, String value)
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(value);
            Toast.makeText(getActivity(),"Copied ! "+"#"+value, Toast.LENGTH_SHORT).show();
        }
        else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied ! ", " #"+value);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(c, "copied to clipboard ! "+"#"+value, Toast.LENGTH_SHORT).show();
        }
    }
    //interface
    public interface ColorSelectListener {
        void onColorSelected(DialogFragment dialog, int color);
    }
}