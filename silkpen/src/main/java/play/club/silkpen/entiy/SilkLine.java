package play.club.silkpen.entiy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/16 13:52
 * 修改人：fuzh2
 * 修改时间：2016/6/16 13:52
 * 修改备注：
 */
public class SilkLine {

    private List<Point> mPoints = new ArrayList<>();
    private Paint mPaint;
    private int color = Color.GREEN;
    private double fadeStep = 6;
    private double headRandomStep = 15;
    private float windX = 6;
    private float windY = 0.6f;

    private boolean isCanDraw = false;

    private Path mPath = new Path();

    public SilkLine() {
        mPaint = new Paint();
        mPaint.setColor(color);
        // 给画笔设置样式
        mPaint.setStyle(Paint.Style.STROKE);// 笔触
        // 设置线条的宽度
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPath.reset();
    }

    public void drawSilkLine(Canvas canvas) {
        if (mPoints.size() > 2 && isCanDraw) {
            canvas.save();
            for (int i = 0; i < 50; i++) {
                newSilkLine();
                canvas.drawPath(mPath, mPaint);
            }
            canvas.restore();
            clearPoints();
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
        }

        mPath.reset();
        for (int j = 0; j < mPoints.size(); j++) {
            if (j == 0) {
                mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
            } else {
                mPath.lineTo(mPoints.get(j).x, mPoints.get(j).y);
            }
        }
    }

    public void clearPoints() {
        if (mPoints != null) {
            mPoints.clear();
        }
        isCanDraw = false;
    }

    public void addPoint(Point p) {
        if (mPoints != null)
            mPoints.add(p);
    }

    public void setPaintColor(int color) {
        if (mPaint != null)
            mPaint.setColor(color);
    }

    public void setCanDraw(boolean pIsCanDraw) {
        this.isCanDraw = pIsCanDraw;
    }

}
