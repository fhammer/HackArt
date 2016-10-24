package play.club.hackart;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/1 20:46
 * 修改人：fuzh2
 * 修改时间：2016/7/1 20:46
 * 修改备注：
 */
public interface PageAction {
    void onColorPikerDialog();//设置背景

    void setSilkLineColor();

    void setSIlkLineWIdth();

    void setSilkRadius();

    void savePic();

    void clear();
}
