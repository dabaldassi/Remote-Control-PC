package levelcontrol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LevelControl
{
    private boolean check(float level, String os) {
	return level >=0 && level <= 100 && System.getProperty("os.name").contains(os);
    }

    private void errorOutput(Process err) {
	
	try {
	    err.getOutputStream().close();

	    String line;

	    BufferedReader stderr = new BufferedReader(new InputStreamReader(err.getErrorStream()));

	    line = stderr.readLine();

	    if(line != null) {
		do {
		    System.err.println(line);
		}while((line = stderr.readLine()) != null);
	    }

	    stderr.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
    
    public void setBrightness(float level) {
	
	try {
	    if(check(level, "Linux")) {
		errorOutput(Runtime.getRuntime().exec("./brightness.sh "+level));
	    }
	    else {
		if(check(level, "Windows")) {
		    errorOutput(Runtime.getRuntime().exec("nircmd.exe setbrightness "+level));
		}
	    }
		
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

	
	   
	
    }

    public void setVolume(float level) {
	
	try {
	    if(check(level, "Linux")) {
		errorOutput(Runtime.getRuntime().exec("amixer sset 'Master' "+level+"%"));
	    }
	    else {
		if(check(level, "Windows"))
		    errorOutput(Runtime.getRuntime().exec("nircmd.exe setsysvolume "+(level*32768/100)));
	    }			
		
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
