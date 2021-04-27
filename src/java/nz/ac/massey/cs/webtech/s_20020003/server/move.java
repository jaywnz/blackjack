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
import java.util.ArrayList;

/**
 *
 * @author Jay
 */
public class move extends HttpServlet {

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
            System.out.println("No session in progress.");
            response.setStatus(404);
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
        ArrayList<Object> userHand = (ArrayList<Object>) session.getAttribute("userHand");
        cards.Deck deck = (cards.Deck) session.getAttribute("deck");
        Object whoseTurn = session.getAttribute("whoseTurn");
        Object userHandTotal = session.getAttribute("userHandTotal");
        
        // check if dealer's turn
        if (whoseTurn.equals(1)) {
            response.setStatus(400);
            return;
        }
        
        // check if user bust
        if ((int)userHandTotal > 21) {
            response.setStatus(400);
            return;
        }
        
        // check servlet path for move type
        String servletPath = request.getServletPath();
        if ("/jack/move/hit".equals(servletPath)) {
            System.out.println("Hit...");
            userHand.add(deck.dealTopCard());
            System.out.println(userHand);
        }
        
        if ("/jack/move/stand".equals(servletPath)) {
            System.out.println("Stand...");
            // change whoseTurn
            session.setAttribute("whoseTurn", 1);
            // deal entire hand for dealer
            // hit if total <=17
               // possible while loop here
            
        }

//        cards x = new cards();
//        cards.Deck y = x.new dealTopCard();
        
//        userHand.add(cards.Deck.dealTopCard());
//        userHand.add(deck.dealTopCard());
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet move</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet move at " + request.getContextPath() + "</h1>");
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
