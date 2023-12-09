package guiCliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ControllerCliente;
import windows.BotonLogOut;
import windows.VentanaCliente;


public class Reservar {

	private static JComboBox<Integer> diaInicio;
	private static JComboBox<Integer> mesInicio;
	private static JComboBox<Integer> anioInicio;
	private static JComboBox<Integer> diaFin;
	private static JComboBox<Integer> mesFin;
	private static JComboBox<Integer> anioFin;
	private static JComboBox<Integer> horaFin;
	private static JComboBox<Integer> horaInicio;
	private static JComboBox<String> categoria;
	
	private static JFrame frame;


	private static JComboBox<String> sedeInicio;
	private static JComboBox<String> sedeFin;
	private static JLabel error;
	public static void cargarVentanaCliente() {
		// TODO Auto-generated method stub
	    frame = new JFrame("Crear Reserva");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Config panel
	    JPanel panel =new JPanel();
	    frame.setSize(500, 500);
	    frame.add(panel);
	    panel.setLayout(null);
	    
	    // Categoria
	    JLabel labelCategoria = new JLabel("Categoria");
	    labelCategoria.setBounds(10,10,80,25);
	    categoria =  new JComboBox<>();
	    categoria.setBounds(100,10,165,25);
	    categoria.addItem("todoterreno");
	    categoria.addItem("familiares");
	    categoria.addItem("lujoso");
	    panel.add(labelCategoria);
	    panel.add(categoria);
	    
		//FechaInicio
		JLabel FechaInicio= new JLabel ("FechaInicio:");
		FechaInicio.setBounds(10,50,80,25);
		
		diaInicio = new JComboBox<>();
		diaInicio.setBounds(100, 50, 50, 25);
	    IntStream.rangeClosed(1, 31).forEach(diaInicio::addItem);
	    
	    mesInicio = new JComboBox<>();
	    mesInicio.setBounds(160, 50, 50, 25);
	    IntStream.rangeClosed(1, 12).forEach(mesInicio::addItem);
		
	    anioInicio = new JComboBox<>();
	    anioInicio.setBounds(220, 50, 70, 25);
	    IntStream.rangeClosed(2023, 2033).forEach(anioInicio::addItem);
	    
	    horaInicio = new JComboBox<>();
	    horaInicio.setBounds(300, 50, 60, 25);
	    IntStream.rangeClosed(0, 23).forEach(horaInicio::addItem);
		
		panel.add(FechaInicio);
		panel.add(diaInicio);
		panel.add(mesInicio);
		panel.add(anioInicio);
		panel.add(horaInicio);
		//FechaFin
		JLabel FechaFin= new JLabel ("FechaFin:");
		FechaFin.setBounds(10,80,80,25);
				
		diaFin = new JComboBox<>();
		diaFin.setBounds(100, 80, 50, 25);
		IntStream.rangeClosed(1, 31).forEach(diaFin::addItem);
		        
		mesFin = new JComboBox<>();
		mesFin.setBounds(160, 80, 50, 25);
		 IntStream.rangeClosed(1, 12).forEach(mesFin::addItem);
				
		anioFin = new JComboBox<>();
		anioFin.setBounds(220, 80, 70, 25);
		IntStream.rangeClosed(2023, 2033).forEach(anioFin::addItem);
		
	    horaFin = new JComboBox<>();
	    horaFin.setBounds(300, 80, 60, 25);
	    IntStream.rangeClosed(0, 23).forEach(horaFin::addItem);
		
		FechaFin.setBounds(10,80,80,25);
		

	     panel.add(FechaFin);
		 panel.add(diaFin);
		 panel.add(mesFin);
		 panel.add(anioFin);
		 panel.add(horaFin);
		 // ETIQUETAS
			JLabel elDia= new JLabel ("Día");
			elDia.setBounds(100,40,80,10);
			
			JLabel elMes= new JLabel ("Mes");
			elMes.setBounds(160,40,80,10);
			
			JLabel elAnio = new JLabel ("Año");
			elAnio.setBounds(220,40,80,10);
			
			JLabel laHora = new JLabel ("Hora"); 
			laHora.setBounds(300,40,80,10);
			 panel.add(elDia);
			 panel.add(elMes);
			 panel.add(elAnio);
			 panel.add(laHora);
		
	    // Sede Inicio
	    JLabel labelSedeInicio = new JLabel("Sede Inicial");
	    labelSedeInicio.setBounds(10,110,80,25);
	    sedeInicio = new JComboBox<>();
	    sedeInicio.setBounds(100,110,165,25);
	    sedeInicio.addItem("norte");
	    sedeInicio.addItem("sur");
	    sedeInicio.addItem("centro");
	    panel.add(labelSedeInicio);
	    panel.add(sedeInicio);
	    
	    // SedeFin
	    JLabel labelSedeFin = new JLabel("Sede Final");
	    labelSedeFin.setBounds(10,130,80,25);
	    sedeFin = new JComboBox<>();
	    sedeFin.setBounds(100,130,165,25);
	    sedeFin.addItem("norte");
	    sedeFin.addItem("sur");
	    sedeFin.addItem("centro");
	    panel.add(labelSedeFin);
	    panel.add(sedeFin);
	    
	    // botón Crear Reservas
	    JButton reservar = new JButton("Reservar");
	    reservar.setBounds(300, 340, 100, 25);
	    reservar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Acción a realizar cuando se presiona el botón 1
	            System.out.println("Botón Reservar presionado");
	            crearResserva();
	            
	        

	        }


	        });
	    panel.add(reservar);
	   
	    
	    // Botón LogOut
	    JButton salir = new JButton("LogOut");
	    salir.setBounds(405, 340, 80, 25);
	    salir.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Acción a realizar cuando se presiona el botón 1
	            System.out.println("Botón LogOut presionado");
	            try {
					logOut();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
	        }


	    });
	    panel.add(salir);
	    
	    // error
	    error= new JLabel("");
	    error.setBounds(10, 240, 400, 25);
	    panel.add(error);
	    
	    
	    frame.setVisible(true);
	    
	}
	protected static void logOut() throws IOException {
		CostumerLogIn.getElCliente().actualizarDatos();
		MainWindow.cargarVentanaPrincipal();
		
	}
	protected static void crearResserva() {
		String nombreCat=(String) categoria.getSelectedItem();
		String sedeRec=(String) sedeInicio.getSelectedItem();
		String sedeFin2=(String) sedeFin.getSelectedItem();
		
		
		int dInicio = (int) diaInicio.getSelectedItem();
		int mInicio = (int) mesInicio.getSelectedItem();
		int aInicio = (int) anioInicio.getSelectedItem();
		int hInicio = (int) horaInicio.getSelectedItem();
		LocalDateTime fechaInicio = LocalDateTime.of(aInicio, mInicio, dInicio, hInicio,0,0);
		
		int dFin = (int) diaFin.getSelectedItem();
		int mFin = (int) mesFin.getSelectedItem();
		int aFin = (int) anioFin.getSelectedItem();
		int hFin = (int) horaFin.getSelectedItem();
		LocalDateTime fechaFin = LocalDateTime.of(aFin, mFin, dFin, hFin,0,0);
		String timeFin=fechaFin.toString();
		String timeRecoger= fechaInicio.toString();
		double cobro=CostumerLogIn.getElCliente().crearReserva(nombreCat, sedeRec, timeRecoger, sedeFin2, timeFin);
		cobro=CostumerLogIn.getElCliente().descuento(cobro);
		if(cobro!=0) {
		System.out.println("Su reserva está lista, se le cobró el 30% correspondiente a"
				+cobro );
		error.setText("Su reserva está lista, se le cobró el 30% correspondiente a"
				+cobro);
		error.setForeground(Color.GREEN);
		frame.revalidate();
		}
		
		else{
			System.out.println("No hay carros disponibles, intente cambiar la categoría o las fechas");
			error.setText("No hay carros disponibles, intente cambiar la categoría o las fechas");
			error.setForeground(Color.RED);
			frame.revalidate();
		}
		
	}}



