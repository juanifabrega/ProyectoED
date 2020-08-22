package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Controlador.*;
import Positions.Position;
import TDALista.PositionList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

/**
 * InterfazGrafica
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Interfaz grafica de simulación de un arbol de archivos, hecha con WindowsBuilder
 * 
 */

public class InterfazGrafica extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_7;
	private Logica log;
	private JTextField textField_6;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafica frame = new InterfazGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazGrafica() {
		setTitle("ED-Drive");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		log= new Logica();
		
		TextArea textArea = new TextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBounds(51, 310, 380, 160);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(105, 57, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(348, 57, 163, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAgregararchivo = new JButton("Agregar Archivo");
		btnAgregararchivo.setEnabled(false);
		btnAgregararchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String vacio="";
				if ( (textField.getText().equals(vacio)) || (textField_1.getText().equals(vacio)) ){
					JOptionPane.showMessageDialog(null, "Faltan completar campos");
				}
				else{
					
					Position<Pair<String,PositionList<String>>> pos= log.esRutaValida(textField_1.getText());
					if (pos==null){
						JOptionPane.showMessageDialog(null, "La ruta no es valida");
					}
					else{
						
						log.agregarArchivo(textField.getText(), pos);
						textField.setText("");
						textField_1.setText("");
						JOptionPane.showMessageDialog(null, "El archivo se agrego corrctamente");
					}
				}
				
			}
		});
		btnAgregararchivo.setBounds(547, 56, 152, 23);
		contentPane.add(btnAgregararchivo);
		
		textField_2 = new JTextField();
		textField_2.setBounds(105, 95, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnEliminararchivo = new JButton("Eliminar Archivo");
		btnEliminararchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String vacio="";
				if ( (textField_2.getText().equals(vacio)) || (textField_3.getText().equals(vacio)) ){
					JOptionPane.showMessageDialog(null, "Faltan completar campos");
				}
				else{
					Position<Pair<String,PositionList<String>>> pos= log.esRutaValida(textField_3.getText());
					if (pos==null){
						JOptionPane.showMessageDialog(null, "La ruta no es valida");
					}
					else{
						boolean pudeEliminar=log.eliminarArchivo(textField_2.getText(), pos);
						if( pudeEliminar){
							JOptionPane.showMessageDialog(null, "El archivo se elimino corrctamente");
							System.out.println(pos.element().getClave());
						}
						else{
							JOptionPane.showMessageDialog(null, "El archivo no se encuentra en la ruta");
							System.out.println(pos.element().getClave());
						}
						
					}
				}
			}
		});
		btnEliminararchivo.setEnabled(false);
		btnEliminararchivo.setBounds(547, 90, 152, 23);
		contentPane.add(btnEliminararchivo);
		
		JButton btnAgregardirctorio = new JButton("Agregar Directorio");
		btnAgregardirctorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String vacio="";
				if ( (textField_4.getText().equals(vacio)) || (textField_5.getText().equals(vacio)) ){
					JOptionPane.showMessageDialog(null, "Faltan completar campos");
				}
				else{
					Position<Pair<String,PositionList<String>>> pos= log.esRutaValida(textField_5.getText());
					if (pos==null){
						JOptionPane.showMessageDialog(null, "La ruta no es valida");
					}
					else{
						log.agregarDirectorio(textField_4.getText(), pos);
						textField_4.setText("");
						textField_5.setText("");
						JOptionPane.showMessageDialog(null, "El directorio se agrego correctamente");
					}
				}
			}
		});
		btnAgregardirctorio.setEnabled(false);
		btnAgregardirctorio.setBounds(547, 130, 152, 23);
		contentPane.add(btnAgregardirctorio);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//segun el valor del comboBox, muestro en determinado orden
				if (comboBox.getSelectedIndex()==0) {
					//por niveles
					textArea.setText(log.porNiveles());
				}
				else{
					if(comboBox.getSelectedIndex()==1){
						//por extension
						textArea.setText(log.porExtension());
					}
					else{
						//por profundidad
						textArea.setText(log.porProfundidad());
					}
				}
				
			}
		});
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Niveles", "Extension", "Profundidad"}));
		comboBox.setBounds(86, 262, 101, 22);
		contentPane.add(comboBox);
		
		JButton btnCantidad = new JButton("Cantidad Directorios y Archivos");
		btnCantidad.setEnabled(false);
		btnCantidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pair<String,String> cantidad= log.cantidad();
				String str= "Cantidad de Directorios:"+cantidad.getClave()+"\n"+"Cantidad de Archivos:"+cantidad.getValor();
				textArea.setText(str);
			}
		});
		btnCantidad.setBounds(207, 262, 246, 23);
		contentPane.add(btnCantidad);
		
		JButton btnMover = new JButton("Mover D1 a D2");
		btnMover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String vacio="";
				if ( (textField_6.getText().equals(vacio)) || (textField_8.getText().equals(vacio)) ){
					JOptionPane.showMessageDialog(null, "Faltan completar campos");
				}
				else{
					Position<Pair<String,PositionList<String>>> posD1= log.esRutaValida(textField_6.getText());
					Position<Pair<String,PositionList<String>>> posD2= log.esRutaValida(textField_8.getText());
					if(posD1!=null && posD2!=null ){
						if(log.esLaRaiz(textField_6.getText())){
							JOptionPane.showMessageDialog(null, "D1 no puede ser la raiz");
						}
						else{
							log.moverDirectorio(textField_6.getText(), textField_8.getText());
							textField_6.setText("");
							textField_8.setText("");
							JOptionPane.showMessageDialog(null, "D1 fue movido exitosamente a D2");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "D1 o D2 no son rutas validas");
					}
				}
			}
		});
		btnMover.setEnabled(false);
		btnMover.setBounds(547, 212, 152, 23);
		contentPane.add(btnMover);
		
		JButton btnGenerarJerarquia = new JButton("Generar Jerarquia");
		
		JButton btnEliminardirectorio = new JButton("Eliminar Directorio");
		btnEliminardirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String vacio="";
				if ( (textField_7.getText().equals(vacio)) ){
					JOptionPane.showMessageDialog(null, "Faltan completar campos");
				}
				else {
					Position<Pair<String,PositionList<String>>> pos= log.esRutaValida(textField_7.getText());
					if (pos==null){
						JOptionPane.showMessageDialog(null, "Ruta invalida");
					}
					else{
						log.eliminarDirectorio(pos);
						if (log.size()==0){
							textArea.setText("");
							btnEliminardirectorio.setEnabled(false);
							btnAgregardirctorio.setEnabled(false);
							btnEliminararchivo.setEnabled(false);
							btnAgregararchivo.setEnabled(false);
							//btnCantidad.setEnabled(false);
							comboBox.setEnabled(false);
							btnGenerarJerarquia.setEnabled(true);
							btnMover.setEnabled(false);
							JOptionPane.showMessageDialog(null, "Se han eliminado todos las carpetas y archivos existentes");
						}
						textField_7.setText("");
						JOptionPane.showMessageDialog(null, "El directorio se elimino correctamente");
					}
				}
			}
		});
		btnEliminardirectorio.setEnabled(false);
		btnEliminardirectorio.setBounds(547, 171, 152, 23);
		contentPane.add(btnEliminardirectorio);
		
		//JButton btnGenerarJerarquia = new JButton("Generar Jerarquia");
		btnGenerarJerarquia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String vacio="";
				if (textField_9.getText().equals(vacio)){
					JOptionPane.showMessageDialog(null, "Debe completar el campo");
				}
				else{
					if (log.generarJerarquia(textField_9.getText())){
						JOptionPane.showMessageDialog(null, "Se ha cargado la gerarquia exitosamente");
						textField_9.setText("");
						btnAgregararchivo.setEnabled(true);
						btnEliminararchivo.setEnabled(true);
						btnAgregardirctorio.setEnabled(true);
						btnEliminardirectorio.setEnabled(true);
						comboBox.setEnabled(true);
						btnCantidad.setEnabled(true);
						btnMover.setEnabled(true);
						btnGenerarJerarquia.setEnabled(false);
					}
					else{
						JOptionPane.showMessageDialog(null, "La gerarquia no ah podido ser cargada");
					}
				}
			}
		});
		btnGenerarJerarquia.setBounds(10, 11, 171, 23);
		contentPane.add(btnGenerarJerarquia);
								
		JLabel lblNuevoArchivo = new JLabel("Nombre Archivo");
		lblNuevoArchivo.setBounds(10, 60, 91, 14);
		contentPane.add(lblNuevoArchivo);
		
		JLabel lblAgregarEnLa = new JLabel("agregar en la ruta");
		lblAgregarEnLa.setBounds(222, 60, 101, 14);
		contentPane.add(lblAgregarEnLa);
		
		JLabel lblNombreArchivo = new JLabel("Nombre Archivo");
		lblNombreArchivo.setBounds(10, 98, 91, 14);
		contentPane.add(lblNombreArchivo);
		
		JLabel lblEliminarDeLa = new JLabel("eliminar de la ruta");
		lblEliminarDeLa.setBounds(222, 98, 131, 14);
		contentPane.add(lblEliminarDeLa);
		
		textField_3 = new JTextField();
		textField_3.setBounds(348, 95, 163, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNombreDirectorio = new JLabel("Nombre Directorio");
		lblNombreDirectorio.setBounds(10, 134, 131, 14);
		contentPane.add(lblNombreDirectorio);
		
		textField_4 = new JTextField();
		textField_4.setBounds(126, 131, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblAgregarEnLa_1 = new JLabel("agregar en la ruta");
		lblAgregarEnLa_1.setBounds(222, 134, 131, 14);
		contentPane.add(lblAgregarEnLa_1);
		
		textField_5 = new JTextField();
		textField_5.setBounds(348, 131, 163, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNombreDirectorio_1 = new JLabel("Ruta del Directorio a eliminar:");
		lblNombreDirectorio_1.setBounds(10, 175, 181, 14);
		contentPane.add(lblNombreDirectorio_1);
		
		textField_7 = new JTextField();
		textField_7.setBounds(192, 172, 319, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblListadoPor = new JLabel("Listado por");
		lblListadoPor.setBounds(10, 266, 66, 14);
		contentPane.add(lblListadoPor);
		
		JLabel lblNewLabel = new JLabel("Ruta Directorio D1:");
		lblNewLabel.setBounds(10, 216, 115, 14);
		contentPane.add(lblNewLabel);
		
		textField_6 = new JTextField();
		textField_6.setBounds(126, 213, 107, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ruta Directorio D2");
		lblNewLabel_1.setBounds(267, 216, 115, 14);
		contentPane.add(lblNewLabel_1);
		
		textField_8 = new JTextField();
		textField_8.setBounds(392, 213, 119, 20);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setBounds(206, 12, 157, 20);
		contentPane.add(textField_9);
		textField_9.setColumns(10);
						
	}
}
