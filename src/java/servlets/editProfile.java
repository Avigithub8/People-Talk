package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class editProfile extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        try{
        	String m="";
            String e=request.getParameter("email");
            String p=request.getParameter("phone");
            String n=request.getParameter("name");
            String g=request.getParameter("gender");
            String oldG=request.getParameter("oldgender");
            String d=request.getParameter("dob");
            String s=request.getParameter("state");
            String c=request.getParameter("city");
            String oldS=request.getParameter("oldstate");
            String oldC=request.getParameter("oldcity");
            String a=request.getParameter("area");
            
           
            System.out.println(p+n+g+d+s+c+a);
            db.dbConnect db=new db.dbConnect();
            java.sql.Statement st=db.getSt();
            
            if(!p.equals("") ) {
                 System.out.println("abc");
            	st.executeUpdate("update userinfo set phone='"+p+"' where email='"+e+"'");
            	m += "Phone,";
            }
            if(!n.equals("") ) {
            	st.executeUpdate("update userinfo set name='"+n+"' where email='"+e+"'");
            	m += "Name,";
            }
            if( !g.equalsIgnoreCase(oldG) ) {
            	st.executeUpdate("update userinfo set gender='"+g+"' where email='"+e+"'");
            	m += "Gender,";
            }
            if( !d.trim().equals("") ) {
            	//code to convert String into Date
                java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date=sdf.parse(d);
                java.sql.Date dob=new java.sql.Date(date.getTime());
                
            	st.executeUpdate("update userinfo set dob='"+dob+"' where email='"+e+"'");
            	m += "DOB,";
            }
            if( !s.equalsIgnoreCase(oldS) ) {
            	st.executeUpdate("update userinfo set state='"+s+"' where email='"+e+"'");
            	m += "State,";
            }
            if( !c.equalsIgnoreCase(oldC) ) {
            	st.executeUpdate("update userinfo set city='"+c+"' where email='"+e+"'");
            	m += "City,";
            }
            if(!a.equals("") ) {
            	st.executeUpdate("update userinfo set area='"+a+"' where email='"+e+"'");
            	m += "Area,";
            }
            if(m.equals("")) {
                session.setAttribute("msg", "Nothing Updated");
            }else {
            	m=m.substring(0,m.length()-1);
                session.setAttribute("msg", m+" Updated Successfully");
            }
              java.util.HashMap userDetails=new java.util.HashMap();
              
                         userDetails.put("email", e);
                        userDetails.put("phone", p);
                        userDetails.put("name", n);
                        userDetails.put("gender", g);
                        userDetails.put("dob", d);
                        userDetails.put("state", s);
                        userDetails.put("city", c);
                        userDetails.put("area", a);
                      session.setAttribute("userDetails",userDetails);
            session.setAttribute("msgType", "Success");
            response.sendRedirect("profile.jsp");
        } catch (Exception ex) {
        	ex.printStackTrace();
            session.setAttribute("msg", "Updation Failed: "+ex);
            session.setAttribute("msgType", "Error");
            response.sendRedirect("profile.jsp");
        }

            }
}

