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

"use strict";

const STATE_SERV = 'http://localhost:8080/assignment2_server_20020003/jack/state';
const STATS_SERV = 'http://localhost:8080/assignment2_server_20020003/jack/stats';
const POSMV_SERV = 'http://localhost:8080/assignment2_server_20020003/jack/possiblemoves';
let state = null;
initialiseState(STATE_SERV);

// retrieve game state from state servlet
function initialiseState(URL) {
    fetch(URL, {method: 'GET'})
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                state = data;
                setCards(state);
            });
}

// parse game state and display cards
function setCards(state) {
    let userCards = state.userHand.split(" ");
    let dealerCards = state.dealerHand.split(" ");

// set up user card suits and values for display
    for (let i = 0; i < userCards.length; i += 2) {
        if (userCards[i].includes("SPADES")) {
            let cardSuit = document.createElement("div");
            cardSuit.className = "spades";
            let valueSpan = document.createElement("span");
            cardSuit.appendChild(valueSpan);

            let cardValue = userCards[i + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("usercards").appendChild(cardSuit);

            continue;
        } else if (userCards[i].includes("CLUBS")) {
            let cardSuit = document.createElement("div");
            cardSuit.className = "clubs";
            let valueSpan = document.createElement("span");
            cardSuit.appendChild(valueSpan);

            let cardValue = userCards[i + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("usercards").appendChild(cardSuit);

            continue;
        } else if (userCards[i].includes("DIAMONDS")) {
            let cardSuit = document.createElement("div");
            cardSuit.className = "diamonds";
            let valueSpan = document.createElement("span");
            cardSuit.appendChild(valueSpan);

            let cardValue = userCards[i + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("usercards").appendChild(cardSuit);

            continue;
        } else if (userCards[i].includes("HEARTS")) {
            let cardSuit = document.createElement("div");
            cardSuit.className = "hearts";
            let valueSpan = document.createElement("span");
            cardSuit.appendChild(valueSpan);

            let cardValue = userCards[i + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("usercards").appendChild(cardSuit);

            continue;
        }
    }

// set up dealer card suits for display
    for (let j = 0; j < dealerCards.length; j += 2) {
        if (dealerCards[j].includes("SPADES")) {
            let cardSuit = document.createElement("div");
            cardSuit.classList.add("spades");
            let valueSpan = document.createElement("span");
            if (j === dealerCards.length - 1 || j === dealerCards.length - 2) {
                valueSpan.setAttribute("id", "hide");
            }
            cardSuit.appendChild(valueSpan);

            let cardValue = dealerCards[j + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("dealercards").appendChild(cardSuit);

            continue;
        } else if (dealerCards[j].includes("CLUBS")) {
            let cardSuit = document.createElement("div");
            cardSuit.classList.add("clubs");
            let valueSpan = document.createElement("span");
            if (j === dealerCards.length - 1 || j === dealerCards.length - 2) {
                valueSpan.setAttribute("id", "hide");
            }
            cardSuit.appendChild(valueSpan);

            let cardValue = dealerCards[j + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("dealercards").appendChild(cardSuit);

            continue;
        } else if (dealerCards[j].includes("DIAMONDS")) {
            let cardSuit = document.createElement("div");
            cardSuit.classList.add("diamonds");
            let valueSpan = document.createElement("span");
            if (j === dealerCards.length - 1 || j === dealerCards.length - 2) {
                valueSpan.setAttribute("id", "hide");
            }
            cardSuit.appendChild(valueSpan);

            let cardValue = dealerCards[j + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("dealercards").appendChild(cardSuit);

            continue;
        } else if (dealerCards[j].includes("HEARTS")) {
            let cardSuit = document.createElement("div");
            cardSuit.classList.add("hearts");
            let valueSpan = document.createElement("span");
            if (j === dealerCards.length - 1 || j === dealerCards.length - 2) {
                valueSpan.setAttribute("id", "hide");
            }
            cardSuit.appendChild(valueSpan);

            let cardValue = dealerCards[j + 1];
            let num = cardValue.split("=");
            num = num[1].slice(0, -2);
            valueSpan.innerHTML = num;
            document.getElementById("dealercards").appendChild(cardSuit);

            continue;
        }
    }
}

// retrieve game statistics from stats servlet
function getStats(URL) {
    let stats = document.getElementById("stats");
    fetch(URL, {method: 'GET'})
            .then(function (response) {
                return response.text();
            })
            .then(function (string) {
                stats.innerHTML = string;
            });
}

// request moves available to user
//function possMoves(URL) {
//    fetch(URL, {method: 'GET'})
//            .then(function (response) {
//                return response.text();
//            })
//            .then(function (data) {
//                reveal = data;
//                setCards(reveal);
//            });
//                if (string.includes("hit")) {
//                    console.log(string);
//                    console.log("returning false");
//                    return false;
//                } else {
//                    console.log(string);
//                    console.log("returning true");
//                    return true;
//                }
//            });


//    let gameEnd = document.getElementById("winner");
//    if (gameEnd.textContent !== ""){
//        initialiseState(STATE_SERV, false);
//        
//    }
