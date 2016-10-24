package play.club.skecher;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import play.club.skecher.Surface;
import play.club.skecher.style.StylesFactory;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/8 20:22
 * 修改人：fuzh2
 * 修改时间：2016/7/8 20:22
 * 修改备注：
 */
public class SkecherFragment extends Fragment {
    private View mInflate;
    private Surface mSf_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflate = inflater.inflate(play.club.skecher.R.layout.layout_skecher, null);
        initView();
        return mInflate;
    }

    private void initView() {
        mSf_view = (Surface) mInflate.findViewById(play.club.skecher.R.id.sf_view);
        mSf_view.setStyle(StylesFactory.getStyle(StylesFactory.RIBBON));
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        mSf_view.setPaintColor(paint);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public Surface getSurface() {
        return this.mSf_view;
    }
}
