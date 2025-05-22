
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * En esta clase se muestra la ventana con la factura y todos los
 * valores que se van a imprimir para poder entregar comprobante a clientes y
 * tecnicos
 */

public class ImprimeFormato extends JPanel implements Printable {

	private static final long serialVersionUID = 1L;
	
	final static Color bg = Color.lightGray;
    final static JButton button = new JButton("Imprimir factura");
	
    boolean impreso = false;
	JFrame frm;

	private String ordenId;
	private String clienteId;
	private String empresa;
	private String reporta;
	private String departamento;
	private String direccion;
	private String fechaIngreso;
	private String fechaEntrega;
	private String colonia;
	private String telefono;
	private String tipoEquipo;
	private String marca;
	private String modelo;
	private String observaciones1;
	private String observaciones2;
	private String observaciones3;
	private String observaciones4;
	private String falla1;
	private String falla2;
	private String falla3;
	private String falla4;
	private String tipoServicio;
	private String fechaEstimada;
	private String tecnicoAsignado;
	private String horaEntrada;
	private String horaSalida;
	
	private int[] cant = new int[9];
	private String[] descripcion = new String[9];
	private float[] precio = new float[9];
	private float[] total = new float[10];

	
    public ImprimeFormato(JFrame frm , MotorBaseDatos BaseDatos, String id) {
    	super(null);
    	
		setBackground(bg);
		
        button.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
    			imprimir();
        	}
        });
        
        setPreferredSize(new Dimension(610,797));
        this.frm = frm;
        
        char s = 10;
		String REGEX = String.valueOf(s);
		
        String temp = null;
        String[] temp2 = null;
        String[] service = BaseDatos.listaValoresCelda("Servicios", 36, "IdOrden="+id);
        
        ordenId = id;
        
        if(service[1] != null) clienteId = service[1];
        if(service[1] != null) empresa = BaseDatos.obtenerValorCelda("Clientes", "NombreCliente", "IdCliente="+service[1]);
        if(service[27] != null) reporta = BaseDatos.obtenerValorCelda("Empleados", "Nombre", "IdEmpleado="+service[27]);
        if(service[35] != null) departamento = service[35];
        if(service[1] != null) direccion = BaseDatos.obtenerValorCelda("Clientes", "Direccion", "IdCliente="+service[1]);
        if(service[2] != null) fechaIngreso = service[2].substring(0,10);
        if(service[5] != null) fechaEntrega = service[5].substring(0,10);
        if(service[1] != null) colonia = BaseDatos.obtenerValorCelda("Clientes", "Colonia", "IdCliente="+service[1]);
        if(service[1] != null) telefono = BaseDatos.obtenerValorCelda("Clientes", "Telefono", "IdCliente="+service[1]);
    	
        if(service[21] != null){
    		if(service[21].length() > 0){
    			tipoEquipo = BaseDatos.obtenerValorCelda("Equipos", "TipoEquipo", "IdEquipo='"+service[21]+"'");
            	temp = BaseDatos.obtenerValorCelda("Equipos", "Marca", "IdEquipo='"+service[21]+"'");
            	marca = BaseDatos.obtenerValorCelda("Marcas", "Marcas", "IdMarca="+temp);
            	modelo = BaseDatos.obtenerValorCelda("Equipos", "Modelo", "IdEquipo='"+service[21]+"'");
    		}
    	} else {
    		tipoEquipo = "Ningún equipo agregado";
        	marca = " ";
        	modelo = " ";
    	}

        if(service[24] != null)  temp2 = service[24].split(REGEX);

    	if(temp2 != null){
    		observaciones1 = temp2[0];
    		
    		if(temp2.length >= 2)
        		observaciones2 = temp2[1];
        	if(temp2.length >= 3)
        		observaciones3 = temp2[2];
        	if(temp2.length >= 4)
        		observaciones4 = temp2[3];
    	}

    	if(service[22] != null) temp2 = service[22].split(REGEX);
    	
    	if(temp2 != null){
    		falla1 = temp2[0];
    	
	    	if(temp2.length >= 2)
	    		falla2 = temp2[1];
	    	if(temp2.length >= 3)
	    		falla3 = temp2[2];
	    	if(temp2.length >= 4)
	    		falla4 = temp2[3];
    	}
    	
    	if(service[9] != null) tipoServicio = service[9];
    	if(service[3] != null && !service[3].substring(0,10).matches("9999-01-01")) fechaEstimada = service[3].substring(0,10);
    	if(service[26] != null)  tecnicoAsignado = BaseDatos.obtenerValorCelda("Empleados", "Nombre", "IdEmpleado="+service[26]);
    	if(service[6] != null) horaEntrada = service[6].substring(11,19);
    	if(service[8] != null) horaSalida = service[8].substring(11,19);
    	
    	Object[][] dataManoObra = BaseDatos.listaTabla("detallesManoObra",3, new String[]{"IdDetalle","IdManoObra","IdCantidad"},"idServicio="+id);
    	Object[][] dataRefacciones = BaseDatos.listaTabla("detallesRefacciones",3, new String[]{"IdDetalle","IdRefaccion","IdCantidad"},"idServicio="+id);
    	
    	String[] temp3;
    	int pasadorManoObra = 0;
    	int pasadorRefacciones = 0;
    	int posicion = 0;
    	
    	for(int z = 0; z < 10; z++){
    		
    		if(pasadorManoObra < dataManoObra.length){
    	    	cant[posicion] = Integer.valueOf((String)dataManoObra[pasadorManoObra][2]);
    	    	temp3 = BaseDatos.listaValoresCelda("ManoObra", 3, "IdManoObra="+dataManoObra[pasadorManoObra][1]);
    	    	descripcion[posicion] = temp3[1];
    	    	precio[posicion] = Float.valueOf(temp3[2]);
    	    	total[posicion] = cant[posicion] * precio[posicion];
    	    	total[9] += total[posicion];
    			pasadorManoObra++;
    			posicion++;
    		}
    		
    		if(pasadorRefacciones < dataRefacciones.length){
    	    	cant[posicion] = Integer.valueOf((String)dataRefacciones[pasadorRefacciones][2]);
    	    	temp3 = BaseDatos.listaValoresCelda("Refacciones", 3, "IdRefaccion="+dataRefacciones[pasadorRefacciones][1]);
    	    	descripcion[posicion] = temp3[1];
    	    	precio[posicion] = Float.valueOf(temp3[2]);
    	    	total[posicion] = cant[posicion] * precio[posicion];
    	    	total[9] += total[posicion];
    			pasadorRefacciones++;
    			posicion++;
    		}
    	}
    }
    
    // manda a imprimir
    private void imprimir(){
    	PrinterJob printJob = PrinterJob.getPrinterJob();
    	
		printJob.setPrintable(this);
		if (printJob.printDialog()) {
            try {
                printJob.print();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //dibuja la factura
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		drawShapes(g2);
	}
	
    //dibuja todo el texto de la factura
	public void drawShapes(Graphics2D g2){

		Font h1 = new Font("Arial", Font.PLAIN, 8);
		Font h2 = new Font("Arial", Font.BOLD, 12);
		Font h3 = new Font("Arial", Font.BOLD, 8);

		//609x792
		Image ds = ClaseGlobal.crearImagen("iconos/orden.gif");

		g2.drawImage(ds,2,2,ImprimeFormato.this);

		short spacing_top = -16;
		short x = 40;
        int y = 114 + spacing_top;
        
        g2.setFont(h2);
        g2.setColor(Color.black);
        
        g2.drawString(ordenId, 490, 77 + spacing_top);
        
        g2.setFont(h1);
        
		if(clienteId != null)
			g2.drawString(clienteId, x+60, y+12);
		if(empresa != null)
			g2.drawString(empresa, x+60, y+26);
		if(reporta != null)
			g2.drawString(reporta, x+60, y+40);
		if(direccion != null)
			g2.drawString(direccion, x+60, y+54);
		if(departamento != null)
			g2.drawString(departamento, x+60, y+68);
		
		x = 446;
		
		if(fechaIngreso != null)
			g2.drawString(fechaIngreso, x, y+14);
		if(fechaEntrega != null)
			g2.drawString(fechaEntrega, x, y+34);
		if(colonia != null)
			g2.drawString(colonia, x, y+53);
		if(telefono != null)
			g2.drawString(telefono, x, y+68);
		
		x = 46;
		y = 215 + spacing_top;
		
		if(tipoEquipo != null)
			g2.drawString(tipoEquipo, x, y+12);
		if(marca != null)
			g2.drawString(marca, x, y+26);
		if(modelo != null)
			g2.drawString(modelo, x, y+40);
		
		x = 306;
		y = 213 + spacing_top;
		
		g2.setFont(h3);
		
		if(observaciones1 != null)
			g2.drawString(observaciones1, x, y+12);
		if(observaciones2 != null)
			g2.drawString(observaciones2, x, y+26);
		if(observaciones3 != null)
			g2.drawString(observaciones3, x, y+40);
		if(observaciones4 != null)
			g2.drawString(observaciones4, x, y+54);
		
		x = 46;
		y = 315 + spacing_top;
		
		if(falla1 != null)
			g2.drawString(falla1, x, y+12);
		if(falla2 != null)
			g2.drawString(falla2, x, y+26);
		if(falla3 != null)
			g2.drawString(falla3, x, y+40);
		if(falla4 != null)
			g2.drawString(falla4, x, y+54);
		
		x = 385;
		y = 308 + spacing_top;
		
		g2.setFont(h1);
		
		if(tipoServicio != null)
			g2.drawString(tipoServicio, x+3, y+14);
		if(fechaEstimada != null)
			g2.drawString(fechaEstimada, x+55, y+32);
		if(tecnicoAsignado != null)
			g2.drawString(tecnicoAsignado, x+58, y+46);
		if(horaEntrada != null)
			g2.drawString(horaEntrada, x+46, y+60);
		if(horaSalida != null)
			g2.drawString(horaSalida, x+37, y+76);
		
		x = 43;
		y = 419 + spacing_top;
		
		if(cant[0] != 0)
			g2.drawString(String.valueOf(cant[0]), x, y+14);
		if(cant[1] != 0)
			g2.drawString(String.valueOf(cant[1]), x, y+30);
		if(cant[2] != 0)
			g2.drawString(String.valueOf(cant[2]), x, y+46);
		if(cant[3] != 0)
			g2.drawString(String.valueOf(cant[3]), x, y+60);
		if(cant[4] != 0)
			g2.drawString(String.valueOf(cant[4]), x, y+74);
		if(cant[5] != 0)
			g2.drawString(String.valueOf(cant[5]), x, y+88);
		if(cant[6] != 0)
			g2.drawString(String.valueOf(cant[6]), x, y+102);
		if(cant[7] != 0)
			g2.drawString(String.valueOf(cant[7]), x, y+116);
		if(cant[8] != 0)
			g2.drawString(String.valueOf(cant[8]), x, y+132);
		//g2.drawString("cant 10", x, y+145);
		
		x = 96;
		
		g2.setFont(h3);
		
		if(descripcion[0] != null)
			g2.drawString(descripcion[0], x, y+14);
		if(descripcion[1] != null)
			g2.drawString(descripcion[1], x, y+30);
		if(descripcion[2] != null)
			g2.drawString(descripcion[2], x, y+46);
		if(descripcion[3] != null)
			g2.drawString(descripcion[3], x, y+60);
		if(descripcion[4] != null)
			g2.drawString(descripcion[4], x, y+74);
		if(descripcion[5] != null)
			g2.drawString(descripcion[5], x, y+88);
		if(descripcion[6] != null)
			g2.drawString(descripcion[6], x, y+102);
		if(descripcion[7] != null)
			g2.drawString(descripcion[7], x, y+116);
		if(descripcion[8] != null)
			g2.drawString(descripcion[8], x, y+132);
		//g2.drawString("descripcion 10", x, y+145);
		
		x = 394;
		g2.setFont(h1);
		
		if(precio[0] != 0)
			g2.drawString(String.valueOf(precio[0]), x, y+14);
		if(precio[1] != 0)
			g2.drawString(String.valueOf(precio[1]), x, y+30);
		if(precio[2] != 0)
			g2.drawString(String.valueOf(precio[2]), x, y+46);
		if(precio[3] != 0)
			g2.drawString(String.valueOf(precio[3]), x, y+60);
		if(precio[4] != 0)
			g2.drawString(String.valueOf(precio[4]), x, y+74);
		if(precio[5] != 0)
			g2.drawString(String.valueOf(precio[5]), x, y+88);
		if(precio[6] != 0)
			g2.drawString(String.valueOf(precio[6]), x, y+102);
		if(precio[7] != 0)
			g2.drawString(String.valueOf(precio[7]), x, y+116);
		if(precio[8] != 0)
			g2.drawString(String.valueOf(precio[8]), x, y+132);
		//g2.drawString("precio 10", x, y+145);
		
		x= 488;
		
		if(total[0] != 0)
			g2.drawString(String.valueOf(total[0]), x, y+14);
		if(total[1] != 0)
			g2.drawString(String.valueOf(total[1]), x, y+30);
		if(total[2] != 0)
			g2.drawString(String.valueOf(total[2]), x, y+46);
		if(total[3] != 0)
			g2.drawString(String.valueOf(total[3]), x, y+60);
		if(total[4] != 0)
			g2.drawString(String.valueOf(total[4]), x, y+74);
		if(total[5] != 0)
			g2.drawString(String.valueOf(total[5]), x, y+88);
		if(total[6] != 0)
			g2.drawString(String.valueOf(total[6]), x, y+102);
		if(total[7] != 0)
			g2.drawString(String.valueOf(total[7]), x, y+116);
		if(total[8] != 0)
			g2.drawString(String.valueOf(total[8]), x, y+132);
		if(total[9] != 0)
			g2.drawString(String.valueOf(total[9]), x, y+148);
		
		if(ordenId != null)
			g2.drawString(ordenId, 474, 647 + spacing_top);
		if(clienteId != null)
			g2.drawString(clienteId, 524, 733 + spacing_top);
		if(ordenId != null)
			g2.drawString(ordenId, 524, 759 + spacing_top);
		
    }
    // se configura la pagina de impresion
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
        if (pi >= 1) {
            return Printable.NO_SUCH_PAGE;
		}
		drawShapes((Graphics2D) g);
        return Printable.PAGE_EXISTS;
    }
 
    // crea la ventana de vista preliminar
 	public void generar(){

		JDialog f = new JDialog(frm, "Vista preliminar", false);
		
		JPanel panel = new JPanel();
		JPanel factura = ImprimeFormato.this;
		
		JScrollPane scroll = new JScrollPane(factura);
		panel.add(button);
		
		Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		f.setIconImage(icon);
		
		f.getContentPane().add(BorderLayout.SOUTH, panel);
		f.getContentPane().add(BorderLayout.CENTER, scroll);
		f.setTitle("Vista preliminar de la factura");
		f.setSize(640, 500);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
 	}
}
