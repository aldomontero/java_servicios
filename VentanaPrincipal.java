/*
 * Created on 24/09/2009
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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/*
 * Esta clase es en donde se crea la ventana principal en donde se acceden a 
 * todas las opciones, tablas y configuraciones de todo el programa
 * 
 */
public class VentanaPrincipal extends JFrame{
		
	private static final long serialVersionUID = 1L;
	public JPanel contenedor;
    public JPanel index;
    public JPanel catalogo;
    public JPanel servicios;
    public JPanel reportes;
    
	private JLabel LabelEstado;
	
	MotorBaseDatos BaseDatos;
	private JMenuBar menuBar;
	JButton toolEmpleado;
	JMenuItem menuArchivoAgregarEmpleado;
	
	VerEmpleados vtnEmpleados;
	VerClientes vtnClientes;
	VerEquipos vtnEquipos;
	VerManoObra vtnManoObra;
	VerRefacciones vtnRefacciones;
	VerServicios vtnServicios;

	Timer anima;
	int x = 0;
	
    public VentanaPrincipal(final MotorBaseDatos BaseDatos) {
    	super("Administracion de ordenes de servicios");
    	
    	this.BaseDatos = BaseDatos;

        //Set up the GUI.
    	index = new JPanel(null);
    	catalogo = new JPanel(null);
    	servicios = new JPanel(null);
    	reportes = new JPanel(null);
    	
        contenedor = new JPanel(null){

			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		Image ds = ClaseGlobal.crearImagen("iconos/panel.gif");

        		g.drawImage(ds,0,0,VentanaPrincipal.this);
        	}
        }; //a specialized layered pane
        
        JPanel bienvenida = new JPanel(new GridLayout(1,2));
        
        bienvenida.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		LabelEstado = new JLabel("Inicio");

		ImageIcon imgCatalogos = ClaseGlobal.crearIcono("iconos/opCatalogos.gif");
		ImageIcon imgOrdenesServicios = ClaseGlobal.crearIcono("iconos/opServicios.gif");
		ImageIcon imgReportes = ClaseGlobal.crearIcono("iconos/opReportes.gif");
		ImageIcon imgSalir = ClaseGlobal.crearIcono("iconos/opSalir.gif");

		JButton toolCatalogos = new JButton("Catálogos", imgCatalogos);
			toolCatalogos.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolCatalogos.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolOrdenesServicios = new JButton("Ordenes",imgOrdenesServicios);
			toolOrdenesServicios.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolOrdenesServicios.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolReportes = new JButton("Reportes",imgReportes);
			toolReportes.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolReportes.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolSalir = new JButton("Salir",imgSalir);
			toolSalir.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolSalir.setHorizontalTextPosition(AbstractButton.CENTER);

		ImageIcon imgClientes = ClaseGlobal.crearIcono("iconos/opClientes.gif");
		ImageIcon imgEquipos = ClaseGlobal.crearIcono("iconos/opEquipos.gif");
		ImageIcon imgRefacciones = ClaseGlobal.crearIcono("iconos/opRefacciones.gif");
		ImageIcon imgTecnicos = ClaseGlobal.crearIcono("iconos/opTecnicos.gif");
		ImageIcon imgMano = ClaseGlobal.crearIcono("iconos/opMano.gif");
		
		JButton toolClientes = new JButton("Clientes", imgClientes);
			toolClientes.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolClientes.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolEquipos = new JButton("Equipos",imgEquipos);
			toolEquipos.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolEquipos.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolTecnicos = new JButton("Técnicos",imgTecnicos);
			toolTecnicos.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolTecnicos.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolRefacciones = new JButton("Refacciones",imgRefacciones);
			toolRefacciones.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolRefacciones.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolManoObra = new JButton("Trabajos",imgMano);
			toolManoObra.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolManoObra.setHorizontalTextPosition(AbstractButton.CENTER);
		final JButton toolCatalogoAtras = new JButton("<<");
		
		ImageIcon imgAgregar = ClaseGlobal.crearIcono("iconos/opAgregar.gif");
		ImageIcon imgBuscar = ClaseGlobal.crearIcono("iconos/opBusqueda.gif");
		ImageIcon imgFiltro = ClaseGlobal.crearIcono("iconos/opFiltro.gif");
		
		JButton toolAgregar = new JButton("Nuevo",imgAgregar);
			toolAgregar.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolAgregar.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolBuscar = new JButton("Buscar",imgBuscar);
			toolBuscar.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolBuscar.setHorizontalTextPosition(AbstractButton.CENTER);
		JButton toolFiltrar = new JButton("Filtrar",imgFiltro);
			toolFiltrar.setVerticalTextPosition(AbstractButton.BOTTOM);
			toolFiltrar.setHorizontalTextPosition(AbstractButton.CENTER);

		final JButton toolServicioAtras = new JButton("<<");
		
		JButton toolReportesPagadas = new JButton("Pagadas");
		JButton toolReportesPendientes = new JButton("Pedientes");
		JButton toolReportesPorCliente = new JButton("Por cliente");
		final JButton toolReporteAtras = new JButton("<<");
		
		index.setBounds(0,300,600,55);
		catalogo.setBounds(600,300,600,55);
		servicios.setBounds(600,300,600,55);
		reportes.setBounds(600,300,600,55);

		toolCatalogoAtras.setBounds(530,360,50,22);
		toolServicioAtras.setBounds(530,360,50,22);
		toolReporteAtras.setBounds(530,360,50,22);

		toolCatalogoAtras.setVisible(false);
		toolServicioAtras.setVisible(false);
		toolReporteAtras.setVisible(false);
		
		toolCatalogos.setBounds(30,2,100,49);
		toolOrdenesServicios.setBounds(150,4,100,49);
		toolReportes.setBounds(270,4,100,49);
		toolSalir.setBounds(390,2,100,49);
		
		toolClientes.setBounds(20,2,90,49);
		toolEquipos.setBounds(130,2,90,49);
		toolTecnicos.setBounds(240,2,90,49);
		toolRefacciones.setBounds(350,2,90,49);
		toolManoObra.setBounds(460,2,90,49);
		
		toolAgregar.setBounds(20,2,100,49);
		toolBuscar.setBounds(140,2,100,49);
		toolFiltrar.setBounds(260,2,100,49);
		
		toolReportesPagadas.setBounds(20,4,120,22);
		toolReportesPendientes.setBounds(160,4,120,22);

		toolReportesPorCliente.setBounds(20,30,120,22);

		
		index.add(toolCatalogos);
		index.add(toolOrdenesServicios);
		index.add(toolReportes);
		index.add(toolSalir);
		
		catalogo.add(toolClientes);
		catalogo.add(toolEquipos);
		catalogo.add(toolTecnicos);
		catalogo.add(toolRefacciones);
		catalogo.add(toolManoObra);
		
		servicios.add(toolAgregar);
		servicios.add(toolBuscar);
		servicios.add(toolFiltrar);
		
		reportes.add(toolReportesPagadas);
		reportes.add(toolReportesPendientes);

		reportes.add(toolReportesPorCliente);
		
		contenedor.add(index);
		contenedor.add(catalogo);
		contenedor.add(servicios);
		contenedor.add(reportes);
		
		contenedor.add(toolCatalogoAtras);
		contenedor.add(toolServicioAtras);
		contenedor.add(toolReporteAtras);
		
		irCatalogo(0,0);
		
		toolCatalogos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(1,0);
					toolCatalogoAtras.setVisible(true);
				}
			}
		});
		
		toolClientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirClientes();
			}
		});

		toolEquipos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirEquipos();
			}
		});
		
		toolTecnicos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirEmpleados();
			}
		});
		
		toolRefacciones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirRefacciones();
			}
		});
		
		toolManoObra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirManoObra();
			}
		});
		
		toolCatalogoAtras.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(0,1);
					toolCatalogoAtras.setVisible(false);
				}
			}
		});
		
		toolOrdenesServicios.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(2,0);
					toolServicioAtras.setVisible(true);
				}
			}
		});
		
		toolServicioAtras.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(0,2);
					toolServicioAtras.setVisible(false);
				}
			}
		});
		
		toolAgregar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ControlServicio(BaseDatos, false, null);
			}
		});
		
		toolBuscar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscar();
			}
		});
		
		toolFiltrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirServicios();
			}
		});
		
		toolReportes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(3,0);
					toolReporteAtras.setVisible(true);
				}
			}
		});
		
		toolReportesPorCliente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReporteCliente(VentanaPrincipal.this, BaseDatos);
			}
		});
		
		toolReportesPagadas.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReportePagado(VentanaPrincipal.this, BaseDatos);
			}
		});
		
		toolReportesPendientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReportePendiente(VentanaPrincipal.this, BaseDatos);
			}
		});
		
		toolReporteAtras.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!anima.isRunning()){
					irCatalogo(0,3);
					toolReporteAtras.setVisible(false);
				}
			}
		});
		
		toolSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salir();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		bienvenida.add(LabelEstado);

        getContentPane().add(contenedor, BorderLayout.CENTER);
        getContentPane().add(bienvenida, BorderLayout.SOUTH);

        setIconImage(icon);
        setJMenuBar(createMenuBar());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600,460);
        setResizable(false);
        setLocationRelativeTo(null);
        
        index.setBackground(Color.white);
        catalogo.setBackground(Color.white);
        servicios.setBackground(Color.white);
        reportes.setBackground(Color.white);
        
        setVisible(true);
        
    }

	// se crea el menu principal
    protected JMenuBar createMenuBar() {
        menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        	JMenuItem menuArchivoSalir = new JMenuItem("Salir ");

        JMenu menuControl = new JMenu("Sistema");
        
        	JMenu menuControlCatalogos = new JMenu("Catálogos");
        		JMenuItem menuControlCatalogosTecnicos = new JMenuItem("Técnicos...");
        		JMenuItem menuControlCatalogosClientes = new JMenuItem("Clientes...");
        		JMenuItem menuControlCatalogosEquipos = new JMenuItem("Equipos...");
        		JMenuItem menuControlCatalogosManoObra = new JMenuItem("Mano de obra...");
        		JMenuItem menuControlCatalogosRefacciones = new JMenuItem("Refacciones...");
        		
        	JMenu menuControlOrdenes = new JMenu("Ordenes de servicio");
	    		JMenuItem menuControlOrdenesCapturar = new JMenuItem("Capturar...");
	    		JMenuItem menuControlOrdenesBuscar = new JMenuItem("Buscar...");
	    		JMenuItem menuControlOrdenesFiltrar = new JMenuItem("Filtrar...");
	    		
        	JMenu menuControlReportes = new JMenu("Reportes");
	    		JMenuItem menuControlReportesPagadas = new JMenuItem("Servicios pagados");
	    		JMenuItem menuControlReportesPendientes = new JMenuItem("Servicios pendientes");
	    		JMenuItem menuControlReportesPorCliente = new JMenuItem("Servicios por cliente");
	    		
        JMenu menuHerramientas = new JMenu("Herramientas");
        	JMenuItem menuHerramientasConexion = new JMenuItem("Configurar conexión...");
        	
        JMenu menuAyuda = new JMenu("Ayuda");
        	JMenuItem menuAyudaAcerca = new JMenuItem("Acerca del sistema");

        menuArchivo.add(menuArchivoSalir);

	        menuControlCatalogos.add(menuControlCatalogosTecnicos);
	        menuControlCatalogos.add(menuControlCatalogosClientes);
	        menuControlCatalogos.add(menuControlCatalogosEquipos);
	        menuControlCatalogos.addSeparator();
	        menuControlCatalogos.add(menuControlCatalogosManoObra);
	        menuControlCatalogos.add(menuControlCatalogosRefacciones);
	        
	        menuControlOrdenes.add(menuControlOrdenesCapturar);
	        menuControlOrdenes.add(menuControlOrdenesBuscar);
	        menuControlOrdenes.add(menuControlOrdenesFiltrar);
	        
	        menuControlReportes.add(menuControlReportesPagadas);
	        menuControlReportes.add(menuControlReportesPendientes);
	        menuControlReportes.add(menuControlReportesPorCliente);
	        
        menuControl.add(menuControlCatalogos);
        menuControl.addSeparator();
        menuControl.add(menuControlOrdenes);
        menuControl.addSeparator();
        menuControl.add(menuControlReportes);

	    menuHerramientas.add(menuHerramientasConexion);

	    menuAyuda.addSeparator();
        menuAyuda.add(menuAyudaAcerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuControl);
        menuBar.add(menuHerramientas);
        menuBar.add(menuAyuda);
 
        menuArchivoSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				salir();
			}
        });
        
        menuControlCatalogosTecnicos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirEmpleados();
			}
        });
        
        menuControlCatalogosClientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirClientes();
			}
        });
        
        menuControlCatalogosEquipos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirEquipos();
			}
        });
        
        menuControlCatalogosManoObra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirManoObra();
			}
        });
        
        menuControlCatalogosRefacciones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirRefacciones();
			}
        });
        
        menuControlOrdenesCapturar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ControlServicio(BaseDatos, false, null);
			}
        });
        
        menuControlOrdenesBuscar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscar();
			}
        });
        
        menuControlOrdenesFiltrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				abrirServicios();
			}
        });
        
        menuHerramientasConexion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BaseDatos.cambiarConfiguracion(VentanaPrincipal.this);
			}
        });
        
        menuAyudaAcerca.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new VentanaAcerca(VentanaPrincipal.this);
			}
        });
        
        menuControlReportesPagadas.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReportePagado(VentanaPrincipal.this, BaseDatos);
			}
		});
        
        menuControlReportesPendientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReportePendiente(VentanaPrincipal.this, BaseDatos);
			}
		});
        
        menuControlReportesPorCliente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReporteCliente(VentanaPrincipal.this, BaseDatos);
			}
		});
        
        return menuBar;
    }
    
    public void irCatalogo(final int escena, final int escenaActiva){
    	
    	anima = new Timer(5, new ActionListener(){
    		
			public void actionPerformed(ActionEvent arg0) {
				if(escenaActiva == 0){
					index.setBounds(x, 300,600,55);
					if (escena == 1){
						catalogo.setVisible(true);
						catalogo.setEnabled(true);
						catalogo.setBounds((600+x), 300, 600, 55);
						LabelEstado.setText("Inicio > Catalogos");
					} else if (escena == 2){
						servicios.setVisible(true);
						servicios.setEnabled(true);
						servicios.setBounds((600+x), 300, 600, 55);
						LabelEstado.setText("Inicio > Ordenes de servicio");
					}else if (escena == 3){
						reportes.setVisible(true);
						reportes.setEnabled(true);
						reportes.setBounds((600+x), 300, 600, 55);
						LabelEstado.setText("Inicio > Reportes");
					}
			    	x -= 20;
			    	if(x <= -610){
			    		anima.stop();
			    		switch(escena){
			    		case 1:
			    			servicios.setVisible(false);
							servicios.setEnabled(false);
							reportes.setVisible(false);
							reportes.setEnabled(false);
							index.setVisible(false);
							index.setEnabled(false);
							break;
			    		case 2:
			    			catalogo.setVisible(false);
			    			catalogo.setEnabled(false);
							reportes.setVisible(false);
							reportes.setEnabled(false);
							index.setVisible(false);
							index.setEnabled(false);
							break;
			    		case 3:
			    			catalogo.setVisible(false);
			    			catalogo.setEnabled(false);
							servicios.setVisible(false);
							servicios.setEnabled(false);
							index.setVisible(false);
							index.setEnabled(false);
			    		}
			    		x = 0;
			    	}
				} else {
					index.setVisible(true);
					index.setEnabled(true);
					if (escenaActiva == 1)
						catalogo.setBounds(x, 300, 600, 55);
					else if (escenaActiva == 2)
						servicios.setBounds(x, 300, 600, 55);
					else if (escenaActiva == 3)
						reportes.setBounds(x, 300, 600, 55);
					index.setBounds(-600+x, 300,600,55);
			    	x += 20;
			    	if(x >= 620){
			    		anima.stop();
			    		LabelEstado.setText("Inicio");
			    		servicios.setVisible(false);
						servicios.setEnabled(false);
						reportes.setVisible(false);
						reportes.setEnabled(false);
						catalogo.setVisible(false);
						catalogo.setEnabled(false);
			    		x = 0;
			    	}
				}
			}
    	});
    	if(escena != escenaActiva)
    		anima.start();	
    }
    
    // sale del programa de manera segura cerrando la conexion con bd
    private void abrirBuscar() {
    	String c = JOptionPane.showInputDialog(VentanaPrincipal.this, "Introduzca el número de identificación de la\norden que desee ver:", "Introduzca", JOptionPane.QUESTION_MESSAGE);
		if(c != null)
			if(BaseDatos.trySQL("SELECT * FROM Servicios WHERE IdOrden="+c))
				new ControlServicio(BaseDatos, false, c);
			else
				JOptionPane.showMessageDialog(VentanaPrincipal.this, "La orden de servicio que usted solicita no se encuentra\nregistrada.", "Error", JOptionPane.WARNING_MESSAGE);
	}
    
    private void salir(){
    	int n = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "\u00bfDesea salir del sistema?","Confirmar salida", JOptionPane.YES_NO_OPTION);		
		if(n == 0){
			//BaseDatos.cerrarDB();
			System.exit(0);
		}
    }

    private void abrirEmpleados(){
    	if(vtnEmpleados == null){
    		vtnEmpleados = new VerEmpleados(BaseDatos);
    	} else {
    		vtnEmpleados.setState(JFrame.NORMAL);
    		vtnEmpleados.setVisible(true);
    	}
    }
    
    private void abrirClientes(){
    	if(vtnClientes == null){
    		vtnClientes = new VerClientes(BaseDatos);
    	} else {
    		vtnClientes.setState(JFrame.NORMAL);
    		vtnClientes.setVisible(true);
    	}
    }
    
    private void abrirEquipos(){
    	if(vtnEquipos == null){
    		vtnEquipos = new VerEquipos(BaseDatos);
    	} else {
    		vtnEquipos.setState(JFrame.NORMAL);
    		vtnEquipos.setVisible(true);
    	}
    }
    
    private void abrirManoObra(){
    	if(vtnManoObra == null){
    		vtnManoObra = new VerManoObra(BaseDatos);
    	} else {
    		vtnManoObra.setState(JFrame.NORMAL);
    		vtnManoObra.setVisible(true);
    	}
    }
    
    private void abrirRefacciones(){
    	if(vtnRefacciones == null){
    		vtnRefacciones = new VerRefacciones(BaseDatos);
    	} else {
    		vtnRefacciones.setState(JFrame.NORMAL);
    		vtnRefacciones.setVisible(true);
    	}
    }
    
    private void abrirServicios(){
    	if(vtnServicios == null){
    		vtnServicios = new VerServicios(BaseDatos);
    	} else {
    		vtnServicios.setState(JFrame.NORMAL);
    		vtnServicios.setVisible(true);
    	}
    }

}
