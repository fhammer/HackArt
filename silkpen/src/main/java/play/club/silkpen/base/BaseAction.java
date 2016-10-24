package play.club.silkpen.base;

import android.graphics.Canvas;

/**
 * 项目名称：HackArt
 * 类描述：绘制实体，基本的约束方法
 * 创建人：fuzh2
 * 创建时间：2016/7/1 11:19
 * 修改人：fuzh2
 * 修改时间：2016/7/1 11:19
 * 修改备注：
 */
public interface BaseAction extends DrawPen {
    void drawSilk(Canvas canvas);

    void actionDown(float x, float y);

    void actionMove(float x, float y);

    void actionUp();
}
