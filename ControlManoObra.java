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
 * NewManoObra.java
 *
 * Created on 2/11/2009, 08:06:16 PM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlManoObra extends JDialog {

	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	JFrame mainFrame;
	boolean visible = true;
	
	/** Creates new form NewManoObra */
    public ControlManoObra(JFrame mainFrame, MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
    	super(mainFrame, "Registro de clientes", true);
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
    	
    	String[] data = BaseDatos.listaValoresCelda("ManoObra",4,"IdManoObra="+claveRegistro);
    	
    	if(data[0] == null){
			JOptionPane.showMessageDialog(mainFrame, "El registro no exite.");
			visible = false;
			return;
		}
    	
    	this.claveRegistro = claveRegistro;
    	
        idManoObra.setText(data[0]);
        descripcion.setText(data[1]);
        importe.setText(data[2]);
        importeTecnico.setText(data[3]);
        
	}

	private void desabilitar(boolean b) {
		idManoObra.setEditable(b);
        descripcion.setEditable(b);
        importeTecnico.setEditable(b);
        importe.setEditable(b);
        btnAceptar.setEnabled(b);
		
	}
                      
    private void initComponents() {
    	
    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");

        lbIdManoObra = new javax.swing.JLabel();
        idManoObra = new javax.swing.JTextField();
        lbDescripcion = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        lbImporteTecnico = new javax.swing.JLabel();
        importeTecnico = new javax.swing.JTextField();
        importe = new javax.swing.JTextField();
        lbImporte = new javax.swing.JLabel();
        lbTop = new javax.swing.JLabel();
        lbDescripcion2 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setName("Form"); // NOI18N

        lbIdManoObra.setText("Id. Mano de Obra*:"); // NOI18N
        lbIdManoObra.setName("lbIdManoObra"); // NOI18N
        content.add(lbIdManoObra);
        lbIdManoObra.setBounds(20, 20, 95, 14);

        idManoObra.setName("idManoObra"); // NOI18N
        content.add(idManoObra);
        idManoObra.setBounds(130, 20, 100, 19);

        lbDescripcion.setText("Descripcion*:"); // NOI18N
        lbDescripcion.setName("lbDescripcion"); // NOI18N
        content.add(lbDescripcion);
        lbDescripcion.setBounds(20, 50, 65, 14);

        descripcion.setName("descripcion"); // NOI18N
        content.add(descripcion);
        descripcion.setBounds(88, 50, 280, 19);

        lbImporteTecnico.setText("Importe técnico:"); // NOI18N
        lbImporteTecnico.setName("lbImporteTecnico"); // NOI18N
        content.add(lbImporteTecnico);
        lbImporteTecnico.setBounds(190, 80, 79, 14);

        importeTecnico.setName("importeTecnico"); // NOI18N
        
        content.add(importeTecnico);
        importeTecnico.setBounds(280, 80, 110, 19);

        importe.setName("importe"); // NOI18N
        content.add(importe);
        importe.setBounds(76, 80, 104, 19);

        lbImporte.setText("Importe*:"); // NOI18N
        lbImporte.setName("lbImporte"); // NOI18N
        content.add(lbImporte);
        lbImporte.setBounds(20, 80, 55, 14);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(240, 260, 75, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        content.add(btnAceptar);
        btnAceptar.setBounds(320, 260, 71, 23);
        
        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion2.setText("Agregar nueva Mano de obra");
        lbDescripcion2.setName("lbDescripcion2");
        lbDescripcion2.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lbDescripcion2.setFont(new Font("Verdana", Font.BOLD, 12));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion2);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(420,390);
    }                                           

    public void guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	String impTec = "0";
    	
    	if(importeTecnico.getText().length() > 0)
    		impTec = importeTecnico.getText();
    	
    	if(claveRegistro == null){

            syntax = idManoObra.getText()+ ",'" +
            		 descripcion.getText()+ "'," +
            		 importe.getText()+","+
            		 impTec;
            
            sinError = BaseDatos.nuevoValor("ManoObra",syntax);
    	}else{
    		syntax = "IdManoObra=" + idManoObra.getText() + 
    				  ",Descripcion='" + descripcion.getText() +
    				  "',Importe=" + importe.getText() +
    				  ",ImporteTecnico=" + impTec;
	
			 sinError = BaseDatos.actualizar("ManoObra",syntax,"IdManoObra="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlManoObra.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
	     	dispose();
		} else {
			JOptionPane.showMessageDialog(ControlManoObra.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    }
    
    private JPanel top;
    private JPanel content;
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField descripcion;
    private javax.swing.JTextField idManoObra;
    private javax.swing.JTextField importe;
    private javax.swing.JTextField importeTecnico;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JLabel lbIdManoObra;
    private javax.swing.JLabel lbImporte;
    private javax.swing.JLabel lbImporteTecnico;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion2;
    // End of variables declaration                   

}
