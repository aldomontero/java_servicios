import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * NewRefacciones.java
 *
 * Created on 2/11/2009, 08:09:07 PM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlRefacciones extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	JFrame mainFrame;
	boolean visible = true;
	
	/** Creates new form NewRefacciones */
	
    public ControlRefacciones(JFrame mainFrame, MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
    	super(mainFrame, "Registro de Refacciones", true);
    	this.BaseDatos = BaseDatos;
    	this.mainFrame = mainFrame;
    	
        initComponents();
        
        if(soloLectura)
        	desabilitar(false);
        
        if(claveRegistro != null)
        	verRegistro(claveRegistro);
        
        Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
        
		setIconImage(icon);
        setLocationRelativeTo(mainFrame);
        setResizable(false);
        setVisible(visible);
    }

    private void verRegistro(String claveRegistro) {
    	
    	String[] data = BaseDatos.listaValoresCelda("Refacciones",3,"IdRefaccion="+claveRegistro);
    	
    	if(data[0] == null){
			JOptionPane.showMessageDialog(mainFrame, "El registro no exite.");
			visible = false;
			return;
		}
    	
    	this.claveRegistro = claveRegistro;
    	
        idRefaccion.setText(data[0]);
        refaccion.setText(data[1]);
        costo.setText(data[2]);
        lbDescripcion.setText("Modificar refacciones");
		
	}

	private void desabilitar(boolean b) {

		idRefaccion.setEditable(b);
        refaccion.setEditable(b);
        costo.setEditable(b);
        btnAceptar.setEnabled(b);
	}
                      
    private void initComponents() {

    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");
    	
        lbIdRefaccion = new javax.swing.JLabel();
        idRefaccion = new javax.swing.JTextField();
        lbRefaccion = new javax.swing.JLabel();
        refaccion = new javax.swing.JTextField();
        lbCosto = new javax.swing.JLabel();
        costo = new javax.swing.JTextField();
        lbTop = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setName("Form"); // NOI18N

        lbIdRefaccion.setText("Id. Refacción*:"); // NOI18N
        lbIdRefaccion.setName("lbIdRefaccion"); // NOI18N
        content.add(lbIdRefaccion);
        lbIdRefaccion.setBounds(30, 20, 89, 14);

        idRefaccion.setName("idRefaccion"); // NOI18N
        content.add(idRefaccion);
        idRefaccion.setBounds(120, 20, 100, 19);

        lbRefaccion.setText("Descripcion*:"); // NOI18N
        lbRefaccion.setName("lbRefaccion"); // NOI18N
        content.add(lbRefaccion);
        lbRefaccion.setBounds(30, 50, 65, 14);

        refaccion.setName("refaccion"); // NOI18N
        content.add(refaccion);
        refaccion.setBounds(100, 50, 250, 19);

        lbCosto.setText("Importe*:"); // NOI18N
        lbCosto.setName("lbCosto"); // NOI18N
        content.add(lbCosto);
        lbCosto.setBounds(30, 80, 50, 14);

        costo.setName("costo"); // NOI18N
        content.add(costo);
        costo.setBounds(83, 80, 100, 19);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(230, 260, 75, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        content.add(btnAceptar);
        btnAceptar.setBounds(310, 260, 71, 23);
        
        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion.setText("Agregar nuevas refacciones");
        lbDescripcion.setName("lbDescripcion");
        lbDescripcion.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lbDescripcion.setFont(new Font("Verdana", Font.BOLD, 12));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(410,390);
    }// </editor-fold>                        

    public void guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	if(claveRegistro == null){

            syntax = idRefaccion.getText()+ ",'" +
            		 refaccion.getText()+ "'," +
            		 costo.getText();
            sinError = BaseDatos.nuevoValor("Refacciones",syntax);
    	}else{
    		syntax = "IdRefaccion=" + idRefaccion.getText() + 
    				  ", Descripcion='" + refaccion.getText() +
    				  "', Importe=" + costo.getText();
	
			 sinError = BaseDatos.actualizar("Refacciones",syntax,"IdRefaccion="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlRefacciones.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
	     	dispose();
		} else {
			JOptionPane.showMessageDialog(ControlRefacciones.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    }
    
    private JPanel top;
    private JPanel content;
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField costo;
    private javax.swing.JTextField idRefaccion;
    private javax.swing.JLabel lbCosto;
    private javax.swing.JLabel lbIdRefaccion;
    private javax.swing.JLabel lbRefaccion;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JTextField refaccion;
    // End of variables declaration                   

}
