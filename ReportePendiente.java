import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection; 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*Librerías necesarias para Jasper Reports*/ 

import net.sf.jasperreports.engine.*; 
import net.sf.jasperreports.view.*; 

public class ReportePendiente extends javax.swing.JDialog{
	
	private static final long serialVersionUID = 1L;
	Connection con = null;
	MotorBaseDatos BaseDatos;
	JFrame target;
	String idTecnico;
	
	public ReportePendiente(JFrame target, MotorBaseDatos BaseDatos2){
		super(target, "Generar reporte por servicios pendientes", true);
		
		this.BaseDatos = BaseDatos2;
		this.target = target;
		
		con = BaseDatos.getConexion(); 
		
		initComponents();
		
		setLocationRelativeTo(target);
		setVisible(true);
	}
	
	private void initComponents(){

    	top = new JPanel(new GridLayout(2,1));
    	content = new JPanel(null);
    	
    	ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topModificar.jpg");
    	
        lbEstatus = new javax.swing.JLabel();
        lbId1 = new javax.swing.JLabel();
        panelPeriodo = new javax.swing.JPanel();
        lbDe = new javax.swing.JLabel();
        lbDe1 = new javax.swing.JLabel();
        fechaFin = new javax.swing.JTextField();
        fechaInicio = new javax.swing.JTextField();
        btnFechaFin = new javax.swing.JButton();
        btnFechaInicio1 = new javax.swing.JButton();
        id = new javax.swing.JComboBox();
        btnSeleccionarCliente = new javax.swing.JButton();
        estatus = new javax.swing.JComboBox();
        lbTop = new javax.swing.JLabel();
        lbDescripcion = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        String[][] dataEmpleados = BaseDatos.advancedSQL("SELECT IdEmpleado, Nombre FROM Empleados WHERE Status=True ORDER BY IdEmpleado", 2);
        String[] listaEmpleados = null;
        
        if(dataEmpleados != null)
        	if(dataEmpleados.length > 0){
        		listaEmpleados = new String[dataEmpleados.length];
        		for(int x = 0; x < dataEmpleados.length; x++)
		        	listaEmpleados[x] = dataEmpleados[x][0] + " - " + dataEmpleados[x][1];
        	}
		        
        
        getContentPane().setLayout(new BorderLayout());

        lbEstatus.setText("Estatus:"); // NOI18N
        lbEstatus.setName("lbEstatus"); // NOI18N
        content.add(lbEstatus);
        lbEstatus.setBounds(10, 50, 60, 14);

        lbId1.setText("Técnico:"); // NOI18N
        lbId1.setName("lbId1"); // NOI18N
        content.add(lbId1);
        lbId1.setBounds(10, 20, 60, 14);

        panelPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodo")); // NOI18N
        panelPeriodo.setName("panelPeriodo"); // NOI18N
        panelPeriodo.setLayout(null);

        lbDe.setText("hasta:"); // NOI18N
        lbDe.setName("lbDe"); // NOI18N
        panelPeriodo.add(lbDe);
        lbDe.setBounds(20, 70, 50, 14);

        lbDe1.setText("A partir de:"); // NOI18N
        lbDe1.setName("lbDe1"); // NOI18N
        panelPeriodo.add(lbDe1);
        lbDe1.setBounds(20, 30, 70, 14);

        fechaFin.setText(""); // NOI18N
        fechaFin.setName("fechaFin"); // NOI18N
        panelPeriodo.add(fechaFin);
        fechaFin.setBounds(90, 70, 120, 20);

        fechaInicio.setText(""); // NOI18N
        fechaInicio.setName("fechaInicio1"); // NOI18N
        panelPeriodo.add(fechaInicio);
        fechaInicio.setBounds(90, 30, 120, 20);

        btnFechaFin.setText("Seleccionar fecha"); // NOI18N
        btnFechaFin.setName("btnFechaFin"); // NOI18N
        panelPeriodo.add(btnFechaFin);
        btnFechaFin.setBounds(220, 70, 30, 23);

        btnFechaFin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AgregarFechaHora tmp = new AgregarFechaHora(target, AgregarFechaHora.FECHA, fechaFin.getText());
				if(tmp.getDate() != null)
					fechaFin.setText(tmp.getDate());
			}
        });
        
        btnFechaInicio1.setText("Seleccionar fecha"); // NOI18N
        btnFechaInicio1.setName("btnFechaInicio1"); // NOI18N
        panelPeriodo.add(btnFechaInicio1);
        btnFechaInicio1.setBounds(220, 30, 30, 23);
        
        btnFechaInicio1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AgregarFechaHora tmp = new AgregarFechaHora(target, AgregarFechaHora.FECHA, fechaInicio.getText());
				if(tmp.getDate() != null)
					fechaInicio.setText(tmp.getDate());
			}
        });
        
        content.add(panelPeriodo);
        panelPeriodo.setBounds(10, 90, 280, 140);

        id.setModel(new javax.swing.DefaultComboBoxModel(listaEmpleados)); // NOI18N
        id.setName("id"); // NOI18N
        content.add(id);
        id.setBounds(60, 20, 170, 20);
        id.setSelectedIndex(-1);
        
        id.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String[] spliter = null;
				
				if(id.getSelectedIndex() != -1){
					spliter = ((String)id.getSelectedItem()).split(" - ");
					idTecnico = spliter[0];
				}
			}
        });
        
        btnSeleccionarCliente.setText("Seleccionar cliente"); // NOI18N
        btnSeleccionarCliente.setName("btnSeleccionarCliente"); // NOI18N
        //content.add(btnSeleccionarCliente);
        btnSeleccionarCliente.setBounds(240, 20, 30, 23);

        estatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pendiente" }));
        estatus.setName("estatus"); // NOI18N
        content.add(estatus);
        estatus.setBounds(60, 50, 170, 20);
        estatus.setEnabled(false);
        
        btnGenerar.setText("Generar"); // NOI18N
        btnGenerar.setName("btnGenerar"); // NOI18N
        content.add(btnGenerar);
        btnGenerar.setBounds(220, 240, 71, 23);

        btnGenerar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(idTecnico != null)
					generarReporte();
			}
        });
        
        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        content.add(btnCancelar);
        btnCancelar.setBounds(130, 240, 80, 23);

        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        lbTop.setIcon(imgTop);
        lbTop.setName("lbTop");

        lbDescripcion.setText("  Especifique los parámetros del reporte.");
        lbDescripcion.setName("lbDescripcion");
        lbDescripcion.setHorizontalAlignment(javax.swing.JLabel.LEFT);
        lbDescripcion.setFont(new Font("Verdana", Font.PLAIN, 11));
        
        top.setBackground(Color.white);
        
        top.add(lbTop);
        top.add(lbDescripcion);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);
        
        setSize(317, 377);
        setResizable(false);
    }
	
	@SuppressWarnings("unchecked")
	protected void generarReporte(){
		try 
        { 
            //Ruta de Archivo Jasper 

            String fileName = "reportes/pendientes.jasper";
            fileName = fileName.replaceAll("%20", " ");
			
            //System.out.println(fileName.substring(0, fileName.length()));
            
            //Obtener una conexión a la base de datos 
            
 
            //Pasamos parametros al reporte Jasper. 
            Map parameters = new HashMap();
            
            String[] data = BaseDatos.listaValoresCelda("Empleados",8,"IdEmpleado="+idTecnico);
            
            parameters.put("REPORT_NOMBRE_TECNICO", data[1]); 
            parameters.put("REPORT_CODIGO_TECNICO", idTecnico);
            parameters.put("REPORT_FECHA_INICIO", fechaInicio.getText());
            parameters.put("REPORT_FECHA_FIN", fechaFin.getText());
            parameters.put("REPORT_TECNICO_COBRANZA", data[4]);
            parameters.put("REPORT_TECNICO_OBJETIVO", data[6]);
            parameters.put("REPORT_TECNICO_COMISION", data[2]);
            
	            //Preparacion del reporte (en esta etapa llena el diseño de reporte) 
	            //Reporte diseñado y compilado con iReport

            JasperPrint jasperPrint = null;
            
            try{
            	jasperPrint = JasperFillManager.fillReport(fileName, parameters, con);
            } catch(Exception e){
            	JOptionPane.showMessageDialog(ReportePendiente.this, "Error al generar el reporte, modifique sus parámetros.");
            }

            //Se lanza el Viewer de Jasper, no termina aplicación al salir
            if(!jasperPrint.getPages().isEmpty()){
            	JasperViewer jviewer = new JasperViewer(jasperPrint,false);
                dispose();
                
                Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
                jviewer.setIconImage(icon);
                jviewer.setTitle("Reporte generado");
                jviewer.setVisible(true); 
            } else {
            	JOptionPane.showMessageDialog(ReportePendiente.this, "No se generó ningún reporte con estos parámetros, vuelva a intentarlo.");
            }
       }
       catch (Exception j) 
       { 
           System.out.println("Mensaje de Error:"+j.getMessage());
       } 
	}
	
	public static boolean isDate(String fechax) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            @SuppressWarnings("unused")
			Date fecha = formatoFecha.parse(fechax);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	private JPanel top;
    private JPanel content;
    
	private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFechaFin;
    private javax.swing.JButton btnFechaInicio1;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnSeleccionarCliente;
    private javax.swing.JComboBox estatus;
    private javax.swing.JTextField fechaFin;
    private javax.swing.JTextField fechaInicio;
    private javax.swing.JComboBox id;
    private javax.swing.JLabel lbDe;
    private javax.swing.JLabel lbDe1;
    private javax.swing.JLabel lbEstatus;
    private javax.swing.JLabel lbId1;
    private javax.swing.JPanel panelPeriodo;
    private javax.swing.JLabel lbTop;
    private javax.swing.JLabel lbDescripcion;
}
