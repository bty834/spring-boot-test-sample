package io.github.bty834.domain.util;

public class SampleUtil {


    public static Long getSomething(String id){
        Long result = 0L;
        try {
            result = Long.parseLong(id);
        } catch (NumberFormatException ignored) {
        }

        return result;
    }
}
