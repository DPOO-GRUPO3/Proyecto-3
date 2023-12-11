package pruebasCargaDatos;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.BaseDatos;

import java.io.InputStream;
import java.io.OutputStream;

import model.Carro;
import model.Reserva;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class carroTest {

    private static final String TEST_FILE_CARROS = "data/carros.txt";
    private static final String BACKUP_FILE_CARROS = "data/testCarrosBackup.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_CARROS, BACKUP_FILE_CARROS);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_CARROS, TEST_FILE_CARROS);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_CARROS).delete();
    }

    @Test
    public void testAgregarReserva() throws IOException {
        // Crear una instancia de Carro
    	BaseDatos datos =new BaseDatos();
    	datos.descargarTodoslosDatos();
    	datos.getMapaReservas().clear();
    	Reserva.setNumeroReservas(0);
    	
    	
        Carro carro = new Carro("MHIL23", "lamborgini", "7", "negro", "b", "auto", "10");
        Reserva reserva = new Reserva(null, null, null, null, null, null, null, "appCliente");

        // Prueba del método agregarReserva
        carro.agregarReserva(reserva);

        // Verificación
        assertEquals(1, carro.getReservas().size());
        assertTrue(carro.getReservas().contains(reserva));
    }

    @Test
    public void testSetFechaDisponibleCons() throws IOException {
    	BaseDatos datos =new BaseDatos();
    	datos.descargarTodoslosDatos();
    	datos.getMapaReservas().clear();
    	Reserva.setNumeroReservas(0);
        // Crear una instancia de Carro
        Carro carro = new Carro("MHIL23", "lamborgini", "7", "negro", "b", "auto", "10");
        LocalDateTime fechaDispCons = LocalDateTime.now();

        // Prueba del método setFechaDisponibleCons
        carro.setFechaDisponibleCons(fechaDispCons);

        // Verificación
        assertEquals(fechaDispCons, carro.getFechaDispCons());
    }

    @Test
    public void testSetEstado() throws IOException {
        // Crear una instancia de Carro
        Carro carro = new Carro("MHIL23", "lamborgini", "7", "negro", "b", "auto", "10");
        String estado = "Alquilado";

        // Prueba del método setEstado
        carro.setEstado(estado);

        // Verificación
        assertEquals(estado, carro.getEstado());
    }

    // Métodos de utilidad para crear, eliminar y verificar archivos
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
}