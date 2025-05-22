/*
 * Created on 26/09/2009
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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * Esta clase muestra la ventana con el logo del programa y en donde
 * se cargan todas las clases principales necesarias para la ejecucion
 * del programa
 * 
 */

class VentanaInicio extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
    private int frameNumber = -1;
    private int pasada = 1;
    private int repetidas = 0;
    private Timer timer;
    private JLabel imagen;
    private JLabel estado;
    private boolean frozen = false;
    private MotorBaseDatos BaseDatos = null;
    ImageIcon icono = null;
    
    public VentanaInicio(int fps) {
    	
        super("Iniciando sistema...");
        int delay = fps;

		icono = ClaseGlobal.crearIcono("iconos/ani1.jpg");
		
        imagen = new JLabel(icono, JLabel.CENTER);
        estado = new JLabel("Inicializando aplicación...", JLabel.CENTER);
		estado.setBackground(new Color(186,179,213));
		estado.setForeground(Color.black);
		
        //Configurar el Timer
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        getContentPane().add(imagen, BorderLayout.CENTER);
        getContentPane().add(estado, BorderLayout.SOUTH);
         
        Image icon = ClaseGlobal.crearImagen("iconos/icon.gif");
		setIconImage(icon);
		
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
        //Comenzar secuencia
		startAnimation();
        
    }
    //Iniciar secuencia
    public void startAnimation() {
        if (frozen) {
            //Si esta desactivado se ejecuta esta linea
        } else {
            //inicia secuencia
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    //Detiene la animacion
    public void stopAnimation() {
        if (timer.isRunning())
            timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        //Avanzada animacion del frame
        frameNumber+=pasada;

        if(frameNumber > 0 && frameNumber < 12){
	        icono = ClaseGlobal.crearIcono("iconos/ani"+frameNumber+".jpg");
	        imagen.setIcon(icono);
        } else {
        	if(frameNumber == 12){
        		pasada = -1;
        		repetidas++;
        	}
        	else if(frameNumber == 0){
        		pasada = 1;
        	}
        }
        
        if(repetidas == 2 && frameNumber == 5){
        	BaseDatos = new MotorBaseDatos();
        }
	    if(repetidas == 3){
        	stopAnimation();
        	dispose();
        	new VentanaPrincipal(BaseDatos);
        }
    }
}
