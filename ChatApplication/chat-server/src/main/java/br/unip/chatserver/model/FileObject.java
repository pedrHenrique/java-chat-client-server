package br.unip.chatserver.model;

import javax.swing.ImageIcon;
import java.io.Serializable;

public class FileObject implements Serializable {
	
	// No Futuro, trocar as String pelos próprias classes dos usuários
	
	private static final long serialVersionUID = 1L;
	
	private String comando;
	
	private String fileRemetente;
	
	private String fileDestinatario;
	
	private String fileName;
	
	private String filePathOrigin;
	
	private byte[] file;
	
	private ImageIcon image;
	
	public FileObject(String fileRemetente, String fileDestinatario, String fileName, byte[] file) {
		this.fileRemetente = fileRemetente;
		this.fileDestinatario = fileDestinatario;
		this.fileName = fileName;
		this.file = file;
	}
	
	public FileObject(String fileRemetente, String fileDestinatario, String fileName, byte[] file, ImageIcon image) {
		this.fileRemetente = fileRemetente;
		this.fileDestinatario = fileDestinatario;
		this.fileName = fileName;
		this.file = file;
		this.image = image;
	}
	
	public String getFileRemetente() {
		return fileRemetente;
	}

	public void setFileRemetente(String fileRemetente) {
		this.fileRemetente = fileRemetente;
	}

	public String getFileDestinatario() {
		return fileDestinatario;
	}

	public void setFileDestinatario(String fileDestinatario) {
		this.fileDestinatario = fileDestinatario;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String name) {
		this.fileName = name;
	}

	public String getFilePathOrigin() {
		return filePathOrigin;
	}

	public void setFilePathOrigin(String filePathOrigin) {
		this.filePathOrigin = filePathOrigin;
	}

	public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
	
	public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
    
    public String getComando() {
		return comando;
	}
    
}
