/*
 * Created on 29/09/2009
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
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/*
 * Esta ventana muestra todos los valores contenidos en la tabla alamcen
 * en la cual se guardan todas las refacciones utilizadas
 */

public class VerServicios extends JFrame {

	private static final long serialVersionUID = 1L;
	
    boolean DEBUG = true;
    boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
    
    JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable table;
    private MotorBaseDatos BaseDatos;

	String codigoSeleccionado = null;
	private int selectedRow = -1;
	
    private JComboBox columnasTabla;
    private JTextField filtro;
    
    private JToolBar toolBar;
    private JToolBar toolBar2;
    
    private Object[][] data;
    private String[] columnaNombres = {"Codigo", "Cliente", "Fecha entrada"};
    private String sintaxisConsulta;
    
    public VerServicios(final MotorBaseDatos BaseDatos) {
        super("Buscar Servicios");
        
        this.BaseDatos = BaseDatos;

		data = new Object[1][1];
		data[0][0] = "Para mostrar datos aquí personalize su consulta en la Barra de filtro...";
		tableModel = new DefaultTableModel(data, columnaNombres);

		toolBar = new JToolBar("Barra de tareas estandar");
		toolBar2 = new JToolBar("Barra de filtro");
		
		ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/buscarServicios.jpg");
		ImageIcon imgCerrar = ClaseGlobal.crearIcono("iconos/opCerrar.gif");
		ImageIcon imgEliminar = ClaseGlobal.crearIcono("iconos/opEliminar.gif");
		ImageIcon imgModificar = ClaseGlobal.crearIcono("iconos/opModificar.gif");
		ImageIcon imgInformacion = ClaseGlobal.crearIcono("iconos/opInformacion.gif");
		ImageIcon imgActualizar = ClaseGlobal.crearIcono("iconos/opActualizar.gif");
		ImageIcon imgImprimir = ClaseGlobal.crearIcono("iconos/opImprimir.gif");
		
		JButton toolEliminar = new JButton("Eliminar", imgEliminar);
			toolEliminar.setMargin(new Insets(2,2,2,2));
		JButton toolModificar = new JButton("Modificar", imgModificar);
			toolModificar.setMargin(new Insets(2,2,2,2));
		JButton toolCerrar = new JButton("Cerrar", imgCerrar);
			toolCerrar.setMargin(new Insets(2,2,2,2));
		JButton toolInformacion = new JButton(imgInformacion);
			toolInformacion.setMargin(new Insets(2,2,2,2));
		JButton toolActualizar = new JButton(imgActualizar);
			toolActualizar.setMargin(new Insets(2,2,2,2));
		JButton toolImprimir = new JButton(imgImprimir);
			toolActualizar.setMargin(new Insets(2,2,2,2));

		JLabel etiquetaFiltro = new JLabel("Filtrar de: ");
		columnasTabla = new JComboBox(new String[]{"No. de identificación de orden", "Nombre del cliente", "Fecha  de entrada", "Fecha  de salida", "Tipo de servicio", "Estatus", "Nombre de técnico asignado"});
			columnasTabla.setPreferredSize(new Dimension(200,23));
		JLabel etiquetaA = new JLabel(" que cotenga: ");
		filtro = new JTextField();
			filtro.setMargin(new Insets(2,2,2,2));
			filtro.setPreferredSize(new Dimension(240,23));
		JButton filtrar = new JButton("Filtrar");
			filtrar.setMargin(new Insets(2,2,2,2));
			filtrar.setToolTipText("Realizar filtro");
		
		JPanel subcontenido = new JPanel(new BorderLayout());
		
		toolBar2.add(etiquetaFiltro);
		toolBar2.add(columnasTabla);
		toolBar2.add(etiquetaA);
		toolBar2.add(filtro);
		toolBar2.add(filtrar);
		
		toolBar.add(toolModificar);
		toolBar.add(toolEliminar);
		toolBar.addSeparator();
		toolBar.add(toolInformacion);
		toolBar.addSeparator();
		toolBar.add(toolImprimir);
		toolBar.addSeparator();
		toolBar.add(toolActualizar);

        table = tabla(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(0).setMinWidth(500);
        JTableHeader columnas = table.getTableHeader();
        columnas.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this window.
        JLabel top = new JLabel(imgTop);
        
        subcontenido.add(toolBar2, BorderLayout.NORTH);
        subcontenido.add(scrollPane, BorderLayout.CENTER);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(subcontenido, BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.SOUTH);

        toolImprimir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			     try {
			           table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(
			             "Servicios"),
			           new MessageFormat("Página {0,number}"));
			         } catch (PrinterException x) {
			           x.printStackTrace();
			         }
			}
		});
        
        toolModificar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ControlServicio(BaseDatos, false, codigoSeleccionado);
			}
		});
        
        toolInformacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ControlServicio(BaseDatos, true, codigoSeleccionado);
			}
		});
        
        toolActualizar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				actualizarTabla();
			}
		});
        
		toolEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(codigoSeleccionado != null && selectedRow != -1){
					int n = JOptionPane.showConfirmDialog(VerServicios.this, "\u00bfEsta seguro que desea eliminar el registro seleccionado?","Confirmar eliminación permanente",JOptionPane.YES_NO_OPTION);		
					if(n == 0)
						if(BaseDatos.eliminar("Servicios","IdOrden="+codigoSeleccionado))
							tableModel.removeRow(selectedRow);
				} else {
					JOptionPane.showMessageDialog(VerServicios.this,"Para continuar seleccione un registro de la lista.","Seleccione registro",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		filtrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filtro.getText().length() > 0)
					filtrar();
				else
					JOptionPane.showMessageDialog(VerServicios.this, "Escriba por lo menos una letra para comenzar el filtro.", "Introduzca criterio", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
        //...Then set the window size or call pack...
    	setSize(new Dimension(700, 500));
    	setLocationRelativeTo(null);
    	setResizable(false);
    	
        this.setVisible(true);
    }

    private void filtrar(){
    	data = null;
    	
    	switch(columnasTabla.getSelectedIndex()){
    		case 0:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE IdOrden LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 1:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE IdCliente IN (SELECT IdCliente FROM Clientes WHERE NombreCliente LIKE '%"+filtro.getText()+"%')";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 2:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE FEntra LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 3:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE FSale LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 4:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE TipoServicio LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 5:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE Estatus LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 6:
    			sintaxisConsulta = "SELECT IdOrden, IdCliente, FEntra FROM Servicios WHERE AtendioE IN (SELECT IdEmpleado FROM Empleados WHERE Nombre LIKE '%"+filtro.getText()+"%')";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    	}
    	
    	if(data != null)
	    	if(data.length == 0){
	    		data = new String[1][1];
	    		data[0][0] = "No se encontro ningún registro";
	    		tableModel.setDataVector(data, columnaNombres);
	    		table.getColumnModel().getColumn(0).setMinWidth(500);
	    		sintaxisConsulta = null;
	    	} else {
	    		tableModel.setDataVector(data, columnaNombres);
	    		table.getColumnModel().getColumn(0).setMinWidth(100);
	    		table.getColumnModel().getColumn(0).setMaxWidth(200);
	    		table.getColumnModel().getColumn(2).setMinWidth(130);
	    		table.getColumnModel().getColumn(2).setMaxWidth(200);
	    		verValores("Clientes", "IdCliente", "NombreCliente", 1);
	        	verSoloFecha();
	    	}
    }
    
    // crea la tabla en donde se mostraran los registros
	private JTable tabla(DefaultTableModel sorter){
		
		table = new JTable(sorter){ // Para volver no editable
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){ return false; }
		};
		
		table.setPreferredScrollableViewportSize(new Dimension(100, 70));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if (ALLOW_ROW_SELECTION) { 
		    
		    ListSelectionModel rowSM = table.getSelectionModel();
		    rowSM.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting()) return;
			    
			    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			    if (lsm.isSelectionEmpty()) {
			    	codigoSeleccionado = null;
			    	selectedRow = -1;
			    } else {
					selectedRow = lsm.getMinSelectionIndex();
					codigoSeleccionado = (String)table.getValueAt(selectedRow,0);
			    }
			}
		    });
		} else {
		    table.setRowSelectionAllowed(false);
		}
		
		return table;
	}
	// actualiza la tabla de los equipos
	private void actualizarTabla(){
		if(sintaxisConsulta != null){
			data = null;
	    	data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
	    	
			tableModel.setDataVector(data, columnaNombres);
			
			table.getColumnModel().getColumn(0).setMinWidth(100);
			table.getColumnModel().getColumn(0).setMaxWidth(200);
			table.getColumnModel().getColumn(2).setMinWidth(130);
			table.getColumnModel().getColumn(2).setMaxWidth(200);
			
			verValores("Clientes", "IdCliente", "NombreCliente", 1);
			verSoloFecha();
		}
    }
	
	// reemplaza los codigos por los nombres o descripciones desde las tablas referenciadas
    private void verValores(String tabla, String columnaId, String columna, int no_columna){
    	for(int n = 0; n < table.getRowCount(); n++){
    		String obtenerValor = BaseDatos.obtenerValorCelda(tabla,columna,columnaId+"="+table.getValueAt(n,no_columna));
    		table.setValueAt(obtenerValor,n,no_columna);
    	}
	}
    
    private void verSoloFecha(){
    	for(int n = 0; n < table.getRowCount(); n++){
    		String obtenerValor = (String)table.getValueAt(n,2);
    		table.setValueAt(obtenerValor.substring(0,10),n,2);
    	}
	}
}
