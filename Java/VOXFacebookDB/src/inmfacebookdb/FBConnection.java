/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmfacebookdb;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;

/**
 *
 * @author frice
 */
public class FBConnection {
    public String getFacebookToken(){
      String accessToken ="EAAEmQsvyVNQBAGYM2LhCpYUR8HM0VViMPQZCWg6J5lhzJ8cnhxZBoC6M4xQB88PXIQTZBduMWgo5P5qZCHmVbfDp8vMNqiIcyN4YBueZC7Niew3lOSm6F11MZC0oqdxLL9EZAF3J5cvwWzPNdT9DnUMZATC2770h51oZD";
      FacebookClient fbClient = new DefaultFacebookClient(accessToken,Version.VERSION_2_9);
        //Get Extended access token
        AccessToken extToken = fbClient.obtainExtendedAccessToken("323543308063956", "326b8ec05e095aacabacec1c77a027be");
        return extToken.getAccessToken();
    }
}
