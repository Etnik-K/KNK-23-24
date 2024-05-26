package app;

import model.User;
import java.util.Locale;

public class SessionManager {
    private static User user;
    private static Locale currentLocale = Locale.getDefault();

    public static void setUser(User newUser) {
        user = newUser;
    }

    public static User getUser() {
        return user;
    }

    public static void logout() {
        user = null;
    }

    public static void setLocale(Locale newLocale) {
        currentLocale = newLocale;
        Locale.setDefault(newLocale);
    }

    public static Locale getLocale() {
        return currentLocale;
    }
}
