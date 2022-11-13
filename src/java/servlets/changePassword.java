package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class changePassword extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        try (PrintWriter out = response.getWriter()) {
           
            String e=request.getParameter("email");
            String op=request.getParameter("password");
            String np=request.getParameter("newpassword");
            String cp=request.getParameter("confirmpassword");
           
            
             if(np.equals(cp)){
                 
            db.dbConnect db=new  db.dbConnect();
             String check = db.changePassword(np, e, op);
                       if(check.equalsIgnoreCase("Success")){
                            session.setAttribute("msg", "Password Change Successfully");
                            response.sendRedirect("profile.jsp");
                       }else{
                            session.setAttribute("msg", "Wrong Current Password!");
                            response.sendRedirect("changepassword.jsp");
                       }
                  }else{
                       session.setAttribute("msg", "Please enter same password!");
                        response.sendRedirect("changepassword.jsp");
                  }
             
           }catch(Exception ex){
               ex.printStackTrace();
                session.setAttribute("msg","Exception"+ ex);
                response.sendRedirect("changepassword.jsp");

           }
    
    }

    
}
