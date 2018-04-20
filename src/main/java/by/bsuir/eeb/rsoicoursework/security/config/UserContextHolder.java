package by.bsuir.eeb.rsoicoursework.security.config;

public class UserContextHolder {
    private static ThreadLocal<Long> userId = new ThreadLocal<>();

    public static Long getUserId() {
        return userId.get();
    }

    public static void setUserId(long userId) {
        UserContextHolder.userId.set(userId);
    }
}
