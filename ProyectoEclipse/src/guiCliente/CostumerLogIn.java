package guiCliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ControllerCliente;
import windows.VentanaPrincipal;

public class CostumerLogIn {
private static ControllerCliente elCliente;
private static JFrame frame;
private static JTextField nombreUsuario;
private static JTextField contrasena;
private static JLabel errorLogIn;

public static void LogInVentana() {
	 frame = new JFrame("SignIn");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Config panel
	    JPanel panel =new JPanel();
	    frame.setSize(500, 500);
	    frame.add(panel);
	    panel.setLayout(null);
	    
	    // Usuario
	    JLabel labelUsuario = new JLabel("Usuario");
	    labelUsuario.setBounds(10,50,80,25);
	    nombreUsuario = new JTextField(20);
	    nombreUsuario.setBounds(60,50,100,25);
	    panel.add(labelUsuario);
	    panel.add(nombreUsuario);
		
	    // Contraseña
	    JLabel labelContra = new JLabel("Contrasena");
	    labelContra.setBounds(180,50,80,25);
	    contrasena = new JTextField(20);
	    contrasena.setBounds(250,50,100,25);
	    panel.add(labelContra);
	    panel.add(contrasena);
	    
	    // botón SignIn
	    JButton inicio = new JButton("LogIn");
	    inicio.setBounds(200, 340, 80, 25);
	    inicio.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Acción a realizar cuando se presiona el botón 1
	            System.out.println("Botón SignIn presionado");
	  
	        	elCliente =new ControllerCliente();
	        	elCliente.setDatos(MainWindow.getDatos());
	        	
	        	String usuario = nombreUsuario.getText();
	        	String contrasenaStr =contrasena.getText();
	        	
	        	elCliente.logIn(usuario, contrasenaStr);
	        	if(elCliente.getCliente()==null){
	        		System.out.println("Error ingresando sesión");
	        		errorLogIn.setText("Error Ingresando sesión!");
	        		errorLogIn.setForeground(Color.RED);
	        		frame.revalidate();
	        	}
	        	else {
	        		System.out.println("Ingresado correctamente");
	        		OpcionesDos.cargarVentanaOpciones();
	        	}
	        }


	    });
	    panel.add(inicio);
	    //Error Login
	    errorLogIn= new JLabel("");
	    errorLogIn.setBounds(10, 400, 500, 25);
	    panel.add(errorLogIn);
	    frame.setVisible(true);
}

public static ControllerCliente getElCliente() {
	return elCliente;
}

public static void setElCliente(ControllerCliente elCliente) {
	CostumerLogIn.elCliente = elCliente;
}

public static JFrame getFrame() {
	return frame;
}

public static void setFrame(JFrame frame) {
	CostumerLogIn.frame = frame;
}

public static JTextField getNombreUsuario() {
	return nombreUsuario;
}

public static void setNombreUsuario(JTextField nombreUsuario) {
	CostumerLogIn.nombreUsuario = nombreUsuario;
}

public static JTextField getContrasena() {
	return contrasena;
}

public static void setContrasena(JTextField contrasena) {
	CostumerLogIn.contrasena = contrasena;
}

public static JLabel getErrorLogIn() {
	return errorLogIn;
}

public static void setErrorLogIn(JLabel errorLogIn) {
	CostumerLogIn.errorLogIn = errorLogIn;
}
}
