import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String message = request.getParameter("message");
        int rating = Integer.parseInt(request.getParameter("rating"));

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO feedback (user_id, message, rating) VALUES (?, ?, ?)");
            pst.setInt(1, userId);
            pst.setString(2, message);
            pst.setInt(3, rating);
            pst.executeUpdate();
            
            response.sendRedirect("feedback.jsp?success=Feedback submitted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}