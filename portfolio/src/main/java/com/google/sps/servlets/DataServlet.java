// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // initialize comments list and query
    try {
        JSONArray commentList = new JSONArray();
        JSONArray emailList = new JSONArray();
        JSONObject j = new JSONObject();
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

        // call datastore query to retrieve Comment Entity's
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        //iterate through Comment Entity's retrieved and add comments to list
        for (Entity entity : results.asIterable()) {
            String commentFromData = (String) entity.getProperty("comment");
            String emailFromData = (String) entity.getProperty("email");
            commentList.put(commentFromData);
            emailList.put(emailFromData);
        }

        // convert comments list to JSON and send it to main page
        j.put("comments", commentList);
        j.put("email", emailList);
        response.getWriter().println(j);
     } catch (JSONException e) {
         throw new RuntimeException(e);
     }
    
    
    
  }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // initalize elements of the comment
        String comment = request.getParameter("comment-box");
        long timestamp = System.currentTimeMillis();
        UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();

        //initialize the comment entity and put in datastore
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("email", email);
        if(comment != null){
            commentEntity.setProperty("comment", comment);
        }
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);

        // redirects to main page
        response.sendRedirect("/index.html");
    }
}
