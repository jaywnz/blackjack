/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_20020003.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet stats</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet stats at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        // get existing session
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("No session in progress.");
            response.setStatus(404);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Blackjack</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>404 Not Found</h1>");
                out.println("<p>There is no currently active session.</p>");
                out.println("<a href='/assignment2_server_20020003/jack/start'>Start a new game</a>.");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }

        // retrieve session variables
        String t = (String) session.getAttribute("totalGames");
        String u = (String) session.getAttribute("userGamesWon");

        if (t == null) {
            t = "0";
        }
        if (u == null) {
            u = "0";
        }

        int totalGames = Integer.parseInt(t);
        int userGamesWon = Integer.parseInt(u);
        double winPercentage = 0.0;

//                // increment user games won
//        if (session.getAttribute("winner").equals("user")) {
//            String wonStr = (String)session.getAttribute("userGamesWon");
//            if (wonStr == null) {
//                wonStr = "1";
//            }
//            int won = Integer.parseInt(wonStr);
//            session.setAttribute("userGamesWon", String.valueOf(won+1));
//        }
//        int userGamesWon = (int)session.getAttribute("userGamesWon");
//        int totalGames = (int)session.getAttribute("totalGames");
        if (totalGames == 0) {
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("No games played this session.");
            }
        } else {
            winPercentage = Math.round((userGamesWon / totalGames) * 100);
        }

        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("Total games played: " + totalGames);
            out.println("Total games won: " + userGamesWon);
            out.println("Win percentage: " + winPercentage + "%");
        }

//        processRequest(request, response);
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
        processRequest(request, response);
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
