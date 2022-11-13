/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getPhoto extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
               try{ 
      
                     String e=request.getParameter("email");
			db.dbConnect db=new db.dbConnect();
			byte[] b=db.getPhoto(e);
			if(b!=null){
				response.getOutputStream().write(b);
			}else {
			   ServletContext ctx=getServletContext();
	                    InputStream fin=ctx.getResourceAsStream("E:\\CABAVI.jpg");
	                    b=new byte[3500];
                            fin.read(b);
	                    
	                    response.getOutputStream().write(b);
                        }
                }catch(Exception ex){
                    ex.printStackTrace();
                    FileInputStream fin=new FileInputStream("E:\\CABAVI.jpg");
                    byte []b=new byte[3500];
                   fin.read(b);
                   response.getOutputStream().write(b);
        
    }

    }
}
