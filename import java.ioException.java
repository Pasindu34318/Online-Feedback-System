import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (role == null || !role.equals("admin")) {
            response.sendRedirect("index.jsp");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Feedback List</h2>");
        out.println("<table border='1'><tr><th>User ID</th><th>Message</th><th>Rating</th><th>Timestamp</th></tr>");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM feedback");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("user_id") + "</td><td>" + rs.getString("message") + 
                            "</td><td>" + rs.getInt("rating") + "</td><td>" + rs.getTimestamp("timestamp") + "</td></tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</table>");
    }
}