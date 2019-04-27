package com.example.dxf.babypainter;

import android.util.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.widget.*;
public class GSeekBar extends SeekBar
{
    private int progress_color;
    public GSeekBar(Context c)
    {
        super(c);
    }
    public GSeekBar(Context c, AttributeSet attrs)
    {
        super(c, attrs);
        TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.GSeekBar, 0, 0);
        progress_color = typedArray.getInt(R.styleable.GSeekBar_progressColor, 0);
        typedArray.recycle();
        if ( progress_color != 0)
        {
            getProgressDrawable().setColorFilter(progress_color, PorterDuff.Mode.SRC_IN);
            getThumb().setColorFilter(progress_color, PorterDuff.Mode.SRC_IN);
        }
    }
}