package com.bb.superdemo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println(isMoneyMatch("1.11"));
        double re = Double.valueOf("00.11");
        System.out.println(re);
    }

    private boolean isMoneyMatch(String money) {
        String matchReg = "([1-9]\\d*)(\\.\\d{1,2})?";
        Pattern pattern = Pattern.compile(matchReg);
        Matcher matcher = pattern.matcher(money);
        return matcher.matches();
    }
}