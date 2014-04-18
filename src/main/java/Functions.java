
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class Functions {
    private static final Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
    private static final String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private static boolean validateDNI(String dni) {
        boolean correcto = false;
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            correcto = reference.equalsIgnoreCase(letra);
        } else {
            correcto = false;
        }
        return correcto;   //Quitar el !, es para hacer pruebas
    }
    
    public static boolean isDNI(String dni) {
        return !Functions.isEmpty(dni) && Functions.validateDNI(dni);
    }
    
    public static boolean isInteger(String data) {
        boolean isCorrect = false;
        if(data != null && !data.isEmpty()) {
            try {
                Integer.parseInt(data);
                isCorrect = true;
            } catch(NumberFormatException cce) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }
    
    private static Collection<String> sucursales;
    
    public static boolean correctSucursal(String sucursal) {
        if(sucursales == null) {
            sucursales = new HashSet<String>();
            sucursales.add("Teruel");
            sucursales.add("Soria");
        }
        return sucursales.contains(sucursal);
    }
    
    /*public static void checkIncorrectAccess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }*/
    
    public static String randomString(String original) {
        String exit = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
            exit = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exit;
    }

    public static NumChar generateNumChar(String randomChar) {
        return new NumChar(randomChar);
    }

    static boolean isID(String id) {
        boolean exit = false;
        if(id != null) {
            try {
                int value = Integer.parseInt(id);
                exit = true; //value >= 0;
            } catch (NumberFormatException cce)  {
                exit = false;
            }
        }
        return exit;
    }
    
    public static String updateSecurity(HttpSession session) {
        String user = (String) session.getAttribute("user_active");
        String random = Functions.randomString(user + new Date());
        session.setAttribute("random_active", random);    // Código seguridad de usuario
        return random;
    }

    
    static boolean checkDataPart(String part, String type) {
        boolean correct = false;
        if(type.equals("dd")) {
            correct =isTypeDate(part, 1, 31);
        } else if(type.equals("mm")) {
            correct =isTypeDate(part, 1, 12);
        } else if(type.equals("yy")) {
            correct =isTypeDate(part, 1950, 2050);
        }
        return correct;
    }
    
    static boolean isTypeDate(String value, int min, int max) {
        boolean isCorrect = false;
        if(!value.isEmpty()) {
            if(isInteger(value)) {
                int val = Integer.parseInt(value);
                if(val >= min && val <= max) {
                    isCorrect = true;
                }
            }
        }
        return isCorrect;
    }
    
    
    static boolean isDate(String date) {
        //Formato dd/mm/yy
        boolean valido = false;
        if (!date.isEmpty()) {
            String[] split = date.split("-");
            if(split.length == 3) {
                valido = checkDataPart(split[2], "dd") && checkDataPart(split[1], "mm") && checkDataPart(split[0], "yy");
            }
        }
        return valido;
    }

    static boolean isSalario(String salario) {
        boolean isCorrect = false;
        if(salario != null && !salario.isEmpty()) {
            try {
                Double.parseDouble(salario);
                isCorrect = true;
            } catch(NumberFormatException cce) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    static boolean isPorcentaje(String porcentaje) {
        boolean isCorrect = false;
        if(porcentaje != null && !porcentaje.isEmpty()) {
            try {
                double port = Double.parseDouble(porcentaje);
                isCorrect = ((port <= 100) && (port >= 0));
            } catch(NumberFormatException cce) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }
    
}
