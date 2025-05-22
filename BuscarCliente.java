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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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

public class BuscarCliente extends JDialog {

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
    private String[] columnaNombres = {"Codigo", "Cliente", "Contacto"};
    private String sintaxisConsulta;
    
    private String[] returnData;
    
    public BuscarCliente(JFrame frm, final MotorBaseDatos BaseDatos) {
        super(frm, "Buscar Cliente", true);
        
        this.BaseDatos = BaseDatos;

		data = new Object[1][1];
		data[0][0] = "Para mostrar datos aquí personalize su consulta en la Barra de filtro...";
		tableModel = new DefaultTableModel(data, columnaNombres);

		toolBar = new JToolBar("Barra de tareas estándar");
		toolBar2 = new JToolBar("Barra de filtro");
		
		ImageIcon imgAceptar = ClaseGlobal.crearIcono("iconos/opTerminar.gif");
		ImageIcon imgCancelar = ClaseGlobal.crearIcono("iconos/opBorrar.gif");

		JButton toolAceptar = new JButton("Aceptar", imgAceptar);
			toolAceptar.setMargin(new Insets(2,2,2,2));
		JButton toolCancelar = new JButton("Cancelar", imgCancelar);
			toolCancelar.setMargin(new Insets(2,2,2,2));

		JLabel etiquetaFiltro = new JLabel("Filtrar de: ");
		columnasTabla = new JComboBox(new String[]{"No. de identificación de cliente", "Nombre del cliente", "Nombre del contacto"});
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
		
		toolBar.add(toolAceptar);
		toolBar.add(toolCancelar);

        table = tabla(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(0).setMinWidth(500);
        JTableHeader columnas = table.getTableHeader();
        columnas.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this window.
        JLabel top = new JLabel("Para buscar un cliente seleccione la columna de filtro y escriba lo que desee buscar y seguidamente en filtrar:");
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        subcontenido.add(toolBar2, BorderLayout.NORTH);
        subcontenido.add(scrollPane, BorderLayout.CENTER);
        
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(subcontenido, BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.SOUTH);

        toolAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!setData()){
					JOptionPane.showMessageDialog(BuscarCliente.this, "Seleccione un registro para agregar.","Seleccione", JOptionPane.WARNING_MESSAGE);
				} else {
					dispose();
				}
			}
		});
		
        toolCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				returnData = null;
				dispose();
			}
		});

		filtrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filtro.getText().length() > 0)
					filtrar();
				else
					JOptionPane.showMessageDialog(BuscarCliente.this, "Escriba por lo menos una letra para comenzar el filtro.", "Introduzca criterio", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
        //...Then set the window size or call pack...
		setSize(new Dimension(550, 450));
    	setLocationRelativeTo(null);
    	setResizable(false);
    	
        this.setVisible(true);
    }

    public String[] getData(){
    	return returnData;
    }
    
    public boolean setData(){
    	boolean completado = false;
    	if(codigoSeleccionado != null){
    		returnData = new String[table.getColumnCount()];
    		for(int n = 0; n < table.getColumnCount(); n++){
    			returnData[n] = (String)table.getValueAt(selectedRow, n);
    		}
    		completado = true;
    	}
    	return completado;
    }
    
    private void filtrar(){
    	data = null;
    	
    	switch(columnasTabla.getSelectedIndex()){
    		case 0:
    			sintaxisConsulta = "SELECT IdCliente, NombreCliente, NombreContacto FROM Clientes WHERE IdCliente LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 1:
    			sintaxisConsulta = "SELECT IdCliente, NombreCliente, NombreContacto FROM Clientes WHERE NombreCliente LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    		case 2:
    			sintaxisConsulta = "SELECT IdCliente, NombreCliente, NombreContacto FROM Clientes WHERE NombreContacto LIKE '%"+filtro.getText()+"%'";
    			data = BaseDatos.advancedSQL(sintaxisConsulta, 3);
    			break;
    	}
    	if(data != null){
	    	if(data.length == 0){
	    		data = new String[1][1];
	    		data[0][0] = "No se encontro ningún registro";
	    		tableModel.setDataVector(data, columnaNombres);
	    		table.getColumnModel().getColumn(0).setMinWidth(500);
	    		sintaxisConsulta = null;
	    	} else {
	    		tableModel.setDataVector(data, columnaNombres);
	    		table.getColumnModel().getColumn(0).setMinWidth(50);
	    		table.getColumnModel().getColumn(0).setMaxWidth(80);
	    		table.getColumnModel().getColumn(2).setMinWidth(130);
	    		table.getColumnModel().getColumn(2).setMaxWidth(200);
	    	}
    	} else {
    		data = new String[1][1];
    		data[0][0] = "No se encontro ningún registro";
    		tableModel.setDataVector(data, columnaNombres);
    		table.getColumnModel().getColumn(0).setMinWidth(500);
    		sintaxisConsulta = null;
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
}
