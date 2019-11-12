import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwb on 2019/11/12 16:04
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappingList =new ArrayList<>();

    static {
        servletMappingList.add(new ServletMapping("findGirl","/gril","FindGirlServlet"));
        servletMappingList.add(new ServletMapping("index","/","IndexServlet"));
    }
}
