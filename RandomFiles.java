import java.io.*;
import java.util.*;
import java.lang.Integer;

// "C:\Program Files\Java\jdk1.7.0_51\bin\java.exe" RandomFiles

public class RandomFiles {
  public static void main(String[] args) {

	Properties prop = new Properties();
	InputStream input = null;
	InputStream is = null;
	OutputStream os = null;
	int numFiles;

	try {
		input = new FileInputStream("config.properties");
		prop.load(input);
		
		File base = new File(prop.getProperty("baseDir"));
		File outputDir = new File(prop.getProperty("outputDir"));
		
		if(outputDir.exists()){
			System.out.println("Please delete output directory and try again.");
			System.exit(0);
		}
		
		File[] allDirs = base.listFiles(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
			return new File(current, name).isDirectory();
		  }
		});
		
		outputDir.mkdir();
		
		numFiles = Integer.parseInt(prop.getProperty("numberOfFiles"));
		Random rn = new Random();
		File[] allFiles;
		File sourceFile;
		File destFile;

		for(int i=0;i<numFiles;i++){
			allFiles = allDirs[rn.nextInt(allDirs.length)].listFiles(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
				return new File(current, name).isFile();
			  }
			});
			sourceFile = allFiles[rn.nextInt(allFiles.length)];
			destFile = new File(prop.getProperty("outputDir") + "\\" + sourceFile.getName());
			System.out.println("SOURCE:" + sourceFile.toString());
			System.out.println("DEST  :" + destFile.toString());
			is = new FileInputStream(sourceFile);
			os = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
		}

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

  }
}