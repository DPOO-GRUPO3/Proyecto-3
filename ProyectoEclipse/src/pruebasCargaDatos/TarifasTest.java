package pruebasCargaDatos;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;



import model.Categoria;
import model.Tarifa;


public class TarifasTest {

    private static final String TEST_FILE_TARIFAS = "data/tarifasExcedente.txt";
    private static final String BACKUP_FILE_TARIFAS = "data/tarifasExcedenteBackup.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_TARIFAS, BACKUP_FILE_TARIFAS);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_TARIFAS, TEST_FILE_TARIFAS);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_TARIFAS).delete();
    }

    @Test
    public void testGetCategoria() throws IOException {
        // Configuración de datos simulados
        Categoria categoria = new Categoria("Deportivo", 50.0);
        Tarifa tarifa = new Tarifa(20.0, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        tarifa.setCategoria(categoria);

        // Prueba del método getCategoria
        Categoria result = tarifa.getCategoria();

        // Verificación
        assertEquals(categoria, result);
    }

    @Test
    public void testGetId() throws IOException {
        // Configuración de datos simulados
        Tarifa tarifa = new Tarifa(20.0, LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        // Prueba del método getId
        String result = tarifa.getId();

        // Verificación
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }


    @Test
    public void testGetFechaInicio() throws IOException {
        // Configuración de datos simulados
        LocalDateTime fechaInicio = LocalDateTime.now();
        Tarifa tarifa = new Tarifa(20.0, fechaInicio, LocalDateTime.now().plusDays(30));

        // Prueba del método getFechaInicio
        LocalDateTime result = tarifa.getFechaInicio();

        // Verificación
        assertEquals(fechaInicio, result);
    }

    @Test
    public void testGetFechaFin() throws IOException {
        // Configuración de datos simulados
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(30);
        Tarifa tarifa = new Tarifa(20.0, LocalDateTime.now(), fechaFin);

        // Prueba del método getFechaFin
        LocalDateTime result = tarifa.getFechaFin();

        // Verificación
        assertEquals(fechaFin, result);
    }

    @Test
    public void testSetCategoria() throws IOException {
        // Configuración de datos simulados
        Categoria categoria = new Categoria("Deportivo", 50.0);
        Tarifa tarifa = new Tarifa(20.0, LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        // Prueba del método setCategoria
        tarifa.setCategoria(categoria);

        // Verificación
        assertEquals(categoria, tarifa.getCategoria());
    }

    // Métodos de utilidad para copiar y restaurar archivos
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