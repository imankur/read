package api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://medium.com/age-of-awareness/ivory-towers-in-a-world-without-elephants-9fe2f6e8e135#.clna4bpm0";
		String url2 = "http://blog.wu-man.com/2012/10/introducing-jreadability-making-web.html";
		String url3 = "http://arrix.blogspot.in/2010/11/server-side-readability-with-nodejs.html";
		Readability readability = null;
		BaseReadableApi base = new BaseReadableApi();	ExeTimeCalculator etc = null;
	  //  ReadableApi rs = new ReadableApi();
		//rs.startParsing(url);
		//Readability readability = new Readability(html); // String
		try {			
			InputStream in =base.fetchAsString(url3,  15000,  true);
			etc = new ExeTimeCalculator();
			etc.addTimeFrame("A");
			 readability = new Readability(in, base.charset, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // URL
		

		readability.init();
		System.out.println(readability.outerHtml());
		etc.addFrameAndPrint("B");
		
	}

}
