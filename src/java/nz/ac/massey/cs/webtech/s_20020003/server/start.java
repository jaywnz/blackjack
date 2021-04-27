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
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author Jay
 */
public class start extends HttpServlet {
    
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
                
        // create new session
        HttpSession session = request.getSession(true);
        
        // construct and shuffle deck
        
        cards.Deck deck = new cards.Deck();
      
        deck.shuffle();
//        System.out.println(deck);

        // initialise session variables
        ArrayList<Object> userHand = new ArrayList<>();
        ArrayList<Object> dealerHand = new ArrayList<>();
        session.setAttribute("whoseTurn", 0);
        int userHandTotal = 0;
//        int dealerHandTotal = 0;
        //String sessionId = session.getId();
        
        // deal 2 cards to user
        userHand.add(deck.dealTopCard());
        userHand.add(deck.dealTopCard());
        System.out.println("userHand=" + userHand);
        session.setAttribute("userHand", userHand);
        
        // add totals and record
        for (Object i : userHand) {
            cards.Card c = (cards.Card) i;
            userHandTotal += c.getValue().getNum();
        }
        
        System.out.println("Total=" + userHandTotal);
        session.setAttribute("userHandTotal", userHandTotal);
                
        // tests
        cards.Card current = (cards.Card)userHand.get(0);
        System.out.println(current);
        System.out.println(current.getValue());
        System.out.println(current.getSuit());
        System.out.println(current.getValue().getNum());

        // deal 2 cards to dealer ((1 face down))
        dealerHand.add(deck.dealTopCard());
        dealerHand.add(deck.dealTopCard());
        System.out.println("dealerHand=" + dealerHand);
        session.setAttribute("dealerHand", dealerHand);
        
        System.out.println(deck);
        session.setAttribute("deck", deck);
        
        // increment number of games played

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet start</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet start at " + request.getContextPath() + "</h1>");
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
