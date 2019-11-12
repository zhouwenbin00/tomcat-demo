import java.io.IOException;

/**
 * Created by zwb on 2019/11/12 16:00
 */
public abstract class MyServlet {

    public abstract void doGet(MyRequest myRequest, MyResponse myResponse) throws IOException;

    public abstract void doPost(MyRequest myRequest, MyResponse myResponse) throws IOException;

    public void service(MyRequest myRequest, MyResponse myResponse) throws IOException {
        if (myRequest.getMethod().equalsIgnoreCase("POST")) {
            doPost(myRequest, myResponse);
        } else if (myRequest.getMethod().equalsIgnoreCase("GET")) {
            doGet(myRequest, myResponse);
        }
    }
}
