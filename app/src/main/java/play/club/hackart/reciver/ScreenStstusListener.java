package play.club.hackart.reciver;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/6 17:19
 * 修改人：fuzh2
 * 修改时间：2016/7/6 17:19
 * 修改备注：
 */
public class ScreenStstusListener implements ScreenStatuManager.ScreenStateListener {

    private Context mContext;

    public ScreenStstusListener(Context pContext) {
        this.mContext = pContext;
    }

    @Override
    public void onScreenOn() {
        Log.d("Screen", "onScreenOn");
        Toast.makeText(mContext,"onScreenOn",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScreenOff() {
        Log.d("Screen", "onScreenOff");
        Toast.makeText(mContext,"onScreenOff",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserPresent() {
        Log.d("Screen", "onUserPresent");
        Toast.makeText(mContext,"onUserPresent",Toast.LENGTH_LONG).show();
    }
}
