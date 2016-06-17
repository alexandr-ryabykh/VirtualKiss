import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class Login extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public Login() {
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("Cp1251");
        response.setContentType("text/html;charset=cp1251");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(login);
        System.out.println(password);
        String token = request.getParameter("token");

        HttpSession session = request.getSession();

        if (login != null && password != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/registration", "root",
                        "root");
                PreparedStatement pst = con
                        .prepareStatement("SELECT * FROM registration WHERE login=? AND password=?");
                pst.setString(1, login);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    if (rs.getString("login").equals(login)
                            && rs.getString("password").equals(password)) {
                        token = rs.getString("token");
                        break;
                    }
                }
                System.out.println(token);
                if (null != token) {
                    request.getSession().setAttribute("token", token);
                    if (session.getAttribute("token") == null) {
                        response.sendRedirect("login.jsp");
                    } else {
                        /*RequestDispatcher rd = request
                                .getRequestDispatcher("userProfile.jsp");
                        rd.forward(request, response);*/
                        response.sendRedirect("userProfile.jsp");
                        //response.sendRedirect(response.encodeRedirectURL("http://localhost:8080/VirtualKiss/PAGES/fileUpload.jsp"));
                    }
                }
                else
                {
                    System.err.println("TOKEN IS NULL");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
