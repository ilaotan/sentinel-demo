package test;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/15 15:51
 */
public class MainTest1 {

    public static void main(String[] args) {

        String uri = "/";

        String[] uris = uri.split("/");
        if (uris != null && uris.length > 0) {
            System.out.println("1111");
            System.out.println(uris[1]);
        }
    }

}
