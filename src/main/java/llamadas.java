
import db.Empleado;
import db.Nomina;
import java.util.List;
import ln.EmpleadoWS;
import ln.EmpleadoWS_Service;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class llamadas {
    
    public static void main(String[] argv) {
        EmpleadoWS_Service ws = new EmpleadoWS_Service();
        EmpleadoWS port = ws.getEmpleadoWSPort();
        List<Empleado> nominas = port.getAllEmpleados();
        for(Empleado n: nominas) {
            System.out.println(n.getName());
        }
    }
}
