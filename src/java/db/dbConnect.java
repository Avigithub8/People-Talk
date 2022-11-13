package db;

import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;

public class dbConnect {
    private Connection c;
    private Statement st;
   public Statement getSt() {
		return st;
	}
	
    public dbConnect() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        c=DriverManager.getConnection(
   "jdbc:mysql://localhost:3306/peopletalk","username","password");
        st=c.createStatement();
        
    } 
    public void disconnect()  throws Exception{
		if(c!=null && c.isClosed()) {
			c.close();
                }
    }
     public HashMap checkLogin (String e,String p)throws SQLException{
         
         PreparedStatement checkLogin=c.prepareStatement("select * from userinfo where email=? and pass=?");
         checkLogin.setString(1,e);
         checkLogin.setString(2,p);
         ResultSet rs=checkLogin.executeQuery();
         if(rs.next()){
             HashMap userDetails=new HashMap();
             userDetails.put("email",rs.getString("email"));
             userDetails.put("name",rs.getString("name"));
             userDetails.put("phone",rs.getString("phone"));
             userDetails.put("gender",rs.getString("gender"));
             userDetails.put("dob",rs.getString("dob"));
             userDetails.put("state",rs.getString("state"));
             userDetails.put("city",rs.getString("city"));
             userDetails.put("area",rs.getString("area"));
            
             
             return userDetails;
            
         }else{
             return null;
         }
    
     }
     public java.util.ArrayList<java.util.HashMap> searchPeople(String s,String ct,String e,String a) throws Exception{     
	PreparedStatement searchPeople=c.prepareStatement("select name,email,phone,dob,gender from userinfo where  state=? and city=? and email!=? and area like ? ");
	searchPeople.setString(1, s);
	searchPeople.setString(2, ct);
	searchPeople.setString(3, e);
	searchPeople.setString(4, "%"+a+"%");
	ResultSet r=searchPeople.executeQuery();
	java.util.ArrayList<java.util.HashMap> allUserDetails=
	        new java.util.ArrayList();                               //without learn autoincrement so use arraylist not hashmap b/c remember key
	while(r.next()){
	    java.util.HashMap UserDetails=new java.util.HashMap();
	    UserDetails.put("email",r.getString("email"));
	    UserDetails.put("name",r.getString("name"));
	    UserDetails.put("phone",r.getString("phone"));
	    UserDetails.put("gender",r.getString("gender"));
	    UserDetails.put("dob",r.getDate("dob"));
	    allUserDetails.add(UserDetails);
	    }
	return allUserDetails;
	}
     
     
       public String registerUser(HashMap userDetails)throws SQLException{
        try{
        PreparedStatement registerUser=c.prepareStatement("insert into userinfo values(?,?,?,?,?,?,?,?,?,?)");
        registerUser.setString(1, (String)userDetails.get("email"));
        registerUser.setString(2, (String)userDetails.get("pass"));
        registerUser.setString(3, (String)userDetails.get("name"));
        registerUser.setString(4, (String)userDetails.get("phone"));
        registerUser.setString(5, (String)userDetails.get("gender"));
        registerUser.setDate(6, (java.sql.Date)userDetails.get("dob"));
        registerUser.setString(7, (String)userDetails.get("state"));
        registerUser.setString(8, (String)userDetails.get("city"));
        registerUser.setString(9, (String)userDetails.get("area"));
      
        registerUser.setBinaryStream(10, (InputStream)userDetails.get("photo"));
      
        int x=registerUser.executeUpdate();
        if(x!=0)
           return "Success";
        else
            return "Failed";
        }catch(Exception ex){
            return (" "+ex);
        }
       }
     public String sendMessage(String s,String r,String m,String f,java.io.InputStream in) throws SQLException {        
    	PreparedStatement sendMessage=c.prepareStatement(
    		    "insert into peoplemsg  (sid,rid,msg,filename,ufile,udate) values (?,?,?,?,?,now())");
    	sendMessage.setString(1, s);
        sendMessage.setString(2, r);
        sendMessage.setString(3, m);
        sendMessage.setString(4, f);
        sendMessage.setBinaryStream(5, in);
        int x=sendMessage.executeUpdate();
        if(x==1)
         return "Success";
        else 
         return "Error";
    }
       public java.util.ArrayList<java.util.HashMap> getMessages(String s,String r) throws SQLException{     
    	PreparedStatement getMessages=c.prepareStatement(
    		    "select * from peoplemsg where sid=? and rid=? ");
    	getMessages.setString(1, s);
    	getMessages.setString(2, r);
    	ResultSet rs=getMessages.executeQuery();
    	java.util.ArrayList<java.util.HashMap> allMessage=
    	        new java.util.ArrayList();
    	while(rs.next()){
    	    java.util.HashMap message=new java.util.HashMap();
    	    message.put("message",rs.getString("msg"));
    	    message.put("datetime",rs.getString("udate"));
    	    message.put("filename",rs.getString("filename"));
    	    message.put("file",rs.getBytes("ufile"));
    	    message.put("pid",rs.getString("pid"));
    	    allMessage.add(message);
    	    }
    	return allMessage;
    }
public byte[] getFile(int e){
    	
        try{
        	PreparedStatement getFile=c.prepareStatement(
        		    "select ufile from peoplemsg where pid=? ");
            getFile.setInt(1, e);
            ResultSet rs=getFile.executeQuery();
            if(rs.next()){
                byte[] b=rs.getBytes("ufile");
                if(b.length!=0)
                    return b;
                else
                    return null;
            }else{
                return null;
            }
        }catch(Exception ex){
            return null;
        }
    }
public java.util.HashMap getPeopleByEmail(String e)throws Exception{
		PreparedStatement getPeopleByEmail=c.prepareStatement("select * from userinfo where email=?");
		getPeopleByEmail.setString(1, e);
        ResultSet rs=getPeopleByEmail.executeQuery();
        if(rs.next()){
            java.util.HashMap userDetails=new java.util.HashMap();
            userDetails.put("name",rs.getString("name"));
            userDetails.put("email",rs.getString("email"));
            userDetails.put("phone",rs.getString("phone"));
            userDetails.put("gender", rs.getString("gender"));
            userDetails.put("dob", rs.getDate("dob"));
            userDetails.put("state", rs.getString("state"));
            userDetails.put("city", rs.getString("city"));
            userDetails.put("area", rs.getString("area"));
            return userDetails;
        }else{
            return null;
        }
	}
       public byte[] getPhoto(String e) throws SQLException{
        	PreparedStatement  getPhoto=c.prepareStatement(
                                                  "select photo from userinfo where email=? ");
        try{
            getPhoto.setString(1, e);
            ResultSet rs=getPhoto.executeQuery();
            if(rs.next()){
                byte[] b=rs.getBytes("photo");
                if(b.length!=0)
                    return b;
                else
                    return null;
            }else{
                return null;
            }
        }catch(Exception ex){
            return null;
        }
    }
        public String getPassword(String e){
        try{
        	PreparedStatement getPassword=c.prepareStatement(
        		    "select pass from userinfo where email=? ");
            getPassword.setString(1, e);
            ResultSet rs=getPassword.executeQuery();
            if(rs.next()){
                    return rs.getString("pass");
            }else{
                return null;
            }
        }catch(Exception ex){
            return null;
        }
    }
         public String changePhoto(String e,java.io.InputStream photo)throws Exception{
        PreparedStatement changePhoto=c.prepareStatement("update userinfo set photo=? where email=?");
        changePhoto.setBinaryStream(1, photo);
        changePhoto.setString(2, e);
       int x=changePhoto.executeUpdate();
        if(x!=0){
            return "Success";
        }else{
            return "Failed";
        }
    }
     public String changePassword(String np,String e,String op)throws Exception{
        PreparedStatement changePassword=c.prepareStatement("update userinfo set pass=? where email=? and pass=?");
        changePassword.setString(1, np);
        changePassword.setString(2, e);
        changePassword.setString(3, op);
       int x=changePassword.executeUpdate();
        if(x!=0){
            return "Success";
        }else{
            return "Failed";
        }
    }
      public java.util.ArrayList<java.util.HashMap> find(String e,String n) throws SQLException{
         PreparedStatement find =c.prepareStatement("select * from userinfo where email!=? and name like ?");
         find.setString(1, e);
         find.setString(2,"%"+n+"%");
        ResultSet rs=find.executeQuery();
        java.util.ArrayList<java.util.HashMap> userDetails = new java.util.ArrayList();
        while(rs.next()){
            java.util.HashMap details= new java.util.HashMap();
            details.put("email", rs.getString(1));
            details.put("name", rs.getString(3));
            details.put("phone", rs.getString(4));
            details.put("gender", rs.getString(5));
            details.put("dob", rs.getDate(6));
            userDetails.add(details);
        }
        return userDetails;
    }
  

}


    

