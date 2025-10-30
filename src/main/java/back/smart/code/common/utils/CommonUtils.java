package back.smart.code.common.utils;

import java.util.UUID;

public class CommonUtils {

    public static  String getRandUUID()  {
        return  UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
