package play.club.silkpen.entiy;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/16 18:18
 * 修改人：fuzh2
 * 修改时间：2016/6/16 18:18
 * 修改备注：
 */
public class WeaveSilk {

    private double fadeStep = 6;
    private double headRandomStep = 15;
    private float windX = 6;
    private float windY = 0.6f;

    private Random mRandom = new Random();

    private List<Point> mPoints;
    // 外侧阴影大小
    private static final float BLUR_SIZE = 5.0F;
    private boolean isLock = true;
    private Path mPath;

    private Paint mPaint;

    public WeaveSilk() {
        mPath = new Path();
        mPoints = new ArrayList<>();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
    }

    public boolean isLock() {
        return isLock;
    }

    public void setIsLock(boolean pIsLock) {
        isLock = pIsLock;
    }

    public void newSilkPoint(Canvas canvas, float pX, float pY) {
        if (!isLock) {
            mPoints.add(new Point(pX, pY));
        } else {
            newSilkLine();
            if (mPoints.size() > 12)
                mPaint.setColor(Color.argb(255, mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128));
            canvas.drawPath(mPath, mPaint);
        }
    }

    /**
     * 生成新的丝线
     */
    private void newSilkLine() {
        for (int i = mPoints.size() - 1; i >= 0; i--) {
            if (i < 2) {
                //The first two dots will fly much stronger.
                mPoints.get(i).x += windX * 1.05 + (1 - 2 * Math.random()) * headRandomStep;
                mPoints.get(i).y += windY * 1.05 + (1 - 2 * Math.random()) * headRandomStep;
            } else {
                //other dots will fly slightly.
                mPoints.get(i).x += windX + (1 - 2 * Math.random()) * fadeStep;
                mPoints.get(i).y += windY + (1 - 2 * Math.random()) * fadeStep;
            }
            if (i > 0) {
                //Except the first dot, all other dot will slightly follow their previous dots, the silk will shrink because of this.
                mPoints.get(i).x += (mPoints.get(i - 1).x - mPoints.get(i).x) / 7;
                mPoints.get(i).y += (mPoints.get(i - 1).y - mPoints.get(i).y) / 7;
            }

            if (i % 11 == 0)
                mPoints.remove(i);
        }

        if (mPoints.size() < 11)
            return;
//        mPath.reset();
        for (int j = 0; j < mPoints.size(); j++) {
            if (j == 0) {
                mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
            } else {
                mPath.lineTo(mPoints.get(j).x, mPoints.get(j).y);
            }
        }
    }
}
