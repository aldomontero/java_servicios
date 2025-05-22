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
 * NewEmpleados.java
 *
 * Created on 2/11/2009, 07:47:10 PM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlEmpleados extends JDialog {

	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	JFrame mainFrame;
	boolean visible = true;
	
	/** Creates new form NewEmpleados */
    public ControlEmpleados(JFrame mainFrame, MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
    	super(mainFrame, "Registro de Técnicos", true);
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
    	
    	String[] data = BaseDatos.listaValoresCelda("Empleados",8,"IdEmpleado="+claveRegistro);

    	if(data[0] == null){
			JOptionPane.showMessageDialog(mainFrame, "El registro no exite.");
			visible = false;
			return;
		}
    	
    	this.claveRegistro = claveRegistro;
    	
    	idEmpleado.setText(data[0]);
    	nombreEmpleado.setText(data[1]);
    	comision.setText(data[2]);
    	if(data[3].equals("1") || data[3].equals("true"))
        	activo.setSelected(true);
        sueldoBase.setText(data[4]);
        PSueldoBase.setText(data[5]);
        objetivoM1.setText(data[6]);
        objetivoM2.setText(data[7]);
        
	}

	private void desabilitar(boolean b) {

		comision.setEditable(b);
        sueldoBase.setEditable(b);
        idEmpleado.setEditable(b);
        nombreEmpleado.setEditable(b);
        objetivoM1.setEditable(b);
        objetivoM2.setEditable(b);
        PSueldoBase.setEditable(b);
        activo.setEnabled(b);
        btnAceptar.setEnabled(b);
	}
                        
    private void initComponents() {
    	
    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");
    	
        lbComision = new javax.swing.JLabel();
        comision = new javax.swing.JTextField();
        lbSueldoBase = new javax.swing.JLabel();
        sueldoBase = new javax.swing.JTextField();
        lbIdEmpleado = new javax.swing.JLabel();
        idEmpleado = new javax.swing.JTextField();
        lbNombreEmpleado = new javax.swing.JLabel();
        nombreEmpleado = new javax.swing.JTextField();
        lbObjetivoM1 = new javax.swing.JLabel();
        objetivoM1 = new javax.swing.JTextField();
        lbObjetivoM2 = new javax.swing.JLabel();
        objetivoM2 = new javax.swing.JTextField();
        lbPSueldoBase = new javax.swing.JLabel();
        lbTop = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        PSueldoBase = new javax.swing.JTextField();
        activo = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setName("Form"); // NOI18N

        lbComision.setText("% Comisión*:"); // NOI18N
        lbComision.setName("lbComision"); // NOI18N
        content.add(lbComision);
        lbComision.setBounds(30, 80, 71, 14);

        comision.setName("comision"); // NOI18N
        content.add(comision);
        comision.setBounds(105, 80, 85, 19);

        lbSueldoBase.setText("Sueldo base*:"); // NOI18N
        lbSueldoBase.setName("lbSueldoBase"); // NOI18N
        content.add(lbSueldoBase);
        lbSueldoBase.setBounds(200, 80, 70, 14);

        sueldoBase.setName("sueldoBase"); // NOI18N
        content.add(sueldoBase);
        sueldoBase.setBounds(276, 80, 110, 19);

        lbIdEmpleado.setText("Id. Técnico*:"); // NOI18N
        lbIdEmpleado.setName("lbIdEmpleado"); // NOI18N
        content.add(lbIdEmpleado);
        lbIdEmpleado.setBounds(30, 20, 65, 14);

        idEmpleado.setName("idEmpleado"); // NOI18N
        content.add(idEmpleado);
        idEmpleado.setBounds(100, 20, 100, 19);

        lbNombreEmpleado.setText("Nombre del técnico*:"); // NOI18N
        lbNombreEmpleado.setName("lbNombreEmpleado"); // NOI18N
        content.add(lbNombreEmpleado);
        lbNombreEmpleado.setBounds(36, 50, 105, 14);

        nombreEmpleado.setName("nombreEmpleado"); // NOI18N
        content.add(nombreEmpleado);
        nombreEmpleado.setBounds(145, 50, 300, 19);

        lbObjetivoM1.setText("En cantidad reparada:"); // NOI18N
        lbObjetivoM1.setName("lbObjetivoM1"); // NOI18N
        content.add(lbObjetivoM1);
        lbObjetivoM1.setBounds(30, 140, 112, 14);

        objetivoM1.setName("objetivoM1"); // NOI18N
        content.add(objetivoM1);
        objetivoM1.setBounds(150, 140, 90, 19);

        lbObjetivoM2.setText("En dinero producido:"); // NOI18N
        lbObjetivoM2.setName("lbObjetivoM2"); // NOI18N
        content.add(lbObjetivoM2);
        lbObjetivoM2.setBounds(250, 140, 102, 14);

        objetivoM2.setName("objetivoM2"); // NOI18N
        content.add(objetivoM2);
        objetivoM2.setBounds(360, 140, 90, 19);

        lbPSueldoBase.setText("P. sueldo base:"); // NOI18N
        lbPSueldoBase.setName("lbPSueldoBase"); // NOI18N
        content.add(lbPSueldoBase);
        lbPSueldoBase.setBounds(30, 110, 91, 14);

        PSueldoBase.setName("PSueldoBase"); // NOI18N
        content.add(PSueldoBase);
        PSueldoBase.setBounds(120, 110, 120, 19);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(310, 270, 75, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        activo.setText("Estado activo"); // NOI18N
        activo.setName("activo"); // NOI18N
        content.add(activo);
        activo.setBounds(30, 170, 91, 23);
        
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        content.add(btnAceptar);
        btnAceptar.setBounds(390, 270, 71, 23);

        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion.setText("Agregar nuevos empleados");
        lbDescripcion.setName("lbDescripcion");
        lbDescripcion.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lbDescripcion.setFont(new Font("Verdana", Font.BOLD, 12));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(485,405);
    }    

    public void guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	String psueldo = "0";
    	String m1 = "0";
    	String m2 = "0";
    	
    	if(PSueldoBase.getText().length() > 0)
    		psueldo = PSueldoBase.getText();
    	if(objetivoM1.getText().length() > 0)
    		m1 = objetivoM1.getText();
    	if(objetivoM2.getText().length() > 0)
    		m2 = objetivoM2.getText();
    	
    	if(claveRegistro == null){
    		int getActivo = 0;
    		if(activo.isSelected())
    			getActivo = 1;
    		
            syntax = idEmpleado.getText()+ ",'" +
        			 nombreEmpleado.getText()+ "'," +
        			 comision.getText()+ "," +
        			 getActivo+ "," +
        			 sueldoBase.getText()+ "," +
        			 psueldo + "," +
        			 m1 + "," +
        			 m2;
            
            sinError = BaseDatos.nuevoValor("Empleados",syntax);
    	}else{
    		int getActivo = 0;
    		if(activo.isSelected())
    			getActivo = 1;
    		
    		syntax = "IdEmpleado=" + idEmpleado.getText() + 
    				 ", Nombre='" + nombreEmpleado.getText() + 
    				 "', Comision=" + comision.getText() + 
    				 ", Status=" + getActivo + 
    				 ", Sbase=" + sueldoBase.getText() + 
    				 ", PSbase=" + psueldo + 
    				 ", ObjetivoM1=" + m1 + 
    				 ", ObjetivoM2=" + m2;
	
			 sinError = BaseDatos.actualizar("Empleados",syntax,"IdEmpleado="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlEmpleados.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
	     	dispose();
		} else {
			JOptionPane.showMessageDialog(ControlEmpleados.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    }
    
    private JPanel top;
    private JPanel content;
    
    // Variables declaration - do not modify   
    private javax.swing.JCheckBox activo;
    private javax.swing.JTextField PSueldoBase;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField comision;
    private javax.swing.JTextField idEmpleado;
    private javax.swing.JLabel lbComision;
    private javax.swing.JLabel lbIdEmpleado;
    private javax.swing.JLabel lbNombreEmpleado;
    private javax.swing.JLabel lbObjetivoM1;
    private javax.swing.JLabel lbObjetivoM2;
    private javax.swing.JLabel lbPSueldoBase;
    private javax.swing.JLabel lbSueldoBase;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JTextField nombreEmpleado;
    private javax.swing.JTextField objetivoM1;
    private javax.swing.JTextField objetivoM2;
    private javax.swing.JTextField sueldoBase;
    
    // End of variables declaration                   

}
