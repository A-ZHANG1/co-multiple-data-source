package trade.spring.data.neo4j.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangtao on 2019-05-10.
 */
public class Utils {

    public static String getTimeStamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

}
