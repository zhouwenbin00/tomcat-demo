import java.io.IOException;

/**
 * Created by zwb on 2019/11/12 16:12
 */
public class IndexServlet extends MyServlet {
    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) throws IOException {
        myResponse.write("hello");
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) throws IOException {
        myResponse.write("hello");
    }
}
