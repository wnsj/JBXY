package com.jiubo.project.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static BigDecimal countPercent(BigDecimal dividend, BigDecimal divisor) {
        if (BigDecimal.ZERO.compareTo(divisor) == 0) return BigDecimal.valueOf(0);
        return dividend.divide(divisor, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    public static boolean isNull(BigDecimal math) {
        if (null == math) return true;
        return BigDecimal.ZERO.compareTo(math) == 0;
    }

    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(0);
        b.subtract(a);
    }
}
