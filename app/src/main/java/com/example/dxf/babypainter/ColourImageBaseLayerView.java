package com.example.dxf.babypainter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColourImageBaseLayerView extends View
{
    private List<Drawable> mDrawble = new ArrayList<>();
    private List<Drawable> mSaveDrawble = new ArrayList<>();
    private List<Integer> mColor = new ArrayList<>();
    private List<Integer> mSaveColor = new ArrayList<>();
    int color;
    private LayerDrawable mDrawables;

    final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    final int sound_btn = sp.load(super.getContext(),R.raw.btn_tab,1);
    final float volume = 1f;

    public ColourImageBaseLayerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mDrawables = (LayerDrawable) getBackground();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(mDrawables.getIntrinsicWidth(), mDrawables.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        sp.play(sound_btn,volume,volume,1,0,1);
        final float x = event.getX();
        final float y = event.getY();


        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            Drawable drawable = findDrawable(x, y);

            if (drawable != null){
                mDrawble.add(drawable);
                mColor.add(color);
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return super.onTouchEvent(event);
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public  void delete(){
           for(int i = 0; i<mDrawble.size() ;i++){
               mDrawble.get(i).setColorFilter(null);
           }
    }

    //撤销
    public void undo() {
        Log.d("mDrable"," "+mDrawble.size());
        Log.d("mColor"," "+mColor.size());
        if(mDrawble!=null&&mDrawble.size()>1){
            mSaveDrawble.add(mDrawble.get(mDrawble.size()-1));
            mSaveColor.add(mColor.get(mColor.size()-1));
            draw();
            mDrawble.remove(mDrawble.size()-1);
            mColor.remove(mColor.size()-1);

        }
        else if(mDrawble!=null&&mDrawble.size()==1){
            mSaveDrawble.add(mDrawble.get(mDrawble.size()-1));
            mSaveColor.add(mColor.get(mColor.size()-1));
            mDrawble.get(0).setColorFilter(null);
            mDrawble.remove(mDrawble.size()-1);
            mColor.remove(mColor.size()-1);
        }
    }

    //反撤销
    public void reundo() {
        if(mSaveDrawble!=null&&!mSaveDrawble.isEmpty()){
            mDrawble.add(mSaveDrawble.get(mSaveDrawble.size()-1));
            mColor.add(mSaveColor.get(mSaveColor.size()-1));
            redraw();
            mSaveDrawble.remove(mSaveDrawble.size()-1);
            mSaveColor.remove(mSaveColor.size()-1);

        }
    }

    public void draw(){
        delete();
        for(int i = 0; i<mDrawble.size()-1 ;i++){
            mDrawble.get(i).setColorFilter(mColor.get(i),PorterDuff.Mode.SRC_ATOP);
        }

    }

    public void redraw(){
        delete();
        for(int i = 0; i<mDrawble.size() ;i++){
            mDrawble.get(i).setColorFilter(mColor.get(i),PorterDuff.Mode.SRC_ATOP);
        }

    }

    private Drawable findDrawable(float x, float y)
    {
        final int numberOfLayers = mDrawables.getNumberOfLayers();
        Drawable drawable = null;
        Bitmap bitmap = null;
        for (int i = numberOfLayers - 1; i >= 0; i--)
        {
            drawable = mDrawables.getDrawable(i);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            try
            {
                int pixel = bitmap.getPixel((int) x, (int) y);
                if (pixel == Color.TRANSPARENT)
                {
                    continue;
                }
            } catch (Exception e)
            {
                continue;
            }
            return drawable;
        }
        return null;
    }

}