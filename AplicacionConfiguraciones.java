
/*
 * Created on 4/10/2009
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * En esta clase se guardan configuraciones, en el programa se utiliza
 * para guardar los datos de la conexion y para guardar los datos de los
 * usuarios
 * 
 */
class SettingException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public SettingException(String err){
		super(err);
	}
}

class SettingNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public SettingNullException(String err){
		super(err);
	}
}

class SettingExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public SettingExistException(String err){
		super(err);
	}
}

class SettingGeneralException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public SettingGeneralException(String err){
		super(err);
	}
}

class configuracion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Object[][] datos;
	private int nDatos;
	private boolean cambiosRealizados;
	
	public configuracion(){
		//crea el array
		nDatos = 0;
		datos = inicializar(nDatos);
		cambiosRealizados = false;
	}
	
	public boolean obtenerCambiosRealizados(){
		return cambiosRealizados;
	}
	
	public void establecerCambiosRealizados(boolean estado){
		cambiosRealizados = estado;
	}
	
	private Object[][] inicializar(int nDatos) throws SettingGeneralException{

		try{
			return new Object[nDatos][2];
		}catch (OutOfMemoryError e){
			throw new SettingGeneralException("Fuera de memoria");
		}
	}
	
	public Object obtenerConfiguracion(String nombreConfiguracion) throws SettingNullException{
		
		int i = buscar(nombreConfiguracion);
		
		if(i >= 0 && i < nDatos){
			return datos[i][1];
		} else {
			throw new SettingNullException("No se encontro la configuracion");
		}
	}

	public Object obtenerConfiguracion(int i) throws SettingNullException{
		if(i >= 0 && i < nDatos){
			return datos[i][1];
		} else {
			throw new SettingNullException("No se encontro la configuracion");
		}
	}
	
	public int longitud() throws SettingNullException{
		if (datos != null)
			return datos.length;
		else
			throw new SettingNullException("No existe ninguna configuracion");
	}
	
	public void eliminarTodo() throws SettingNullException{
		if(datos != null)
			datos = null;
		else
			throw new SettingNullException("No existe ninguna configuracion");
		cambiosRealizados = true;
	}
	
	public void guardarConfiguracion(String nombreNuevaConfiguracion, Object nuevaConfiguracion) throws SettingExistException{
		
		if(buscar(nombreNuevaConfiguracion) == -1){
			
			Object[][] copiaDeLista;
			
			// el array crece conforme se le van aadiendo nuevos elementos
			copiaDeLista = datos;
			nDatos = copiaDeLista.length;
			datos = inicializar(nDatos + 1);
			
			for (int i = 0; i < nDatos; i++)
				datos[i] = copiaDeLista[i];
	
			datos[nDatos][0] = nombreNuevaConfiguracion;
			datos[nDatos][1] = nuevaConfiguracion;
			
			nDatos++;
			cambiosRealizados = true;
			
		} else {
			throw new SettingExistException("Ya existe la configuracion");
		}

	}
	
	public boolean eliminarConfiguracion(String eliminaNombreConfiguracion) throws SettingNullException{
		
		Object[][] copiaDeLista;
		int posi = buscar(eliminaNombreConfiguracion);
		
			if(posi != -1){
				//el array disminuye cuando se eliminan elementos
				datos[posi] = null;
				copiaDeLista = datos;
				
				if (copiaDeLista.length != 0){
					int k = 0;
					nDatos = copiaDeLista.length;
					datos = inicializar(nDatos - 1);
					
					for (int i = 0; i < nDatos; i++)
						if (copiaDeLista[i] != null)
							datos[k++] = copiaDeLista[i];

					nDatos--;
					cambiosRealizados = true;
					return true;
				}
			} else {
				throw new SettingNullException("No se encontro la configuracion");
			}
		return false;
	}

	public boolean actualizarConfiguracion(String nombreConfiguracion, Object nuevoValor)
					throws SettingNullException {
		
		int posi = buscar(nombreConfiguracion);
		
		if(posi != -1){
			if (datos[posi][1] != null){
				datos[posi][1] = nuevoValor;
				cambiosRealizados = true;
				return true;
			}
		} else {
			throw new SettingNullException("No se encontro la configuracion");
		}
		return false;
	}
	
	public int buscar(String NombreConfiguracion){
		int posi = 0;
		String nombreConfig;

		if (posi < nDatos)
				for (int i = posi; i < nDatos; i++){
					nombreConfig = (String)datos[i][0];
					if (nombreConfig.matches(NombreConfiguracion))
						return i;
				}
					
		return -1;
	}
}

/*
 * Esta clase que se declara a continuacion realiza el guardado de 
 * las configuraciones de los objetos llamados a este en archivos en
 * el sistema
 * 
 */

public class AplicacionConfiguraciones{
	
	private configuracion configuracion = null;
	private String nombreArchivoDestino;
	private boolean existeArchivo;
	
	public AplicacionConfiguraciones(){
		nombreArchivoDestino = "defaultSettings.dat";
		leer();
	}

	public AplicacionConfiguraciones(String nombreArchivoDestino){
		this.nombreArchivoDestino = nombreArchivoDestino;
		leer();
	}

	public boolean siExisteArchivo(){
		return existeArchivo;
	}
	
	public void leer() throws SettingException{
		
		ObjectInputStream ois = null;
		
		try{
			File fichero = new File(nombreArchivoDestino);
			
			if (!fichero.exists()){
				configuracion = new configuracion();
				existeArchivo = false;
			} else {
				ois = new ObjectInputStream(new FileInputStream(nombreArchivoDestino));
				configuracion = (configuracion)ois.readObject();
				existeArchivo = true;
			}
		} catch (ClassNotFoundException e){
			throw new SettingException("Archivo de configuracion no compatible");
		} catch(IOException e){
			throw new SettingException(e.toString());
		} finally {
			try{
				if(ois != null)
					ois.close();
			}catch (IOException e){
				throw new SettingException(e.toString());
			}
		}
	}

	public boolean escribir() throws SettingException{
		ObjectOutputStream ous = null;
		
		try{
			if(configuracion.obtenerCambiosRealizados()){
				ous = new ObjectOutputStream(new FileOutputStream(nombreArchivoDestino));
				ous.writeObject(configuracion);
				return true;
			}
			configuracion = null;
		}catch(IOException e){
			throw new SettingException("No se pudo guardar la configuracion");
		}finally{
			try{
				if(ous != null)
				ous.close();
			}catch(IOException e){
				throw new SettingException(e.toString());
			}
		}
		
		return false;
	}

	public boolean obtenerCambiosRealizados(){
		return configuracion.obtenerCambiosRealizados();
	}
	
	public void establecerCambiosRealizados(boolean estado){
		configuracion.establecerCambiosRealizados(estado);
	}

	public Object obtenerConfiguracion(String nombreConfiguracion){
		return configuracion.obtenerConfiguracion(nombreConfiguracion);
	}

	public Object obtenerConfiguracion(int i){
		return configuracion.obtenerConfiguracion(i);
	}
	
	public int longitud(){
		return configuracion.longitud();
	}
	
	public void eliminarTodo(){
		configuracion.eliminarTodo();
	}
	
	public void guardarConfiguracion(String nombreNuevaConfiguracion, Object nuevaConfiguracion){
		configuracion.guardarConfiguracion(nombreNuevaConfiguracion, nuevaConfiguracion);
	}
	
	public boolean eliminarConfiguracion(String eliminaNombreConfiguracion){
		return configuracion.eliminarConfiguracion(eliminaNombreConfiguracion);
	}

	public boolean actualizarConfiguracion(String nombreConfiguracion, Object nuevoValor){
		return configuracion.actualizarConfiguracion(nombreConfiguracion, nuevoValor);
	}
	
	public int buscar(String NombreConfiguracion){
		return configuracion.buscar(NombreConfiguracion);
	}
	
}

