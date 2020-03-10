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
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    UserService userService = UserServiceFactory.getUserService();

    // user is not logged in
    if(!userService.isUserLoggedIn()){
        String loginUrl = userService.createLoginURL("/login");
        out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
        return;
    }

    // User is logged in 
    String userEmail = userService.getCurrentUser().getEmail();
    String logoutUrl = userService.createLogoutURL("/");
    out.println("<h1>Home</h1>");
    out.println("<p>Hello " + userEmail + "!</p>");
    out.println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

  }
}
