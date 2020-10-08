package code.tournoi.squash.controler;

import dao.JpaUtil;
import code.tournoi.squash.view.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }

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
        String action = request.getParameter("todo");

        if (action == null) {
            return;
        }

        switch (action) {

            case "authentificate": //Connexion : connect (mail, password)
                new ActionAuthentification().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "subscribe":
                new ActionSubscribe().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "getUserData":
                new ActionGetUserData().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "changerMdp":
                new ActionChangeMdp().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "disconnect":
                new ActionDisconnect().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "afficheSports":
                new ActionAfficheSports().execute(request);
                new SerialisationAfficheSports().serialise(request, response);
                break;

            case "supprimerJoueur":
                new ActionSupprimerJoueur().execute(request);
                new ActionDisconnect().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "pauseJoueur":
                new ActionPauseJoueur().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "modif":
                new ActionModifierInformations().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "demandeSport":
                new ActionDemandeSport().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "getSportLevel":
                new ActionGetSportLevel().execute(request);
                new SerialisationGetLevelSport().serialise(request, response);
                break;

            case "inscriptionSport":
                new ActionInscriptionSport().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "matchDemandes":
                new ActionMatchDemandes().execute(request);
                new SerialisationMatchDemandes().serialise(request, response);
                break;

            case "futurMatch":
                new ActionFuturMatch().execute(request);
                new SerialisationFuturMatch().serialise(request, response);
                break;

            case "infoMatch":
                new ActionInfoMatch().execute(request);
                new SerialisationMatch().serialise(request, response);
                break;

            case "nouvelleDate":
                new ActionNouvelleDate().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "matchAccepte":
                new ActionMatchAccepte().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "annoncerResultats":
                new ActionAnnoncerResultats().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "getClassement":
                new ActionGetClassement().execute(request);
                new SerialisationClassement().serialise(request, response);
                break;

            case "getDefiePossible":
                new ActionGetDefiePossible().execute(request);
                new SerialisationClassement().serialise(request, response);
                break;

            case "getUserInfo":
                new ActionGetUserInfo().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "creerMatch":
                new ActionCreerMatch().execute(request);
                new SerialisationMatch().serialise(request, response);
                break;

            case "getHistorique":
                new ActionGetHistorique().execute(request);
                new SerialisationListeMatch().serialise(request, response);
                break;

            case "ajoutSport":
                new ActionAjoutSport().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "afficheMatch":
                new ActionAfficheMatch().execute(request);
                new SerialisationListeMatch().serialise(request, response);
                break;

            case "afficheJoueurs":
                new ActionAfficheJoueurs().execute(request);
                new SerialisationAfficheJoueurs().serialise(request, response);
                break;

            case "pauseCompte":
                new ActionPauseCompte().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "supprimerCompte":
                new ActionSupprimerCompte().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "modifieNiveau":
                new ActionModifieNiveau().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;

            case "getUserLevel":
                new ActionGetUserLevel().execute(request);
                new SerialisationGetUserLevel().serialise(request, response);
                break;

            case "getAllAdmin":
                new ActionGetAllAdmin().execute(request);
                new SerialisationGetAllAdmin().serialise(request, response);
                break;

            case "accepteJoueur":
                new ActionAccepteJoueur().execute(request);
                new SerialisationBoolean().serialise(request, response);
                break;
                
            case "notif":
                new ActionNotif().execute(request);
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
