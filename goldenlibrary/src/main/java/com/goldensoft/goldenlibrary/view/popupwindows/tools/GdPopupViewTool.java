package com.goldensoft.goldenlibrary.view.popupwindows.tools;

import java.util.Locale;

/**
 * Created by golden on 2018/4/3.
 */

public class GdPopupViewTool {
    static boolean isRtl() {
        Locale defLocal = Locale.getDefault();
        return Character.getDirectionality(defLocal.getDisplayName(defLocal).charAt(0))
                == Character.DIRECTIONALITY_RIGHT_TO_LEFT;
    }
}
