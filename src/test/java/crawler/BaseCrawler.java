package crawler;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaseCrawler {
	
	public Document getDocument(String url) {
		Document document = null;
		int tryCount = 0;
		try {
			do{
				document = Jsoup.connect(url).timeout(3000).get();
				tryCount++;
			} while(document == null && tryCount <3);
		} catch(Exception e) {}
		return document;
	}
	
    public void getImages(String url, String fileName) throws Exception{
        URL urlStr = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(6 * 10000);
        if (conn.getResponseCode() <10000){
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            if(data.length>0){
                FileOutputStream outputStream = new FileOutputStream("G:/eclipse/pro_testFM/src/main/webapp/static/music/img/" + fileName + ".jpg");
                outputStream.write(data);
                outputStream.close();
            }
        }
         
    }
     
    public byte[] readStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inputStream.read(buffer)) !=-1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }
}
