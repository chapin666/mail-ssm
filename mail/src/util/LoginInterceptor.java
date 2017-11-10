package util;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

    private static final long serialVersionUID = -2645672087541419081L;

    public void destroy() {
        
    }

    public void init() {
    }

    public String intercept(ActionInvocation ai) throws Exception {
        String invoke = ai.invoke();
        System.out.println(this.getClass().getName() + " after");
        return invoke;
    }

}
