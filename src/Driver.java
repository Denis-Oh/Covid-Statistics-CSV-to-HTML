import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 

        File errorlog = new File("Exceptions.log");
        try {
        if(!errorlog.exists())  
            errorlog.createNewFile();
        
        PrintStream errorStream = new PrintStream(errorlog);
        
        System.setErr(errorStream);
        } catch (IOException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
        Scanner keyIn = new Scanner(System.in);        
        
        System.out.println("Enter the name of the CSV file you would like to convert to a table:");
        String filename= keyIn.nextLine();
              
		ConvertCSVtoHTML(filename);
		
        //OUTPUT
//        int i=0;
        
		System.out.println("Which file's HTML output would you like to see?");
		filename = keyIn.nextLine();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename+".html"));
			StringBuilder content = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}
			
			System.out.println("\nHTML output: ");
			System.out.println(content.toString());
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("There was an error with the file");
                        try {
			BufferedReader br = new BufferedReader(new FileReader(filename+".html"));
			StringBuilder content = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}
			
			System.out.println("\nHTML output: ");
			System.out.println(content.toString());
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("There was an error with the file");
                        System.exit(0);
		} catch (IOException ex) {
			e.printStackTrace();
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		keyIn.close();
        }
        
        
    
    /**
     * Converts a file from csv format to html
     * @param filename Name of the file without the .csv extension
     */
    static public void ConvertCSVtoHTML(String filename){
        try{
        File inFile = new File(filename+".csv");
        if(!inFile.exists()) if(inFile.createNewFile()){
        System.err.println("File "+filename+".csv could not be opened for reading.");
        System.err.println("Please check the file is readable. This program will terminate after closing any opened files");
        System.out.println("File "+filename+".csv could not be opened for reading.");
        System.out.println("Please check the file is readable. This program will terminate after closing any opened files");
        inFile.delete();
        System.exit(0);
        }
        File outFile = new File(filename+".html");
        if(!outFile.exists()) if(outFile.createNewFile()){
        outFile.delete();
        }
        Scanner fileScanner = new Scanner(new FileInputStream(inFile));
        PrintWriter output = new PrintWriter(new FileOutputStream(outFile));
        output.println("<!DOCTYPE html>\n"
        		+ "<html>\n"
        		+ "<style>\n"
        		+ "table {font-family: ariel, sans-serif;border-collapse: collapse;}\n"
        		+ "td, th {border: 1px solid #000000; text-align: left; padding:8px;}\n"
        		+ "tr:nth-child(even) {background-color: #dddddd;}\n"
        		+ "span{font-size: small}\n"
        		+ "</style>\n"
        		+ "<body>\n"
        		+ "<table>\n");

        int count = 0;
        String[] Attributes = new String[4];
        try{
        output.println("<caption>"+fileScanner.nextLine().replaceAll(",", "")+"</caption>");
        
       while(fileScanner.hasNext()){
           count++;
       String line = fileScanner.nextLine();
       Scanner stringScan = new Scanner(line);
       stringScan.useDelimiter(",");
       
       if(fileScanner.hasNext()){
           output.println("<tr>");
       for(int i=0; i<4;i++){
           String data = stringScan.next();
           if(count==1) Attributes[i]=data;
           if (data.isBlank()){
               stringScan.close();
               if (count==1) throw new CSVAttributeMissing();
               throw new CSVDataMissing(Attributes[i]);
           }
           
       output.println("<td>"+data+"</td>");
       }
       output.println("</tr>");
       } else {
           output.println("</table>");
    	   output.println("<span>"+line.replaceAll(",", "")+"</span>");
       }
       stringScan.close();
       }
        
        } catch (CSVAttributeMissing e){
            e.display(filename);
            outFile.delete();
       
       } catch (CSVDataMissing e){
       e.display(filename, count);
       }
        
        output.println("</body>");
        output.println("</html>");
        fileScanner.close();
        output.close();
        } catch (IOException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
