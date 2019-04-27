package com.example.dxf.babypainter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SimpleDoodleView extends View {

    private final static String TAG = "SimpleDoodleView";

    public Paint mPaint = new Paint();
    private List<Path> mPathList = new ArrayList<>(); // 保存涂鸦轨迹的集合
    private List<Paint> mPaintList = new ArrayList<>(); //保存涂鸦画笔颜色的集合
    private List<Path> mSavePathList = new ArrayList<>(); // 保存涂鸦轨迹的集合
    private List<Paint> mSavePaintList = new ArrayList<>(); //保存涂鸦画笔颜色的集合
    private TouchGestureDetector mTouchGestureDetector; // 触摸手势监听
    private float mLastX, mLastY;
    private Path mCurrentPath; // 当前的涂鸦轨迹
    private Paint mCurrentPaint; //当前的画笔颜色

    public SimpleDoodleView(Context context) {
        super(context);
        // 设置画笔
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 由手势识别器处理手势
        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            @Override
            public void onScrollBegin(MotionEvent e) { // 滑动开始
                Log.d(TAG, "onScrollBegin: ");
                mCurrentPath = new Path(); // 新的涂鸦

                mCurrentPaint = new Paint();//新的画笔
                mCurrentPaint.setColor(mPaint.getColor());
                mCurrentPaint.setStyle(Paint.Style.STROKE);
                mCurrentPaint.setStrokeWidth(20);
                mCurrentPaint.setAntiAlias(true);
                mCurrentPaint.setStrokeCap(Paint.Cap.ROUND);

                mPathList.add(mCurrentPath); // 添加的集合中
                mPaintList.add(mCurrentPaint);
                mCurrentPath.moveTo(e.getX(), e.getY());
                mLastX = e.getX();
                mLastY = e.getY();
                invalidate(); // 刷新
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { // 滑动中
                Log.d(TAG, "onScroll: " + e2.getX() + " " + e2.getY());
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e2.getX() + mLastX) / 2,
                        (e2.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                mLastX = e2.getX();
                mLastY = e2.getY();
                invalidate(); // 刷新
                return true;
            }

            @Override
            public void onScrollEnd(MotionEvent e) { // 滑动结束
                Log.d(TAG, "onScrollEnd: ");
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e.getX() + mLastX) / 2,
                        (e.getY() + mLastY) / 2); // 使用贝塞尔曲线 让涂鸦轨迹更圆滑
                mCurrentPath = null; // 轨迹结束
                invalidate(); // 刷新
            }

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean consumed = mTouchGestureDetector.onTouchEvent(event); // 由手势识别器处理手势
        if (!consumed) {
            return super.dispatchTouchEvent(event);
        }
        return true;
    }

    //撤销
    public void undo() {
        if(mPathList!=null&&mPathList.size()>=1){
            mSavePathList.add(mPathList.get(mPathList.size()-1));
            mSavePaintList.add(mPaintList.get(mPaintList.size()-1));

            mPathList.remove(mPathList.size()-1);
            mPaintList.remove(mPaintList.size()-1);
            invalidate();
        }
    }

    //反撤销
    public void reundo() {
        if(mSavePathList!=null&&!mSavePathList.isEmpty()){
            mPathList.add(mSavePathList.get(mSavePathList.size()-1));
            mPaintList.add(mSavePaintList.get(mSavePaintList.size()-1));

            mSavePathList.remove(mSavePathList.size()-1);
            mSavePaintList.remove(mSavePaintList.size()-1);
            invalidate();
        }
    }

    //全部清除
    protected void delete(){
        mPathList.clear();
        mPaintList.clear();
        invalidate();
    }

    @Override
    // 绘制涂鸦轨迹
    protected void onDraw(Canvas canvas) {
        if(mPaintList!=null&&mPaintList.size()>0){
            for(int i=0;i<mPaintList.size();i++){
                canvas.drawPath(mPathList.get(i), mPaintList.get(i));//设置画笔，路径
            }
        }

    }

    //修改画笔颜色
 protected void setColor(int color){
     this.mPaint.setColor(color);
 }

 //得到画笔颜色
 protected int getColor(){
        return this.mPaint.getColor();
    }

}

