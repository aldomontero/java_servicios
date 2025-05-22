/*
 * Created on 27/09/2009
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * En esta clase se configura la conexion por la cual se desea conectar
 * el administrador, si llega a fallar la conexion, se muestra esta ventana
 * para poder cambiar los valores predeterminados
 */
public class DialogoConexion extends JDialog {

	private static final long serialVersionUID = 1L;

	JFrame frame = null;
	
	private JComboBox conector;
	private JTextField url;
	private JTextField clave;
	private JTextField usuario;
	
	AplicacionConfiguraciones conexionSettings = null;
	
	public DialogoConexion(JFrame frame, boolean nuller, final AplicacionConfiguraciones conexionSettings, final boolean porError){
		
		super(frame, "Conexion...",nuller);
		
		this.frame = frame;
		this.conexionSettings = conexionSettings;

		ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topConexion.jpg");
		
		JLabel lbTitulo = new JLabel(imgTop);
		String conectores[] = {"Driver de Microsoft Access","SQL Server","MySQL"};
		JLabel lbCodigo = new JLabel("Conectar usando:");
		JLabel lbDescripcion = new JLabel("Dirección URL de la Base de datos:");
		JLabel lbMarca = new JLabel("Nombre de usuario:");
		JLabel lbCosto = new JLabel("Contraseña:");

		conector = new JComboBox(conectores);
		url = new JTextField();
		usuario = new JTextField();
		clave = new JPasswordField();
		
		if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("SQL")){
			conector.setSelectedIndex(1);
			String datos[] = (String[])conexionSettings.obtenerConfiguracion("SQL");
			url.setText(datos[0]);
			usuario.setText(datos[1]);
			clave.setText(datos[2]);
		} else if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("MySQL")){
			String datos[] = (String[])conexionSettings.obtenerConfiguracion("MySQL");
			conector.setSelectedIndex(2);
			url.setText(datos[0]);
			usuario.setText(datos[1]);
			clave.setText(datos[2]);
		} else if(conexionSettings.obtenerConfiguracion("Predeterminada").equals("MDB")){
			String datos[] = (String[])conexionSettings.obtenerConfiguracion("MDB");
			conector.setSelectedIndex(0);
			url.setText(datos[0]);
			usuario.setText(datos[1]);
			clave.setText(datos[2]);
		}

		JPanel panelTitulo = new JPanel();
		JPanel panelCaptura = new JPanel();
		JPanel panelBoton = new JPanel();
		
		JButton aceptar = new JButton("Aceptar");	
        JButton cancelar = new JButton("Cancelar");
		
        conector.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(conector.getSelectedIndex() == 0){
					String datos[] = (String[])conexionSettings.obtenerConfiguracion("MDB");
					url.setText(datos[0]);
					usuario.setText(datos[1]);
					clave.setText(datos[2]);
				} else if (conector.getSelectedIndex() == 1){
					String datos[] = (String[])conexionSettings.obtenerConfiguracion("SQL");
					url.setText(datos[0]);
					usuario.setText(datos[1]);
					clave.setText(datos[2]);
				}  else if (conector.getSelectedIndex() == 2){
					String datos[] = (String[])conexionSettings.obtenerConfiguracion("MySQL");
					url.setText(datos[0]);
					usuario.setText(datos[1]);
					clave.setText(datos[2]);
				}
			}
        });
        
		aceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String Setting[] = {url.getText(),usuario.getText(),clave.getText()};

				if(url.getText().length() > 3){
					switch(conector.getSelectedIndex()){
					case 0:
						conexionSettings.actualizarConfiguracion("MDB", Setting);
						conexionSettings.actualizarConfiguracion("Predeterminada", "MDB");
						break;
					case 1:
						conexionSettings.actualizarConfiguracion("SQL", Setting);
						conexionSettings.actualizarConfiguracion("Predeterminada", "SQL");
						break;
					case 2:
						conexionSettings.actualizarConfiguracion("MySQL", Setting);
						conexionSettings.actualizarConfiguracion("Predeterminada", "MySQL");
						break;
					}
	
					if(!conexionSettings.escribir()){
		        		JOptionPane.showMessageDialog(null,"No se puede generar la informacion requerida, reintente.");
					} else {
						if(porError){
							JOptionPane.showMessageDialog(null,"Para aplicar los cambios se cerrara la aplicacion y a continuacion, abra de nuevo el programa.");
							System.exit(0);
						}else
							dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Escriba una url minimo de 4 caracteres.");
				}
			}	
		});
		
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(porError)
					System.exit(0);
				else
					dispose();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(porError)
					System.exit(0);
				else
					dispose();
			}
		});
		
		lbTitulo.setFont(new Font("Arial",Font.BOLD,14));

		panelTitulo.setLayout(new GridLayout(1,1));
		panelTitulo.add(lbTitulo);
		panelTitulo.setBackground(Color.white);
		
		panelCaptura.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		panelCaptura.setLayout(null);
		
		short vertical = 24;
		short left = 10;
		
		lbCodigo.setBounds(left,10,110,23);
		conector.setBounds(left+110,10,160,21);
		
		lbDescripcion.setBounds(left,vertical*2,230,23);
		lbMarca.setBounds(left,vertical*4,140,23);
		lbCosto.setBounds(left,vertical*6,140,23);
		
		url.setBounds(left,vertical*3,240,22);
		usuario.setBounds(left,vertical*5,210,22);
		clave.setBounds(left,vertical*7,210,22);
		
		panelCaptura.add(lbCodigo);
		panelCaptura.add(conector);
		panelCaptura.add(lbDescripcion);
		panelCaptura.add(lbMarca);
		panelCaptura.add(url);
		panelCaptura.add(usuario);
		panelCaptura.add(lbCosto);
		panelCaptura.add(clave);
		
		panelBoton.add(cancelar);
		panelBoton.add(aceptar);
		
		getContentPane().add(panelTitulo, BorderLayout.NORTH);		
		getContentPane().add(panelCaptura, BorderLayout.CENTER);
		getContentPane().add(panelBoton, BorderLayout.SOUTH);
		
		if(porError){
			lbTitulo.setText("Error de conexion, reconfigure");
			this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		}
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
		setSize(new Dimension(318, 360));
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
}

