package play.club.hackart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import play.club.artpen.dialog.ColorPickerDialog;
import play.club.artpen.dialog.PaintChooseDialog;
import play.club.silkpen.base.DrawPen;
import play.club.silkpen.ui.PaintDialog;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
public class RandomSilkFragment extends Fragment implements PageAction {

    private View mInflate;
    private DrawPen mSilv_silk;
    private int mPaintColor = Color.RED;
    private int mBgColor = Color.BLACK;

    private PaintChooseDialog mPaintChooseDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflate = inflater.inflate(play.club.silkpen.R.layout.silk_random, null);
        initView(mInflate);
        return mInflate;
    }

    private void initView(View pInflate) {
        mSilv_silk = (DrawPen) pInflate.findViewById(R.id.slv_silk);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onColorPikerDialog() {
        String colorPickerTitle = getResources().getString(
                play.club.artpen.R.string.color_pick_title);
        new ColorPickerDialog(getActivity(), mPaintColor, colorPickerTitle,
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        // TODO Auto-generated method stub
                        mBgColor = color;
                        mSilv_silk.setBgColor(mBgColor);
                    }
                }).show();
    }

    @Override
    public void setSilkLineColor() {
        String colorPickerTitle = getResources().getString(
                play.club.artpen.R.string.color_pick_title);
        new ColorPickerDialog(getActivity(), mPaintColor, colorPickerTitle,
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        // TODO Auto-generated method stub
                        mPaintColor = color;
                        mSilv_silk.setPaintColor(mPaintColor);
                    }
                }).show();
    }


    @Override
    public void setSIlkLineWIdth() {
        showWidthDialog();
    }

    @Override
    public void setSilkRadius() {
        showWidthDialog();
    }

    private void showWidthDialog() {
        final PaintDialog dialog = new PaintDialog(getActivity(), mSilv_silk.getRaduioWidth(), mSilv_silk.getLineWidth());
        dialog.setmChoosePaintListener(new PaintDialog.ChoosePaintListener() {
            @Override
            public void ok(int radiuSize, int paintWidth) {
                mSilv_silk.setPaintWidth(paintWidth);
                mSilv_silk.setRadiuoWidth(radiuSize);
            }

            @Override
            public void cancel() {
            }
        });
        dialog.show();
    }

    @Override
    public void savePic() {
        mSilv_silk.savePic();
    }

    @Override
    public void clear() {
        mSilv_silk.clearPath();
    }

}
