package Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.dao.AuthenticationDB;

public class Authentication {
    public static int login(HttpServletRequest request, String email, String password) throws Exception {
    	AuthenticationDB adb = new AuthenticationDB();
    	
    	JSONObject res = adb.checkUser(email, password);
        if (res.getInt("code") == 0) {
        	request.getSession().setAttribute("user", email);
        	request.getSession().setAttribute("name", res.get("name"));
            return 1;
        }
        return 0;
    }

    public static int logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.invalidate();
        return 1;
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }
}
