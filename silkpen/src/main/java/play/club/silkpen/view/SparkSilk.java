package play.club.silkpen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import play.club.silkpen.entiy.Spark;
import play.club.silkpen.utils.DensityUtils;


/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/17 11:09
 * 修改人：fuzh2
 * 修改时间：2016/6/17 11:09
 * 修改备注：
 */
public class SparkSilk extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Context mContext;

    private boolean isLoop = false;
    private float X;
    private float Y;

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private int WIDTH;
    private int HEIGHT;
    private Spark mStarts;

    public SparkSilk(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public SparkSilk(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        WIDTH = DensityUtils.getScreenWidth(mContext);
        HEIGHT = DensityUtils.getScreenHeight(mContext);
        mStarts = new Spark(WIDTH, HEIGHT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.isLoop = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawbackGround();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.isLoop = false;
    }

    private void drawbackGround() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    @Override
    public void run() {
        long startTimps = 0l;
        // 火花数组
        int[][] sparks = new int[100][10];
        while (isLoop) {
            startTimps = System.currentTimeMillis();
            try {
                mCanvas = mHolder.lockCanvas(null);
                if (mCanvas != null) {
                    synchronized (mHolder) {
                        // 清屏
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        //绘画
                        for (int[] n : sparks)
                            mStarts.drawSpark(mCanvas, (int) X, (int) Y, n);
                        // 控制帧数
                        Thread.sleep(Math.max(0, 30 - (System.currentTimeMillis() - startTimps)));
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getPointerCount()) {
            // 单点触摸
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        onMouseMove();
                        X = event.getX();
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        onMouseUp();
                        break;
                    default:
                        break;
                }
                break;
        }

        return true;
    }

    private void onMouseUp() {
        mStarts.isActive = false;
    }

    private void onMouseMove() {
        mStarts.isActive = true;
    }
}
