package play.club.silkpen.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.List;

import play.club.silkpen.R;


public class PaintDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private ListView mListView;

    private SeekBar mSbRadiu; //滑块选择画笔大小
    private SeekBar mSbLineWidth; //滑块选择画笔大小
    private TextView mRadiuSizeText; //显示滑块选择的画笔的大小
    private TextView mWidthSizeText; //显示滑块选择的画笔的大小

    private Button mBtnOk; //确定按钮
    private Button mBtnCancel; //取消按钮

    private int mRadiuSize; //选择画笔大小
    private int mLineSize; //选择画笔大小

    private ChoosePaintListener mChoosePaintListener; //选择之后回调监听


    public PaintDialog(Context context, int radoiSize, int pLineSize) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mRadiuSize = radoiSize;
        this.mLineSize = pLineSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_paint_width);
        dialogShowStyle();
        initView();
        seekBarOnChange();

    }

    public void dialogShowStyle() {
//        Window dialogWindow = getWindow();
//        Display display = dialogWindow.getWindowManager().getDefaultDisplay();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = display.getWidth();
//        lp.height = display.getHeight() - 200;
//        lp.x = 0;
//        lp.y = 0;
//        dialogWindow.setAttributes(lp);
    }

    private void initView() {
        mSbRadiu = (SeekBar) findViewById(R.id.sb_radiu);
        mSbLineWidth = (SeekBar) findViewById(R.id.sb_paintwidth);
        mRadiuSizeText = (TextView) findViewById(R.id.tv_radiu);
        mWidthSizeText = (TextView) findViewById(R.id.tv_paintwidth);
        mSbRadiu.setMax(100);
        mSbLineWidth.setMax(100);
        mSbRadiu.setProgress(mRadiuSize);
        mSbLineWidth.setProgress(mLineSize);
        mRadiuSizeText.setText(String.valueOf(mRadiuSize));
        mWidthSizeText.setText(String.valueOf(mLineSize));

        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnCancel = (Button) findViewById(R.id.btn_cancle);
        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    private void seekBarOnChange() {
        mSbRadiu.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mRadiuSize = progress;
                mRadiuSizeText.setText(String.valueOf(mRadiuSize));
            }
        });

        mSbLineWidth.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineSize = progress;
                mWidthSizeText.setText(String.valueOf(mLineSize));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.btn_ok) {
            mChoosePaintListener.ok(mRadiuSize, mLineSize);
        } else if (id == R.id.btn_cancle) {
            mChoosePaintListener.cancel();
        } else {

        }
        this.dismiss();
    }

    public void setmChoosePaintListener(ChoosePaintListener mChoosePaintListener) {
        this.mChoosePaintListener = mChoosePaintListener;
    }

    public interface ChoosePaintListener {
        void ok(int radiuSize, int paintWidth); //确定回调函数

        void cancel(); //取消回调函数
    }

}
