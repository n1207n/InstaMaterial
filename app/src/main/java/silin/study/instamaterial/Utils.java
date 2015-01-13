package silin.study.instamaterial;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class Utils {
    private Context mContext;
    private WindowManager mWindowManager;

    public Utils(Context context) {
        mContext = context;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public Point getScreenSize() {
        Point point = new Point();

        mWindowManager.getDefaultDisplay().getSize(point);

        return point;
    }
}
