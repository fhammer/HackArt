package play.club.silkpen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import play.club.silkpen.entiy.WeaveSilk;


/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/16 18:06
 * 修改人：fuzh2
 * 修改时间：2016/6/16 18:06
 * 修改备注：
 */
public class SilkLine extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;

    private boolean isRun;
    private Canvas mCanvas;
    private float X, Y;
    private WeaveSilk mWeaveSilk;

    public SilkLine(Context context) {
        super(context);
        init();
    }

    public SilkLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        this.setFocusable(true);
        this.setDrawingCacheEnabled(true);
        this.setKeepScreenOn(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mWeaveSilk = new WeaveSilk();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
        drawBackground();
        new Thread(this).start();
    }

    /**
     * 画背景，黑色
     */
    private void drawBackground() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun=false;
    }

    @Override
    public void run() {
        long startTimps = 0l;
        while (isRun) {
            startTimps = System.currentTimeMillis();
            try {
                mCanvas = mHolder.lockCanvas(null);
                if (mCanvas != null) {
                    synchronized (mHolder) {
                        // 清屏
//                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        //画线
                        mWeaveSilk.newSilkPoint(mCanvas, X, Y);
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
                        mWeaveSilk.setIsLock(false);
                        X = event.getX();
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        mWeaveSilk.setIsLock(true);
                        break;
                    default:
                        break;
                }
                break;
        }
        return true;
    }
}
