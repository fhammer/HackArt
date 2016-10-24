package play.club.skecher;

import android.graphics.Bitmap;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;

import play.club.skecher.style.StylesFactory;


/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/6/30 16:29
 * 修改人：fuzh2
 * 修改时间：2016/6/30 16:29
 * 修改备注：
 */
public class HistoryHelper {
	private final Surface mSurface;

	private State mUndoState = new State();
	private State mRedoState = new State();

	private boolean isSwaped = false;

	public HistoryHelper(Surface surface) {
		mSurface = surface;
	}

	public void undo() {
		if (mRedoState.mBuffer == null || mUndoState.mBuffer == null) {
			return;
		}
		restoreState(mSurface.getBitmap(), isSwaped ? mRedoState : mUndoState);
		isSwaped = !isSwaped;
	}

	private void restoreState(Bitmap bitmap, State state) {
		Buffer byteBuffer = ByteBuffer.wrap(state.mBuffer);
		bitmap.copyPixelsFromBuffer(byteBuffer);
		StylesFactory.restoreState(state.stylesState);
	}

	public void saveState() {
		saveState(mSurface.getBitmap(), isSwaped ? mRedoState : mUndoState);
		isSwaped = !isSwaped;
	}

	private void saveState(Bitmap bitmap, State state) {
		state.mBuffer = new byte[bitmap.getRowBytes() * bitmap.getHeight()];
		Buffer byteBuffer = ByteBuffer.wrap(state.mBuffer);
		bitmap.copyPixelsToBuffer(byteBuffer);
		StylesFactory.saveState(state.stylesState);
	}

	private static class State {
		byte[] mBuffer = null;
		final HashMap<Integer, Object> stylesState = new HashMap<Integer, Object>();
	}
}
