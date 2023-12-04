package guiCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.BaseDatos;
import view.Aplicacion;

public class MainWindow {
	private static JFrame frame;
	private static BaseDatos datos;
	
	public static void cargarVentanaPrincipal() {
		// TODO Auto-generated method stub
	    frame = new JFrame("RamenAutosClientes");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Config panel
	    JPanel panel =new JPanel();
	    frame.setSize(200, 200);
	    frame.add(panel);
	    panel.setLayout(null);
	    
	    // Botón LogIn
	    JButton inicio = new JButton("LogIn");
	    inicio.setBounds(10, 10, 120, 25);
	    
	    JButton sign = new JButton("SignIn");
	    sign.setBounds(10, 40, 120, 25);
	    
        // Agregar ActionListener a los botones
        inicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción a realizar cuando se presiona el botón 1
                System.out.println("Botón 1 presionado");
                
            }
        });

        sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción a realizar cuando se presiona el botón 2
                System.out.println("Botón 2 presionado");
                SignIn.signInVentana(datos);
            }
        });
        // Agregar los botones al JPanel
        panel.add(inicio);
        panel.add(sign);



        // Mostrar la ventana
        frame.setVisible(true);
}
	public static void main(String[] args) {
		datos=Aplicacion.cargarDatos();
		cargarVentanaPrincipal();
    }
	public static JFrame getFrame() {
		return frame;
	}
	public static void setFrame(JFrame frame) {
		MainWindow.frame = frame;
	}
	public static BaseDatos getDatos() {
		return datos;
	}
	public static void setDatos(BaseDatos datos) {
		MainWindow.datos = datos;
	}}

