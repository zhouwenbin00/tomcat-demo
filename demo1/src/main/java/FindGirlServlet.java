import java.io.IOException;

/**
 * Created by zwb on 2019/11/12 16:02
 */
public class FindGirlServlet extends MyServlet {


    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("get grils");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("post grils");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
