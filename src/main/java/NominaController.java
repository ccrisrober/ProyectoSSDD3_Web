
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.Empleado;
import db.Nomina;
import db.VisitaNomina;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ln.EmpleadoWS;
import ln.NominaWS;
import ln.VisitaNominaWS;

@WebServlet(urlPatterns = {"/NominaController"})
public class NominaController extends Controller {

    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/EmpleadoWS?wsdl")
    static EmpleadoWS empleadoService;
    
    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/ominaWS?wsdl")
    static NominaWS nominaService;
    
    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/VisitaNominaWS?wsdl")
    static VisitaNominaWS vnService;
    
    private static String ID_EMP = "id_emp";
    private static String NOMINA = "Nómina";
    
    private List<String> addValidation (List<String> footer) {
        footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
        footer.add("assets/js/nomina/create.js");
        return footer;
    }

    public void postCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String id_aux = request.getParameter("id_empfield");
        String date = request.getParameter("datefield");
        String salario_aux = request.getParameter("salariofield");
        String retencion_aux = request.getParameter("retencionfield");
        
        String data = "";
        if (!Functions.isID(id_aux)) {
            data += "<li>Empleado desconocido</li>";
        }
        if(!Functions.isDate(date)) {
            data += "<li>Fecha vacía o incorrecta.</li>";
        }
        if(!Functions.isSalario(salario_aux)) {
            data += "<li>Salario vacío o incorrecto.</li>";
        }
        if(!Functions.isPorcentaje(retencion_aux)) {
            data += "<li>Retención vacía o incorrecta.</li>";
        }
        
        List<String> footer = new LinkedList<String>();
        if (data.isEmpty()) {
            int id = Integer.parseInt(id_aux);
            double salario = Double.parseDouble(salario_aux);
            double retencion = Double.parseDouble(retencion_aux);
            boolean insertNomina = nominaService.insertNomina(id, date, salario, retencion);

            if (insertNomina) {          // Se ha insertado
                request.setAttribute("ok", "Insertado con éxito.");
            } else {
                data = "Problema al insertar.";
            }
        }
        if(!data.isEmpty()) {
            request.setAttribute(ERROR, data);
        
            footer = addValidation(footer);
        }
        request.setAttribute(ID_EMP, id_aux);
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(NOMINA);
        ltv.add("Crear");
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("nomina/create.jsp", "", tv, null, footer, null, "", true, "Crear nómina");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }

    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String id_emp = request.getParameter(ID_EMP);
        String id_nom_aux = request.getParameter("id_nom");
        
        // Comprobamos que los datos son buenos
        if(!Functions.isID(id_nom_aux)) {
            request.setAttribute(ERROR, "Nónima desconocida");
        } else {
         // Llegamos aquí, todo debe ir bien
            // Borramos la nómina
            boolean delete = nominaService.deleteNomina(Integer.parseInt(id_nom_aux));
            if(!delete) {
                request.setAttribute(ERROR, "No se ha podido borrar");
            } else {
                request.setAttribute("ok", "Se ha borrado con éxito");
            }
        }
        response.sendRedirect("EmpleadoController?action=profile&id=" + id_emp);
    }

    public void actionEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);

        String id_emp_aux = request.getParameter(ID_EMP);
        String id_nom_aux = request.getParameter("id_nom");

        // Comprobamos que los datos son buenos
        String error = checkIDs(id_emp_aux, id_nom_aux);
        
        List<String> footer = new LinkedList<String>();
        if(error.isEmpty()) {
            int id_nom = Integer.parseInt(id_nom_aux);
            Nomina nom = nominaService.getNomina(id_nom);
            if(nom == null) {
                error = "No se encuentra nómina.";
                System.out.println("No hay nómina");
            } else {
                request.setAttribute("id_empleado", nom.getIdEmpleado());
                request.setAttribute("id_nomina", nom.getIdNomina());
                request.setAttribute("date", nom.getFecha());
                request.setAttribute("salario", nom.getSalario());
                request.setAttribute("retencion", nom.getRetencion());
                footer = addValidation(footer);
                System.out.println("Habemus nómina");
            }
        }
        if(!error.isEmpty()) {
            request.setAttribute(ERROR, error);
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add("Nóminas");
        ltv.add("Editar");
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("nomina/update.jsp", "", tv, null, footer, null, "", true, "Editar nómina");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }

    public void postUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String id_emp_aux = request.getParameter("id_empfield");
        String id_nom_aux = request.getParameter("id_nomfield");
        String date = request.getParameter("datefield");
        String salario_aux = request.getParameter("salariofield");
        String retencion_aux = request.getParameter("retencionfield");
        
        String data = "";
        if(!Functions.isID(id_emp_aux)) {
            data += "<li>Empleado no encontrado</li>";
        }
        if(!Functions.isID(id_nom_aux)) {
            data += "<li>Nómina no encontrada</li>";
        }
        if(!Functions.isDate(date)) {
            data += "<li>Fecha incorrecta</li>";
        }
        if(!Functions.isSalario(salario_aux)) {
            data += "<li>Salario incorrecto</li>";
        }
        if(!Functions.isPorcentaje(retencion_aux)) {
            data += "<li>Porcentaje de retención incorrecto</li>";
        }
        List<String> footer = new LinkedList<String>();
        
        List<String> ltv = new LinkedList<String>();
        ltv.add("Nominas");
        ltv.add("Editar");
        
        if(!data.isEmpty()) {
            request.setAttribute(ERROR, data);
            
            request.setAttribute("id_empleado", id_emp_aux);
            request.setAttribute("id_nomina", id_nom_aux);
            Nomina nom = nominaService.getNomina(Integer.parseInt(id_nom_aux));
            if(nom != null) {
                request.setAttribute("id_empleado", nom.getIdEmpleado());
                request.setAttribute("id_nomina", nom.getIdNomina());
                request.setAttribute("date", nom.getFecha());
                request.setAttribute("salario", nom.getSalario());
                request.setAttribute("retencion", nom.getRetencion());
                footer = addValidation(footer);
                System.out.println("Habemus nómina");
            }
        } else {
            int id_emp = Integer.parseInt(id_emp_aux);
            int id_nom = Integer.parseInt(id_nom_aux);
            Float salario = Float.parseFloat(salario_aux);
            Float retencion = Float.parseFloat(retencion_aux);
            boolean update = nominaService.updateNomina(id_emp, id_nom, date, salario, retencion);
            if(update) {
                request.setAttribute("ok", "Nómina editada con éxito");
                request.setAttribute("id_empleado", id_emp);
                footer.add("assets/js/nomina/update.js");
                
            } else {
                request.setAttribute(ERROR, "No se ha podido editar. Pruebe de nuevo.");
                ltv.add("Error");
                request.setAttribute("id_empleado", id_emp_aux);
                request.setAttribute("id_nomina", id_nom_aux);
                Nomina nom = nominaService.getNomina(id_nom);
                if(nom != null) {
                    request.setAttribute("id_empleado", nom.getIdEmpleado());
                    request.setAttribute("id_nomina", nom.getIdNomina());
                    request.setAttribute("date", nom.getFecha());
                    request.setAttribute("salario", nom.getSalario());
                    request.setAttribute("retencion", nom.getRetencion());
                    footer = addValidation(footer);
                    System.out.println("Habemus nómina");
                }
            }
        }
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("nomina/update.jsp", "", tv, null, footer, null, "", true, "Actualizar nómina");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }

    public void actionImprimir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String id_emp_aux = request.getParameter(ID_EMP);
        String error = "";

        if (!Functions.isID(id_emp_aux)) {
            error += "<li>Empleado desconocido</li>";
        }

        // Comprobar errores
        if (error.isEmpty()) {
            try {
                int id_emp = Integer.parseInt(id_emp_aux);

                OutputStream out = response.getOutputStream();

                Empleado emp = empleadoService.getEmpleado(id_emp);

                Document document = new Document();
                PdfWriter.getInstance(document, out).setInitialLeading(20);
                document.open();

                if (emp != null) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    java.util.Date utilDate = cal.getTime();
                    java.sql.Date sqlDate = new Date(utilDate.getTime());
                    vnService.insertVisitaNominaEmpleado(emp.getId(), sqlDate.toString());

                    document.add(new Paragraph(
                        "Nominas de " + emp.getName() + " " + emp.getSurname(),
                        FontFactory.getFont(
                            "arial", // fuente
                            22, // tamaño
                            Font.ITALIC, // estilo
                            BaseColor.BLACK
                        )
                    ));

                    com.itextpdf.text.List overview = new com.itextpdf.text.List(false, 10);
                    overview.add(new ListItem("Usuario: " + emp.getUsername()));
                    overview.add(new ListItem("Nombre: " + emp.getName()));
                    overview.add(new ListItem("Apellidos: " + emp.getSurname()));
                    overview.add(new ListItem("Departamento: " + emp.getDepartamento()));
                    overview.add(new ListItem("Sucursal: " + emp.getSucursal()));
                    document.add(overview);

                    Paragraph paragraph1 = new Paragraph("");
                    paragraph1.setSpacingAfter(10);
                    paragraph1.setSpacingBefore(10);
                    document.add(paragraph1);

                    PdfPTable table = createTable(id_emp);

                    table.setWidthPercentage(80);
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    document.add(table);

                } else {
                    document.add(new Paragraph("No se encuentra empleado"));
                }

                document.close();
            } catch (DocumentException ex) {
                Logger.getLogger(NominaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NominaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     * Creates a table; widths are set with setWidths().
     *
     * @return a PdfPTable
     * @throws DocumentException
     */
    private PdfPTable createTable(int id_emp) throws DocumentException {
        PdfPTable table = new PdfPTable(5); // A columnas

        Font font = new Font();
        font.setStyle(Font.BOLD);

        PdfPCell cell1 = new PdfPCell(new Phrase("ID", font));
        PdfPCell cell2 = new PdfPCell(new Phrase("Mes", font));
        PdfPCell cell3 = new PdfPCell(new Phrase("Año", font));
        PdfPCell cell4 = new PdfPCell(new Phrase("Sueldo", font));
        PdfPCell cell5 = new PdfPCell(new Phrase("Retención (%)", font));

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);

        List<Nomina> nominas = nominaService.getNominasEmpleado(id_emp);

        if (nominas != null) {
            String[] split;
            int i = 0;
            for (Nomina n : nominas) {
                PdfPCell cellNum = new PdfPCell(new Phrase(++i + ""));
                cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellNum);

                split = n.getFecha().split("-");

                PdfPCell cellYear = new PdfPCell(new Phrase(split[0]));
                PdfPCell cellMonth = new PdfPCell(new Phrase(split[1]));
                cellMonth.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellYear.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cellMonth);
                table.addCell(cellYear);
                PdfPCell cellSalario = new PdfPCell(new Phrase(n.getSalario() + ""));
                PdfPCell cellRetencion = new PdfPCell(new Phrase(n.getRetencion() + ""));
                cellSalario.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellRetencion.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellSalario);
                table.addCell(cellRetencion);
            }
        }
        return table;
    }

    private String checkIDs(String id_emp, String id_nom) {
        // Comprobamos que los datos son buenos
        String data = "";
        if (!Functions.isID(id_emp)) {
            data += "<li>El empleado no existe existe, o es incorrecto";
        }
        if (!Functions.isID(id_nom)) {
            data += "<li>La nómina no existe o es incorrecto";
        }
        return data;
    }

    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);

        String id_emp_aux = request.getParameter(ID_EMP);
        
        if(!Functions.isID(id_emp_aux)) {
            return;
        }
        request.setAttribute(ID_EMP, id_emp_aux);
        
        List<String> ltv = new LinkedList<String>();
        //ltv.add("Dashboard");     // El primero siempre es dashboard
        ltv.add(NOMINA);
        ltv.add("Crear");
        
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        List<String> footer = new LinkedList<String>();
        
        footer = addValidation(footer);
        
        PageTemplate pt = new PageTemplate("nomina/create.jsp", "", tv, null, footer, null, "", true, "Crear nómina");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);

    }

    public void actionShow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);

        String id_n_aux = request.getParameter("id_nom");
        String dni = request.getParameter("dnifield");
        int id_n = 0;
        String error = "";
        if(!Functions.isDNI(dni)) {
            error += "<li>No se encuentra empleado.</li>";
        }
        if (!Functions.isInteger(id_n_aux)) {
            error += "<li>Nómina no encontrada.</li>";
        } else {
            id_n = Integer.parseInt(id_n_aux);
        }

        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
        } else {
            Nomina nom = nominaService.getNomina(id_n);        // Extraemos la nómina con id = "id_n"

            if(nom == null) {
                request.setAttribute(ERROR, "Nómina no encontrada");
            } else {
                request.setAttribute("nomina", nom);
                //Aquí insertamos parte de la consulta de +0.5
                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new Date(utilDate.getTime());
                vnService.insertVisitaNomina(nom.getIdNomina(), dni, sqlDate.toString());
            }
        }
        List<String> ltv = new LinkedList<String>();
        //ltv.add("Dashboard");     // El primero siempre es dashboard
        ltv.add(NOMINA);
        ltv.add("Ver " + id_n);
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("nomina/view.jsp", "", tv, null, null, null, "", true, "Ver nómina");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);

    }
    
    
    public void actionImprimirHistorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        try {
            String dni = request.getParameter("dnifield");
            String error = "";

            if (!Functions.isDNI(dni)) {
                error += "<li>Empleado desconocido</li>";
            }
            
            OutputStream out = response.getOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out).setInitialLeading(20);
            document.open();

            Paragraph paragraph1 = new Paragraph("");
            paragraph1.setSpacingAfter(10);
            paragraph1.setSpacingBefore(10);
            document.add(paragraph1);

            PdfPTable table = createTableHistorico(dni, request);

            table.setWidthPercentage(80);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            document.add(table);

            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(NominaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NominaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    /**
     * Creates a table; widths are set with setWidths().
     *
     * @return a PdfPTable
     * @throws DocumentException
     */
    private PdfPTable createTableHistorico(String dni, HttpServletRequest request) throws DocumentException {
        PdfPTable table = new PdfPTable(3); // A columnas

        Font font = new Font();
        font.setStyle(Font.BOLD);

        PdfPCell cell1 = new PdfPCell(new Phrase("ID", font));
        PdfPCell cell2 = new PdfPCell(new Phrase(NOMINA, font));
        PdfPCell cell3 = new PdfPCell(new Phrase("Fecha", font));

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        List<VisitaNomina> tickets = vnService.getTicket(dni);

        if (tickets != null) {
            int i = 0;
            for (VisitaNomina vn: tickets) {
                System.out.println(vn);
                PdfPCell cellNum = new PdfPCell(new Phrase(++i + ""));
                cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellNum);

                PdfPCell cell_nom;  // = new PdfPCell(new Phrase(vn.getId_nomina() + ""));
                PdfPCell cell_date = new PdfPCell(new Phrase(vn.getFecha()));
                cell_date.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                Anchor anchor = new Anchor(vn.getIdNomina() + "");
                anchor.setReference(this.getURL_hyperlink(request) + "NominaController?action=show&id_nom=" + vn.getIdNomina() + "&dnifield=" + vn.getDni());

                anchor.setName(vn.getIdNomina() + "");
                Phrase myurl = new Phrase();
                myurl.add(anchor);
                cell_nom = new PdfPCell(myurl);
                cell_nom.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell_nom);
                table.addCell(cell_date);
                
            }
        }
        return table;
    }
}
