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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Esta clase realiza la conexion con la base de datos ya sea local o desde
 * sql server, hay que configurar correctamente sql server para poder realizar una conexion
 * correcta
 */

public class MotorBaseDatos {

	private Connection conexion;
	private Statement sentencia;
	private AplicacionConfiguraciones conexionSettings = null;
	private DialogoConexion dc = null;
	
	// Crea la conexion a la base de datos
	public MotorBaseDatos(){

		conexionSettings = new AplicacionConfiguraciones("Conexion.dat");
		String nombreDBase = "Desconocido";
		String url = "Desconocido";
		String usuario;
		String clave;
		
		if(!conexionSettings.siExisteArchivo()){
			String SettingMDB[] = {"multiservicios","",""};
			String SettingSQL[] = {"localhost/multiservicios","src",""};
			String SettingMySQL[] = {"//localhost/mysql","root",""};
			conexionSettings.guardarConfiguracion("MDB", SettingMDB);
			conexionSettings.guardarConfiguracion("SQL", SettingSQL);
			conexionSettings.guardarConfiguracion("MySQL", SettingMySQL);
			conexionSettings.guardarConfiguracion("Predeterminada", "MDB");
			if(!conexionSettings.escribir()){
        		JOptionPane.showMessageDialog(null,"No se puede generar la informacion requerida.");
        		System.exit(0);
			}
		}
		
		if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("MDB")){
			try{  
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage()+"\n\nError interno, no se puede continuar");
				JOptionPane.showMessageDialog(null,"Error al encontrar el driver de la base de datos.","Error externo",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

			try{
				
				String datos[] = (String[])conexionSettings.obtenerConfiguracion("MDB");
				
				nombreDBase = datos[0];
				usuario = datos[1];
				clave = datos[2];
				
				conexion  = DriverManager.getConnection("jdbc:odbc:" + nombreDBase, usuario, clave);
				sentencia = conexion.createStatement();
			}catch(SQLException ex){
				System.err.println(ex.getMessage()+"\n\nError interno, no se pudo conectar con: "+nombreDBase+", no se puede continuar");
				dc = new DialogoConexion(null, true, conexionSettings, true);
				dc.setVisible(true);
				System.exit(0);
			}
		} else if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("SQL")){
			try{  
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage()+"\nError interno, no se puede continuar");
				dc = new DialogoConexion(null, true, conexionSettings, true);
				dc.setVisible(true);
				System.exit(0);
			}

			try{
				String datos[] = (String[])conexionSettings.obtenerConfiguracion("SQL");
				
				url = datos[0];
				usuario = datos[1];
				clave = datos[2];
				
				conexion  = DriverManager.getConnection("jdbc:sqlserver:" + url, usuario, clave);
				sentencia = conexion.createStatement();
			}catch(SQLException ex){
				System.err.println(ex.getMessage()+"\nError interno, no se pudo conectar con: "+url+", no se puede continuar");
				dc = new DialogoConexion(null, true, conexionSettings, true);
				dc.setVisible(true);
				System.exit(0);
			}
		} else if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("MySQL")){
			try{  
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage()+"\nError interno, no se puede continuar");
				dc = new DialogoConexion(null, true, conexionSettings, true);
				dc.setVisible(true);
				System.exit(0);
			}

			try{
				String datos[] = (String[])conexionSettings.obtenerConfiguracion("MySQL");
				
				url = datos[0];
				usuario = datos[1];
				clave = datos[2];
				
				conexion  = DriverManager.getConnection("jdbc:mysql:" + url, usuario, clave);
				sentencia = conexion.createStatement();
			}catch(SQLException ex){
				JOptionPane.showMessageDialog(null, ex.getMessage()+"\nError interno, no se pudo conectar con: "+url+", no se puede continuar");
				dc = new DialogoConexion(null, true, conexionSettings, true);
				dc.setVisible(true);
				System.exit(0);
			}
		}
	}
	
	
	// cambia la configuracion de la conexion
	public void cambiarConfiguracion(JFrame target){
		dc = new DialogoConexion(target, true, conexionSettings, false);
		dc.setVisible(true);	
	}
	
	// actualiza la base de datos
	public boolean actualizar(String tabla, String setSQL, String whereSQL){
		boolean n = false;
		
		try{
			
      		sentencia.executeUpdate("UPDATE "+tabla+" SET "+setSQL+" WHERE "+whereSQL);
	     	n = true;

		} catch (SQLException sql) {
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}

	// Guarda un nuevo valor
	public boolean nuevoValor(String tabla, String valores){
		boolean n = false;

		try{
      		sentencia.executeUpdate("INSERT INTO "+tabla+" VALUES("+valores+")");
	     	n = true;
		} catch (SQLException sql) {
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}
	// elimina un registro
	public boolean eliminar(String tabla, String whereSQL){
		boolean n = false;
		
		try{
			sentencia.executeUpdate("DELETE FROM " +tabla + " WHERE " + whereSQL);
	     	n = true;
		} catch (SQLException sql) {
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}
	
//	 obtiene los valores de una determinada tabla
	public String[][] listaTabla(String tabla, int numeroColumnas){
		String s[][] = null;
		
		s = listaTabla(tabla, numeroColumnas, null, null);
		
		return s;
	}
//	 obtiene los valores de una determinada tabla
	public String[][] listaTabla(String tabla, int numeroColumnas, String whereSQL){
		String s[][] = null;
		
		s = listaTabla(tabla, numeroColumnas, null, whereSQL);
		
		return s;
	}
//	 obtiene los valores de una determinada tabla
	public String[][] listaTabla(String tabla, String includeColumns[], String whereSQL){
		String s[][] = null;
		
		s = listaTabla(tabla, 0, includeColumns, whereSQL);
		
		return s;
	}
//	 obtiene los valores de una determinada tabla
	public String[][] listaTabla(String tabla, String includeColumns[]){
		String s[][] = null;
		
		s = listaTabla(tabla, 0, includeColumns, null);
		
		return s;
	}
	// obtiene los valores de una determinada tabla
	public String[][] listaTabla(String tabla,int numeroColumnas, String includeColumns[], String whereSQL){
		String s[][] = null;
		String columns = "";
		String syntax;
	
		int count = 0;
		
		if(numeroColumnas == 0)
			numeroColumnas = includeColumns.length;
			
		if(includeColumns != null)
			for(int n = 0; n < includeColumns.length; n++){
				columns += includeColumns[n];
				if(n < (includeColumns.length-1))
					columns += ", ";
			}
		
		if(whereSQL == null && includeColumns == null)
			syntax = "SELECT * FROM " +tabla;		
		else if (whereSQL == null)
			syntax = "SELECT " +columns+ " FROM " +tabla;
		else if (includeColumns == null)
			syntax = "SELECT * FROM " +tabla+ " WHERE " +whereSQL;
		else
			syntax = "SELECT " +columns+ " FROM " +tabla+ " WHERE " +whereSQL;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			while (rs.next()){
				count++;
			}
			if(rs.wasNull() == false){
				rs = sentencia.executeQuery(syntax);
				s = new String[count][numeroColumnas];
				count = -1;
				while (rs.next()){
					count++;
					for(int n = 0 ;n < numeroColumnas; n++)
						s[count][n] = rs.getString(n+1);
				}
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	// realiza una consulta avanzada
	public String[][] advancedSQL(String syntax, int numeroColumnas){
		
		String s[][] = null;
	
		int count = 0;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			while (rs.next()){
				count++;
			}
			if(rs.wasNull() == false){
				rs = sentencia.executeQuery(syntax);
				s = new String[count][numeroColumnas];
				count = -1;
				while (rs.next()){
					count++;
					for(int n = 0 ;n < numeroColumnas; n++)
						s[count][n] = rs.getString(n+1);
				}
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	// intenta si se puede ejecutar una sentencia SQL
	public boolean trySQL(String syntax){
		
		boolean exito = true;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			
			if(rs.wasNull()){
				exito = false;
			}
			else{
				rs.next();
				if(rs.getString(1) == null)
					if(rs.getString(1).length() > 0) 
						exito = false;
			}
			
		} catch(SQLException bbb){
			exito = false;
			System.err.println("Error: Ocurrio un error al obtener datos en SQL");
		}
		return exito;
	}
	// obtiene un campo de un registro determinado
	public String obtenerValorCelda(String tabla, String nombreColumna, String whereSQL){
		String s = new String();
		String syntax;

		syntax = "SELECT "+nombreColumna+" FROM " +tabla+ " WHERE " +whereSQL;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			
			while (rs.next()){
				s= rs.getString(1);
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	// obtiene un registro
	public String[] listaValoresCelda(String tabla,int numeroColumnas, String whereSQL){
		String s[] = new String[numeroColumnas];
		String syntax;

		syntax = tabla + " WHERE " + whereSQL;

		try{
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + syntax);
			while (rs.next()){
				for(int n = 0 ;n < numeroColumnas; n++)
					s[n] = rs.getString(n+1);
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	
	public Connection getConexion(){
		return conexion;
	}
	
	// cierra la base de datos
	public void cerrarDB(){
		try{
			System.out.println("Cerrando conexion con la base de datos");
			conexion.close();
			if(conexion.isClosed())
				System.out.println("Cerrada base de datos");
		} catch (SQLException ex){
      		System.err.println("Error: " + ex.getMessage());	
      	}
	}
}
