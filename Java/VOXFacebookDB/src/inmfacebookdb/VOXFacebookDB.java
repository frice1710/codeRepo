/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmfacebookdb;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Insight;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.Post.Likes;
import com.restfb.types.User;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author frice
 */
public class VOXFacebookDB {

    /**
     * @param args the command line arguments
    */ 
    public static void main(String[] args) throws Exception {
       FBConnection fbConn = new FBConnection();
       FacebookClient fbClient = new DefaultFacebookClient(fbConn.getFacebookToken(),Version.VERSION_2_9);
      
       VOXFacebookDB fbData= new VOXFacebookDB();
       
      User me = fbClient.fetchObject("voxpopulidublin", User.class);
      //Page page = fbClient.fetchObject("voxpopulidublin",Page.class);
      //Fetch All Posts
       Connection<Post> myPageFeed = fbClient.fetchConnection(me.getId()+"/posts",Post.class,Parameter.with("fields", "id,created_time,message,type,link,shares,reactions,likes"));
        int index=0;   
        //System.out.println("number of posts"+myPageFeed.getData().size());
        for(List<Post> pagePosts:myPageFeed){
            index++;
            for(Post aPost: pagePosts){
                //fetch page ID, Date, Type, Content and Link
               mySQLInsert.writeMetaDataToDB(aPost);
         
               //fetch page likes,shares and comments;              
               Post PostMetrics =fbClient.fetchObject(aPost.getId(), Post.class, Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
               mySQLInsert.writeMetricsDataToDB(PostMetrics);
        
               //fetch page insights
               Connection<Insight> myPageInsights = fbClient.fetchConnection(aPost.getId()+"/insights", Insight.class, Parameter.with("metric", "post_impressions,post_consumptions,post_engaged_users,post_engaged_fan"));
               mySQLInsert.UpdateDBwitInsights(myPageInsights,aPost.getId());
          
            }
            if(index>30)
                break;
        }

        
        
    // Creating Thread to run as a service and update the Metics values and the Inight values in the DB every week    
    Runnable runnable = new Runnable(){
      

           @Override
           public void run() {
           for(List<Post> pagePosts:myPageFeed){
            for(Post aPost: pagePosts){
                     
               //fetch page likes,shares and comments;              
               Post PostMetrics =fbClient.fetchObject(aPost.getId(), Post.class, Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
               mySQLInsert.writeMetricsDataToDB(PostMetrics);
        
               //fetch page insights
               Connection<Insight> myPageInsightsUpdate = fbClient.fetchConnection(aPost.getId()+"/insights", Insight.class, Parameter.with("metric", "post_impressions,post_consumptions,post_engaged_users,post_engaged_fan"));
                try {
                    mySQLInsert.UpdateDBwitInsights(myPageInsightsUpdate,aPost.getId());
                } catch (Exception e) {
                    e.printStackTrace();

                }
          
            }
        }

           }
    };    
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(runnable, 0, 7, TimeUnit.DAYS);
    
    
    }
    
    public String convertDateString(String fbDateString){
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

class FetchObjectsResults{
    @Facebook
    User me;

    @Facebook("voxpopulidublin")
    Page page;
    
}
