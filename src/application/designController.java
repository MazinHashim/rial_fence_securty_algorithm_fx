package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class designController {
	
    @FXML
    private JFXTextField keyText;
    @FXML
    private TextArea textArea;
    

    @FXML
    private void decryptFile(ActionEvent event) {
		textArea.setText("");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\Mazin\\Desktop"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT Files","*.txt"));
		File selected = fileChooser.showOpenDialog(null);

		if(!keyText.getText().equals("")) {
			textArea.setText(decryption(selected, Integer.parseInt(keyText.getText())).toString());
		}
		else
			JOptionPane.showMessageDialog(null, "Please Enter Encrytion key");
    }

    @FXML
    private void encryptFile(ActionEvent event) {
		textArea.setText("");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\Mazin\\Desktop"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT Files","*.txt"));
		File selected = fileChooser.showOpenDialog(null);

		if(!keyText.getText().equals("")) {
			textArea.setText(encryption(selected, Integer.parseInt(keyText.getText())).toString());
		}
		else
			JOptionPane.showMessageDialog(null, "Please Enter Encrytion key");
    }
    
	public String readFromFile(File name){
		String readed = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));
			String line = null;
			StringBuilder strb = new StringBuilder();
			while((line = reader.readLine()) != null){
				strb.append(line);
			}
			readed = strb.toString();
			reader.close();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "The file "+name.getName()+" could not be found","Open File",JOptionPane.ERROR_MESSAGE);
		}
		return readed;
	}
	public String encryption(File select,int depth) {
		String readedtext;
		readedtext = readFromFile(select).toLowerCase();
		int r = depth,len = readedtext.length(),c = len/depth;
		char mat[][] = new char[r][c];
		
		int k = 0;
		
		String ciphertext = "";
		for(int i=0; i<c; i++){
			for(int j=0; j<r; j++){
				if(k!=len)
					mat[j][i] = readedtext.charAt(k++);
				else
					mat[j][i] = 'X';
			}
		}
		for(int i=0; i<r; i++){
			for(int j=0; j<c; j++){
				ciphertext += mat[i][j];
			}
		}
		writeInToFile(ciphertext,select);
		return ciphertext;
	}
	public String decryption(File select,int depth) {
		String readedtext;
		readedtext = readFromFile(select).toLowerCase();
		int r = depth,len = readedtext.length(),c = len/depth;
		char mat[][] = new char[r][c];
		
		int k = 0;
		
		String plaintext = "";

		for(int i=0; i<r; i++){
			for(int j=0; j<c; j++){
				mat[i][j] = readedtext.charAt(k++);
			}
		}
		for(int i=0; i<c; i++){
			for(int j=0; j<r; j++){
				plaintext += mat[j][i];
			}
		}
		writeInToFile(plaintext,select);
		return plaintext;
	}
	public void writeInToFile(String text,File name){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(name));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
