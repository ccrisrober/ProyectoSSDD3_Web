
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ln.EmpleadoWS;

@WebServlet(urlPatterns = {"/Index"})
public class Index extends Controller {

    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/EmpleadoWS?wsdl")
    static EmpleadoWS empleadoService;

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
        //if(!checkAccessLogin(request, response)) {

        obtenerPorcentajesMapa(request);

        List<String> ltv = new LinkedList<String>();

        TreeView tv = new TreeView(ltv, "fa-dashboard");

        List<String> header = new LinkedList<String>();
        header.add("assets/css/jvectormap/jquery-jvectormap-1.2.2.css");

        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js");
        footer.add("assets/js/plugins/jvectormap/jquery-jvectormap-es-mill-en.js");
        footer.add("assets/js/index/index.js");

        List<String> jspservlet = new LinkedList<String>();
        jspservlet.add("index.jsp");

        PageTemplate pt = new PageTemplate("index.jsp", "index", tv, header, footer, jspservlet, "", true, "Dashboard");
        request.getSession().setAttribute("templatepage", pt);

        getServletContext().getRequestDispatcher("/templates/template.jsp").forward(request, response);

        //}
    }

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
    }

    private void obtenerPorcentajesMapa(HttpServletRequest request) {
        List<Float> datosmapa = empleadoService.totalEmpleados();    // 0: Soria, 1: Teruel

        request.setAttribute("datosSoria", datosmapa.get(0));
        request.setAttribute("datosTeruel", datosmapa.get(1));
    }

}
