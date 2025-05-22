import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/*
 * NewJFrame.java
 *
 * Created on 29/10/2009, 05:38:07 AM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	JFrame mainFrame;
	boolean visible = true;
	
	/** Creates new form NewJFrame */
    public ControlCliente(JFrame mainFrame, MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
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
		String[] data = BaseDatos.listaValoresCelda("Clientes",15,"IdCliente="+claveRegistro);
		
		if(data[0] == null){
			JOptionPane.showMessageDialog(mainFrame, "El registro no exite.");
			visible = false;
			return;
		}

    	this.claveRegistro = claveRegistro;
    	
        idCliente.setText(data[0]);
        nombreCliente.setText(data[1]);
        nombreContacto.setText(data[2]);
        direccion.setText(data[3]);
        colonia.setText(data[4]);
        codigoPostal.setText(data[5]);
        ciudad.setText(data[6]);
        telefono.setText(data[7]);
        extension.setText(data[8]);
        fax.setText(data[9]);
        eMail.setText(data[10]);
        fechaAlta.setText(data[11]);
        tipoCliente.setSelectedItem(data[12]);
        
        if(data[13].equals("1") || data[13].equals("true"))
        	activo.setSelected(true);
        notas.setText(data[14]);
        
	}

	private void desabilitar(boolean b) {
		idCliente.setEditable(b);
        nombreCliente.setEditable(b);
        nombreContacto.setEditable(b);
        direccion.setEditable(b);
        colonia.setEditable(b);
        codigoPostal.setEditable(b);
        ciudad.setEditable(b);
        telefono.setEditable(b);
        extension.setEditable(b);
        eMail.setEditable(b);
        fax.setEditable(b);
        tipoCliente.setEnabled(b);
        fechaAlta.setEditable(b);
        activo.setEnabled(b);
        notas.setEditable(b);
        btnAceptar.setEnabled(b);
	}

    private void initComponents() {

    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");
    	
        idCliente = new javax.swing.JTextField();
        lbIdCliente = new javax.swing.JLabel();
        lbNombreCliente = new javax.swing.JLabel();
        nombreCliente = new javax.swing.JTextField();
        lbNombreContacto = new javax.swing.JLabel();
        nombreContacto = new javax.swing.JTextField();
        lbDireccion = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        lbColonia = new javax.swing.JLabel();
        colonia = new javax.swing.JTextField();
        lbCodigoPostal = new javax.swing.JLabel();
        codigoPostal = new javax.swing.JTextField();
        lbCiudad = new javax.swing.JLabel();
        ciudad = new javax.swing.JTextField();
        lbTelefono = new javax.swing.JLabel();
        telefono = new javax.swing.JTextField();
        lbExtension = new javax.swing.JLabel();
        extension = new javax.swing.JTextField();
        lbEmail = new javax.swing.JLabel();
        eMail = new javax.swing.JTextField();
        lbFax = new javax.swing.JLabel();
        fax = new javax.swing.JTextField();
        lbTipoCliente = new javax.swing.JLabel();
        tipoCliente = new javax.swing.JComboBox();
        lbFechaAlta = new javax.swing.JLabel();
        fechaAlta = new javax.swing.JTextField();
        activo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        notas = new javax.swing.JTextArea();
        lbNotas = new javax.swing.JLabel();
        lbTop = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        btnDepartamento = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setName("Form"); // NOI18N

        idCliente.setName("idCliente"); // NOI18N
        content.add(idCliente);
        idCliente.setBounds(85, 20, 75, 19);

        lbIdCliente.setText("Id. Cliente*:"); // NOI18N
        lbIdCliente.setName("lbIdCliente"); // NOI18N
        content.add(lbIdCliente);
        lbIdCliente.setBounds(20, 20, 60, 14);

        lbNombreCliente.setText("Empresa*:"); // NOI18N
        lbNombreCliente.setName("lbNombreCliente"); // NOI18N
        content.add(lbNombreCliente);
        lbNombreCliente.setBounds(20, 50, 100, 14);

        nombreCliente.setName("nombreCliente"); // NOI18N
        content.add(nombreCliente);
        nombreCliente.setBounds(125, 50, 315, 19);

        lbNombreContacto.setText("Nombre del Contacto*:"); // NOI18N
        lbNombreContacto.setName("lbNombreContacto"); // NOI18N
        content.add(lbNombreContacto);
        lbNombreContacto.setBounds(25, 270, 115, 14);

        nombreContacto.setName("nombreContacto"); // NOI18N
        content.add(nombreContacto);
        nombreContacto.setBounds(140, 270, 280, 19);

        lbDireccion.setText("Dirección:"); // NOI18N
        lbDireccion.setName("lbDireccion"); // NOI18N
        content.add(lbDireccion);
        lbDireccion.setBounds(20, 300, 50, 14);

        direccion.setName("direccion"); // NOI18N
        content.add(direccion);
        direccion.setBounds(70, 300, 350, 19);

        lbColonia.setText("Colonia:"); // NOI18N
        lbColonia.setName("lbColonia"); // NOI18N
        content.add(lbColonia);
        lbColonia.setBounds(20, 80, 39, 14);

        colonia.setName("colonia"); // NOI18N
        content.add(colonia);
        colonia.setBounds(70, 80, 180, 19);

        lbCodigoPostal.setText("Código Postal:"); // NOI18N
        lbCodigoPostal.setName("lbCodigoPostal"); // NOI18N
        content.add(lbCodigoPostal);
        lbCodigoPostal.setBounds(260, 80, 80, 14);

        codigoPostal.setName("codigoPostal"); // NOI18N
        content.add(codigoPostal);
        codigoPostal.setBounds(345, 80, 95, 19);

        lbCiudad.setText("Ciudad:"); // NOI18N
        lbCiudad.setName("lbCiudad"); // NOI18N
        content.add(lbCiudad);
        lbCiudad.setBounds(20, 110, 37, 14);

        ciudad.setName("ciudad"); // NOI18N
        content.add(ciudad);
        ciudad.setBounds(70, 110, 330, 19);

        lbTelefono.setText("Telefono*:"); // NOI18N
        lbTelefono.setName("lbTelefono"); // NOI18N
        content.add(lbTelefono);
        lbTelefono.setBounds(20, 140, 55, 14);

        telefono.setName("telefono"); // NOI18N
        content.add(telefono);
        telefono.setBounds(75, 140, 155, 19);

        lbExtension.setText("Extensión:"); // NOI18N
        lbExtension.setName("lbExtension"); // NOI18N
        content.add(lbExtension);
        lbExtension.setBounds(245, 140, 60, 14);

        extension.setName("extension"); // NOI18N
        content.add(extension);
        extension.setBounds(305, 140, 95, 19);

        lbEmail.setText("E-mail:"); // NOI18N
        lbEmail.setName("lbEmail"); // NOI18N
        content.add(lbEmail);
        lbEmail.setBounds(20, 170, 32, 14);

        eMail.setName("eMail"); // NOI18N
        content.add(eMail);
        eMail.setBounds(60, 170, 150, 19);

        lbFax.setText("Fax:"); // NOI18N
        lbFax.setName("lbFax"); // NOI18N
        content.add(lbFax);
        lbFax.setBounds(225, 170, 30, 14);

        fax.setName("fax"); // NOI18N
        content.add(fax);
        fax.setBounds(255, 170, 145, 19);

        lbTipoCliente.setText("Tipo de cliente:"); // NOI18N
        lbTipoCliente.setName("lbTipoCliente"); // NOI18N
        content.add(lbTipoCliente);
        lbTipoCliente.setBounds(20, 200, 73, 14);

        tipoCliente.setName("tipoCliente"); // NOI18N
        tipoCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Residencial", "Comercial", "Contrato"}));
        content.add(tipoCliente);
        tipoCliente.setBounds(100, 200, 120, 19);

        lbFechaAlta.setText("Fecha alta*:"); // NOI18N
        lbFechaAlta.setName("lbFechaAlta"); // NOI18N
        content.add(lbFechaAlta);
        lbFechaAlta.setBounds(230, 200, 65, 14);

        fechaAlta.setName("fechaAlta"); // NOI18N
        content.add(fechaAlta);
        fechaAlta.setBounds(295, 200, 125, 19);

        fechaAlta.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
		    	AgregarFechaHora tmp = new AgregarFechaHora(null, AgregarFechaHora.FECHA - AgregarFechaHora.HORA, fechaAlta.getText());
		    	
	    		if(tmp.getDate() != null)
	    			fechaAlta.setText(tmp.getDate() + " " + tmp.getTime());
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        activo.setText("Cliente activo"); // NOI18N
        activo.setName("activo"); // NOI18N
        content.add(activo);
        activo.setBounds(20, 230, 91, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        notas.setColumns(20);
        notas.setRows(5);
        notas.setName("notas"); // NOI18N
        jScrollPane1.setViewportView(notas);

        content.add(jScrollPane1);
        jScrollPane1.setBounds(60, 340, 380, 92);

        lbNotas.setText("Notas:"); // NOI18N
        lbNotas.setName("lbNotas"); // NOI18N
        content.add(lbNotas);
        lbNotas.setBounds(20, 340, 32, 14);

        btnDepartamento.setText("Departamentos"); // NOI18N
        btnDepartamento.setName("btnDepartamento"); // NOI18N
        content.add(btnDepartamento);
        btnDepartamento.setBounds(200, 450, 107, 23);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(310, 450, 75, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        content.add(btnAceptar);
        btnAceptar.setBounds(390, 450, 71, 23);
        
        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion.setText("Agregar nuevos clientes");
        lbDescripcion.setName("lbDescripcion");
        lbDescripcion.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lbDescripcion.setFont(new Font("Verdana", Font.BOLD, 12));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(480,590);
        
    }// </editor-fold>

    public void guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	String f = "0";
    	String e = "0";
    	String c = "0";
    	
    	if(fax.getText().length() > 0)
    		f = fax.getText();
    	
    	if(extension.getText().length() > 0)
    		e = extension.getText();
    	
    	if(codigoPostal.getText().length() > 0)
    		c = codigoPostal.getText();
    	
    	if(claveRegistro == null){
    		int getActivo = 0;
    		if(activo.isSelected())
    			getActivo = 1;
    		
            syntax = idCliente.getText()+ ",'" +
            		 nombreCliente.getText()+ "','" +
            		 nombreContacto.getText()+ "','" +
            		 direccion.getText()+ "','" +
            		 colonia.getText()+ "'," +
            		 c + ",'" +
            		 ciudad.getText()+ "'," +
            		 telefono.getText()+ "," +
            		 e+ "," +
            		 f+ ",'" +
            		 eMail.getText()+ "','" +
            		 fechaAlta.getText()+ "','" +
            		 tipoCliente.getSelectedItem()+ "'," +
            		 getActivo+ ",'" +
            		 notas.getText()+ "'";
            sinError = BaseDatos.nuevoValor("Clientes",syntax);
    	}else{
    		int getActivo = 0;
    		if(activo.isSelected())
    			getActivo = 1;
    		
    		syntax = "IdCliente=" + idCliente.getText() + 
    				 ",NombreCliente='" + nombreCliente.getText() + 
    				 "',NombreContacto='" + nombreContacto.getText() + 
    				 "',Direccion='" + direccion.getText() + 
    				 "',Colonia='" + colonia.getText() + 
    				 "',CP=" + c + 
    				 ",Ciudad='" + ciudad.getText() + 
    				 "',Telefono=" + telefono.getText() + 
    				 ",Extension=" + e + 
    				 ",Fax=" + f + 
    				 ",Email='" + eMail.getText() + 
    				 "',Fecha_alta='" + fechaAlta.getText() + 
    				 "',TipoCliente='" + tipoCliente.getSelectedItem() + 
    				 "',Activo=" + getActivo + 
    				 ",Notas='" + notas.getText()+"'";

			 sinError = BaseDatos.actualizar("Clientes",syntax,"IdCliente="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlCliente.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
	     	dispose();
		} else {
			JOptionPane.showMessageDialog(ControlCliente.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    }
    
    /**
    * @param args the command line arguments
    */

    private JPanel top;
    private JPanel content;
    
    // Variables declaration - do not modify
    private javax.swing.JCheckBox activo;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDepartamento;
    private javax.swing.JTextField ciudad;
    private javax.swing.JTextField codigoPostal;
    private javax.swing.JTextField colonia;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField eMail;
    private javax.swing.JTextField extension;
    private javax.swing.JTextField fax;
    private javax.swing.JTextField fechaAlta;
    private javax.swing.JTextField idCliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCiudad;
    private javax.swing.JLabel lbCodigoPostal;
    private javax.swing.JLabel lbColonia;
    private javax.swing.JLabel lbDireccion;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbExtension;
    private javax.swing.JLabel lbFax;
    private javax.swing.JLabel lbFechaAlta;
    private javax.swing.JLabel lbIdCliente;
    private javax.swing.JLabel lbNombreCliente;
    private javax.swing.JLabel lbNombreContacto;
    private javax.swing.JLabel lbNotas;
    private javax.swing.JLabel lbTelefono;
    private javax.swing.JLabel lbTipoCliente;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JTextField nombreCliente;
    private javax.swing.JTextField nombreContacto;
    private javax.swing.JTextArea notas;
    private javax.swing.JTextField telefono;
    private javax.swing.JComboBox tipoCliente;
    // End of variables declaration

}
