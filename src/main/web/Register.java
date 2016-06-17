import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Register extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public Register() {
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("Cp1251");
        response.setContentType("text/html;charset=cp1251");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String token = DigestUtils.md5Hex(password + login);
        System.out.println(token);
        if(token.equals(token)) {
            HttpSession session = request.getSession();
            session.setAttribute("token", token);

            System.out.println(token);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            /*Connection con;*/
            /*Properties p = new Properties();
            p.setProperty("user", "root");
            p.setProperty("password", "root");
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", "Cp1251");*/
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "root");
            PreparedStatement pst = con.prepareStatement("INSERT INTO registration (login, email, password, token) VALUES (?,?,?,?)");
            System.out.println("login is: " + login + " email is: " + email + " password is: " + password);
            pst.setString(1, login);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, token);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("welcome.jsp");
    }
}
