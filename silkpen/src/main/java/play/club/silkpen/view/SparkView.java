package play.club.silkpen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Date;

import play.club.silkpen.entiy.Spark;
import play.club.silkpen.utils.DensityUtils;


public class SparkView
        extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean isRun;

    private Spark sparkManager;

    // 当前触摸点X，Y坐标
    private double X, Y;

    // 屏幕宽高
    public static int WIDTH, HEIGHT;

    public SparkView(Context context) {
        super(context);
        init(context);
    }

    public SparkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    private void init(Context context) {
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        getScreenXY();
        WIDTH = DensityUtils.getScreenWidth(context);
        HEIGHT = DensityUtils.getScreenHeight(context);
        // 火花管理器
        sparkManager = new Spark(WIDTH, HEIGHT);

        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    private void getScreenXY() {
        // 设置视图宽高（像素）
//        DisplayMetrics metric = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        WIDTH = metric.widthPixels;
//        HEIGHT = metric.heightPixels;
    }

    @Override
    public void run() {
        // 火花数组
        int[][] sparks = new int[400][10];

        Date date = null;
        while (isRun) {
            date = new Date();
            try {
                mCanvas = mHolder.lockCanvas(null);
                if (mCanvas != null) {
                    synchronized (mHolder) {
                        // 清屏
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        // 循环绘制所有火花
                        for (int[] n : sparks) {
                            n = sparkManager.drawSpark(mCanvas, (int) X, (int) Y, n);
                        }

                        // 控制帧数
                        Thread.sleep(Math.max(0, 30 - (new Date().getTime() - date.getTime())));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getPointerCount()) {
            // 单点触摸
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        sparkManager.isActive = true;
                        X = event.getX();
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        sparkManager.isActive = false;
                        break;
                    default:
                        break;
                }
                break;
        }

        return true;
    }

    // Surface的大小发生改变时调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Action","bac");
        drawBackgound();
    }

    // Surface创建时激发，一般在这里调用画面的线程
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
        new Thread(this).start();
    }

    // 销毁时激发，一般在这里将画面的线程停止、释放。
    @Override
    public void surfaceDestroyed(SurfaceHolder argholder0) {
        isRun = false;
    }

    private void drawBackgound() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }
}
