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

import controller.BaseDatos;
import model.Cliente;
import model.Licencia;
import model.Tarjeta;
import windows.VentanaPrincipal;

public class SignIn {
private static JFrame frame;
private static JTextField nombreUsuario;
private static JTextField contrasena;
//Datos personales
private static JTextField nombre;
private static JTextField email;
private static JTextField nacionalidad;
private static JTextField rutaImagen;


//Datos Licencia:
private static JTextField numero;
private static JTextField pais;
private static JTextField fechaVens;
private static JTextField rutaImagenLicencia;

//Datos Tarjeta:
private static JTextField numeroTarjeta;
private static JTextField codigo;

private static JLabel errorSignIn;
public static void signInVentana(BaseDatos datos) {

	
    frame = new JFrame("SignIn");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Config panel
    JPanel panel =new JPanel();
    frame.setSize(500, 500);
    frame.add(panel);
    panel.setLayout(null);
    
    //LOGIN INFO
    JLabel labelLogIn = new JLabel("LOGIN INFO");
    labelLogIn.setBounds(10,20,80,25);
    panel.add(labelLogIn);
    // Usuario
    JLabel labelUsuario = new JLabel("Usuario");
    labelUsuario.setBounds(10,50,80,25);
    nombreUsuario = new JTextField(20);
    nombreUsuario.setBounds(60,50,100,25);
    panel.add(labelUsuario);
    panel.add(nombreUsuario);
	
    // Contraseña
    JLabel labelContra = new JLabel("Contrasena");
    labelContra.setBounds(200,50,80,25);
    contrasena = new JTextField(20);
    contrasena.setBounds(270,50,100,25);
    panel.add(labelContra);
    panel.add(contrasena);
    
    //DATOS GENERALES
    JLabel labelDatos = new JLabel("DATOS PERSONALES");
    labelDatos.setBounds(10,80,150,25);
    panel.add(labelDatos);
	
    // Nombre
    JLabel labelNombre = new JLabel("Nombre");
    labelNombre.setBounds(10,110,50,25);
    nombre = new JTextField(20);
    nombre.setBounds(60,110,150,25);
    panel.add(labelNombre);
    panel.add(nombre);
    
    // Email
    JLabel labelMail = new JLabel("eMail");
    labelMail.setBounds(220,110,50,25);
    email = new JTextField(20);
    email.setBounds(270,110,150,25);
    panel.add(labelMail);
    panel.add(email);
    
    // Nacionalidad
    JLabel labelNacionalidad = new JLabel("Nacionalidad");
    labelNacionalidad.setBounds(10,140,100,25);
    nacionalidad = new JTextField(20);
    nacionalidad.setBounds(90,140,100,25);
    panel.add(labelNacionalidad);
    panel.add(nacionalidad);
    
    // RutaImagen
    JLabel labelRutaGen= new JLabel("Ruta de imagen de ID");
    labelRutaGen.setBounds(200,140,150,25);
    rutaImagen = new JTextField(20);
    rutaImagen.setBounds(350,140,100,25);
    panel.add(labelRutaGen);
    panel.add(rutaImagen);
    
    //DATOS LICENCIA
    JLabel labelLic = new JLabel("DATOS LICENCIA");
    labelLic.setBounds(10,170,150,25);
    panel.add(labelLic);
    
    // numero
    JLabel labelNumero= new JLabel("Número");
    labelNumero.setBounds(10,200,60,25);
    numero = new JTextField(20);
    numero.setBounds(70,200,100,25);
    panel.add(labelNumero);
    panel.add(numero);
    
    // pais
    JLabel labelPais= new JLabel("País");
    labelPais.setBounds(180,200,40,25);
    pais = new JTextField(20);
    pais.setBounds(220,200,80,25);
    panel.add(labelPais);
    panel.add(pais);
    
    // Fecha de vencimiento
    JLabel labelFechaVens= new JLabel("Fecha de vencimiento (MM/AA)");
    labelFechaVens.setBounds(10,230,200,25);
    fechaVens = new JTextField(20);
    fechaVens.setBounds(200,230,40,25);
    panel.add(labelFechaVens);
    panel.add(fechaVens);
    
    // Ruta Imagen licencia
    JLabel labelImLic= new JLabel("Ruta imagen de licencia");
    labelImLic.setBounds(250,230,140,25);
    rutaImagenLicencia = new JTextField(20);
    rutaImagenLicencia.setBounds(390,230,80,25);
    panel.add(labelImLic);
    panel.add(rutaImagenLicencia);
    
    //DATOS TARJETA
    JLabel labelTarj = new JLabel("DATOS TARJETA");
    labelTarj.setBounds(10,260,150,25);
    panel.add(labelTarj);
    
    // Num Tarjeta
    JLabel labelNumTar= new JLabel("Número");
    labelNumTar.setBounds(10,290,50,25);
    numeroTarjeta = new JTextField(20);
    numeroTarjeta.setBounds(60,290,80,25);
    panel.add(labelNumTar);
    panel.add(numeroTarjeta);
    
    // codigo
    JLabel labelCodigo= new JLabel("Código");
    labelCodigo.setBounds(150,290,50,25);
    codigo = new JTextField(20);
    codigo.setBounds(200,290,80,25);
    panel.add(labelCodigo);
    panel.add(codigo);
    
    // botón SignIn
    JButton inicio = new JButton("SignIn");
    inicio.setBounds(320, 340, 80, 25);
    inicio.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acción a realizar cuando se presiona el botón 1
            System.out.println("Botón SignIn presionado");
            try {
				crearYvalidarUsuario(datos);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        }


    });
    panel.add(inicio);
    // botón volver
    JButton volver = new JButton("Volver");
    volver.setBounds(405, 340, 80, 25);
    volver.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acción a realizar cuando se presiona el botón 1
            System.out.println("Botón Volver presionado");
            MainWindow.cargarVentanaPrincipal();
            
        }


    });
    panel.add(volver);
    // errorLogIn
    errorSignIn= new JLabel("");
    errorSignIn.setBounds(10, 400, 500, 25);
    panel.add(errorSignIn);
    frame.setVisible(true);
    
}

private static void crearYvalidarUsuario(BaseDatos datos) throws IOException {
	// TODO Auto-generated method stub
	String user=nombreUsuario.getText();
	System.out.println(user);
	String password=contrasena.getText();
	
	//Prueba para ver si ya existe ese usuario
	Cliente clienteExistente=datos.getMapaClientes().get(user);
	if (clienteExistente!=null) {
		errorSignIn.setText("Usuario en usu, pruebe con otro");
		errorSignIn.setForeground(Color.RED);
		frame.revalidate();
	}
	// Prueba de que totod los campos estén llenos
	else if(nombreUsuario.getText().isEmpty()||contrasena.getText().isEmpty()
			||nombre.getText().isEmpty()||email.getText().isEmpty()||
			nacionalidad.getText().isEmpty()||rutaImagen.getText().isEmpty()
			||numero.getText().isEmpty()||pais.getText().isEmpty()
			||fechaVens.getText().isEmpty()||rutaImagenLicencia.getText().isEmpty()
			||numeroTarjeta.getText().isEmpty()||codigo.getText().isEmpty()) 
	{
		errorSignIn.setText("Debe llenar todos los campos");
		errorSignIn.setForeground(Color.RED);
		frame.revalidate();
	}
	else {

		crearClienteyActualizar(crearTarjetayActualizar(),crearLicenciayActualizar());
		MainWindow.getDatos().cargarTodosLosDatos();
		
		errorSignIn.setText("SignIn realizado correctamente, presione volver para hacer LogIn");
		errorSignIn.setForeground(Color.GREEN);
		
	}
}

private static void crearClienteyActualizar(Tarjeta tar,Licencia lic) throws IOException {
	// TODO Auto-generated method stub
	String usuarioC=nombreUsuario.getText();
	String contr=contrasena.getText();
	String nombreC=nombre.getText();
	String emailC=email.getText();
	String nacC=nacionalidad.getText();
	String rutaC=rutaImagen.getText();
	
	Cliente usuarioNuevo=new Cliente(usuarioC,contr,nombreC,emailC,nacC,rutaC);
	
	usuarioNuevo.setLicencia(lic);
	usuarioNuevo.setTarjeta(tar);
	
	MainWindow.getDatos().getMapaClientes().put(usuarioC, usuarioNuevo);
	
}

private static Licencia crearLicenciayActualizar() {
	// TODO Auto-generated method stub
	String numeroL=numero.getText();
	String paisL=pais.getText();
	String fechaVensL=fechaVens.getText();
	String rutaL=rutaImagenLicencia.getText();
	
	Licencia nuevaLic =new Licencia(numeroL,paisL,fechaVensL,rutaL);
	
	MainWindow.getDatos().getMapaLicencias().put(numeroL, nuevaLic);
	return nuevaLic;
}

private static Tarjeta crearTarjetayActualizar() {
	// TODO Auto-generated method stub
	String numero=numeroTarjeta.getText();
	String codigoT=codigo.getText();
	
	Tarjeta nuevaTar=new Tarjeta(numero,codigoT);
	
	MainWindow.getDatos().getMapaTarjetas().put(numero, nuevaTar);
	
	return nuevaTar;
	
	
	
}
	
}

