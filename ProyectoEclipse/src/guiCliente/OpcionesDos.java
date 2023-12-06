package guiCliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OpcionesDos {
	private static JFrame frame;
	
	public static void cargarVentanaOpciones() {
		// TODO Auto-generated method stub
	    frame = new JFrame("Cliente");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Config panel
	    JPanel panel =new JPanel();
	    frame.setSize(500, 500);
	    frame.add(panel);
	    panel.setLayout(null);
	    
	    //Letrero bienvendido
	    JLabel labelBienvenido = new JLabel("Bienvenido "+CostumerLogIn.getNombreUsuario().getText());
	    labelBienvenido.setBounds(10,20,280,25);
	    labelBienvenido.setForeground(Color.BLUE);
	    panel.add(labelBienvenido);
	    
	    // Botón Ver disponibilidad
	    JButton dispo = new JButton("Ver Disponibilidad");
	    dispo.setBounds(10, 50, 160, 25);
	    
	    JButton res = new JButton("Crear Reserva");
	    res.setBounds(10, 80, 160, 25);
	    
        // Agregar ActionListener a los botones
        dispo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción a realizar cuando se presiona el botón 1
                System.out.println("Botón Dispon presionado");
                DisponibilidadVehiculos.cargarVentanaDisponibilidad();
                
            }
        });

        res.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción a realizar cuando se presiona el botón 2
                System.out.println("Botón Reservar presionado");
                Reservar.cargarVentanaCliente();
            }
        });
        // Agregar los botones al JPanel
        panel.add(dispo);
        panel.add(res);



        // Mostrar la ventana
        frame.setVisible(true);
}
}
