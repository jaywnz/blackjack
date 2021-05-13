/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_20020003.server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jay
 */
public class stats extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.simple.parser.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        // get existing session
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("No session in progress.");
            response.setStatus(404);
            return;
        }

        // retrieve session variables
        String t = (String) session.getAttribute("totalGames");
        String u = (String) session.getAttribute("userGamesWon");

        // initialise if not already
        if (t == null) {
            t = "0";
        }
        if (u == null) {
            u = "0";
        }

        int totalGames = Integer.parseInt(t);
        int userGamesWon = Integer.parseInt(u);
        double winPercentage;

        if (totalGames == 0) {
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("No games played this session.");
                return;
            }
        }

        winPercentage = Math.round(((double) userGamesWon / (double) totalGames) * 100);

        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("Total games played: " + totalGames);
            out.println("Total games won: " + userGamesWon);
            out.println("Win percentage: " + winPercentage + "%");
        }

        // save stats to JSON
        JSONObject stats = new JSONObject();
        JSONArray data = new JSONArray();
        data.add(totalGames);
        data.add(userGamesWon);
        data.add(winPercentage);

        String sessionId = (String) session.getId();
        stats.put(sessionId, data);
        System.out.println(stats);

        // write stats to local file
        File f = new File("C:\\Users\\Jay\\Desktop\\stats.json");
        if (f.isFile()) {
            try (Reader reader = new FileReader("C:\\Users\\Jay\\Desktop\\stats.json")) {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(reader);
                obj.putAll(stats);
                try (FileWriter file = new FileWriter("C:\\Users\\Jay\\Desktop\\stats.json")) {
                    file.write(obj.toJSONString());
                    file.flush();
                    file.close();
                }
            }
        } else {
            try (FileWriter file = new FileWriter("C:\\Users\\Jay\\Desktop\\stats.json")) {
                file.write(stats.toJSONString());
                file.flush();
                file.close();
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(stats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(stats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
