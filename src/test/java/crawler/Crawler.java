package crawler;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.Singer;
import model.Song;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.fabric.xmlrpc.base.Array;

import dao.HandleSongDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class Crawler extends BaseCrawler{
	
	@Autowired
	private HandleSongDao songDao;
	
	@Test
	public void insertSingerByBatch() {
		String urlStr = "http://music.migu.cn/webfront/singer/findAllSingersByCountryclass.do?country=null&gender=null&firstletter=";
		String[] firName = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for (int i = 0, size = firName.length; i < size; i++) {
			Document document = getDocument(urlStr + firName[i]);
			if (document == null) continue;
			Elements elements = document.select("li > a");
			for (int  j = 0, jSize = elements.size(); j < jSize; j++) {
				Singer singer = new Singer();
				singer.setName(elements.get(j).attr("title"));
				String imgPath = "singer-" + i + "-" + j; 
				singer.setAvatrImg("music/img/" + imgPath + ".jpg");
				
				String nextUrl = elements.get(j).attr("href");
				document = getDocument(nextUrl.replace("#", ""));
				if (document == null) continue;
				Elements tagElements = document.select("div.yk_singer_detail_info > img");
				String imgUrl = tagElements.get(0).attr("src");
				try {
					getImages(imgUrl, imgPath );
				} catch(Exception e) {
					continue;
				} 
				songDao.insertSingerByBatch(singer);
				String miguSingerId = nextUrl.substring(nextUrl.lastIndexOf("/") + 1, nextUrl.length());
				insertAlbumByBatch(miguSingerId, singer.getId(), singer.getName());
			}
		}

	}
	
	public void insertAlbumByBatch(String miguSingerId, Long singerId, String singerName) {
		try {
			int index = 1;
			Elements elements = null;
			String urlStr = "http://music.migu.cn/webfront/singer/album.do?id=" + miguSingerId + "&pagenum=";
			do{
				Document document = getDocument(urlStr + index);
				if (document == null) return;
				elements = document.select("ul > li");
				for (int i = 0, size = elements.size(); i < size; i++) {
					Album album = new Album();
					String imgPath = "album-" + singerId + "-" + index + "-" + i;
					String imgUrl = elements.get(i).select("a > img").get(0).attr("src");
					
					album.setSingerId(singerId);
					album.setSingerName(singerName);
					album.setAvatrImg("music/img/" + imgPath + ".jpg");
					
					Elements tagElements = elements.get(i).select("h2.zj_info_title > em");
					album.setName(tagElements.get(0).attr("title"));
					album.setPublishTime(Timestamp.valueOf(tagElements.get(tagElements.size() - 1).attr("title") + " 00:00:00"));
					album.setIntro(elements.get(i).select("p").get(0).attr("title"));
					try {
						getImages(imgUrl, imgPath );
					} catch(Exception e) {
						continue;
					}
					songDao.insertAlbumByBatch(album);
					
					String miguAlbumId = elements.get(i).attr("id");
					InsertSongByBatch(miguAlbumId, miguSingerId, album.getId(), singerId, singerName, album.getName(), album.getAvatrImg(), index);
				}
				index++;
			}while(elements != null && elements.size() > 0);
		} catch(Exception e) {
			System.out.println(miguSingerId + "::" + singerName + "\n" + e.getMessage() + "\n");
		}
	}
	
	public void InsertSongByBatch(String miguAlbumId, String miguSingerId, Long albumId, Long singerId, String singerName, String albumName, String imgPath, int index) {
		try {
			String url = "http://music.migu.cn/webfront/singer/albumsong.do?albumId=" + miguAlbumId + "&no=" + index + "&singerId=" + miguSingerId;
			Document document = getDocument(url);
			if (document == null) return;
			Elements elements = document.select("ul.mod_song_list");
			if (elements == null || elements.size() == 0) return;
			elements = elements.get(0).select("li");
			List<Song> songs = new ArrayList<Song>();
			for (int i = 0, size = elements.size(); i < size; i++) {
				Song song  = new Song();
				
				song.setAlbumId(albumId);
				song.setAlbumName(albumName);
				song.setSingerId(singerId);
				song.setSingerName(singerName);
				song.setName(elements.get(i).select("span.song_name").get(0).select("a").get(0).attr("title"));
				song.setAvatrImg(imgPath);
				songs.add(song);
			}
			if (songs.size() != 0)
				songDao.insertSongByBatch(songs);
		} catch(Exception e) {
			System.out.println(miguAlbumId + "::" + miguSingerId + "::" + index + "\n" + e.getMessage() + "\n");
		}
	}
	

}
