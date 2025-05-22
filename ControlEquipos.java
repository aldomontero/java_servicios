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
import javax.swing.JTextField;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewEquipos.java
 *
 * Created on 2/11/2009, 07:56:48 PM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlEquipos extends JDialog {

	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	JFrame mainFrame;
	boolean visible = true;
	
	private Object[][] marcas;
	
	/** Creates new form NewEquipos */
    public ControlEquipos(JFrame mainFrame, MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
    	super(mainFrame, "Registro de equipos", true);
    	this.BaseDatos = BaseDatos;
    	this.mainFrame = mainFrame;
    	
    	marcas = BaseDatos.listaTabla("Marcas",2);
    	
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
    	
    	String[] data = BaseDatos.listaValoresCelda("Equipos",11,"IdEquipo="+claveRegistro);
    	
    	if(data[0] == null){
			JOptionPane.showMessageDialog(mainFrame, "El registro no existe.");
			visible = false;
			return;
		}
    	
    	this.claveRegistro = claveRegistro;
    	
    	idEquipo.setText(data[0]);
    	cliente.setText(data[1]);
    	
    	String tempcategoria = BaseDatos.obtenerValorCelda("Marcas","Marcas","IdMarca="+(String)data[2]);
        marca.setSelectedItem((Object)tempcategoria);
        
        modelo.setText(data[3]);
        noSerie.setText(data[4]);
        noInventario.setText(data[5]);
        usuario.setText(data[6]);
        telefono.setText(data[7]);
        jTextArea1.setText(data[8]);
        
        if(!data[9].substring(0, 10).matches("9999-01-01"))
        	ultimoServicio.setText(data[9]);
        
        if(!data[10].substring(0, 10).matches("9999-01-01"))
        	proximoServicio.setText(data[10]);

	}

	private void desabilitar(boolean b) {
		
		marca.setEditable(b);
        modelo.setEditable(b);
        noInventario.setEditable(b);
        idEquipo.setEditable(b);
        cliente.setEditable(b);
        usuario.setEditable(b);
        noSerie.setEditable(b);
        telefono.setEditable(b);
        ultimoServicio.setEditable(b);
        proximoServicio.setEditable(b);
        jTextArea1.setEditable(b);
        btnAceptar.setEnabled(b);
	}
                       
    private void initComponents() {

    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");
    	
        lbMarca = new javax.swing.JLabel();
        marca = new javax.swing.JComboBox();
        lbModelo = new javax.swing.JLabel();
        modelo = new javax.swing.JTextField();
        lbNoSerie = new javax.swing.JLabel();
        noInventario = new javax.swing.JTextField();
        lbIdEquipo = new javax.swing.JLabel();
        idEquipo = new javax.swing.JTextField();
        lbCliente = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        lbNoInventario = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        lbUsuario = new javax.swing.JLabel();
        noSerie = new javax.swing.JTextField();
        lbTelefono = new javax.swing.JLabel();
        telefono = new javax.swing.JTextField();
        lbUltimoServicio = new javax.swing.JLabel();
        ultimoServicio = new javax.swing.JTextField();
        proximoServicio = new javax.swing.JTextField();
        lbProximoServicio = new javax.swing.JLabel();
        lbCaracteristicas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lbTop = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnBuscarCliente1 = new javax.swing.JButton();
        
        setName("Form"); // NOI18N

        lbMarca.setText("Marca:"); // NOI18N
        lbMarca.setName("lbMarca"); // NOI18N
        content.add(lbMarca);
        lbMarca.setBounds(230, 50, 33, 14);

        marca.setName("marca"); // NOI18N
        content.add(marca);
        marca.setBounds(270, 50, 160, 19);

        String[] temp = new String[marcas.length];
		for(int n = 0; n < marcas.length;n++)
			temp[n] = (String)marcas[n][1];
		marca.setModel(new javax.swing.DefaultComboBoxModel(temp));
		
        lbModelo.setText("Modelo:"); // NOI18N
        lbModelo.setName("lbModelo"); // NOI18N
        content.add(lbModelo);
        lbModelo.setBounds(20, 80, 38, 14);

        modelo.setName("modelo"); // NOI18N
        content.add(modelo);
        modelo.setBounds(60, 80, 150, 19);

        lbNoSerie.setText("No. serie:"); // NOI18N
        lbNoSerie.setName("lbNoSerie"); // NOI18N
        content.add(lbNoSerie);
        lbNoSerie.setBounds(220, 80, 55, 14);

        noInventario.setName("noInventario"); // NOI18N
        content.add(noInventario);
        noInventario.setBounds(112, 110, 68, 19);

        lbIdEquipo.setText("Clave Equipo*:"); // NOI18N
        lbIdEquipo.setName("lbIdEquipo"); // NOI18N
        content.add(lbIdEquipo);
        lbIdEquipo.setBounds(20, 20, 80, 14);

        idEquipo.setName("idEquipo"); // NOI18N
        content.add(idEquipo);
        idEquipo.setBounds(96, 20, 124, 19);

        lbCliente.setText("Código de Cliente*:"); // NOI18N
        lbCliente.setName("lbCliente"); // NOI18N
        content.add(lbCliente);
        lbCliente.setBounds(20, 50, 98, 14);

        cliente.setName("cliente"); // NOI18N
        content.add(cliente);
        cliente.setBounds(120, 50, 70, 19);

        cliente.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					if(cliente.getText().length() > 0){
						new ControlCliente(mainFrame, BaseDatos, true, cliente.getText());
					}
				}
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        lbNoInventario.setText("No. Inventario:"); // NOI18N
        lbNoInventario.setName("lbNoInventario"); // NOI18N
        content.add(lbNoInventario);
        lbNoInventario.setBounds(20, 110, 80, 14);

        btnBuscarCliente1.setText(".."); // NOI18N
        btnBuscarCliente1.setName("btnBuscarCliente1"); // NOI18N
        content.add(btnBuscarCliente1);
        btnBuscarCliente1.setBounds(200, 50, 20, 23);

        btnBuscarCliente1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BuscarCliente bc = new BuscarCliente(null, BaseDatos);
				if(bc.getData() != null){
					cliente.setText(bc.getData()[0]);
				}
			}
        });
        
        usuario.setName("usuario"); // NOI18N
        content.add(usuario);
        usuario.setBounds(270, 110, 160, 19);

        lbUsuario.setText("Tipo de Equipo:"); // NOI18N
        lbUsuario.setName("lbUsuario"); // NOI18N
        content.add(lbUsuario);
        lbUsuario.setBounds(190, 110, 100, 14);

        noSerie.setName("noSerie"); // NOI18N
        content.add(noSerie);
        noSerie.setBounds(275, 80, 140, 19);

        lbTelefono.setText("Télefono:"); // NOI18N
        lbTelefono.setName("lbTelefono"); // NOI18N
        content.add(lbTelefono);
        lbTelefono.setBounds(20, 140, 46, 14);

        telefono.setName("telefono"); // NOI18N
        content.add(telefono);
        telefono.setBounds(70, 140, 140, 19);

        lbUltimoServicio.setText("Último servicio:"); // NOI18N
        lbUltimoServicio.setName("lbUltimoServicio"); // NOI18N
        content.add(lbUltimoServicio);
        lbUltimoServicio.setBounds(220, 140, 80, 14);

        ultimoServicio.setName("ultimoServicio"); // NOI18N
        content.add(ultimoServicio);
        ultimoServicio.setBounds(305, 140, 125, 19);

        ultimoServicio.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(ultimoServicio, AgregarFechaHora.FECHA + AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        proximoServicio.setName("proximoServicio"); // NOI18N
        content.add(proximoServicio);
        proximoServicio.setBounds(115, 170, 115, 19);
        
        proximoServicio.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(proximoServicio, AgregarFechaHora.FECHA + AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        lbProximoServicio.setText("Próximo servicio:"); // NOI18N
        lbProximoServicio.setName("lbProximoServicio"); // NOI18N
        content.add(lbProximoServicio);
        lbProximoServicio.setBounds(20, 170, 95, 14);

        lbCaracteristicas.setText("Características:"); // NOI18N
        lbCaracteristicas.setName("lbCaracteristicas"); // NOI18N
        content.add(lbCaracteristicas);
        lbCaracteristicas.setBounds(20, 200, 75, 14);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        content.add(jScrollPane1);
        jScrollPane1.setBounds(110, 200, 320, 80);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(310, 320, 75, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        content.add(btnAceptar);
        btnAceptar.setBounds(390, 320, 71, 23);

        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion.setText("Agregar nuevos equipos");
        lbDescripcion.setName("lbDescripcion");
        lbDescripcion.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lbDescripcion.setFont(new Font("Verdana", Font.BOLD, 12));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(480,470);
    }// </editor-fold>                        

    public void guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	String tel = "0";
    	String ser = "0";
    	String inv = "0";
    	
    	String prox = "9999-01-01 00:00:00";
    	String ulti = "9999-01-01 00:00:00";
    	
    	if(telefono.getText().length() > 0)
    		tel = telefono.getText();
    	
    	if(noSerie.getText().length() > 0)
    		ser = noSerie.getText();
    	
    	if(noInventario.getText().length() > 0)
    		inv = noInventario.getText();
    	
    	if(proximoServicio.getText().length() > 0)
    		prox = proximoServicio.getText();
    	
    	if(ultimoServicio.getText().length() > 0)
    		ulti = ultimoServicio.getText();
    	
    	if(claveRegistro == null){

            syntax = idEquipo.getText()+ "," +
        			 cliente.getText()+ "," +
        			 (String)marcas[marca.getSelectedIndex()][0]+ ",'" +
        			 modelo.getText()+ "'," +
        			 ser+ "," +
        			 inv+ ",'" +
        			 usuario.getText()+ "'," +
        			 tel+ ",'" +
        			 jTextArea1.getText()+ "','" +
        			 ulti + "','" +
        			 prox + "'";
            sinError = BaseDatos.nuevoValor("Equipos",syntax);
            
    	}else{

    		syntax = "IdEquipo=" + idEquipo.getText() + 
    				 " , IdCliente=" + cliente.getText() + 
    				 " , Marca=" + (String)marcas[marca.getSelectedIndex()][0] + 
    				 " , Modelo='" + modelo.getText() + 
    				 "' , NSerie=" + ser + 
    				 " , NInvent=" + inv + 
    				 " , TipoEquipo='" + usuario.getText() + 
    				 "' , Telefono=" + tel + 
    				 " , Caracteristica='" + jTextArea1.getText() + 
    				 "' , Ult_servicio='" + ulti  + 
    				 "' , Prox_servicio='" + prox +"'";
	
			 sinError = BaseDatos.actualizar("Equipos",syntax,"IdEquipo="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlEquipos.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
	     	dispose();
		} else {
			JOptionPane.showMessageDialog(ControlEquipos.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    }
    
    protected void setOverAllTime(JTextField entrada, int i) {
    	AgregarFechaHora tmp = new AgregarFechaHora(mainFrame, i, entrada.getText());
    	
    	switch (i){
	    	case AgregarFechaHora.FECHA:
	    		if(tmp.getDate() != null)
	    			entrada.setText(tmp.getDate());
	    		break;
	    	case AgregarFechaHora.HORA:
	    		if(tmp.getTime() != null)
	    			entrada.setText(tmp.getTime());
	    		break;
	    	case AgregarFechaHora.FECHA + AgregarFechaHora.HORA:
	    		if(tmp.getTime() != null)
	    			entrada.setText(tmp.getDate() + " " + tmp.getTime());
	    		break;
    	}
	}
    
    private JPanel top;
    private JPanel content;
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnBuscarCliente1;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField idEquipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbCaracteristicas;
    private javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbIdEquipo;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbModelo;
    private javax.swing.JLabel lbNoInventario;
    private javax.swing.JLabel lbNoSerie;
    private javax.swing.JLabel lbProximoServicio;
    private javax.swing.JLabel lbTelefono;
    private javax.swing.JLabel lbUltimoServicio;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JComboBox marca;
    private javax.swing.JTextField modelo;
    private javax.swing.JTextField noInventario;
    private javax.swing.JTextField noSerie;
    private javax.swing.JTextField proximoServicio;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField ultimoServicio;
    private javax.swing.JTextField usuario;
    // End of variables declaration                   

}
