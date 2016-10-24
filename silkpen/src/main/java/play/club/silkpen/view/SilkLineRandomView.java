package play.club.silkpen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import play.club.silkpen.base.DrawPen;
import play.club.silkpen.entiy.FinalLine;
import play.club.silkpen.utils.BitmapUtils;
import play.club.silkpen.utils.DensityUtils;

/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/16 18:06
 * 修改人：fuzh2
 * 修改时间：2016/6/16 18:06
 * 修改备注：
 */
public class SilkLineRandomView extends SurfaceView
        implements SurfaceHolder.Callback, Runnable, DrawPen {

    private final int RADIUO_MOULT = 2;
    private final float LINE_MOULT = 10.0f;

    private SurfaceHolder mHolder;

    private boolean isRun;
    private Canvas mCanvas;
    private float X, Y;
    private FinalLine mWeaveSilk;

    private int width;
    private int height;
    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;
    private Context mContext;

    public SilkLineRandomView(Context context) {
        super(context);
        init(context);
    }

    public SilkLineRandomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context pContext) {
        this.mContext = pContext;
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
//        this.setDrawingCacheEnabled(true);
        this.setKeepScreenOn(true);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mWeaveSilk = new FinalLine();

        height = DensityUtils.getScreenHeight(pContext);
        width = DensityUtils.getScreenWidth(pContext);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
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
        drawBackground();
        initBitmap();
    }

    private void initBitmap() {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        mBitmapCanvas = new Canvas(mBitmap);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
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
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        //画线
                        mWeaveSilk.drawSilk(mBitmapCanvas);
                        mCanvas.drawBitmap(mBitmap, 0, 0, null);
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
        X = event.getX();
        Y = event.getY();
        switch (event.getPointerCount()) {
            // 单点触摸
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mWeaveSilk.actionDown(X, Y);
                    case MotionEvent.ACTION_MOVE:
                        mWeaveSilk.actionMove(X, Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                break;
        }
        return true;
    }

    /**
     * 设置背景颜色
     *
     * @param pBgColor
     */
    public void setBgColor(int pBgColor) {
        mWeaveSilk.setBgColor(pBgColor);
    }

    /**
     * 设置新的颜色
     *
     * @param color
     */
    public void setPaintColor(int color) {
        mWeaveSilk.setPaintColor(color);
    }

    /**
     * 设置画笔的宽度
     *
     * @param width
     */
    public void setPaintWidth(float width) {
        mWeaveSilk.setPaintWidth(width / LINE_MOULT);
    }

    @Override
    public void setRadiuoWidth(int width) {
        mWeaveSilk.setRadiuo(width * RADIUO_MOULT);
    }

    /**
     * 清屏
     */
    public void clearPath() {
        mWeaveSilk.clearPath();
    }

    public void savePic() {
        BitmapUtils.saveBitmapInExternalStorage(mBitmap, mContext);
    }

    public int getLineWidth() {
        return (int) (mWeaveSilk.getPaintWidth() * LINE_MOULT);
    }

    public int getRaduioWidth() {
        return mWeaveSilk.getRadiuo() / RADIUO_MOULT;
    }

}
