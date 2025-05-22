import javax.swing.UIManager;

public class Launch {
	public static void main(String args[]){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception x) {
			System.out.println("Error al cambiar el estilo de Windows: " + x.getMessage());
		}
		
		new VentanaInicio(100);
		//new VentanaPrincipal(new MotorBaseDatos());
		//new ImprimeFormato(null, new MotorBaseDatos(), "565").generar();
		//new reporte(new motorBaseDatos());
	}
}
