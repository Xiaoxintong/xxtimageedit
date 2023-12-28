package xxt.kareluo.imaging.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by felix on 2017/12/1 下午3:07.
 */

public class XXTIMGColorGroup extends RadioGroup {

    public XXTIMGColorGroup(Context context) {
        super(context);
    }

    public XXTIMGColorGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getCheckColor() {
        int checkedId = getCheckedRadioButtonId();
        XXTIMGColorRadio radio = findViewById(checkedId);
        if (radio != null) {
            return radio.getColor();
        }
        return Color.RED;
    }

    public void setCheckColor(int color) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            XXTIMGColorRadio radio = (XXTIMGColorRadio) getChildAt(i);
            if (radio.getColor() == color) {
                radio.setChecked(true);
                break;
            }
        }
    }
}
