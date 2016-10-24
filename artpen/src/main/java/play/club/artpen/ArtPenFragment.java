package play.club.artpen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import play.club.artpen.dialog.ColorPickerDialog;
import play.club.artpen.dialog.PaintChooseDialog;
import play.club.artpen.util.PaintFactory;
import play.club.artpen.view.DrawView;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 15:25
 * 修改人：fuzh2
 * 修改时间：2016/6/30 15:25
 * 修改备注：
 */
public class ArtPenFragment extends Fragment implements View.OnClickListener {

    private View mInflate;

    private PaintFactory mPaintFactory;
    private List mPaintInfoList = null;

    private ImageView newBtn;
    private ImageView saveBtn;
    private ImageView paintBtn;
    private ImageView colorBtn;
    private ImageView undoBtn;
    private ImageView redoBtn;

    private LinearLayout mCanvasLayout; // 包含画布的linearlayout

    private DrawView mDrawView;
    private LinearLayout.LayoutParams mLayoutParams;

    private PaintChooseDialog mPaintChooseDialog;

    private int mPaintColor = Color.BLACK; //画笔默认颜色
    private int mPaintId = 1; //画笔ID，默认为曲线
    private int mPaintSize = 30; //画笔大小，默认为30

    private boolean hasMeasured = false;
    public static int mLayoutWidth; //linearLayout宽
    public static int mLayoutHeight; //linearLayout高

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.activity_artpen_main, null, false);
        return mInflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPaintFactory = PaintFactory.getInstance();

        mPaintInfoList = mPaintFactory.getPaintInfoList();

        initWdget();

        addWdgetToOnClickListener();

        obtainViewSize();
    }


    private void initWdget() {
        newBtn = (ImageView) mInflate.findViewById(R.id.new_btn);
        saveBtn = (ImageView) mInflate.findViewById(R.id.save_btn);
        paintBtn = (ImageView) mInflate.findViewById(R.id.paint_btn);
        colorBtn = (ImageView) mInflate.findViewById(R.id.color_btn);
        undoBtn = (ImageView) mInflate.findViewById(R.id.undo_btn);
        redoBtn = (ImageView) mInflate.findViewById(R.id.redo_btn);

        mCanvasLayout = (LinearLayout) mInflate.findViewById(R.id.canvas_layout);

        mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void addWdgetToOnClickListener() {
        newBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        paintBtn.setOnClickListener(this);
        colorBtn.setOnClickListener(this);
        undoBtn.setOnClickListener(this);
        redoBtn.setOnClickListener(this);
    }

    private void initCanvasView(int width, int height) {
        mDrawView = mPaintFactory.createPaint(getActivity(),
                mPaintId, mLayoutWidth, mLayoutHeight, mPaintSize, mPaintColor);
        mDrawView.setPaint(mPaintSize, mPaintColor);

        mDrawView.setMinimumWidth(width);
        mDrawView.setMinimumHeight(height);

        mCanvasLayout.addView(mDrawView, mLayoutParams);
    }


    // 获取LinearLayout的宽和高
    private void obtainViewSize() {
        ViewTreeObserver vto = mCanvasLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {

                    mLayoutWidth = mCanvasLayout.getMeasuredWidth();
                    mLayoutHeight = mCanvasLayout.getMeasuredHeight();

                    hasMeasured = true;
                    initCanvasView(mLayoutWidth, mLayoutHeight);
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int viewId = v.getId();
        if (viewId == R.id.new_btn) {
            mDrawView.flushCanvas();
        } else if (viewId == R.id.save_btn) {
            mDrawView.savePic();
        } else if (viewId == R.id.paint_btn) {
            chooseColor();
        } else if (viewId == R.id.color_btn) {
            onColorPikerDialog();
        } else if (viewId == R.id.undo_btn) {
            mDrawView.undo();
        } else if (viewId == R.id.redo_btn) {
            mDrawView.redo();
        } else {

        }
    }

    private void chooseColor() {
        mPaintChooseDialog = new PaintChooseDialog(getActivity(), mPaintInfoList, mPaintId, mPaintSize);
        mPaintChooseDialog.show();
        mPaintChooseDialog.setmChoosePaintListener(new PaintChooseDialog.ChoosePaintListener() {

            @Override
            public void ok(int paintId, int size) {
                // TODO Auto-generated method stub
                if (paintId != mPaintId) {
                    mPaintId = paintId;
                    Bitmap bitmap = mDrawView.getmBitmap();
                    mCanvasLayout.removeAllViews();
                    mDrawView = null;
                    mDrawView = mPaintFactory.createPaint(getActivity(),
                            mPaintId, mLayoutWidth, mLayoutHeight, mPaintSize, mPaintColor);

                    mDrawView.undo();
                    mDrawView.redo();
                    mCanvasLayout.addView(mDrawView, mLayoutParams);
                    mDrawView.invalidate();
                }
                mPaintSize = size;
                mDrawView.setPaintSize(mPaintSize);
            }

            @Override
            public void cancel() {
                // TODO Auto-generated method stub
            }
        });
    }

    private void onColorPikerDialog() {
        String colorPickerTitle = getResources().getString(
                R.string.color_pick_title);
        new ColorPickerDialog(getActivity(), mPaintColor, colorPickerTitle,
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        // TODO Auto-generated method stub
                        mPaintColor = color;
                        mDrawView.setPaintColor(mPaintColor);
                    }
                }).show();
    }

}
