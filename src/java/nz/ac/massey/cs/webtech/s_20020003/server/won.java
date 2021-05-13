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
public class won extends HttpServlet {

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

        // get existing session
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("No game in progress.");
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
                out.println("<p>There is no currently active game.</p>");
                out.println("<a href='/assignment2_server_20020003/jack/start'>Start a new game</a>.");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }

        // retrieve session variables
        Object whoseTurn = session.getAttribute("whoseTurn");
        int userHandTotal = (int) session.getAttribute("userHandTotal");
        int dealerHandTotal = (int) session.getAttribute("dealerHandTotal");

        // check if game is in play
        if (whoseTurn.equals("user")) {
            session.setAttribute("winner", "none");
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(session.getAttribute("winner"));
            }
            return;
        } else {
            // determine winner on computer's turn
            while (session.getAttribute("winner").equals("none")) {
                // dealer bust
                if (dealerHandTotal > 21 && userHandTotal <= 21) {
                    session.setAttribute("winner", "user");
                    // dealer and user bust
                } else if (dealerHandTotal > 21 && userHandTotal > 21) {
                    break;
                    // user bust
                } else if (userHandTotal > 21 && dealerHandTotal <= 21) {
                    session.setAttribute("winner", "computer");
                    // draw
                } else if (dealerHandTotal == userHandTotal) {
                    session.setAttribute("winner", "draw");
                    // dealer wins
                } else if (dealerHandTotal > userHandTotal) {
                    session.setAttribute("winner", "computer");
                    // user wins
                } else {
                    session.setAttribute("winner", "user");
                }
            }
        }

        // increment user games won
        if (session.getAttribute("winner").equals("user")) {
            String wonStr = (String) session.getAttribute("userGamesWon");
            if (wonStr == null) {
                wonStr = "0";
            }
            int won = Integer.parseInt(wonStr);
            session.setAttribute("userGamesWon", String.valueOf(won + 1));
        }

        // increment total games played
        String totStr = (String) session.getAttribute("totalGames");
        if (totStr == null) {
            totStr = "0";
        }
        int tot = Integer.parseInt(totStr);
        session.setAttribute("totalGames", String.valueOf(tot + 1));

        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(session.getAttribute("winner"));
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
        processRequest(request, response);
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
