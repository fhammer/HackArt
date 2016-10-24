package play.club.skecher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：HackArt
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2016/7/9 17:32
 * 修改人：fuzh2
 * 修改时间：2016/7/9 17:32
 * 修改备注：
 */
public class ColorRender {
    /**
     * startColor：开始颜色hex endColor：结束颜色hex step:几个阶级（几步）
     *
     * @param startColor
     * @param endColor
     * @param step
     */
    public static List<String> gradientColor(String startColor, String endColor, double step) {
        Integer startRGB[] = colorRgb(startColor);// 转换为rgb数组模式
        int startR = startRGB[0];
        int startG = startRGB[1];
        int startB = startRGB[2];

        Integer endRGB[] = colorRgb(endColor);
        int endR = endRGB[0];
        int endG = endRGB[1];
        int endB = endRGB[2];
        //
        double sR = (endR - startR) / step;// 总差值
        double sG = (endG - startG) / step;
        double sB = (endB - startB) / step;

        //
        List<String> colorArr = new ArrayList<String>();
        for (int i = 0; i < step; i++) {
            // 计算每一步的hex值
            String hex = colorHex(
                    "rgb(" + (new BigDecimal(sR * i).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + startR) + ","
                            + (new BigDecimal(sG * i).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + startG) + ","
                            + (new BigDecimal(sB * i).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + startB) + ")");
            hex = formatColor(hex);
            colorArr.add(hex);
        }
        return colorArr;
    }

    private static String formatColor(String pHex) {
        if (pHex.length() < 7)
            pHex = pHex + "0";
        return pHex;
    }

    /**
     * 将rgb表示方式转换为hex表示方式
     *
     * @return
     */
    private static String colorHex(String rgb) {
        // String reg = "^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})";
        if (rgb.matches("^(rgb|RGB).*")) {
            String aColor[] = rgb.replaceAll("[RGB|rgb|(|)]", "").split(",");
            String strHex = "#";
            for (int i = 0; i < aColor.length; i++) {
                String hex = Integer.toHexString(Integer.parseInt(aColor[i]));
                hex = Integer.parseInt(hex, 16) < 10 ? "0" + hex : hex;
                if ("0".equals(hex)) {
                    hex += hex;
                }
                strHex += hex;
            }
            return strHex;
        } else {
            return rgb;
        }
    }

    /**
     * 只接受 #十六进制的数据 <br/>
     * 后期可以考虑加透明色
     *
     * @param sc
     * @return
     */
    private static Integer[] colorRgb(String sc) {
        String reg = "^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})";
        String sColor = sc.toLowerCase();
        boolean b = sc.matches(reg);
        if (b) {
            List<Integer> changeColor = new ArrayList<>();
            for (int i = 1; i < 7; i += 2) {
                changeColor.add(Integer.parseInt(sColor.substring(i, i + 2), 16));
            }
            return changeColor.toArray(new Integer[changeColor.size()]);
        }
        return new Integer[]{};
    }

//    public static void main(String[] args) {
//        List<String> list = gradientColor("#BB0025", "#FFEEF1", 100);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//    }
}
