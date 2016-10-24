package play.club.garden.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Vector;

import play.club.garden.modle.Bloom;
import play.club.garden.modle.Garden;
import play.club.garden.utils.DensityUtils;


/**
 * 项目名称：TestHeart
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/27 14:56
 * 修改人：fuzh2
 * 修改时间：2016/6/27 14:56
 * 修改备注：
 */
public class ArtView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Garden mGarden;
    private Paint mBgPaint;

    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;

    private int width;
    private int height;

    private boolean isLoop = false;

    private Vector<Bloom> mBlooms = new Vector<>();
    private Canvas mCanvas;

    public ArtView(Context context) {
        super(context);
        init(context);
    }

    public ArtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context pContext) {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mGarden = new Garden();
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.rgb(0xff, 0xff, 0xe0));
        setKeepScreenOn(true);
        height = DensityUtils.getScreenHeight(pContext);
        width = DensityUtils.getScreenWidth(pContext);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initBitmap();
        isLoop = true;
        new Thread(this).start();
    }

    private void initBitmap() {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isLoop = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int) event.getX();
        int rawY = (int) event.getY();

        switch (event.getPointerCount()) {
            case 1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mBlooms.clear();
//                        mBlooms.add(getBloom(rawX,rawY));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mBlooms.add(getBloom(rawX, rawY));
                        break;
                    case MotionEvent.ACTION_UP:
                        mBlooms.add(getBloom(rawX, rawY));
                        break;
                }
                break;
        }
        return true;
    }

    private Bloom getBloom(int x, int y) {
        return mGarden.createRandomBloom(x, y);
    }

    @Override
    public void run() {
        while (isLoop) {
            try {
                mCanvas = mHolder.lockCanvas();
//                mCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
                for (Bloom b : mBlooms) {
                    b.draw(mBitmapCanvas);
                }
                mCanvas.drawBitmap(mBitmap, 0, 0, null);
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null)
                    mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void clear() {
        mBlooms.clear();
        mBlooms.removeAllElements();
        mBlooms = new Vector<>();
        mBitmapCanvas.drawColor(Color.BLACK);
    }
}
