
public class CSVDataMissing extends Exception{
	
	String type;
    public CSVDataMissing(String type){
    this.type = type;
    }
	
	public void display(String fileName, int line){
	    System.err.println("Warning: In file "+fileName+" line "+line+" is not converted to HTML : missing data: "+type);
	    }
	
}
