package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class changePhoto extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           HttpSession session=request.getSession();
        try{
            String e=request.getParameter("email");
            Part part=request.getPart("photo");
            InputStream is=null;
            if(part!=null){
                is=part.getInputStream();
            }
            db.dbConnect db=new db.dbConnect();
            String m=db.changePhoto(e, is);
            db.disconnect();
             java.util.HashMap userDetails=new java.util.HashMap();
                        userDetails.put("photo", part);
                     session.setAttribute("userDetails",userDetails);
            if(m.equalsIgnoreCase("Success")){
                session.setAttribute("msg", "Photo Changed Successfully");
                session.setAttribute("msgType", "Success");
                response.sendRedirect("profile.jsp");
            }else {
            	session.setAttribute("msg", "Photo Updation Failed");
                session.setAttribute("msgType", "Error");
                response.sendRedirect("editprofile.jsp");
            }
        
        }catch(Exception ex){
            session.setAttribute("msg", "Photo Updation Failed: "+ex);
            session.setAttribute("msgType", "Error");
            response.sendRedirect("editprofile.jsp");
       
        }
    }
}
