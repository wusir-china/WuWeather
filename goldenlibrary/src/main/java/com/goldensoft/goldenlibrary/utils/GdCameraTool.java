package com.goldensoft.goldenlibrary.utils;

import android.hardware.Camera;

/**
 *相机闪光灯操作
 * @author golden
 * @date 2018/4/3
 */

public class GdCameraTool {

    private static Camera camera;

    /**
     * 打开闪光灯
     *
     * @return
     */
    public static void openFlashLight() {
        try {
            if (camera == null) {
                camera = Camera.open();
                camera.startPreview();
            }
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭闪光灯
     *
     * @return
     */
    public static void closeFlashLight() {
        try {
            if (camera == null) {

            } else {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
