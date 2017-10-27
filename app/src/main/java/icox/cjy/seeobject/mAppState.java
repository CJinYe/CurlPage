package icox.cjy.seeobject;

import android.content.Context;

/**
 * Created by icoxdev on 15-5-7.
 */
public class mAppState {
    private static mAppState INSTANCE;
    private static Context sContext;
    public static mAppState getInstance() {
        if (INSTANCE == null)
            INSTANCE = new mAppState();
        return INSTANCE;
    }
    private mAppState() {
        if (sContext == null) {
            throw new IllegalStateException("CenterAppState inited before app context set");
        }
    }
    public static void setApplicationContext(Context context) {
        sContext = context.getApplicationContext();
    }

}
