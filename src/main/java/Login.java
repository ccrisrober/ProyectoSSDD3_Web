
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import ln.UserWS;

@WebServlet(urlPatterns = {"/Login"})
public class Login extends Controller {

    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/UserWS?wsdl")
    static UserWS service;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/pages/login.jsp").forward(request, response);
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
        HttpSession session = request.getSession(true);
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        if (Functions.isEmpty(user) || Functions.isEmpty(password)) {
            request.setAttribute("error", "Ingresa valores en ambos campos");
            getServletContext().getRequestDispatcher("/pages/login.jsp").forward(request, response);
        } else {
            boolean logueado = service.validar(user, password);

            if (!logueado) {
                request.setAttribute("error", "Datos incorrectos");
                getServletContext().getRequestDispatcher("/pages/login.jsp").forward(request, response);
            } else {
                // Creo variables sesión
                session.setAttribute("user_active", user);        // Nombre usuario
                Functions.updateSecurity(session);

                response.sendRedirect("Index");
            }

        }
    }
}
