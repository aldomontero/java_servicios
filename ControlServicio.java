import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/*
 * NewServicio.java
 *
 * Created on 8/11/2009, 12:23:31 PM
 * La validación de los campos se realiza configurando las tablas de la base de datos
 * Para evitarse de la programación la validación se encuentra dentro de los formatos de cada campo
 * de cada una de las tablas de la BD
 */

/**
 *
 * @author Aldo Montero Murillo
 */
public class ControlServicio extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private MotorBaseDatos BaseDatos;
	private String claveRegistro = null;
	
	private boolean esRegistrado;
	boolean visible = true;
	
	/** Creates new form NewServicio */
    public ControlServicio(MotorBaseDatos BaseDatos, boolean soloLectura, String claveRegistro) {
    	super("Registro de Servicios");
    	this.BaseDatos = BaseDatos;
    	esRegistrado = false;
    	
        initComponents();
        
        if(soloLectura)
        	desabilitar(false);
        
        if(claveRegistro != null)
        	verRegistro(claveRegistro);
        
        Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
        
		setIconImage(icon);
        setSize(701, 615);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(visible);
    }

    private void verRegistro(String claveRegistro) {
    	
    	String[] data = BaseDatos.listaValoresCelda("Servicios",36,"IdOrden="+claveRegistro);
    	
    	if(data[0] == null){
			JOptionPane.showMessageDialog(null, "El registro no exite.");
			visible = false;
			return;
		}
    	
    	this.claveRegistro = claveRegistro;
    	
    	setTitle("Detalle del servicio No. " + claveRegistro);
    	
        idOrden.setText(data[0]);
        idCliente.setText(data[1]);
        if(data[2] != null)
        	fechaEntrada.setText(data[2].substring(0,10));
        if(data[3] != null)
        	if(!data[3].substring(0, 10).matches("9999-01-01"))
        		fechaProgramada.setText(data[3].substring(0,10));
        if(data[4] != null)
        	if(!data[4].substring(0, 10).matches("9999-01-01"))
        		horaProgramada.setText(data[4].substring(11,19));
        if(data[5] != null)
        	fechaSalida.setText(data[5].substring(0,10));
        if(data[6] != null)
        	horaEntrada.setText(data[6].substring(11,19));
        if(data[7] != null)
        	horaInicia.setText(data[7].substring(11,19));
        if(data[8] != null)
        	horaSalida.setText(data[8].substring(11,19));
        tipoServicio.setSelectedItem(data[9]);
        
        if(data[10] != null)
        	if(data[10].equals("1") || data[10].equals("true"))
        		mantenimiento.setSelected(true);
        if(data[11] != null)
        	if(data[11].equals("1") || data[11].equals("true"))
        		reparacion.setSelected(true);
        if(data[12] != null)
        	if(data[12].equals("1") || data[12].equals("true"))
        		diagnostico.setSelected(true);
        if(data[13] != null)
        	if(data[13].equals("1") || data[13].equals("true"))
        		instalacion.setSelected(true);
        if(data[14] != null)
        	if(data[14].equals("1") || data[14].equals("true"))
        		garantia.setSelected(true);
        if(data[15] != null)
        	if(data[15].equals("1") || data[15].equals("true"))
        		domicilio.setSelected(true);
        if(data[16] != null)
        	if(data[16].equals("1") || data[16].equals("true"))
        		actualizacion.setSelected(true);
        if(data[17] != null)
        	if(data[17].equals("1") || data[17].equals("true"))
        		configuracion.setSelected(true);
        if(data[18] != null)
        	if(data[18].equals("1") || data[18].equals("true"))
        		asesoria.setSelected(true);
        if(data[19] != null)
        	if(data[19].equals("1") || data[19].equals("true"))
        		remoto.setSelected(true);
        
        estatus.setSelectedItem(data[20]);
        
        equipo.setText(data[21]);
        falla.setText(data[22]);
        accesorios.setText(data[23]);
        diagnosticos.setText(data[24]);
        
        String obtenerNombre;
        String noCoincidentes = null;
        
        if(data[25] != null)
	        if(!data[25].matches("0")){
	        	obtenerNombre = BaseDatos.obtenerValorCelda("Empleados", "Nombre", "IdEmpleado="+data[25]);
	        	if(obtenerNombre != null)
	        		entregoEquipo.setSelectedItem(data[25]+" - "+obtenerNombre);
	        	if(entregoEquipo.getSelectedIndex() == -1)
		        	noCoincidentes = "Empleados marcados como inactivos se encuentran registrados en este servicio:\n\nEntrego equipo: "+ obtenerNombre;
	        }
        
        if(data[26] != null)
	        if(!data[26].matches("0")){
	        	obtenerNombre = BaseDatos.obtenerValorCelda("Empleados", "Nombre", "IdEmpleado="+data[26]);
	        	if(obtenerNombre != null)
	        		atendioEquipo.setSelectedItem(data[26]+" - "+obtenerNombre);
	        	if(atendioEquipo.getSelectedIndex() == -1)
		        	if(noCoincidentes == null)
		        		noCoincidentes = "Empleados marcados como inactivos se encuentran registrados en este servicio:\n\nAtendio equipo: "+ obtenerNombre;
		        	else
		        		noCoincidentes += "\nAtendio equipo: "+ obtenerNombre;
	        }
        
        if(data[27] != null)
	        if(!data[27].matches("0")){
	        	obtenerNombre = BaseDatos.obtenerValorCelda("Empleados", "Nombre", "IdEmpleado="+data[27]);
	        	if(obtenerNombre != null)
	        		recibioEquipo.setSelectedItem(data[27]+" - "+obtenerNombre);
	        	if(recibioEquipo.getSelectedIndex() == -1)
		        	if(noCoincidentes == null)
		        		noCoincidentes = "Empleados marcados como inactivos se encuentran registrados en este servicio:\n\nRecibio equipo: "+ obtenerNombre;
		        	else
		        		noCoincidentes += "\nRecibio equipo: "+ obtenerNombre;
	        }
        
        if(noCoincidentes != null)
        	JOptionPane.showMessageDialog(null, noCoincidentes+"\n\nEstos no se mostrarán en la ventana de servicio.", "Empleados inactivos", JOptionPane.INFORMATION_MESSAGE);
        
        total.setText(data[28]);
        importeServicio.setText(data[29]);
        factura.setText(data[30]);
        comision.setText(data[31]);
        
        if(data[32] != null)
        	if(data[32].equals("1") || data[32].equals("true"))
        		pagoComision.setSelected(true);
        
        fechaPago.setText(data[33]);
		
        documento.setText(data[34]);
        departamento.setText(data[35]);
        
        esRegistrado = true;
        
        Object[][] dataTemp = BaseDatos.listaTabla("detallesManoObra",3, new String[]{"IdDetalle","IdManoObra","IdCantidad"},"idServicio="+claveRegistro);
        modelManoObra.setDataVector(dataTemp, new String [] {"Código de registro", "Descripción", "Cantidad a realizar", "Costo"});
        
        verValores(tablaManoObra,"ManoObra", "IdManoObra","Descripcion");
        
        dataTemp = BaseDatos.listaTabla("detallesRefacciones",3, new String[]{"IdDetalle","IdRefaccion","IdCantidad"},"idServicio="+claveRegistro);
        modelRefacciones.setDataVector(dataTemp, new String [] {"Código de registro", "Refacción", "Cantidad", "Costo"});
        
        verValores(tablaRefacciones,"Refacciones", "IdRefaccion", "Refaccion");
        
	}

	private void desabilitar(boolean b) {
		idOrden.setEditable(b);
        idCliente.setEditable(b);
        fechaEntrada.setEditable(b);
        fechaProgramada.setEditable(b);
        horaProgramada.setEditable(b);
        fechaSalida.setEditable(b);
        horaEntrada.setEditable(b);
        horaInicia.setEditable(b);
        horaSalida.setEditable(b);
        tipoServicio.setEditable(b);
        mantenimiento.setEnabled(b);
        reparacion.setEnabled(b);
        diagnostico.setEnabled(b);
        instalacion.setEnabled(b);
        garantia.setEnabled(b);
        domicilio.setEnabled(b);
        actualizacion.setEnabled(b);
        configuracion.setEnabled(b);
        asesoria.setEnabled(b);
        remoto.setEnabled(b);
        estatus.setEditable(b);
        equipo.setEditable(b);
        falla.setEditable(b);
        accesorios.setEditable(b);
        diagnosticos.setEditable(b);
        departamento.setEditable(b);
        documento.setEditable(b);
        entregoEquipo.setEditable(b);
        atendioEquipo.setEditable(b);
        recibioEquipo.setEditable(b);
        total.setEditable(b);
        importeServicio.setEditable(b);
        factura.setEditable(b);
        comision.setEditable(b);
        pagoComision.setEnabled(b);
        fechaPago.setEditable(b);
        btnGuardar.setEnabled(b);
        btnAceptar.setEnabled(b);
        btnBuscarCliente1.setEnabled(b);
        btnBuscarEquipo1.setEnabled(b);
        btnEliminarManoObra.setEnabled(b);
        btnAgregarManoObra.setEnabled(b);
        btnEliminarRefaccion.setEnabled(b);
        btnAgregarRefaccion.setEnabled(b);
	}
	
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        servicioDescripcion = new javax.swing.JTabbedPane();
        panelServicio = new javax.swing.JPanel();
        instalacion = new javax.swing.JCheckBox();
        idOrden = new javax.swing.JTextField();
        lbIdOrden = new javax.swing.JLabel();
        lbIdCliente = new javax.swing.JLabel();
        idCliente = new javax.swing.JTextField();
        fechaEntrada = new javax.swing.JTextField();
        lbFechaEntrada = new javax.swing.JLabel();
        fechaProgramada = new javax.swing.JTextField();
        lbTipoServicio = new javax.swing.JLabel();
        lbHoraProgramada = new javax.swing.JLabel();
        horaProgramada = new javax.swing.JTextField();
        horaInicia = new javax.swing.JTextField();
        lbhoraInicia = new javax.swing.JLabel();
        horaEntrada = new javax.swing.JTextField();
        documento = new javax.swing.JTextField();
        lbHoraEntrada = new javax.swing.JLabel();
        lbDocumento = new javax.swing.JLabel();
        fechaSalida = new javax.swing.JTextField();
        lbFechaSalida = new javax.swing.JLabel();
        lbHoraSalida = new javax.swing.JLabel();
        horaSalida = new javax.swing.JTextField();
        tipoServicio = new javax.swing.JComboBox();
        lbFechaProgramada = new javax.swing.JLabel();
        mantenimiento = new javax.swing.JCheckBox();
        reparacion = new javax.swing.JCheckBox();
        diagnostico = new javax.swing.JCheckBox();
        configuracion = new javax.swing.JCheckBox();
        pagoComision = new javax.swing.JCheckBox();
        domicilio = new javax.swing.JCheckBox();
        actualizacion = new javax.swing.JCheckBox();
        remoto = new javax.swing.JCheckBox();
        asesoria = new javax.swing.JCheckBox();
        lbEstatus = new javax.swing.JLabel();
        estatus = new javax.swing.JComboBox();
        lbEquipo = new javax.swing.JLabel();
        equipo = new javax.swing.JTextField();
        lbAccesorios = new javax.swing.JLabel();
        lbDiagnosticos = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        diagnosticos = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        accesorios = new javax.swing.JTextArea();
        departamento = new javax.swing.JTextField();
        lbEntregoEquipo = new javax.swing.JLabel();
        lbDepartamento = new javax.swing.JLabel();
        entregoEquipo = new javax.swing.JComboBox();
        lbAtendioEquipo = new javax.swing.JLabel();
        atendioEquipo = new javax.swing.JComboBox();
        lbRecibioEquipo = new javax.swing.JLabel();
        recibioEquipo = new javax.swing.JComboBox();
        lbTotal = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        lbImporteServicio = new javax.swing.JLabel();
        importeServicio = new javax.swing.JTextField();
        lbFactura = new javax.swing.JLabel();
        factura = new javax.swing.JTextField();
        lbComision = new javax.swing.JLabel();
        comision = new javax.swing.JTextField();
        garantia = new javax.swing.JCheckBox();
        lbFechaPago = new javax.swing.JLabel();
        fechaPago = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnBuscarCliente1 = new javax.swing.JButton();
        btnBuscarEquipo1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        falla = new javax.swing.JTextArea();
        lbFalla = new javax.swing.JLabel();
        panelManoObra = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaManoObra = new javax.swing.JTable(){ // Para volver no editable
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){
				return false; 
			}
		};
		
		JTableHeader columnasManoObra = tablaManoObra.getTableHeader();
        columnasManoObra.setReorderingAllowed(false);
        
        lbManoObraDescripcion = new javax.swing.JLabel();
        btnEliminarManoObra = new javax.swing.JButton();
        btnAgregarManoObra = new javax.swing.JButton();
        panelRefacciones = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaRefacciones = new javax.swing.JTable(){ // Para volver no editable
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){
				return false; 
			}
		};
		
		JTableHeader columnasRefacciones = tablaRefacciones.getTableHeader();
        columnasRefacciones.setReorderingAllowed(false);
        
        lbManoObraDescripcion1 = new javax.swing.JLabel();
        btnEliminarRefaccion = new javax.swing.JButton();
        btnAgregarRefaccion = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        getContentPane().setLayout(null);

        String[][] dataEmpleados = BaseDatos.advancedSQL("SELECT IdEmpleado, Nombre FROM Empleados WHERE Status=True ORDER BY IdEmpleado", 2);
        String[] listaEmpleados = new String[dataEmpleados.length];
        
        if(dataEmpleados != null)
        	if(dataEmpleados.length > 0){
        		listaEmpleados = new String[dataEmpleados.length];
        		for(int x = 0; x < dataEmpleados.length; x++)
		        	listaEmpleados[x] = dataEmpleados[x][0] + " - " + dataEmpleados[x][1];
        	}
        
        servicioDescripcion.setName("servicioDescripcion"); // NOI18N

        panelServicio.setName("panelServicio"); // NOI18N
        panelServicio.setLayout(null);

        instalacion.setText("Instalación"); // NOI18N
        instalacion.setName("instalacion"); // NOI18N
        panelServicio.add(instalacion);
        instalacion.setBounds(420, 140, 77, 23);

        idOrden.setName("idOrden"); // NOI18N
        panelServicio.add(idOrden);
        idOrden.setBounds(85, 20, 65, 19);

        lbIdOrden.setText("Id. Orden*:"); // NOI18N
        lbIdOrden.setName("lbIdOrden"); // NOI18N
        panelServicio.add(lbIdOrden);
        lbIdOrden.setBounds(20, 20, 65, 14);

        lbIdCliente.setText("Id. Cliente*:"); // NOI18N
        lbIdCliente.setName("lbIdCliente"); // NOI18N
        panelServicio.add(lbIdCliente);
        lbIdCliente.setBounds(160, 20, 65, 14);

        idCliente.setName("idCliente"); // NOI18N
        panelServicio.add(idCliente);
        idCliente.setBounds(225, 20, 75, 19);

        fechaEntrada.setName("fechaEntrada"); // NOI18N
        panelServicio.add(fechaEntrada);
        fechaEntrada.setBounds(120, 50, 90, 19);

        lbFechaEntrada.setText("Fecha de entrada*:"); // NOI18N
        lbFechaEntrada.setName("lbFechaEntrada"); // NOI18N
        panelServicio.add(lbFechaEntrada);
        lbFechaEntrada.setBounds(20, 50, 95, 14);

        fechaProgramada.setName("fechaProgramada"); // NOI18N
        panelServicio.add(fechaProgramada);
        fechaProgramada.setBounds(125, 110, 95, 19);

        lbTipoServicio.setText("Tipo de servicio:"); // NOI18N
        lbTipoServicio.setName("lbTipoServicio"); // NOI18N
        panelServicio.add(lbTipoServicio);
        lbTipoServicio.setBounds(400, 200, 90, 14);

        lbHoraProgramada.setText("Hora programada:"); // NOI18N
        lbHoraProgramada.setName("lbHoraProgramada"); // NOI18N
        panelServicio.add(lbHoraProgramada);
        lbHoraProgramada.setBounds(230, 110, 105, 14);

        horaProgramada.setName("horaProgramada"); // NOI18N
        panelServicio.add(horaProgramada);
        horaProgramada.setBounds(335, 110, 95, 19);

        horaInicia.setName("horaInicia"); // NOI18N
        panelServicio.add(horaInicia);
        horaInicia.setBounds(405, 20, 95, 19);

        lbhoraInicia.setText("Hora inicia:"); // NOI18N
        lbhoraInicia.setName("lbhoraInicia"); // NOI18N
        panelServicio.add(lbhoraInicia);
        lbhoraInicia.setBounds(340, 20, 65, 14);

        horaEntrada.setName("horaEntrada"); // NOI18N
        panelServicio.add(horaEntrada);
        horaEntrada.setBounds(305, 50, 95, 19);

        lbHoraEntrada.setText("Hora entrada*:"); // NOI18N
        lbHoraEntrada.setName("lbHoraEntrada"); // NOI18N
        panelServicio.add(lbHoraEntrada);
        lbHoraEntrada.setBounds(220, 50, 105, 14);

        fechaSalida.setName("fechaSalida"); // NOI18N
        panelServicio.add(fechaSalida);
        fechaSalida.setBounds(110, 80, 90, 19);

        lbFechaSalida.setText("Fecha de salida*:"); // NOI18N
        lbFechaSalida.setName("lbFechaSalida"); // NOI18N
        panelServicio.add(lbFechaSalida);
        lbFechaSalida.setBounds(20, 80, 85, 14);

        lbHoraSalida.setText("Hora de salida*:"); // NOI18N
        lbHoraSalida.setName("lbHoraSalida"); // NOI18N
        panelServicio.add(lbHoraSalida);
        lbHoraSalida.setBounds(210, 80, 85, 14);

        horaSalida.setName("horaSalida"); // NOI18N
        panelServicio.add(horaSalida);
        horaSalida.setBounds(295, 80, 90, 19);

        tipoServicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Domicilio", "Taller", "Garantía", "Soporte Remoto" }));
        tipoServicio.setName("tipoServicio"); // NOI18N
        panelServicio.add(tipoServicio);
        tipoServicio.setBounds(490, 200, 130, 22);

        lbFechaProgramada.setText("Fecha programada:"); // NOI18N
        lbFechaProgramada.setName("lbFechaProgramada"); // NOI18N
        panelServicio.add(lbFechaProgramada);
        lbFechaProgramada.setBounds(20, 110, 105, 14);

        mantenimiento.setText("Mantenimiento:"); // NOI18N
        mantenimiento.setName("mantenimiento"); // NOI18N
        panelServicio.add(mantenimiento);
        mantenimiento.setBounds(20, 140, 100, 23);

        reparacion.setText("Reparación"); // NOI18N
        reparacion.setName("reparacion"); // NOI18N
        panelServicio.add(reparacion);
        reparacion.setBounds(160, 140, 79, 23);

        diagnostico.setText("Diagnóstico"); // NOI18N
        diagnostico.setName("diagnostico"); // NOI18N
        panelServicio.add(diagnostico);
        diagnostico.setBounds(290, 140, 81, 23);

        configuracion.setText("Configuración"); // NOI18N
        configuracion.setName("configuracion"); // NOI18N
        panelServicio.add(configuracion);
        configuracion.setBounds(420, 170, 100, 23);

        pagoComision.setText("Pago comisión"); // NOI18N
        pagoComision.setName("pagoComision"); // NOI18N
        panelServicio.add(pagoComision);
        pagoComision.setBounds(20, 460, 100, 23);

        domicilio.setText("Domicilio"); // NOI18N
        domicilio.setName("domicilio"); // NOI18N
        panelServicio.add(domicilio);
        domicilio.setBounds(160, 170, 65, 23);

        actualizacion.setText("Actualización"); // NOI18N
        actualizacion.setName("actualizacion"); // NOI18N
        panelServicio.add(actualizacion);
        actualizacion.setBounds(290, 170, 90, 23);

        remoto.setText("Remoto"); // NOI18N
        remoto.setName("remoto"); // NOI18N
        panelServicio.add(remoto);
        remoto.setBounds(540, 140, 63, 23);

        asesoria.setText("Asesoría"); // NOI18N
        asesoria.setName("asesoria"); // NOI18N
        panelServicio.add(asesoria);
        asesoria.setBounds(540, 170, 67, 23);

        lbEstatus.setText("Estatus:"); // NOI18N
        lbEstatus.setName("lbEstatus"); // NOI18N
        panelServicio.add(lbEstatus);
        lbEstatus.setBounds(20, 200, 40, 14);

        estatus.setName("estatus"); // NOI18N
        panelServicio.add(estatus);
        estatus.setBounds(70, 200, 120, 19);
        estatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Abierto", "Proceso", "Espera parte", "Pagado", "Pendiente", "Cancelado", "Sin costo", "Garantía" }));
        
        lbEquipo.setText("Equipo:"); // NOI18N
        lbEquipo.setName("lbEquipo"); // NOI18N
        panelServicio.add(lbEquipo);
        lbEquipo.setBounds(210, 200, 45, 14);

        equipo.setName("equipo"); // NOI18N
        panelServicio.add(equipo);
        equipo.setBounds(255, 200, 75, 19);

        lbAccesorios.setText("Accesorios:"); // NOI18N
        lbAccesorios.setName("lbAccesorios"); // NOI18N
        panelServicio.add(lbAccesorios);
        lbAccesorios.setBounds(20, 230, 60, 14);

        lbDiagnosticos.setText("Diagnóstico:"); // NOI18N
        lbDiagnosticos.setName("lbDiagnosticos"); // NOI18N
        panelServicio.add(lbDiagnosticos);
        lbDiagnosticos.setBounds(330, 230, 60, 14);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        diagnosticos.setName("diagnosticos"); // NOI18N
        jScrollPane3.setViewportView(diagnosticos);

        panelServicio.add(jScrollPane3);
        jScrollPane3.setBounds(400, 230, 260, 50);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        accesorios.setName("accesorios"); // NOI18N
        jScrollPane4.setViewportView(accesorios);

        panelServicio.add(jScrollPane4);
        jScrollPane4.setBounds(80, 230, 240, 50);

        lbEntregoEquipo.setText("Entregó equipo:"); // NOI18N
        lbEntregoEquipo.setName("lbEntregoEquipo"); // NOI18N
        panelServicio.add(lbEntregoEquipo);
        lbEntregoEquipo.setBounds(20, 340, 80, 14);

        entregoEquipo.setName("entregoEquipo"); // NOI18N
        panelServicio.add(entregoEquipo);
        entregoEquipo.setBounds(100, 340, 230, 19);
        entregoEquipo.setModel(new javax.swing.DefaultComboBoxModel(listaEmpleados));
        entregoEquipo.setSelectedIndex(-1);
        
        lbAtendioEquipo.setText("Atendió equipo:"); // NOI18N
        lbAtendioEquipo.setName("lbAtendioEquipo"); // NOI18N
        panelServicio.add(lbAtendioEquipo);
        lbAtendioEquipo.setBounds(340, 340, 80, 14);

        atendioEquipo.setName("atendioEquipo"); // NOI18N
        panelServicio.add(atendioEquipo);
        atendioEquipo.setBounds(420, 340, 230, 19);
        atendioEquipo.setModel(new javax.swing.DefaultComboBoxModel(listaEmpleados));
        atendioEquipo.setSelectedIndex(-1);
        
        lbRecibioEquipo.setText("Recibió equipo:"); // NOI18N
        lbRecibioEquipo.setName("lbRecibioEquipo"); // NOI18N
        panelServicio.add(lbRecibioEquipo);
        lbRecibioEquipo.setBounds(20, 370, 80, 14);

        recibioEquipo.setName("recibioEquipo"); // NOI18N
        panelServicio.add(recibioEquipo);
        recibioEquipo.setBounds(100, 370, 230, 19);
        recibioEquipo.setModel(new javax.swing.DefaultComboBoxModel(listaEmpleados));
        recibioEquipo.setSelectedIndex(-1);
        
        lbTotal.setText("Total:"); // NOI18N
        lbTotal.setName("lbTotal"); // NOI18N
        panelServicio.add(lbTotal);
        lbTotal.setBounds(20, 400, 30, 14);

        total.setName("total"); // NOI18N
        panelServicio.add(total);
        total.setBounds(60, 400, 110, 19);

        lbImporteServicio.setText("Importe servicio:"); // NOI18N
        lbImporteServicio.setName("lbImporteServicio"); // NOI18N
        panelServicio.add(lbImporteServicio);
        lbImporteServicio.setBounds(180, 400, 90, 14);

        importeServicio.setName("importeServicio"); // NOI18N
        panelServicio.add(importeServicio);
        importeServicio.setBounds(270, 400, 110, 19);

        lbFactura.setText("Factura:"); // NOI18N
        lbFactura.setName("lbFactura"); // NOI18N
        panelServicio.add(lbFactura);
        lbFactura.setBounds(390, 400, 50, 14);

        factura.setName("factura"); // NOI18N
        panelServicio.add(factura);
        factura.setBounds(440, 400, 110, 19);

        lbComision.setText("Comisión:"); // NOI18N
        lbComision.setName("lbComision"); // NOI18N
        panelServicio.add(lbComision);
        lbComision.setBounds(20, 430, 50, 14);

        comision.setName("comision"); // NOI18N
        panelServicio.add(comision);
        comision.setBounds(70, 430, 110, 19);

        garantia.setText("Garantía"); // NOI18N
        garantia.setName("garantia"); // NOI18N
        panelServicio.add(garantia);
        garantia.setBounds(20, 170, 67, 23);

        lbFechaPago.setText("Límite pago*:"); // NOI18N
        lbFechaPago.setName("lbFechaPago"); // NOI18N
        panelServicio.add(lbFechaPago);
        lbFechaPago.setBounds(200, 430, 85, 14);

        fechaPago.setName("fechaPago"); // NOI18N
        panelServicio.add(fechaPago);
        fechaPago.setBounds(275, 430, 120, 19);

        lbDocumento.setText("Documento:"); // NOI18N
        lbDocumento.setName("lbDocumento"); // NOI18N
        panelServicio.add(lbDocumento);
        lbDocumento.setBounds(410, 430, 85, 14);

        documento.setName("documento"); // NOI18N
        panelServicio.add(documento);
        documento.setBounds(475, 430, 100, 19);
        
        lbDepartamento.setText("Departamento:"); // NOI18N
        lbDepartamento.setName("lbDepartamento"); // NOI18N
        panelServicio.add(lbDepartamento);
        lbDepartamento.setBounds(200, 460, 85, 14);

        departamento.setName("departamento"); // NOI18N
        panelServicio.add(departamento);
        departamento.setBounds(280, 460, 230, 19);
        
        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.setName("btnBuscar"); // NOI18N
        panelServicio.add(btnBuscar);
        btnBuscar.setBounds(590, 10, 70, 22);

        btnBuscarCliente1.setText(".."); // NOI18N
        btnBuscarCliente1.setName("btnBuscarCliente1"); // NOI18N
        panelServicio.add(btnBuscarCliente1);
        btnBuscarCliente1.setBounds(310, 20, 20, 23);

        btnBuscarCliente1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BuscarCliente bc = new BuscarCliente(null, BaseDatos);
				if(bc.getData() != null){
					idCliente.setText(bc.getData()[0]);
				}
			}
        });
        
        btnBuscarEquipo1.setText(".."); // NOI18N
        btnBuscarEquipo1.setName("btnBuscarEquipo1"); // NOI18N
        panelServicio.add(btnBuscarEquipo1);
        btnBuscarEquipo1.setBounds(340, 200, 20, 23);

        btnBuscarEquipo1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BuscarEquipo bc = new BuscarEquipo(null, BaseDatos);
				if(bc.getData() != null){
					equipo.setText(bc.getData()[0]);
				}
			}
        });
        
        servicioDescripcion.addTab("Servicio", panelServicio); // NOI18N

        panelManoObra.setName("panelManoObra"); // NOI18N
        panelManoObra.setLayout(null);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablaManoObra.setModel(modelManoObra);
        tablaManoObra.setName("tablaManoObra"); // NOI18N
        jScrollPane1.setViewportView(tablaManoObra);

        panelManoObra.add(jScrollPane1);
        jScrollPane1.setBounds(20, 60, 630, 210);

        lbManoObraDescripcion.setText("Agrege, elimine una o mas manos de obras realizadas en este servicio:"); // NOI18N
        lbManoObraDescripcion.setName("lbManoObraDescripcion"); // NOI18N
        panelManoObra.add(lbManoObraDescripcion);
        lbManoObraDescripcion.setBounds(20, 30, 350, 14);

        btnEliminarManoObra.setText("Eliminar"); // NOI18N
        btnEliminarManoObra.setName("btnEliminarManoObra"); // NOI18N
        panelManoObra.add(btnEliminarManoObra);
        btnEliminarManoObra.setBounds(100, 280, 73, 23);

        btnAgregarManoObra.setText("Agregar"); // NOI18N
        btnAgregarManoObra.setName("btnAgregarManoObra"); // NOI18N
        panelManoObra.add(btnAgregarManoObra);
        btnAgregarManoObra.setBounds(20, 280, 73, 23);

        servicioDescripcion.addTab("Mano de obra", panelManoObra); // NOI18N

        panelRefacciones.setName("panelRefacciones"); // NOI18N
        panelRefacciones.setLayout(null);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

		tablaRefacciones.setModel(modelRefacciones);
        tablaRefacciones.setName("tablaRefacciones"); // NOI18N
        jScrollPane2.setViewportView(tablaRefacciones);

        panelRefacciones.add(jScrollPane2);
        jScrollPane2.setBounds(20, 60, 630, 210);

        lbManoObraDescripcion1.setText("Agrege, elimine una o mas refacciones utilizadas para este servicio:"); // NOI18N
        lbManoObraDescripcion1.setName("lbManoObraDescripcion1"); // NOI18N
        panelRefacciones.add(lbManoObraDescripcion1);
        lbManoObraDescripcion1.setBounds(20, 30, 350, 14);

        btnEliminarRefaccion.setText("Eliminar"); // NOI18N
        btnEliminarRefaccion.setName("btnEliminarRefaccion"); // NOI18N
        panelRefacciones.add(btnEliminarRefaccion);
        btnEliminarRefaccion.setBounds(100, 280, 73, 23);

        btnAgregarRefaccion.setText("Agregar"); // NOI18N
        btnAgregarRefaccion.setName("btnAgregarRefaccion"); // NOI18N
        panelRefacciones.add(btnAgregarRefaccion);
        btnAgregarRefaccion.setBounds(20, 280, 73, 23);

        servicioDescripcion.addTab("Refacciones", panelRefacciones); // NOI18N

        getContentPane().add(servicioDescripcion);
        servicioDescripcion.setBounds(10, 10, 680, 530);

        btnImprimir.setText("Imprimir"); // NOI18N
        btnImprimir.setName("btnImprimir"); // NOI18N
        getContentPane().add(btnImprimir);
        btnImprimir.setBounds(340, 550, 80, 23);

        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N
        getContentPane().add(btnAceptar);
        btnAceptar.setBounds(610, 550, 73, 23);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(520, 550, 80, 23);

        btnGuardar.setText("Guardar"); // NOI18N
        btnGuardar.setName("btnGuardar"); // NOI18N
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(430, 550, 80, 23);

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        falla.setName("falla"); // NOI18N
        jScrollPane5.setViewportView(falla);

        panelServicio.add(jScrollPane5);
        jScrollPane5.setBounds(80, 290, 580, 40);

        lbFalla.setText("Falla:"); // NOI18N
        lbFalla.setName("lbFalla"); // NOI18N
        panelServicio.add(lbFalla);
        lbFalla.setBounds(20, 290, 30, 14);
        
        btnBuscar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
        });
        
        btnImprimir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ImprimeFormato(ControlServicio.this, BaseDatos, claveRegistro).generar();
			}
        });
        
        btnAgregarManoObra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(esRegistrado && claveRegistro.length() > 0){
					AgregarConcepto temp = new AgregarConcepto(ControlServicio.this, BaseDatos, AgregarConcepto.MANO_OBRA);
					String[] dat = temp.getData();
					if(dat != null){
						String cantidad = JOptionPane.showInputDialog(ControlServicio.this, "Introduzca la cantidad de trabajos\na realizar:");
						if(cantidad != null)
							if(BaseDatos.nuevoValor("DetallesManoObra", claveRegistro+dat[0]+","+claveRegistro+","+dat[0]+","+cantidad))
								modelManoObra.addRow(new Object[]{claveRegistro+dat[0] , dat[1], cantidad, dat[2]});
							else
								JOptionPane.showMessageDialog(ControlServicio.this, "Ocurrio un error al agregar, tal vez hay datos incorrectos o ya exista el registro,\n verifique y reintente");
					}
				} else {
					JOptionPane.showMessageDialog(ControlServicio.this,"Tiene que registrar la orden de servicio para poder continuar.");
				}
			}
        });
        
        btnEliminarManoObra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(tablaManoObra.getSelectedRow() >= 0){
					int n = JOptionPane.showConfirmDialog(ControlServicio.this, "\u00bfEsta seguro que desea eliminar el registro seleccionado?","Confirmar eliminación permanente",JOptionPane.YES_NO_OPTION);		
					if(n == 0)
						if(BaseDatos.eliminar("detallesManoObra","IdDetalle="+tablaManoObra.getValueAt(tablaManoObra.getSelectedRow(), 0)))
							modelManoObra.removeRow(tablaManoObra.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(ControlServicio.this,"Para continuar seleccione un registro de la lista.","Seleccione registro",JOptionPane.INFORMATION_MESSAGE);
				}
			}
        });
        
        btnAgregarRefaccion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(esRegistrado && claveRegistro.length() > 0){
					AgregarConcepto temp = new AgregarConcepto(ControlServicio.this, BaseDatos, AgregarConcepto.REFACCION);
					String[] dat = temp.getData();
					if(dat != null){
						String cantidad = JOptionPane.showInputDialog(ControlServicio.this, "Introduzca la cantidad de refacciones\na utilizar:");
						if(cantidad != null)
							if(BaseDatos.nuevoValor("DetallesRefacciones", claveRegistro+dat[0]+","+claveRegistro+","+dat[0]+","+cantidad))
								modelRefacciones.addRow(new Object[]{claveRegistro+dat[0] , dat[1], cantidad, dat[2]});
							else
								JOptionPane.showMessageDialog(ControlServicio.this, "Ocurrio un error al agregar, tal vez hay datos incorrectos o ya exista el registro,\n verifique y reintente");
					}
				} else {
					JOptionPane.showMessageDialog(ControlServicio.this,"Tiene que registrar la orden de servicio para poder continuar.");
				}
			}
        });
        
        btnEliminarRefaccion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(tablaRefacciones.getSelectedRow() >= 0){
					int n = JOptionPane.showConfirmDialog(ControlServicio.this, "\u00bfEsta seguro que desea eliminar el registro seleccionado?","Confirmar eliminación permanente",JOptionPane.YES_NO_OPTION);		
					if(n == 0)
						if(BaseDatos.eliminar("detallesRefacciones","IdDetalle="+tablaRefacciones.getValueAt(tablaRefacciones.getSelectedRow(), 0)))
							modelRefacciones.removeRow(tablaRefacciones.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(ControlServicio.this,"Para continuar seleccione un registro de la lista.","Seleccione registro",JOptionPane.INFORMATION_MESSAGE);
				}
			}
        });
        
        idCliente.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					if(idCliente.getText().length() > 0){
						new ControlCliente(ControlServicio.this, BaseDatos, true, idCliente.getText());
					}
				}
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        equipo.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					if(equipo.getText().length() > 0){
						new ControlEquipos(ControlServicio.this, BaseDatos, true, equipo.getText());
					}
				}
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        fechaEntrada.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(fechaEntrada, AgregarFechaHora.FECHA); 
				}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        horaEntrada.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(horaEntrada, AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        fechaSalida.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(fechaSalida, AgregarFechaHora.FECHA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        horaSalida.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(horaSalida, AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        fechaPago.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(fechaPago, AgregarFechaHora.FECHA + AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        horaProgramada.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(horaProgramada, AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        fechaProgramada.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(fechaProgramada, AgregarFechaHora.FECHA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        horaInicia.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) { 
				setOverAllTime(horaInicia, AgregarFechaHora.HORA); 
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
        });
        
        btnGuardar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
        });
        
        btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(guardar())
					dispose();
			}
        });
        
        pack();
    }// </editor-fold>

    protected void setOverAllTime(JTextField entrada, int i) {
    	AgregarFechaHora tmp = new AgregarFechaHora(ControlServicio.this, i, entrada.getText());
    	
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

	public boolean guardar(){
    	String syntax = null;
    	boolean sinError = false;
    	
    	String e = "0";
    	String a = "0";
    	String r = "0";
    	String to = "0";
    	String is = "0";
    	String fa = "0";
    	String co = "0";
    	
    	String hora = "9999-01-01 00:00:00";
    	String fech = "9999-01-01 00:00:00";
    	String inic = "2000-01-01 00:00:00";
    	
    	String[] spliter = null;
    	
    	if(fechaProgramada.getText().length() > 0)
    		fech = fechaProgramada.getText();
    	
    	if(horaProgramada.getText().length() > 0)
    		hora = "2000-01-01 "+horaProgramada.getText();
    	
    	if(horaInicia.getText().length() > 0)
    		inic = "2000-01-01 "+horaInicia.getText();
    	
    	if(total.getText().length() > 0)
    		to = total.getText();
    	
    	if(importeServicio.getText().length() > 0)
    		is = importeServicio.getText();
    	
    	if(factura.getText().length() > 0)
    		fa = factura.getText();
    	
    	if(comision.getText().length() > 0)
    		co = comision.getText();
    	
    	if(entregoEquipo.getSelectedItem() != null){
    		spliter = ((String)entregoEquipo.getSelectedItem()).split(" - ");
    		e = spliter[0];
    	}
    	if(atendioEquipo.getSelectedItem() != null){
    		spliter = ((String)atendioEquipo.getSelectedItem()).split(" - ");
    		a = spliter[0];
    	}
		if(recibioEquipo.getSelectedItem() != null){
			spliter = ((String)recibioEquipo.getSelectedItem()).split(" - ");
			r = spliter[0];
		}

    	if(claveRegistro == null){

            syntax = idOrden.getText()+ "," +
            		 idCliente.getText()+ ",'" +
            		 fechaEntrada.getText()+ "','" +
            		 fech + "','" +
            		 hora + "','" +
            		 fechaSalida.getText()+ "','2000-01-01 " +
            		 horaEntrada.getText()+ "','" +
            		 inic + "','2000-01-01 " +
            		 horaSalida.getText()+ "','" +
            		 tipoServicio.getSelectedItem()+ "'," +
            		 mantenimiento.isSelected()+ "," +
            		 reparacion.isSelected()+ "," +
            		 diagnostico.isSelected()+ "," +
            		 instalacion.isSelected()+ "," +
            		 garantia.isSelected()+ "," +
            		 domicilio.isSelected()+ "," +
            		 actualizacion.isSelected()+ "," +
            		 configuracion.isSelected()+ "," +
            		 asesoria.isSelected()+ "," +
            		 remoto.isSelected()+ ",'" +
            		 estatus.getSelectedItem()+ "','" +
            		 equipo.getText()+ "','" +
            		 falla.getText()+ "','" +
            		 accesorios.getText()+ "','" +
            		 diagnosticos.getText()+ "'," +
            		 e + "," +
            		 a + "," +
            		 r + "," +
            		 to + "," +
            		 is + "," +
            		 fa + "," +
            		 co + "," +
            		 pagoComision.isSelected()+ ",'" +
            		 fechaPago.getText()+"','" +
            		 documento.getText()+"','" +
            		 departamento.getText() + "'";
            
            sinError = BaseDatos.nuevoValor("Servicios",syntax);
    	}else{

    		syntax = "IdOrden=" + idOrden.getText() +
	 			 	 ", IdCliente=" + idCliente.getText() +
		 			 ", FEntra='" + fechaEntrada.getText() +
		 			 "', FProgramada='" + fech  +
		 			 "', HProgramada='" + hora +
		 			 "', FSale='" + fechaSalida.getText() +
		 			 "', HEntra='2000-01-01 " + horaEntrada.getText() +
		 			 "', HInicia='" + inic +
		 			 "', HSale='2000-01-01 " + horaSalida.getText() +
		 			 "', TipoServicio='" + tipoServicio.getSelectedItem() +
		 			 "', Mantto=" + mantenimiento.isSelected() +
		 			 ", Reparacion=" + reparacion.isSelected() +
		 			 ", Diagnostico=" + diagnostico.isSelected() +
		 			 ", Instalacion=" + instalacion.isSelected() +
		 			 ", Garantia=" + garantia.isSelected() +
		 			 ", Domicilio=" + domicilio.isSelected() +
		 			 ", Actualizacion=" + actualizacion.isSelected() +
		 			 ", Configuracion=" + configuracion.isSelected() +
		 			 ", Asesoria=" + asesoria.isSelected() +
		 			 ", Remoto=" + remoto.isSelected() +
		 			 ", Estatus='" + estatus.getSelectedItem() +
		 			 "', Equipo='" + equipo.getText() +
		 			 "', Falla='" + falla.getText() +
		 			 "', Accesorios='" + accesorios.getText() +
		 			 "', Diagnostic='" + diagnosticos.getText() +
		 			 "', EntregaE=" + e +
		 			 ", AtendioE=" + a +
		 			 ", RecibioE=" + r +
		 			 ", Total=" + to +
		 			 ", IServicio=" + is +
		 			 ", Factura=" + fa +
		 			 ", Comision=" + co +
		 			 ", PagoCom=" + pagoComision.isSelected() +
		  			 ", FPago='" + fechaPago.getText() +
		  			 "', Documento='" + documento.getText() +
		  			 "', Departamento='" + departamento.getText()+"'";
	
			 sinError = BaseDatos.actualizar("Servicios",syntax,"IdOrden="+claveRegistro);
    	}
    	
    	if(sinError){
    		JOptionPane.showMessageDialog(ControlServicio.this,"Datos guardados exitosamente.","Guardado",JOptionPane.INFORMATION_MESSAGE);
    		claveRegistro = idOrden.getText();
		} else {
			JOptionPane.showMessageDialog(ControlServicio.this,"No se puede guardar, posiblemente halla un error en la concordancia\nde parametros o falten parametros. Por favor verifique sus datos.","Error al guardar",JOptionPane.WARNING_MESSAGE);
		}
    	
    	return sinError;
    }
    
	private void buscar() {
    	String c = JOptionPane.showInputDialog(ControlServicio.this, "Introduzca el número de identificación de la\norden que desee ver:", "Introduzca", JOptionPane.QUESTION_MESSAGE);
		if(c != null)
			if(BaseDatos.trySQL("SELECT * FROM Servicios WHERE IdOrden="+c)){
				dispose();
				new ControlServicio(BaseDatos, false, c);
			} else {
				JOptionPane.showMessageDialog(ControlServicio.this, "La orden de servicio que usted solicita no se encuentra\nregistrada.", "Error", JOptionPane.WARNING_MESSAGE);
			}
	}
	
    private void verValores(JTable table,String tabla, String columnaId, String columna){
    	
    	for(int n = 0; n < table.getRowCount(); n++){
    		String[] obtenerValor = BaseDatos.listaValoresCelda(tabla,3,columnaId+"="+table.getValueAt(n,1));
    		table.setValueAt(obtenerValor[1],n,1);
    		table.setValueAt(obtenerValor[2],n,3);
    	}
    	
	}
    // Variables declaration - do not modify
    private DefaultTableModel modelRefacciones = new javax.swing.table.DefaultTableModel(
    	null,
        new String [] {"Código de registro", "Refacción", "Costo", "Cantidad"}
    );
    
    private DefaultTableModel modelManoObra = new javax.swing.table.DefaultTableModel(
    	null,
    	new String [] {"Código de registro", "Descripción", "Cantidad a realizar", "Costo"}
    );
    
    private javax.swing.JTextArea accesorios;
    private javax.swing.JCheckBox actualizacion;
    private javax.swing.JCheckBox asesoria;
    private javax.swing.JComboBox atendioEquipo;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarManoObra;
    private javax.swing.JButton btnAgregarRefaccion;
    private javax.swing.JButton btnBuscarCliente1;
    private javax.swing.JButton btnBuscarEquipo1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarManoObra;
    private javax.swing.JButton btnEliminarRefaccion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JTextField comision;
    private javax.swing.JCheckBox configuracion;
    private javax.swing.JCheckBox diagnostico;
    private javax.swing.JTextArea diagnosticos;
    private javax.swing.JCheckBox domicilio;
    private javax.swing.JComboBox entregoEquipo;
    private javax.swing.JTextField equipo;
    private javax.swing.JComboBox estatus;
    private javax.swing.JTextField factura;
    private javax.swing.JTextArea falla;
    private javax.swing.JTextField fechaEntrada;
    private javax.swing.JTextField fechaPago;
    private javax.swing.JTextField fechaProgramada;
    private javax.swing.JTextField fechaSalida;
    private javax.swing.JCheckBox garantia;
    private javax.swing.JTextField horaEntrada;
    private javax.swing.JTextField horaInicia;
    private javax.swing.JTextField horaProgramada;
    private javax.swing.JTextField horaSalida;
    private javax.swing.JTextField idCliente;
    private javax.swing.JTextField idOrden;
    private javax.swing.JTextField documento;
    private javax.swing.JTextField departamento;
    private javax.swing.JTextField importeServicio;
    private javax.swing.JCheckBox instalacion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbAccesorios;
    private javax.swing.JLabel lbAtendioEquipo;
    private javax.swing.JLabel lbComision;
    private javax.swing.JLabel lbDiagnosticos;
    private javax.swing.JLabel lbEntregoEquipo;
    private javax.swing.JLabel lbEquipo;
    private javax.swing.JLabel lbEstatus;
    private javax.swing.JLabel lbDocumento;
    private javax.swing.JLabel lbFactura;
    private javax.swing.JLabel lbFalla;
    private javax.swing.JLabel lbFechaEntrada;
    private javax.swing.JLabel lbFechaPago;
    private javax.swing.JLabel lbFechaProgramada;
    private javax.swing.JLabel lbFechaSalida;
    private javax.swing.JLabel lbHoraEntrada;
    private javax.swing.JLabel lbHoraProgramada;
    private javax.swing.JLabel lbHoraSalida;
    private javax.swing.JLabel lbIdCliente;
    private javax.swing.JLabel lbIdOrden;
    private javax.swing.JLabel lbImporteServicio;
    private javax.swing.JLabel lbManoObraDescripcion;
    private javax.swing.JLabel lbManoObraDescripcion1;
    private javax.swing.JLabel lbRecibioEquipo;
    private javax.swing.JLabel lbTipoServicio;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbDepartamento;
    private javax.swing.JLabel lbhoraInicia;
    private javax.swing.JCheckBox mantenimiento;
    private javax.swing.JCheckBox pagoComision;
    private javax.swing.JPanel panelManoObra;
    private javax.swing.JPanel panelRefacciones;
    private javax.swing.JPanel panelServicio;
    private javax.swing.JComboBox recibioEquipo;
    private javax.swing.JCheckBox remoto;
    private javax.swing.JCheckBox reparacion;
    private javax.swing.JTabbedPane servicioDescripcion;
    private javax.swing.JTable tablaManoObra;
    private javax.swing.JTable tablaRefacciones;
    private javax.swing.JComboBox tipoServicio;
    private javax.swing.JTextField total;
    // End of variables declaration

}

