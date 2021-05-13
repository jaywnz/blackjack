/*
 * Copyright (C) 2021 Jay
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nz.ac.massey.cs.webtech.s_20020003.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

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
        String sessionId = session.getId();

        if (session.getAttribute("userHand") == null) {
            System.out.println("No game in progress.");
            response.setStatus(404);
            return;
        }

        // retrieve session variables
        ArrayList<Object> userHand = (ArrayList<Object>) session.getAttribute("userHand");
        ArrayList<Object> dealerHand = (ArrayList<Object>) session.getAttribute("dealerHand");
        cards.Deck deck = (cards.Deck) session.getAttribute("deck");
        Object whoseTurn = session.getAttribute("whoseTurn");
        int userHandTotal = (int) session.getAttribute("userHandTotal");
        int dealerHandTotal = (int) session.getAttribute("dealerHandTotal");

        // check servlet path for move type
        String servletPath = request.getServletPath();

        // check if dealer's turn
        if (whoseTurn.equals("computer")) {
            System.out.println("It is not the user's turn.");
            response.setStatus(400);
            return;
        }

        // check if user already bust, if so, force stand
        if (userHandTotal > 21) {
            System.out.println("User is bust.");
            response.setStatus(400);
            servletPath = "/jack/move/stand";
        }

        // user chooses hit
        if ("/jack/move/hit".equals(servletPath)) {
            System.out.println("Hitting...");
            userHand.add(0, deck.dealTopCard());
            System.out.println("userHand: " + userHand);
            // add hit to user total
            cards.Card lastUserCard = (cards.Card) userHand.get(0);
            userHandTotal += lastUserCard.getValue().getNum();
            System.out.println("Total: " + userHandTotal);
            session.setAttribute("userHandTotal", userHandTotal);
        }

        // user chooses stand
        if ("/jack/move/stand".equals(servletPath)) {
            System.out.println("Standing...");

            // adjust user total for aces
            for (Object i : userHand) {
                cards.Card c = (cards.Card) i;
                // TODO deal with case where user has 21 already, e.g., A A 9
                if (c.getValue().getAbbr().equals("A") && (userHandTotal + 10) <= 21) {
                    userHandTotal += 10;
                }
            }
            session.setAttribute("userHandTotal", userHandTotal);
            System.out.println("User stands with: " + userHandTotal);

            // change turn to dealer
            session.setAttribute("whoseTurn", "computer");

            // deal entire hand for dealer
            while (dealerHandTotal <= 17) {
                dealerHand.add(0, deck.dealTopCard());
                cards.Card lastDealerCard = (cards.Card) dealerHand.get(0);
                dealerHandTotal += lastDealerCard.getValue().getNum();
            }

            // adjust dealer total for aces
            for (Object j : dealerHand) {
                cards.Card d = (cards.Card) j;
                if (d.getValue().getAbbr().equals("A") && (dealerHandTotal + 10) <= 21) {
                    dealerHandTotal += 10;
                }
            }

            session.setAttribute("dealerHandTotal", dealerHandTotal);
            System.out.println("dealerHand: " + dealerHand);
            System.out.println("Dealer stands with: " + dealerHandTotal);

            // call won servlet to calculate winner
            CloseableHttpClient client = HttpClients.createDefault();
//            HttpPost post = new HttpPost("https://blackjack-159352.herokuapp.com/assignment2_server_20020003/jack/won");
            HttpPost post = new HttpPost("http://localhost:8080/assignment2_server_20020003/jack/won");
            post.addHeader("Cookie", "JSESSIONID=" + sessionId);
            client.execute(post);
        }
        response.sendRedirect("../../blackjack.jsp");
    }

    // getResponseBody() Author: slal
    private static String getResponseBody(final InputStream stream) throws UnsupportedOperationException, IOException {

        StringBuilder builder = new StringBuilder();

        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
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
