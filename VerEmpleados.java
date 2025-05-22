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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

public class VerEmpleados extends JFrame {

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
	
    private JToolBar toolBar;
    
    private Object[][] data;
    private String[] columnaNombres = {"Codigo", "Nombre", "Comision","Estatus","Sueldo base","PS base","Objetivo M1", "Objetivo M2"};

    public VerEmpleados(final MotorBaseDatos BaseDatos) {
        super("Listado de empleados");
        
        this.BaseDatos = BaseDatos;

        try{
		data = BaseDatos.listaTabla("Empleados",8);
        }catch(Exception e){
            
        }
		tableModel = new DefaultTableModel(data, columnaNombres);

		toolBar = new JToolBar("Barra de tareas estandar");
		
		ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topTecnicos.jpg");
		ImageIcon imgCerrar = ClaseGlobal.crearIcono("iconos/opCerrar.gif");
		ImageIcon imgNuevo = ClaseGlobal.crearIcono("iconos/opNuevo.gif");
		ImageIcon imgEliminar = ClaseGlobal.crearIcono("iconos/opEliminar.gif");
		ImageIcon imgModificar = ClaseGlobal.crearIcono("iconos/opModificar.gif");
		ImageIcon imgInformacion = ClaseGlobal.crearIcono("iconos/opInformacion.gif");
		ImageIcon imgActualizar = ClaseGlobal.crearIcono("iconos/opActualizar.gif");
		ImageIcon imgImprimir = ClaseGlobal.crearIcono("iconos/opImprimir.gif");
		
		JButton toolNuevo = new JButton("Agregar nuevo", imgNuevo);
			toolNuevo.setMargin(new Insets(2,2,2,2));
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

		toolBar.add(toolNuevo);
		toolBar.add(toolModificar);
		toolBar.add(toolEliminar);
		toolBar.addSeparator();
		toolBar.add(toolInformacion);
		toolBar.addSeparator();
		toolBar.add(toolImprimir);
		toolBar.addSeparator();
		toolBar.add(toolActualizar);

        //JTable table = new JTable(myModel);          //OLD
        table = tabla(tableModel);             //NEW
        table.setAutoCreateRowSorter(true);
        JTableHeader columnas = table.getTableHeader();
        columnas.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this window.
        JLabel top = new JLabel(imgTop);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.SOUTH);
        
        toolNuevo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ControlEmpleados(VerEmpleados.this, BaseDatos, false, null);
			}
		});
        
        toolImprimir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			     try {
			           table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(
			             "Empleados"),
			           new MessageFormat("Página {0,number}"));
			         } catch (PrinterException x) {
			           x.printStackTrace();
			         }
			}
		});
        
        toolModificar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ControlEmpleados(VerEmpleados.this, BaseDatos, false, codigoSeleccionado);
			}
		});
        
        toolInformacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ControlEmpleados(VerEmpleados.this, BaseDatos, true, codigoSeleccionado);
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
					int n = JOptionPane.showConfirmDialog(VerEmpleados.this, "\u00bfEsta seguro que desea eliminar el registro seleccionado?","Confirmar eliminación permanente",JOptionPane.YES_NO_OPTION);		
					if(n == 0)
						if(BaseDatos.eliminar("Empleados","IdEmpleado="+codigoSeleccionado))
							tableModel.removeRow(selectedRow);
				} else {
					JOptionPane.showMessageDialog(VerEmpleados.this,"Para continuar seleccione un registro de la lista.","Seleccione registro",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		cambiarForma();
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
        //...Then set the window size or call pack...
    	setSize(new Dimension(700, 500));
    	setLocationRelativeTo(null);
    	setResizable(false);
        setVisible(true);
    }

    // crea la tabla en donde se mostraran los registros
	private JTable tabla(final DefaultTableModel sorter){
		
		table = new JTable(sorter){ // Para volver no editable

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){
				return false; 
			}
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
		
		table.getColumnModel().getColumn(1).setMinWidth(200);

		return table;
	}
	// actualiza la tabla de los equipos
	private void actualizarTabla(){
    	data = null;

    	data = BaseDatos.listaTabla("Empleados", 8);
		tableModel.setDataVector(data, columnaNombres);
		table.getColumnModel().getColumn(1).setMinWidth(200);
		
		cambiarForma();
    }

	private void cambiarForma(){
    	
    	for(int n = 0; n < table.getRowCount(); n++){
    		String s;
    		
    		if(table.getValueAt(n,3).equals("1"))
    			s = "Activo";
    			else
    				s = "Inactivo";

    		table.setValueAt(s,n,3);
    	}
    	
	}
}
