
import db.Empleado;
import db.Nomina;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ln.EmpleadoWS;

@WebServlet(urlPatterns = {"/EmpleadoController"})
public class EmpleadoController extends Controller {
    
    @WebServiceRef(wsdlLocation="http://localhost:8080/ProyectoSSDD3_LN/EmpleadoWS?wsdl")
    static EmpleadoWS service;
    
    private static String EMPLEADOS = "Empleados";
        
    public void actionProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String id = request.getParameter("id");
        
        String name = "Unknow";
        
        List<String> footer = new LinkedList<String>();
        
        if(!Functions.isID(id)) {
            request.setAttribute(ERROR, "Empleado incorrecto");
        } else {
            int id_empleado = Integer.parseInt(id);
            Empleado emp = service.getEmpleado(id_empleado);
            if (emp == null) {
                request.setAttribute(ERROR, "Empleado no encontrado.");
            } else {
                name = emp.getName() + " " + emp.getSurname();
                List<Nomina> nominas = service.obtenerNominas(id_empleado); // Obtenemos la lista de nóminas del empleado
                if (nominas == null) {
                    nominas = new LinkedList<Nomina>();
                }
                System.out.println("SIZE: " + nominas.size());
                float salario = 0, retencion = 0;     // Extraemos la media de los salarios y del % de retención
                for(Nomina nom: nominas) {
                    salario += nom.getSalario();
                    retencion += nom.getRetencion();
                }
                int totalNominas = nominas.size();
                request.setAttribute("salariomedio", Math.rint((salario/totalNominas)*100)/100);
                request.setAttribute("retencionmedia",Math.rint((retencion/totalNominas)*100)/100);
                
                request.setAttribute("empleado", emp);
                request.setAttribute("nominas", nominas);
                footer.add("assets/js/plugins/datatables/jquery.dataTables.js");
                footer.add("assets/js/plugins/datatables/dataTables.bootstrap.js");
                footer.add("assets/js/empleado/profile.js");
                footer.add("assets/js/nomina/create.js");
            }
        }
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Perfil");
        ltv.add(name);
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("empleado/profile.jsp", "", tv, null, footer, null, "", true, "Perfil " + name);
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }
    
    public void postUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String name = request.getParameter("namefield");
        String surname = request.getParameter("surnamefield");
        String dni = request.getParameter("dnifield");
        String id_aux = request.getParameter("idfield");
        String user = request.getParameter("userfield");
        String pass = request.getParameter("passwordfield");
        String departamento = request.getParameter("departamentofield");
        String sucursal = request.getParameter("sucursalfield");
        /*String charSec = request.getParameter("ac");
        String intSec = request.getParameter("ai");*/

        String error = "";

        // Comprobamos los datos
        if (Functions.isEmpty(name)) {
            error += "<li>Nombre está vacío</li>";
        }
        if (Functions.isEmpty(surname)) {
            error += "<li>Apellido está vacío</li>";
        }
        if (!Functions.isDNI(dni)) {
            error += "<li>DNI está vacío</li>";
        }
        if (Functions.isEmpty(user)) {
            error += "<li>Usuario está vacío</li>";
        }
        if (Functions.isEmpty(pass)) {
            error += "<li>Password está vacío</li>";
        }
        if (Functions.isEmpty(departamento)) {
            error += "<li>Departamento está vacío</li>";
        }
        if (Functions.isEmpty(sucursal)) {
            error += "<li>Sucursal está vacío</li>";
        }
        if (!Functions.isID(id_aux)) {                  //Esto al ser un campo oculto debería mostrarlo como un error oculto
            error += "<li>Empleado incorrecto.</li>";
        }

        // Comprobamos que sucursal está bien
        if(!Functions.correctSucursal(sucursal)) {
            error += "<li>La sucursal es incorrecta.</li>";
        }
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
        NumChar generateSecurity = Functions.generateNumChar(random);
        
        NumChar formSecurity = new NumChar(intSec, charSec);
        
        if(!generateSecurity.equals(formSecurity)) {
            error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
        }*/
        
        List<String> footer = new LinkedList<String>();
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Perfil");
        ltv.add("Editar");
        
        // Si la variable error tiene contenido, salimos y marcamos los errores
        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
            ltv.add("Error");
            request.setAttribute("id", id_aux);
            /*request.setAttribute("asociatedchar", charSec);
            request.setAttribute("asociatedpos", intSec);*/
        } else {
    
            int id = Integer.parseInt(id_aux);          // Llegados aquí, al ir todo bien, insertamos Empleado
            boolean update = service.updateEmpleado(id, dni, user, pass, name, surname, departamento, sucursal);
            if(update) {
                request.setAttribute("ok", "Editado con éxito");
                footer.add("assets/js/empleado/update.js");
            } else {
                request.setAttribute(ERROR, "No se ha podido actualizar. Pruebe de nuevo.");
                ltv.add("Error");
                request.setAttribute("departamento", departamento);
                request.setAttribute("dni", dni);
                request.setAttribute("name", name);
                request.setAttribute("password", pass);
                request.setAttribute("sucursal", sucursal);
                request.setAttribute("surname", surname);
                request.setAttribute("username", user);
                request.setAttribute("id", id);
                /*request.setAttribute("asociatedchar", charSec);
                request.setAttribute("asociatedpos", intSec);*/
            }
        }
        
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("empleado/update.jsp", "", tv, null, footer, null, "", true, "Actualizar " + user);
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }
    
    public void actionEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        
        String id_aux = request.getParameter("id");
        
        List<String> footer = new LinkedList<String>();
        
        String name = "Unknow";
        if(!Functions.isID(id_aux)) {
            request.setAttribute(ERROR, "Identificador incorrecto " + id_aux);
        } else {
            int id = Integer.parseInt(id_aux);
            Empleado emp = service.getEmpleado(id);
            if(emp == null) {
                request.setAttribute(ERROR, "Empleado no encontrado");
            } else {
                name = emp.getName();
                
                String random = Functions.updateSecurity(request.getSession(true));  // Código seguridad de usuario
        
                NumChar generateSecurity = Functions.generateNumChar(random);

                request.setAttribute("asociatedchar", generateSecurity.getChar());
                request.setAttribute("asociatedpos", generateSecurity.getPos());
                
                request.setAttribute("departamento", emp.getDepartamento());
                request.setAttribute("dni", emp.getDni());
                request.setAttribute("id", emp.getId());
                request.setAttribute("name", emp.getName());
                request.setAttribute("password", emp.getPassword());
                request.setAttribute("sucursal", emp.getSucursal());
                request.setAttribute("surname", emp.getSurname());
                request.setAttribute("username", emp.getUsername());
                footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
                footer.add("assets/js/empleado/edit.js");
            }
        }
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Perfil");
        ltv.add(name);
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        PageTemplate pt = new PageTemplate("empleado/update.jsp", "", tv, null, footer, null, "", true, "Editar " + name);
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
        
    }
    
    /**
     * Accedemos con AJAX, así que no redirigimos a ningún sitio :)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    public void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String dni = request.getParameter("dni");
        String id = request.getParameter("id");
        //String data = "";
        // Comprobamos datos
        if(!Functions.isDNI(dni)) {
            request.setAttribute(ERROR, "DNI incorrecto");
        } else if (!Functions.isID(id)) {
            request.setAttribute(ERROR, "ID incorrecto");
        } else {
            Empleado empleado = service.getEmpleado(Integer.parseInt(id));
            if(empleado != null) {
                if(empleado.getName().equals((String)request.getSession(true).getAttribute("user_active"))) {
                    request.setAttribute(ERROR, "No te puedes borrar a ti mismo");
                } else {
                    boolean delete = service.deleteEmpleado(dni);  //facade.deleteEmpleado(id);    // Borramos el empleado
                    if(delete) {
                        request.setAttribute("ok", "Se ha borrado con éxito");
                    } else {
                        request.setAttribute(ERROR, "No se ha podido borrar");
                    }
                }
            } else {
                request.setAttribute(ERROR, "El empleado no existe");
            }
        }
        request.setAttribute("id", dni.hashCode());
        this.actionList(request, response);
    }
    
    public void actionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        
        List<Empleado> empleados = service.getAllEmpleados();
        if(empleados == null) {
            empleados = new ArrayList<Empleado>();
        }
        request.setAttribute("empleados", empleados);
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Listar");
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        List<String> footer = new LinkedList<String>();
        footer.add("assets/js/plugins/datatables/jquery.dataTables.js");
        footer.add("assets/js/plugins/datatables/dataTables.bootstrap.js");
        footer.add("assets/js/empleado/list.js");
        
        PageTemplate pt = new PageTemplate("empleado/list.jsp", "", tv, null, footer, null, "", true, "Lista empleados");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
         
    }
    
    public void actionCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
            
        String random = Functions.updateSecurity(request.getSession(true));  // Código seguridad de usuario
        
        NumChar generateSecurity = Functions.generateNumChar(random);
        
        request.setAttribute("asociatedchar", generateSecurity.getChar());
        request.setAttribute("asociatedpos", generateSecurity.getPos());
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Crear");
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        List<String> footer = new LinkedList<String>();
        footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
        footer.add("assets/js/empleado/create.js");
        
        PageTemplate pt = new PageTemplate("empleado/create.jsp", "", tv, null, footer, null, "", true, "Crear empleado");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }

    public void postCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        checkAccessLogin(request, response);
        String name = request.getParameter("namefield");
        String surname = request.getParameter("surnamefield");
        String dni = request.getParameter("dnifield");
        String user = request.getParameter("userfield");
        String pass = request.getParameter("passwordfield");
        String departamento = request.getParameter("departamentofield");
        String sucursal = request.getParameter("sucursalfield");
        String charSec = request.getParameter("ac");
        String intSec = request.getParameter("ai");
        
        String error = "";

        // Comprobamos los datos
        if(Functions.isEmpty(charSec)) {
            charSec = "#";      // Selecciono un valor incorrecto
        }
        if(Functions.isEmpty(intSec)) {
            intSec = "-1";      // Selecciono un valor incorrecto  
        }        
        if (Functions.isEmpty(name)) {
            error += "<li>Nombre está vacío</li>";
        }
        if (Functions.isEmpty(surname)) {
            error += "<li>Apellido está vacío</li>";
        }
        if (!Functions.isDNI(dni)) {
            error += "<li>DNI está vacío</li>";
        }
        if (Functions.isEmpty(user)) {
            error += "<li>Usuario está vacío</li>";
        }
        if (Functions.isEmpty(pass)) {
            error += "<li>Password está vacío</li>";
        }
        if (Functions.isEmpty(departamento)) {
            error += "<li>Departamento está vacío</li>";
        }
        if (Functions.isEmpty(sucursal)) {
            error += "<li>Sucursal está vacío</li>";
        }

        // Comprobamos que sucursal está bien
        if(!Functions.correctSucursal(sucursal)) {
            error += "<li>La sucursal es incorrecta.</li>";
        }
        
        /*String random = (String) request.getSession(true).getAttribute("random_active");  // Extraigo el user conectado
        
        NumChar generateSecurity = Functions.generateNumChar(random);
        
        NumChar formSecurity = new NumChar(intSec, charSec);
        
        if(!generateSecurity.equals(formSecurity)) {
            error += "<li>Se ha producido un problema de seguridad. " + generateSecurity.toString() + " : " + formSecurity.toString() + "</li>";
        }*/

        // Si la variable error tiene caracteres, salimos y marcamos los errores
        if (!error.isEmpty()) {
            request.setAttribute(ERROR, "<ul>" + error + "</ul>");
        } else {
            // Llegados aquí, va todo bien
            // Insertamos Empleado
            Object result = service.insertEmpleado(dni, surname, pass, name, surname, departamento, sucursal);

            if (result instanceof Boolean) {
                boolean insert = (Boolean) result;
                if (insert) {
                    request.setAttribute("ok", "Insertado con éxito");
                } else {
                    request.setAttribute(ERROR, "No se ha podido ingresar. Pruebe de nuevo.");
                }
            } else if (result instanceof String) {
                request.setAttribute(ERROR, result);
            } else {
                request.setAttribute(ERROR, "Se ha producido un error desconodido.");
            }
        }
        
        List<String> ltv = new LinkedList<String>();
        ltv.add(EMPLEADOS);
        ltv.add("Crear");
        TreeView tv = new TreeView(ltv, DASHBOARD);
        
        List<String> footer = new LinkedList<String>();
        footer.add("http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js");
        footer.add("assets/js/empleado/create.js");
        
        PageTemplate pt = new PageTemplate("empleado/create.jsp", "", tv, null, footer, null, "", true, "Crear empleado");
        request.getSession().setAttribute(templatepage, pt);
        
        getServletContext().getRequestDispatcher(TEMPLATE).forward(request, response);
    }
}
