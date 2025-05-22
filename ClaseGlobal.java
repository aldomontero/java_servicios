/*
 * Created on 25/09/2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Aldo Montero Murillo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

/*
 * En esta clase se obtienen las imagenes para ser mostradas en el programa
 * la ruta predeterminada es la del proyecto, entre otros recursos
 * utilizados en todo el programa 
 */

public class ClaseGlobal {

		// Retorna una imagen de icono, si es nulo retorna un error en la direccion del archivo. */
	    public static ImageIcon crearIcono(String path) {
			java.net.URL imgURL = ClaseGlobal.class.getResource(path);
			if (imgURL != null)
			    return new ImageIcon(imgURL);
			else
			    return null;
	    }
	    
	    protected static Image crearImagen(String path) {
	        java.net.URL imgURL = ClaseGlobal.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL).getImage();
	        } else {
	            return null;
	        }
	    }
	    
//	  metodo para validar si la fecha es correcta
	    public static boolean isDate(String fechax) {
	        try {
	            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	            Date fecha = formatoFecha.parse(fechax);
	            fecha.toString();
	        } catch (Exception e) {
	            return false;
	        }
	        return true;
	    }

	    //metodo para validar correo electronio
	    public static boolean isEmail(String correo) {
	        Pattern pat = null;
	        Matcher mat = null;        
	        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
	        mat = pat.matcher(correo);
	        if (mat.find()) {
	            System.out.println("[" + mat.group() + "]");
	            return true;
	        }else{
	            return false;
	        }        
	    }

}
