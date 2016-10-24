package play.club.silkpen.entiy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/1 9:10
 * 修改人：fuzh2
 * 修改时间：2016/7/1 9:10
 * 修改备注：
 */
public class FinalLine {
    private final int FINAL_LINE_RADIUO = 100;

    private Vector<Point> mPoints;//保存所有的数据点

    private Path mPath;//当前设置色值的的绘制路径

    private Paint mPaint;
    private int foregroudColor = Color.RED;//前景色
    private int bgColor = Color.WHITE;//背景色
    private float paintWidth = 0.5f;//画笔宽度发
    private int radiuo = FINAL_LINE_RADIUO;

    private ArrayList<Path> mPaths = new ArrayList<>();//历史路径
    private ArrayList<Integer> colors = new ArrayList<>();//历史路径对应的颜色
    private ArrayList<Float> lineWidths = new ArrayList<>();//历史路径对应的坏宽度


    public FinalLine() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(foregroudColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
        mPath = new Path();
        mPoints = new Vector<>();
    }

    /**
     * 绘制背景和路线
     *
     * @param canvas
     */
    public void drawSilk(Canvas canvas) {
        canvas.drawColor(bgColor);//画背景
        for (int i = 0; i < mPaths.size(); i++) {
            mPaint.setColor(colors.get(i));
            mPaint.setStrokeWidth(lineWidths.get(i));
            canvas.drawPath(mPaths.get(i), mPaint);
        }
        mPaint.setColor(foregroudColor);
        mPaint.setStrokeWidth(paintWidth);
        canvas.drawPath(mPath, mPaint);//画路径
    }

    /**
     * 手指按下的时候
     *
     * @param x
     * @param y
     */
    public void actionDown(float x, float y) {
        mPath.moveTo(x, y);
        newLine(x, y);
    }

    /**
     * 手指移动
     *
     * @param x
     * @param y
     */
    public void actionMove(float x, float y) {
        mPath.lineTo(x, y);
        newLine(x, y);
    }

    /**
     * 手指抬起
     */
    public void actionUp() {

    }

    /**
     * 连接半径在指定范围的所有点
     *
     * @param x
     * @param y
     */
    private void newLine(float x, float y) {
        if (mPoints.size() > 0) {
            for (Point p : mPoints) {
                double r = Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
                if ((r <= radiuo)) {
                    mPath.moveTo(x, y);
                    mPath.lineTo(p.x, p.y);
                }
            }
        } else {
        }
        mPath.moveTo(x, y);//移动到最新点，防止错误交叉
        mPoints.add(new Point(x, y));
    }

    /**
     * 设置背景颜色
     *
     * @param pBgColor
     */
    public void setBgColor(int pBgColor) {
        this.bgColor = pBgColor;
    }

    /**
     * 设置新的颜色
     *
     * @param color
     */
    public void setPaintColor(int color) {
        savePreStats();
        this.foregroudColor = color;
    }

    /**
     * 保存之前的绘制状态
     */
    private void savePreStats() {
        mPaths.add(mPath);
        Float flw = Float.parseFloat(paintWidth + "");
        lineWidths.add(flw);
        Integer integer = Integer.parseInt(foregroudColor + "");
        colors.add(integer);
        mPath = new Path();
    }

    /**
     * 设置画笔的宽度
     *
     * @param width
     */
    public void setPaintWidth(float width) {
        savePreStats();
        this.paintWidth = width;
    }

    /**
     * 清屏
     */
    public void clearPath() {
        mPoints.clear();
        mPaths.clear();
        colors.clear();
        lineWidths.clear();
        mPath.reset();
    }

    public int getRadiuo() {
        return radiuo;
    }

    public void setRadiuo(int pRadiuo) {
        radiuo = pRadiuo;
    }

    public float getPaintWidth() {
        return paintWidth;
    }

    public int getBgColor() {
        return bgColor;
    }

    public int getForegroudColor() {
        return foregroudColor;
    }

    public void setForegroudColor(int pForegroudColor) {
        foregroudColor = pForegroudColor;
    }

}
