package windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import model.Pago;
import view.Aplicacion;

public class VentanaPago extends JFrame implements ActionListener{
	
	private JComboBox<String> comboBox;
	private JTextField numero2;
	private JTextField codigo2;
	private ArrayList<String> datos;
	
	
	public VentanaPago(ArrayList<String> datos)
	{
		this.datos = datos;
		
		setTitle("Facturacion");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridBagLayout()); // GridLayout con 2 columnas
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//Opcionaes de pago
		
		JLabel pago = new JLabel("Metodo de Pago");
		
		comboBox = new JComboBox<>();

        // Llamar a un método para cargar las opciones desde el archivo al JComboBox
        cargarOpcionesDesdeArchivo(comboBox, "data/configuracion.txt");
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(pago);
        gbc.gridx = 1;
        panel.add(comboBox);
		
		//numero
		
		JLabel numero1= new JLabel ("Número: ");
		numero2 = new JTextField();
		
		gbc.gridx = 0;
        gbc.gridy = 1;
		panel.add(numero1,gbc);
		gbc.gridx = 1;
		panel.add(numero2, gbc);
		
		//numero
		
		JLabel codigo1= new JLabel ("Número: ");
		codigo2 = new JTextField();
				
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(codigo1,gbc);
		gbc.gridx = 1;
		panel.add(codigo2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		JButton pago1 = new JButton("Pagar");
		pago1.setActionCommand("PAGAR");
		pago1.addActionListener(this);
		
		panel.add(pago1);
		
		add(panel);
		setVisible(true);
	}
	
	private static void cargarOpcionesDesdeArchivo(JComboBox<String> comboBox, String nombreArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            List<String> opciones = new ArrayList<>();
            opciones.add("");
            // Leer cada línea del archivo y agregarla a la lista
            while ((linea = lector.readLine()) != null) {
                opciones.add(linea);
            }

            // Agregar las opciones al modelo del JComboBox
            for (String opcion : opciones) {
                comboBox.addItem(opcion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals("PAGAR")){
			
			String metodo = String.valueOf(comboBox.getSelectedItem());
			String numero = numero2.getText();
			String codigo = codigo2.getText();
			
			Class<?> clase = null;
			
			try {
				clase = Class.forName("model." + metodo);
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				Pago pago = (Pago) clase.getDeclaredConstructor(String.class, String.class).newInstance(numero,codigo);
				pago.bloquear();
				pago.CargarDatos();
				JOptionPane.showMessageDialog(null, "Su pago ha sido aprobado");
				
				//Crear Factura PDF
				
				 // Crear un nuevo documento PDF
	            PDDocument document = new PDDocument();

	            // Añadir una nueva página al documento
	            PDPage page = new PDPage();
	            document.addPage(page);
	            
	            PDPageContentStream contentStream = new PDPageContentStream(document, page);
	            //PDType0Font font = PDType0Font.load(document, VentanaPago.class.getResourceAsStream("letra.ttf"));
                //contentStream.setFont(font, 12);
	            
	            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                
	            contentStream.beginText();
	            contentStream.newLineAtOffset(50, 700); // Posición del texto en la página
	            contentStream.showText("Hola, este es un documento PDF creado con Apache PDFBox.");
	            contentStream.endText();
	            contentStream.close();

	            // Guardar el documento en un archivo
	            document.save("data/mi_documento.pdf");

	            // Cerrar el documento
	            document.close();
				
				
				new VentanaEmpleado();
				dispose();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
			}
		
	}
	
	public static void main(String[] args) {
	
		ArrayList<String> prueba = new ArrayList<String>();
		new VentanaPago(prueba);
    }
	


}
