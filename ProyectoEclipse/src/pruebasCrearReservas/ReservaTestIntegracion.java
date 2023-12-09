package pruebasCrearReservas;


import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.*;
import controller.*;
import static org.junit.jupiter.api.Assertions.*;


public class ReservaTestIntegracion {
	// Arrange
		@Test

		public void testCrearReservaBien() throws IOException{
			//Descarga de datos y dejar reservas vacías (Carro lujoso también sin reservas)
			BaseDatos datos =new BaseDatos();
			datos.descargarTodoslosDatos();
			datos.setMapaReservas(new HashMap<String,Reserva>());
			datos.getMapaCarros().get("MHIL23").getReservas().clear();
			datos.getMapaCarros().get("MHIL23").setFechaDisponibleCons(null);
			datos.getMapaCarros().get("MHIL23").setUsoActual(null);
			
			//dejar solo 2 carros en el sistema para que la prueba funcione
			//bien. Asumimos que no se eliminan carros y aprovechamos la 
			//persistencia
			Carro carro1=datos.getMapaCarros().get("MHIL23");
			Carro carro2=datos.getMapaCarros().get("ABC123");
			datos.getMapaCarros().clear();
			datos.getMapaCarros().put("MHIL23", carro1);
			datos.getMapaCarros().put("ABC123", carro2);
			
			
			//Crear controllerCliente con algún usuario sin tarjeta bloqueada
			ControllerCliente elCliente= new ControllerCliente();
			elCliente.setDatos(datos);
			List<Cliente> userList = 
					new ArrayList<Cliente>(datos.getMapaClientes().values());
			
			Cliente sel=userList.get(1);
			sel.getTarjeta().desbloquear();
			elCliente.setCliente(sel);
			Reserva.setNumeroReservas(0);
			
			//Creamos reserva de carro lujoso (del que solo hay uno)
			
			double cobro=elCliente.crearReserva("lujoso", "sur", "2024-01-01T00:00",
					"sur", "2024-02-01T00:00");
			
			//Miramos si el cobro es correcto
			assertEquals(106950,cobro);
			
			//Tratamos de crear reserva y vemos que no se crea, el cobro es 0, la tarjeta
			// está bloqueada y el size del mapa reservas debe ser 1.
			double cobro1=elCliente.crearReserva("todoterreno", "sur", "2024-01-01T00:00",
					"sur", "2024-02-01T00:00");
			assertEquals(0,cobro1);
			assertEquals(true,elCliente.getCliente().getTarjeta().getBloqueo());
			assertEquals(1,datos.getMapaReservas().size());
			
			// prueba de que no dejará reservar si desbloqueamos la 
			//tarjeta otro carro lujoso en las mismas fechas 
			elCliente.getCliente().getTarjeta().desbloquear();
			double cobro2=elCliente.crearReserva("lujoso", "sur", "2024-01-01T00:00",
					"sur", "2024-02-01T00:00");
			
			assertEquals(0,cobro2);
			assertEquals(1,datos.getMapaReservas().size());
			
			// Sin embargo si dejará crear reserva de otro carro
			// como de familiares, que hay muchos
			double cobro3=elCliente.crearReserva("familiares", "sur", "2024-01-01T00:00",
					"sur", "2024-02-01T00:00");
			
			assertEquals(95790,cobro3);
			assertEquals(2,datos.getMapaReservas().size());
			
			// prueba de que no dejará reservar si reserva interseca intervalo por arriba
			elCliente.getCliente().getTarjeta().desbloquear();
			double cobro4=elCliente.crearReserva("lujoso", "sur", "2024-01-05T00:00",
					"sur", "2024-02-08T00:00");
			
			assertEquals(0,cobro4);
			assertEquals(2,datos.getMapaReservas().size());
			
			// ni por abajo
			double cobro5=elCliente.crearReserva("lujoso", "sur", "2023-12-25T00:00",
					"sur", "2024-01-08T00:00");
			
			assertEquals(0,cobro5);
			assertEquals(2,datos.getMapaReservas().size());
			
			// si existe alquiler en vez de reserva
			List<Sede> sedeList = 
					new ArrayList<Sede>(datos.getMapaSedes().values());
			Sede ced=sedeList.get(0);
			datos.getMapaReservas().clear();
			elCliente.getCliente().getTarjeta().desbloquear();
			datos.getMapaCarros().get("MHIL23").getReservas().clear();
			Alquiler alquiler=new Alquiler(elCliente.getCliente(),
					LocalDateTime.parse("2024-02-01T00:00"),LocalDateTime.parse("2024-01-01T00:00"),
					ced,ced,datos.getMapaCarros().get("MHIL23"));
			datos.getMapaAlquileres().put(String.valueOf(Alquiler.getNumAlqus()), alquiler);
			datos.getMapaCarros().get("MHIL23").setUsoActual(alquiler);
			
			double cobro6=elCliente.crearReserva("lujoso", "sur", "2024-01-05T00:00",
					"sur", "2024-02-08T00:00");
			
			assertEquals(0,cobro6);
			
			// Pero si reserva para después si servira
			double cobro7=elCliente.crearReserva("lujoso", "sur", "2024-02-19T00:00",
					"sur", "2024-02-22T00:00");
			assertEquals(10350,cobro7);
			
			// Si el carro tiene fecha disponible posterior
			datos.getMapaCarros().get("MHIL23").setUsoActual(null);
			elCliente.getCliente().getTarjeta().desbloquear();
			datos.getMapaCarros().get("MHIL23").setFechaDisponibleCons(LocalDateTime.parse("2024-02-08T00:00"));
			double cobro8=elCliente.crearReserva("lujoso", "sur", "2024-02-05T00:00",
					"sur", "2024-02-18T00:00");
			assertEquals(cobro8,0);
			//Si reservamos después si sirve
			double cobro9=elCliente.crearReserva("lujoso", "sur", "2024-02-10T00:00",
					"sur", "2024-02-18T00:00");
			assertEquals(cobro9,27600);
			
	}
}
