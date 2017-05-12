/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmfacebookdb;

import com.restfb.json.JsonObject;
import com.restfb.types.Insight;
import com.restfb.types.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author frice
 */
public class mySQLInsert {
    
    public static void writeMetaDataToDB(Post myPost){
        String ID=myPost.getId();
        String type=myPost.getType();
        String message=myPost.getMessage();
        String link=myPost.getLink();
        
        String createdTime = convertDateString(myPost.getCreatedTime().toString());
       
        System.out.println("write metadata method PostID"+myPost.getId());
        //System.out.println("date String"+ createdTime);
        
        try{
        
       Connection conn= MySQLConnection.getDBConnection();
       Statement myStatement= conn.createStatement();
       ResultSet rs = myStatement.executeQuery("select * from fbpostmetadata ");
       
       String query = "Insert into fbpostmetadata (postID,createdTime, type,message,link)\n" +
                        "values  (?,?,?,?,?)";
       
      PreparedStatement ps = conn.prepareStatement(query);
       ps.setString(1, ID);
       ps.setString(2, createdTime);
       ps.setString(3, type);
       ps.setString(4, message);
       ps.setString(5, link);
       
       ps.execute();
       
       conn.close();
       
       }catch(Exception e){e.printStackTrace();}
        
        
    }
    
    public static void writeMetricsDataToDB(Post PostMetrics){
    
        String ID = PostMetrics.getId();
        String likes=PostMetrics.getLikesCount().toString();
        String shares = PostMetrics.getSharesCount().toString();
        String comments =PostMetrics.getSharesCount().toString();
        
        
        try{
        System.out.println("write Metrics Method"+ ID);
        //System.out.println("likes"+ likes);
        Connection conn= MySQLConnection.getDBConnection();
       Statement myStatement= conn.createStatement();
       //ResultSet rs = myStatement.executeQuery("select * from fbpostmetadata ");
       
       String query = "Insert into fbpostmetrics (postID,Likes, shares,comments)\n" +
                        "values  (?,?,?,?)";
        
      PreparedStatement ps = conn.prepareStatement(query);
       ps.setString(1, ID);
       ps.setString(2, likes);
       ps.setString(3, shares);
       ps.setString(4, comments);
      
       
       ps.execute();
       
       conn.close();
        
        
        
        }catch(Exception e){e.printStackTrace();}
        
        
        
    }
    
    public static void UpdateDBwithMetrics(Post PostMetrics) throws Exception{
        
        Connection conn = MySQLConnection.getDBConnection();
        try{
        
        PreparedStatement ps= null;
        
        String ID = PostMetrics.getId();
        String likes=PostMetrics.getLikesCount().toString();
        String shares = PostMetrics.getSharesCount().toString();
        String comments =PostMetrics.getSharesCount().toString();
        
        
        String updateQuery = "Update fbpostmetrics set Likes = ?, shares = ?, comments=?"
                       + " where postID='"+ID+"'";
        
        ps=conn.prepareStatement(updateQuery);
        ps.setString(1, likes);
        ps.setString(2, shares);
        ps.setString(3, comments);
        
        ps.executeUpdate();
        
        }catch(Exception e){e.printStackTrace();}finally{conn.close();}
        
    }
    
    
    
    public static void UpdateDBwitInsights(com.restfb.Connection<Insight> myPageInsights,String ID) throws Exception{
        
        Connection conn= MySQLConnection.getDBConnection();
        try{
        
        PreparedStatement ps = null;
         for(Insight insight:myPageInsights.getData()){ 
               String value=null;
               String columnName=null;
               System.out.println(insight.getName());
               if(insight.getName().equalsIgnoreCase("post_impressions")){
                   System.out.println("1");
                   columnName="impressions";
               }
               else if(insight.getName().equals("post_engaged_users")){
                   columnName="engagement";
               }else if(insight.getName().equals("post_engaged_fan")){
                   columnName="engagers";
               }
               else if(insight.getName().equals("post_consumptions")){
                   columnName="reach";
               };
               
               for(JsonObject insightValue:insight.getValues()){
                   value = insightValue.getString("value");
                   System.out.println(insightValue.get("value"));
                   System.out.println("--------------------------");
               }
               String updateQuery = "Update fbpostmetrics set "+columnName+" = ?"
                       + " where postID='"+ID+"'";
        
              ps=conn.prepareStatement(updateQuery);
               
              ps.setString(1, value);
              
              ps.executeUpdate();
              //System.out.println(">>>>> SQL MOCKUP >>>>>"+ mockQuery);
              ps.close();
    } 
       
        }catch(Exception e){
        e.printStackTrace();
        
        }finally{
            
            conn.close();
        }
    
}
    
    
      public static String convertDateString(String fbDateString){
         try{
                 DateFormat fbDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
                 DateFormat dbDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                 Date fbDate = fbDateFormat.parse(fbDateString);
                 String dbDate = dbDateFormat.format(fbDate);
                 
                  //   System.out.println("dbDate"+dbDate);
                     return dbDate;
                 
                 }catch(Exception e){ e.printStackTrace();}
         return null;
    }
    
      
      
      
}
