package play.club.hackart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import play.club.garden.view.ArtView;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/5 14:38
 * 修改人：fuzh2
 * 修改时间：2016/7/5 14:38
 * 修改备注：
 */
public class GardenFragment extends Fragment implements PageAction {
    private View mInflate;
    private ArtView mArtView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflate = inflater.inflate(play.club.garden.R.layout.layout_garden, null);
        mArtView = (ArtView) mInflate.findViewById(R.id.art_view);
        return mInflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onColorPikerDialog() {

    }

    @Override
    public void setSilkLineColor() {

    }

    @Override
    public void setSIlkLineWIdth() {

    }

    @Override
    public void setSilkRadius() {

    }

    @Override
    public void savePic() {

    }

    @Override
    public void clear() {
        mArtView.clear();
    }
}
