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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

public class AgregarConcepto extends JDialog {

	private static final long serialVersionUID = 1L;
	public static final int REFACCION = 1;
	public static final int MANO_OBRA = 2;
	
    boolean DEBUG = true;
    boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
    
    JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable table;

	String codigoSeleccionado = null;
	private int selectedRow = -1;
	
    private JToolBar toolBar;
    
    private Object[][] data;
    private String[] columnaNombres = {"Codigo", "Descripcion", "Coste"};

    private String[] returnData;
    
    public AgregarConcepto(JFrame target, final MotorBaseDatos BaseDatos, int space) {
        super(target, "", true);

        switch(space){
        	case REFACCION:
        		setTitle("Agregar Refaccion");
        		data = BaseDatos.advancedSQL("SELECT * FROM Refacciones ORDER BY Descripcion",3);
        	break;
        	case MANO_OBRA:
        		setTitle("Agregar Mano de obra");
        		data = BaseDatos.advancedSQL("SELECT * FROM ManoObra ORDER BY Descripcion",3);
        }
		
		tableModel = new DefaultTableModel(data, columnaNombres);

		toolBar = new JToolBar("Barra de tareas estandar");
		
		ImageIcon imgAceptar = ClaseGlobal.crearIcono("iconos/opTerminar.gif");
		ImageIcon imgCancelar = ClaseGlobal.crearIcono("iconos/opBorrar.gif");
		ImageIcon imgTop = ClaseGlobal.crearIcono("iconos/topAgregar.jpg");

		JButton toolAceptar = new JButton("Aceptar", imgAceptar);
			toolAceptar.setMargin(new Insets(2,2,2,2));
		JButton toolCancelar = new JButton("Cancelar", imgCancelar);
			toolCancelar.setMargin(new Insets(2,2,2,2));

		toolBar.add(toolAceptar);
		toolBar.add(toolCancelar);

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
        
        toolAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!setData()){
					JOptionPane.showMessageDialog(AgregarConcepto.this, "Seleccione un registro para agregar.","Seleccione", JOptionPane.WARNING_MESSAGE);
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
        
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
        //...Then set the window size or call pack...
    	setSize(new Dimension(550, 450));
    	setLocationRelativeTo(null);
    	setResizable(false);
        setVisible(true);
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
		
		table.getColumnModel().getColumn(0).setMinWidth(25);
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		return table;
	}
}
