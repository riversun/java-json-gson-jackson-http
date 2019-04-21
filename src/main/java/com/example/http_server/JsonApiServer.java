package com.example.http_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.gson.Model;
import com.example.gson.Person;
import com.example.gson.Pet;
import com.google.gson.Gson;

/**
 * ・GETされると、JSON文字列を返す<br>
 * ・JSONをPOSTされると、JSONを解析して結果を返す<br>
 * HTTPサーバー
 */
public class JsonApiServer {
    public static void main(String[] args) {
        final int PORT = 8080;
        final Class<? extends Servlet> servlet = ExampleServlet.class;
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.addServlet(new ServletHolder(servlet), "/api");

        final Server jettyServer = new Server(PORT);
        jettyServer.setHandler(servletHandler);
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("serial")
    public static class ExampleServlet extends HttpServlet {

        private final Gson mGson = new Gson();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            Model model = new Model();
            model.person = new Person();
            model.person.firstName = "ジョン";
            model.person.lastName = "ドゥ";
            model.person.address = "ニューヨーク";
            model.person.pets = new ArrayList<Pet>();
            Pet pet1 = new Pet();
            pet1.type = "犬";
            pet1.name = "ジョリー";
            model.person.pets.add(pet1);
            Pet pet2 = new Pet();
            pet2.type = "猫";
            pet2.name = "グリザベラ";
            model.person.pets.add(pet2);
            Pet pet3 = new Pet();
            pet3.type = "魚";
            pet3.name = "ニモ";
            model.person.pets.add(pet3);

            String json = mGson.toJson(model);

            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            final PrintWriter out = resp.getWriter();
            out.println(json);
            out.close();

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            final StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
            }

            final String body = sb.toString();
            final Model model = mGson.fromJson(body, Model.class);

            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            final PrintWriter out = resp.getWriter();
            out.println("Server Generated Message");
            out.println("Firstname is " + model.person.firstName);
            out.close();

        }

    }

}