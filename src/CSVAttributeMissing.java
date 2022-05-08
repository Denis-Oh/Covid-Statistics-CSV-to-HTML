
public class CSVAttributeMissing extends Exception{

	public void display(String fileName){
		 System.err.println("ERROR: In file "+fileName+". Missing Attribute. File is not converted to HTML.");		 
	 }
	
}
