package pruebasCargaDatos;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import model.Categoria;
import model.Tarifa;
import model.Temporada;

public class CategoriaTest {

    private static final String TEST_FILE_CATEGORIAS = "data/categorias.txt";
    private static final String BACKUP_FILE_CATEGORIAS = "data/testCategoriasBackup.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_CATEGORIAS, BACKUP_FILE_CATEGORIAS);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_CATEGORIAS, TEST_FILE_CATEGORIAS);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_CATEGORIAS).delete();
    }

    @Test
    public void testSetTarifa() throws IOException {
        // Configuración de datos simulados
        Categoria categoria = new Categoria("Deportivo", 50.0);
        Temporada temporada = new Temporada(LocalDateTime.now(), LocalDateTime.now().plusDays(30), 30.0);

        // Prueba del método setTarifa
        categoria.setTarifa(temporada);

        // Verificación
        assertTrue(categoria.getTarifa().contains(temporada));
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