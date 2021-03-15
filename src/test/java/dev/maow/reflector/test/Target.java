package dev.maow.reflector.test;

public class Target {
    public static String memeSchool = "meme schoolius";

    private final String thisFieldWontBeFinalAnymoreHehe = "This is a story all about how-";

    public void print(String msg) {
        System.out.println("pizza : " + msg);
    }

    private void privatePrint(String msg) {
        System.out.println("private pizza : " + msg);
    }

    public static void staticPrint(String msg) {
        System.out.println("static... pizza? : " + msg);
    }
}
