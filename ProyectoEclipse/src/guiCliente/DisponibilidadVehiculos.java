package guiCliente;
//
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisponibilidadVehiculos {

	private static JFrame frame;
	private static JComboBox<Integer> dia;
	private static JComboBox<Integer> mes;
	private static JComboBox<Integer> anio;
	private static JComboBox<String> sede;
	private static JComboBox<String> lista;

	public static void cargarVentanaDisponibilidad() {
		// TODO Auto-generated method stub
		frame = new JFrame("Disponibilidad");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Config panel
		JPanel panel = new JPanel();
		frame.setSize(500, 500);
		frame.add(panel);
		panel.setLayout(null);

		// fecha
		JLabel labelFecha = new JLabel("Fecha");
		labelFecha.setBounds(10, 40, 50, 25);
		panel.add(labelFecha);

		dia = new JComboBox<>();
		dia.setBounds(100, 40, 50, 25);
		IntStream.rangeClosed(1, 31).forEach(dia::addItem);

		mes = new JComboBox<>();
		mes.setBounds(160, 40, 50, 25);
		IntStream.rangeClosed(1, 12).forEach(mes::addItem);

		anio = new JComboBox<>();
		anio.setBounds(220, 40, 70, 25);
		IntStream.rangeClosed(2023, 2033).forEach(anio::addItem);

		panel.add(dia);
		panel.add(mes);
		panel.add(anio);

		// ETIQUETAS
		JLabel elDia = new JLabel("Día");
		elDia.setBounds(100, 20, 80, 10);

		JLabel elMes = new JLabel("Mes");
		elMes.setBounds(160, 20, 80, 10);

		JLabel elAnio = new JLabel("Año");
		elAnio.setBounds(220, 42, 80, 10);

		panel.add(elDia);
		panel.add(elMes);
		panel.add(elAnio);

		// Sede
		JLabel labelSede = new JLabel("Sede");
		labelSede.setBounds(10, 70, 50, 25);
		sede = new JComboBox<>();
		sede.setBounds(60, 70, 165, 25);
		sede.addItem("norte");
		sede.addItem("sur");
		sede.addItem("centro");
		panel.add(labelSede);
		panel.add(sede);

		// Boton consultar
		JButton reservar = new JButton("Consultar");
		reservar.setBounds(300, 240, 100, 25);
		reservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Acción a realizar cuando se presiona el botón 1
				System.out.println("Botón consultar presionado");
				consultar();

			}

		});
		panel.add(reservar);
		
		// Boton volver
	    JButton salir = new JButton("Volver");
	    salir.setBounds(405, 240, 80, 25);
	    salir.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Acción a realizar cuando se presiona el botón 1
	        	OpcionesDos.cargarVentanaOpciones();
	            
	        }


	    });
	    panel.add(salir);
	    
	    lista=new JComboBox<>();
	    lista.setBounds(10, 340, 490, 25);
	    panel.add(lista);
	    frame.setVisible(true);
	}

	protected static void consultar() {
		// TODO Auto-generated method stub
		
		String sedeNombre=(String) sede.getSelectedItem();
		int diaNum = (int) dia.getSelectedItem();
		int mesNum = (int) mes.getSelectedItem();
		int anioNum = (int) anio.getSelectedItem();
		LocalDateTime fecha = LocalDateTime.of(anioNum, mesNum, diaNum,0,0,0);
		
		ArrayList<String> listaMostrar= 
				CostumerLogIn.getElCliente().vehículosDisponiblesEnSedeEnFechaDada(fecha, 
						sedeNombre);
		
		for(String linea:listaMostrar) {
			lista.addItem(linea);
		}
		
		frame.revalidate();
		
	}
}
