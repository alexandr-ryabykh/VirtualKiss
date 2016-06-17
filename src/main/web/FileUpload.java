import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class FileUpload extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private final String UPLOAD_DIRECTORY = "D:/JetBrains/IntelliJ IDEA 15.0.2/projects/VirtualKiss/src/main/webapp/images";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)

            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute("token");

        if (ServletFileUpload.isMultipartContent(request)) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/registration", "root",
                        "root");
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String link = new File(item.getName()).getName();
                        // обрезать строку до расширения
                        // String fileName = name.split("\\.")[0];
                        item.write(new File(UPLOAD_DIRECTORY + File.separator
                                + link));
                        String sql_insert = "Insert into images (token, link) VALUES (?,?)";
                        PreparedStatement pst = con
                                .prepareStatement(sql_insert);
                        System.out.println("photo is: " + link + "\n"
                                + "token is: " + token);
                        pst.setString(1, token);
                        pst.setString(2, link);
                        pst.execute();
                        /*String fileName = link + File.separator + item.getName();
                        request.setAttribute("fileName", fileName);*/
                    }

                }
                response.sendRedirect("result.jsp");
                /*RequestDispatcher rd = request
                        .getRequestDispatcher("result.jsp");
                rd.forward(request, response);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
