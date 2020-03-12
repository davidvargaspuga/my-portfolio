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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // response.setContentType("text/html");
    try {
        PrintWriter out = response.getWriter();
        UserService userService = UserServiceFactory.getUserService();
        
        String loginURL = userService.createLoginURL("/");
        boolean isLoggedIn = userService.isUserLoggedIn();

        JSONObject data = new JSONObject();
        data.put("isLoggedIn", isLoggedIn);
        data.put("loginURL", loginURL);
        response.getWriter().println(data);
    } catch (JSONException e) {
         throw new RuntimeException(e);
    }
    // //returns login link as a string if not logged in
    // // otherwise, returns true as a boolean
    // if(!isLoggedIn){
    //     out.println("<p>Login <a href=\"" + loginURL + "\">here</a> to comment!</p>");
    //     return;
    // } else{
    //     String json = new Gson().toJson(isLoggedIn);
    //     out.println(json);
    // } 
  }
}
