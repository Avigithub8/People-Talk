package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class find extends HttpServlet {

   
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession session = request.getSession();
       try{
           String email = request.getParameter("email");
           String name = request.getParameter("sname");
           System.out.print(name);
           db.dbConnect db = new db.dbConnect();
           java.util.ArrayList<java.util.HashMap> allUserDetails=db.find(email,name);
           if(allUserDetails.size()>0){
                  java.util.HashMap address=new java.util.HashMap();
                session.setAttribute("allUserDetails",allUserDetails);
                  session.setAttribute("sname",name);
                response.sendRedirect("peoplesearch.jsp");
            }else{
                session.setAttribute("msg","No Data FOund!");
                response.sendRedirect("profile.jsp");
            }
       }catch(Exception ex){
           session.setAttribute("msg", "Failed! "+ex);
           response.sendRedirect("profile.jsp");
            
        }
    }

} 
