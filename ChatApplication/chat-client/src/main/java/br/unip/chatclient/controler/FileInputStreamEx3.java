package br.unip.chatclient.controler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

public class FileInputStreamEx3 {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String caminhoArquivo = "D:\\Pedro\\Downloads\\image.png";
        String nomeArquivo = "teste.png"; 
        
        FileInputStream fis = new FileInputStream(caminhoArquivo);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String line;
        
        while ((line = br.readLine()) != null) {
            builder.append(line);
        } 

//            int i = 0;
//
//            do {
//                  
//                byte[] buf = new byte[1024];
//                i = fis.read(buf);
//                                 
//                builder.append(new String(buf, StandardCharsets.UTF_8));
//            } while (i != -1);
        
//        FileOutputStream fileOutPut = new FileOutputStream("C:\\Users\\Pedro\\Desktop\\" + fileName);
//        File file = new File("C:\\Users\\Pedro\\Desktop\\" + nomeArquivo);
//        file.createNewFile();
//        FileUtils.writeStringToFile(file, builder.toString(), Charset.forName("UTF-8"));
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("TempFile2.png"))) {
        	bw.append(builder);//Internally it does aSB.toString();
        	bw.flush();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
}

