package play.club.silkpen.entiy;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.Random;
import java.util.Vector;

/**
 * 项目名称：ArtPen
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/20 9:37
 * 修改人：fuzh2
 * 修改时间：2016/6/20 9:37
 * 修改备注：
 */
public class ArtLine {

    private static final float BLUR_SIZE = 5.0F;

    //
    private float posX = 0;
    private float posY = 400;

    //generate a random point or color
    private Random mRandom;

    private Point startPoint, endPoint, c1Point, c2Point, endPoint2;

    private Paint mPaint;

    private Path mPath;

    private boolean isActive = false;

    private Vector<Point> mPoints;

    private int[][] sparks = new int[400][10];
    private Spark mSpark;

    public ArtLine() {
        initPaint();
        mRandom = new Random();
        mPath = new Path();
        startPoint = new Point();
        endPoint = new Point();
        c1Point = new Point();
        c2Point = new Point();
        endPoint2 = new Point();

        mPoints = new Vector<>(10);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(0.5f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(128);
        mPaint.setDither(true);
//        mPaint.setColor(Color.RED);
        mPaint.setARGB(250, 255, 45, 45);
        mPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
//        Shader mShader = new LinearGradient(0, 0, 100, 100,
//                new int[]{0xFF0000, 0xFF4444, 0xFF6666, 0xFFAAAA,
//                        0xFFCCCC}, null, Shader.TileMode.REPEAT);
//        mPaint.setShader(mShader);
//        mPaint.setARGB(128, 128, 128, 128);
        mSpark = new Spark(1080, 1920);
    }

    public void newArtLine(Canvas canvas, float x, float y) {
        if (isActive) {
            drawline(canvas, x, y);
        } else {
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void random_2(Canvas canvas, float x, float y) {
        mPoints.add(new Point(Math.round(x), Math.round(y)));
        if (mPoints.size() > 3) {
            startPoint = mPoints.get(mRandom.nextInt(mPoints.size()));
            endPoint = mPoints.get(mPoints.size() - 1);
            c1Point = mPoints.get(mRandom.nextInt(mPoints.size()));
            c2Point = mPoints.get(mRandom.nextInt(mPoints.size()));

            for (int i = 0; i < 50; i = i + 2) {
                int dx = (int) Math.round(Math.sin(Math.toRadians(i)) * 10 + startPoint.x);
                int dy = (int) Math.round(Math.cos(Math.toRadians(i)) * 10 + startPoint.y);
                Log.d("Point", "dx=" + dx + "  dy=" + dy);
                startPoint.set(dx, dy);
                mPath.moveTo(startPoint.x, startPoint.y);
                mPath.cubicTo(c1Point.x, c1Point.y, c2Point.x, c2Point.y, endPoint.x, endPoint.y);
                mPaint.setARGB(128 + mRandom.nextInt(128), mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128);
                canvas.drawPath(mPath, mPaint);
            }
        }
    }

    private void drawStaticPoint(Canvas canvas, float x, float y) {
        startPoint.set(Math.round(x), Math.round(y) + 5);
        endPoint = getRandomPoint(startPoint.x, startPoint.y, mRandom.nextInt(400));
        c1Point = getRandomPoint(startPoint.x, startPoint.y, mRandom.nextInt(1080 / 16));
        c2Point = getRandomPoint(endPoint.x, endPoint.y, mRandom.nextInt(1080 / 16));

        for (int i = 0; i < 50; i = i + 5) {
            int dx = (int) Math.round(Math.sin(Math.toRadians(i)) * 20 + x);
            int dy = (int) Math.round(Math.cos(Math.toRadians(i)) * 20 + y);
            Log.d("Point", "dx=" + dx + "  dy=" + dy);
            startPoint.set(dx, dy);
            mPath.moveTo(startPoint.x, startPoint.y);
            mPath.cubicTo(c1Point.x, c1Point.y, c2Point.x, c2Point.y, endPoint.x, endPoint.y);
        }
        canvas.drawPath(mPath, mPaint);
    }

    private void drawStar(Canvas pCanvas, int x, int y) {
        // 循环绘制所有火花
        for (int[] n : sparks) {
            n = mSpark.drawSpark(pCanvas, x, y, n);
        }
    }

    private void drawline(Canvas canvas, float x, float y) {
        startPoint.set(Math.round(x), Math.round(y) + 5);
        endPoint = getRandomPoint(startPoint.x, startPoint.y, 300);
        c1Point = getRandomPoint(startPoint.x, startPoint.y, 160);
        c2Point = getRandomPoint(endPoint.x, endPoint.y, 160);
        for (int i = 0; i < 20; i++) {
            mPath.moveTo(startPoint.x, startPoint.y);
            mPath.cubicTo(c1Point.x, c1Point.y, c2Point.x, c2Point.y, endPoint.x, endPoint.y);
            c2Point.set(c2Point.x - 3, c2Point.y);
            c1Point.set(c2Point.x, c2Point.y - 2);
        }

//        drawStar(canvas,(int)x,(int)y);
        canvas.drawPath(mPath, mPaint);
    }

    private void movePoint(int px) {
        if (Math.abs(endPoint.x - c2Point.x) > px) {
            int newY = (endPoint.x - px) / (endPoint.x - c2Point.x) * (endPoint.y - c2Point.y);
            c2Point.set(c2Point.x - px, newY);
        }
    }

    /**
     * 判断当前点是不是和上一个点相聚较远
     * 相距5个像素点时认为触点移动了
     *
     * @param pX
     * @param pY
     * @return
     */
    private boolean isPointChange(float pX, float pY) {

        float xDur = Math.abs(this.posX - pX);
        float yDur = Math.abs(this.posY - pY);

        if (xDur > 5 || yDur > 5)
            return true;
        else
            return false;
    }

    private void random_1(float x, float y) {
        mPath.reset();
        startPoint = new Point(Math.round(x), Math.round(y));
        for (int i = 0; i < 320; i++) {
            endPoint = getRandomPoint(startPoint.x, startPoint.y, 400);
            c1Point = getRandomPoint(startPoint.x, startPoint.y, mRandom.nextInt(1080 / 16));
            c2Point = getRandomPoint(endPoint.x, endPoint.y, mRandom.nextInt(1080 / 16));

            mPath.moveTo(startPoint.x, startPoint.y);
            mPath.cubicTo(c1Point.x, c1Point.y, c2Point.x, c2Point.y, endPoint.x, endPoint.y);
        }
    }

    /**
     * 根据基准点获取指定范围为半径的随机点
     */
    private Point getRandomPoint(int baseX, int baseY, int r) {
        if (r <= 0) {
            r = 1;
        }
        int x = mRandom.nextInt(r);
        int y = (int) Math.sqrt(r * r - x * x);

        x = baseX + getRandomPNValue(x);
        y = baseY + getRandomPNValue(y);

        return new Point(x, y);
    }

    /**
     * 根据range范围，和chance几率。返回一个随机值
     */
    private int getRandom(int range, int chance) {
        int num = 0;
        switch (chance) {
            case 0:
                num = mRandom.nextInt(range);
                break;
            default:
                num = mRandom.nextInt(range / 4);
                break;
        }

        return num;
    }

    /**
     * 获取随机正负数
     */
    private int getRandomPNValue(int value) {
        return mRandom.nextBoolean() ? value : 0 - value;
    }

    /**
     * 计算塞贝儿曲线
     *
     * @param t  时间，范围0-1
     * @param s  起始点
     * @param c1 拐点1
     * @param c2 拐点2
     * @param e  终点
     * @return 塞贝儿曲线在当前时间下的点
     */
    private Point CalculateBezierPoint(float t, Point s, Point c1, Point c2, Point e) {
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        Point p = new Point((int) (s.x * uuu), (int) (s.y * uuu));
        p.x += 3 * uu * t * c1.x;
        p.y += 3 * uu * t * c1.y;
        p.x += 3 * u * tt * c2.x;
        p.y += 3 * u * tt * c2.y;
        p.x += ttt * e.x;
        p.y += ttt * e.y;

        return p;
    }

    public void setIsActive(boolean pIsActive) {
        this.isActive = pIsActive;

        mSpark.isActive = pIsActive;
    }

//    private int gradeColor() {
//        int oldColor = Color.parseColor("#bb0025");
//        int newColor = Color.parseColor("#FFEEF1");
//        int step = 20; // 分多少步完成
//        for (int i = 1; i <= step; i++) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            int r = Color.red(oldColor) + (Color.red(newColor) - Color.red(oldColor)) * i / step;
//            int g = Color.green(oldColor) + (Color.green(newColor) - Color.green(oldColor)) * i / step;
//            int b = Color.blue(oldColor) + (Color.blue(newColor) - Color.blue(oldColor)) * i / step;
//
//        }
//        return r + g + b;
//    }

}
