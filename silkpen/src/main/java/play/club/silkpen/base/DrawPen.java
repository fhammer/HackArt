package play.club.silkpen.base;

/**
 * 项目名称：HackArt
 * 类描述：绘制view基本的通用方法
 * 创建人：fuzh2
 * 创建时间：2016/7/1 11:15
 * 修改人：fuzh2
 * 修改时间：2016/7/1 11:15
 * 修改备注：
 */
public interface DrawPen {

    void clearPath();

    void setPaintWidth(float width);

    void setRadiuoWidth(int width);

    void setPaintColor(int color);

    void setBgColor(int pBgColor);

    void savePic();

    int getLineWidth();

    int getRaduioWidth();
}
