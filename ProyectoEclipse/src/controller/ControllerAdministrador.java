package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeAll;

import model.Administrador;
import model.Carro;
//import java.util.HashMap;
//import controller.BaseDatos;
import model.Empleado;
import windows.VentanaAdministrador;
import windows.VentanaPrincipal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import windows.VentanaSeleccionSede;

public class ControllerAdministrador {
	
    private static final String TEST_FILE_PATH = "data/clientes.txt";
    private static final String BACKUP_FILE_PATH = "data/backc.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_PATH, BACKUP_FILE_PATH);
    }
    
    private static void copyFile(String sourcePath, String destinationPath) throws IOException {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // Copiar el archivo
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }


    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
	
	private Administrador administrador;
	private BaseDatos datos;
	

	public ControllerAdministrador() {
		this.administrador=null;
		this.datos=null;
	}
	
	public Administrador getAdministrador() {
		return this.administrador;
	}
	
	public void setDatos(BaseDatos datos) {
		this.datos=datos;
	}
	
	public void LogIn(String usuario,String contrasena) {
		
		//String usuario = VentanaPrincipal.getNombreUsuario().getText();
		//String contrasena =VentanaPrincipal.getContrasena().getText();

		Administrador administrador = datos.getMapaAdministradores().get(usuario);
		
		if(administrador != null && administrador.getUsuario().equals(usuario) && administrador.getContrasena().equals(contrasena)) {
			this.administrador=administrador;
			
		}
		else {
			System.out.println("Error al ingresar");
		}
	}
	
	public static boolean login(BaseDatos datos) {
		
        //ControllerAdministrador elAdministrador = new ControllerAdministrador();
		//administrador.setDatos(datos);
        
        String usuario = VentanaPrincipal.getNombreUsuario().getText();
        String contrasena = VentanaPrincipal.getContrasena().getText();
        
        Administrador administrador = datos.getMapaAdministradores().get(usuario);
        
		if(administrador.getUsuario().equals(usuario)&& administrador.getContrasena().equals(contrasena)) {
		    //administrador=administrador;
            System.out.println("Ingresado correctamente");
            VentanaSeleccionSede.mostrarVentana();
            return true;
			
		}
		else {
			System.out.println("Error al ingresar");
			VentanaPrincipal.getErrorLogIn().setText("Error Ingresando sesión!");
			return false;
		}
		
    }
	
		
		
	/*
	 * setters 
	 * 
	*/


	public static void agregarEmpleado(String id, String nombre, String login, String password, String email, String sede ) throws Exception {
		agregarEmpleado(id, nombre, login, password, email, sede);
	}
	
	public static void agregarVehiculo(String placa, String marca, String modelo,String color, String tipoTransmision) throws Exception {
		agregarVehiculo(placa, marca, modelo, color,tipoTransmision);
	}
	
	public void actualizarDatos() throws IOException {
		datos.cargarTodosLosDatos();
	}
	
	
	public static String crearLineaEmpleado(String id, String nombre, String usuario, String contrasena, String email, String sede) {

		return id + ";" + nombre + ";" + usuario + ";" + contrasena + ";" + email + ";"+ sede;
	}
	
	public String crearLineaVehiculo(Carro carro) {
		String placa = carro.getPlaca();
		String marca = carro.getmarca();
		String modelo = carro.getModelo();
		String color = carro.getColor();
		String tipoTransmision = carro.getTipoTransmision();

		String nombreCategoria = carro.getCategoria().getNombre();
		String nombreSede = carro.getSede().getNombre();
		String estado = carro.getEstado();
		String fechaDisp = String.valueOf(carro.getFechaDispCons());
		String str = placa + ";" + marca + ";" + modelo + ";" + color + ";" + tipoTransmision + ";" + ";"
				+ nombreCategoria + ";" + nombreSede + ";" + estado + ";" + fechaDisp;
		return str;
	}
	
	
    public void eliminarlineaEmpleados(String archivo, String contenidoAEliminar) {
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter("/data/tempEmpleados.txt"));

            String lineaActual;

            // Lee linea por linea y copia todas las lineas excepto la que deseas eliminar
            while ((lineaActual = reader.readLine()) != null) {
                if (!lineaActual.contains(contenidoAEliminar)) {
                    writer.write(lineaActual + System.getProperty("line.separator"));
                }
            }

            // Cierra los archivos
            reader.close();
            writer.close();

            // Borra el archivo original y renombra el archivo temporal
            if (new File(archivo).delete()) {
                new File("temp.txt").renameTo(new File(archivo));
                System.out.println("Fila eliminada exitosamente.");
            } else {
                System.out.println("No se pudo eliminar la fila.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void agregarLineaEmpleados(String archivo, String nuevaLinea) {
        try {
            // Abre el archivo en modo de adicion
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true));
            
            // Escribe la nueva linea en el archivo
            writer.write(nuevaLinea + System.getProperty("line.separator"));
            
            writer.close();
            
            System.out.println("Nueva l�nea agregada exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void eliminarLineaVehiculos(String archivo, String placa) {
        try {
            // Abre el archivo original y un archivo temporal para escritura
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/tempVehiculos.txt"));
            String lineaActual;

            // Lee l�nea por l�nea y copia todas las l�neas excepto las que contienen el fragmento
            while ((lineaActual = reader.readLine()) != null) {
                if (!lineaActual.contains(placa)) {
                    writer.write(lineaActual + System.getProperty("line.separator"));
                }
            }

            // Cierra los archivos
            reader.close();
            writer.close();

            // Borra el archivo original y renombra el archivo temporal
            if (new File(archivo).delete()) {
                new File("data/tempVehiculos.txt").renameTo(new File(archivo));
                System.out.println("Carro eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el Carro.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void eliminarLineaEmpleados(String archivo, String fragmento) {
        try {
            // Abre el archivo original y un archivo temporal para escritura
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/tempEmpleados.txt"));
            String lineaActual;

            // Lee l�nea por l�nea y copia todas las l�neas excepto las que contienen el fragmento
            while ((lineaActual = reader.readLine()) != null) {
                if (!lineaActual.contains(fragmento)) {
                    writer.write(lineaActual + System.getProperty("line.separator"));
                }
            }

            // Cierra los archivos
            reader.close();
            writer.close();

            // Borra el archivo original y renombra el archivo temporal
            if (new File(archivo).delete()) {
                new File("data/tempEmpleados.txt").renameTo(new File(archivo));
                System.out.println("Empleado eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el empleado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void agregar(String archivo, String lineaEliminar) {
     
    	agregarLineaEmpleados(archivo, lineaEliminar);
    }

    public void eliminar(String archivo, String lineaEliminar) {
        
        eliminarlineaEmpleados(archivo, lineaEliminar);
    }

}
