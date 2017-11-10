package util;

import java.util.UUID;

public class CreateUUID {
    public static String getuuid() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getuuid());
    }
}