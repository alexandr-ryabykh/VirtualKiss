import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)

            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute("token");

         try {
     Class.forName("com.mysql.jdbc.Driver");
     Connection con = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/registration", "root",
             "root");
     PreparedStatement pst = con
             .prepareStatement("SELECT DISTINCT link FROM images WHERE token=?");
     pst.setString(1,token);
     pst.execute();
 } catch (Exception e){
     e.printStackTrace();
 }

    }
}
