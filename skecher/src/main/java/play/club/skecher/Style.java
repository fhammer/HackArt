package play.club.skecher;

import android.graphics.Canvas;

import java.util.HashMap;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
public interface Style {
    /**
     * 收集手指按下的位置坐标
     *
     * @param x
     * @param y
     */
    void strokeStart(float x, float y);

    /**
     * 手指不断滑动的坐标
     *
     * @param c
     * @param x
     * @param y
     */
    void stroke(Canvas c, float x, float y);

    /**
     * 在指定的画布上绘制
     *
     * @param c
     */
    void draw(Canvas c);

    /**
     * 设置颜色
     *
     * @param color
     */
    void setColor(int color);

    /**
     * 保存当前的状态
     *
     * @param state
     */
    void saveState(HashMap<Integer, Object> state);

    /**
     * 合并状态
     *
     * @param state
     */
    void restoreState(HashMap<Integer, Object> state);

    /**
     * 设置颜色渐变的范围，在需要颜色渐变的图形上调用
     *
     * @param startColor 开始颜色
     * @param endColor   结束颜色
     */
    void setColorBrige(String startColor, String endColor);
}
