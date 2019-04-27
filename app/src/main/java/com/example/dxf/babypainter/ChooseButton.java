package com.example.dxf.babypainter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ChooseButton extends View {
    int x;//横坐标
    int y;//纵坐标
    int maxr;//半径
    int minr;//小半径
    int k;//块数
    int radius;//角度
    int state = 0;

    int color[] = {
            Color.rgb(255,0,255),
            Color.rgb(129,0,127),
            Color.rgb(138,42,227),
            Color.rgb(65,105,226),
            Color.rgb(63,224,208),
            Color.rgb(0,255,1),
            Color.rgb(128,255,0),
            Color.rgb(255,255,1),
            Color.rgb(254,165,0),
            Color.rgb(254,0,0),
    };

    public ChooseButton(Context context,int x,int y,int maxr,int minr,int k) {
        this(context, null);
        this.x = x;
        this.y = y;
        this.maxr = maxr;
        this.minr = minr;
        this.k = k;
        if(k > 0)
        {this.radius = 360/k;}
    }

    public ChooseButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChooseButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);}

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNomal(canvas,x,y,maxr,minr);

    }

    protected void drawNomal(Canvas canvas,int x ,int y , int maxr ,int minr){
        Paint mPaint= new Paint();
        //mPaint.setStyle(Paint.Style.STROKE);
        //canvas.drawCircle(x,y,r,mPaint);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(x,y,maxr+30,mPaint);

        for(int i = 0;i<k;i++){
            mPaint.setColor(color[i]);
            canvas.drawArc(x-maxr,y-maxr,x+maxr,y+maxr,i*radius,radius,true,mPaint);
        }
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(x,y,minr,mPaint);
        mPaint.setColor(Color.rgb(113,211,248));
        canvas.drawCircle(x,y,minr-20,mPaint);
    }

    public boolean compare(float num1, int num2){
        if((num1-num2)*(num1-num2)<1) {
        return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_DOWN:
                //获取屏幕上点击的坐标
                int x=(int)event.getX();
                int y =(int)event.getY();
                if((x-this.x)*(x-this.x)+(y-this.y)*(y-this.y) >= minr*minr && (x-this.x)*(x-this.x)+(y-this.y)*(y-this.y) <= maxr*maxr)
                {
                    //Toast.makeText(super.getContext(), "You clicked "+x+" "+y, Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = loadBitmapFromView(this);
                    int pixel = bitmap.getPixel(x,y);
//获取颜色
                    float redValue = Color.red(pixel);
                    float greenValue = Color.green(pixel);
                    float blueValue = Color.blue(pixel);
                    if(compare(redValue,255) && compare(greenValue,0) && compare(blueValue,255)){
                        state = 1;
                    }
                    else if(compare(redValue,129) && compare(greenValue,0) && compare(blueValue,127)){
                        state = 2;
                    }
                    else if(compare(redValue,138) && compare(greenValue,42) && compare(blueValue,227)){
                        state = 3;
                    }
                    else if(compare(redValue,65) && compare(greenValue,105) && compare(blueValue,226)){
                        state = 4;
                    }
                    else if(compare(redValue,63) && compare(greenValue,224)&& compare(blueValue,208)){
                        state = 5;
                    }
                    else if(compare(redValue,0) && compare(greenValue,255) && compare(blueValue,1)){
                        state = 6;
                    }
                    else if(compare(redValue,128) && compare(greenValue,255) && compare(blueValue,0)){
                        state = 7;
                    }
                    else if(compare(redValue,255) && compare(greenValue,255) && compare(blueValue,1)){
                        state = 8;
                    }
                    else if(compare(redValue,254) && compare(greenValue,165) &&compare(blueValue,0)){
                        state = 9;
                    }
                    else if(compare(redValue,254) && compare(greenValue,0) && compare(blueValue,0)){
                        state = 10;
                    }
                    else{
                        state = 0;
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                super.onTouchEvent(event);
        }
        //这句话不要修改
        return super.onTouchEvent(event);
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

}