package play.club.silkpen.entiy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.Random;
import java.util.Vector;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 17:50
 * 修改人：fuzh2
 * 修改时间：2016/6/30 17:50
 * 修改备注：
 */
public class RandomLine {

    private final int LINE_SIZE = 2;
    private final int POINT_STEP = 20;
    private final float mLineWidth = 0.02f;
    private Path mOldPath;
    private Path mCurPath;
    private Vector<Point> mPoints;
    private Paint mPaint;
    private int apla = 128;
    private int step = 3;
    private int mColor = Color.RED;
    private Point prePoint;

    private Random mRandom;

    private float dx;
    private float dy;

    private final int MAX_DX = 10;
    private final int MAX_DY = 10;

    public RandomLine() {
        mCurPath = new Path();
        mOldPath = new Path();
        mPoints = new Vector<>();

        mPaint = new Paint();
        mPaint.setColor(mColor);
//        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mLineWidth);

        mRandom = new Random();
    }

    public void drawSilk(Canvas canvas) {
        canvas.drawPath(mOldPath, mPaint);
        Log.d("TAG", "++++++++++++++++++++=");
    }

    public void actionDown(float x, float y) {
        mPoints.clear();
        mPoints.add(new Point(x, y));
    }

    public void actionMove(float x, float y) {
        mPoints.add(new Point(x, y));
        if (mPoints.size() < 3) {
            return;
        } else {
            Point p = null;
            for (int i = 3; i < mPoints.size(); i += POINT_STEP) {
                p = mPoints.get(i);
                dx = p.x - x;
                dy = p.y - y;
                if (Math.abs(dx) > MAX_DX && Math.abs(dy) > MAX_DY) {
                    newLine(i, 10, x, y);
                } else {
                    int location = mRandom.nextInt(i);
                    mOldPath.moveTo(mPoints.get(location).x, mPoints.get(location).y);
                    location = mRandom.nextInt(i);
                    mOldPath.quadTo(mPoints.get(location).x, mPoints.get(location).y, x, y);
                }
            }
        }
    }


    private void newLine(int pos, float dv, float pX, float py) {
        for (int i = 0; i < LINE_SIZE; i++) {
            mCurPath = new Path();
            int location = mRandom.nextInt(pos);
            mCurPath.moveTo(mPoints.get(location).x, mPoints.get(location).y);

            location = mRandom.nextInt(pos);
            mCurPath.cubicTo(mPoints.get(location).x, mPoints.get(location).y,
                    pX, py, pX + getRandomPos(dv), py + getRandomPos(dv));

            mOldPath.addPath(mCurPath);
        }
    }

    private int getRandomPos(float dv) {
        if (dv > 0)
            return mRandom.nextInt(Math.round(dv));
        else
            return -mRandom.nextInt(Math.abs(Math.round(dv)));
    }

}
