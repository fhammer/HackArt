package play.club.silkpen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import play.club.silkpen.entiy.Circle;


/**
 * @author fuzh2 手写笔
 */
public class Pen extends SurfaceView implements SurfaceHolder.Callback,
        Runnable {
    private Paint paint;// 画笔
    private SurfaceHolder holder;// 指针
    private Canvas canvas;// 画布
    private Path path;// 路径
    // 用链表存储对象
    ArrayList<Circle> circles = new ArrayList<Circle>();
    boolean isRunning = true;

    public Pen(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();

    }

    private void init() {
        // paint = new Paint();
        // 创建路径对象
        // path = new Path();
        holder = this.getHolder();
        holder.addCallback(this);
        this.setFocusable(true);
        this.setDrawingCacheEnabled(true);
        this.setKeepScreenOn(true);
    }

    public Pen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void run() {
        while (isRunning) {
            drawView();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    int x;

    // 实现鼠标在屏幕上绘画
    private void drawView() {
        try {
            if (holder != null) {
                canvas = holder.lockCanvas();
                // 抗锯齿
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
                        Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
                // canvas.drawPath(path, paint);// 画路径
                // canvas.drawCircle(200, 200, x++, paint);
                //迭代链表
                for (Circle c : circles) {//加强for循环
                    c.drawCircle(canvas);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    // 触屏
    public boolean onTouchEvent(MotionEvent e) {
        // TODO Auto-generated method stub
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // path.moveTo(event.getX(), event.getY());
                // 创建圆的对象
                Circle circle = new Circle(e.getX(), e.getY());
                circles.add(circle);
                break;
            case MotionEvent.ACTION_MOVE:
//                path.lineTo(e.getX(),e.getY());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
       Log.d("ViewChange","surfaceChanged");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("ViewChange","surfaceCreated");
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        Log.d("ViewChange","surfaceDestroyed");
    }

}
