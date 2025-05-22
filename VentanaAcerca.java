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
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class VentanaAcerca extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Creditos del programa
	 * <b></b> las etiquetas b sirven para colocar en negritas el texto dentro de ellas
	 * <i></i> para colocarlas en cursivas
	 * <br> es un salto de linea
	 *  
	 */
	
	
	String info = "<html>"+
			  "<b>Admin. ordenes servicios, versión 1.0 </b>" + "<br>" +
			  "2010<br>Última fecha de revisión 30 de Noviembre del 2011<br><br>"+
			  "Programación: Aldo Montero";
	
	String logo = "topVilla.jpg";
	
	/*
	 * Ventana de creditos
	 */
	
    public VentanaAcerca(JFrame frame) {
		
		super(frame,"Acerca de Sistema de reportes y servicios",true);
		
		
		ImageIcon img = ClaseGlobal.crearIcono("iconos/"+logo);
		JLabel lbTitulo = new JLabel(img);
		JLabel lbInfo = new JLabel(info);

		JPanel panelInfo = new JPanel();
		JPanel panelBoton = new JPanel();
		
		JButton aceptar = new JButton("Aceptar");
		
		aceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}	
		});
		
		panelInfo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		panelInfo.setLayout(null);
		
		Insets insets = panelInfo.getInsets();
		
		lbInfo.setBounds(25 + insets.left, 5 + insets.top, 430, 240);

		panelInfo.add(lbInfo);
		
		setSize(new Dimension(430, 430));
		
		panelBoton.add(aceptar);
		
		getContentPane().add(lbTitulo, BorderLayout.NORTH);		
		getContentPane().add(panelInfo, BorderLayout.CENTER);
		getContentPane().add(panelBoton, BorderLayout.SOUTH);
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
    }
}
