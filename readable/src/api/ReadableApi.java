package api;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReadableApi extends BaseReadableApi{
	ExeTimeCalculator etc;
	Result mData = new Result();
	Document doc = null;
	public void startParsing (String url) {
		etc = new ExeTimeCalculator();
		etc.addTimeFrame("A");
		InputStream in = null;
		try {
			in = fetchAsString(url, 15000, true);
			etc.addTimeFrame("B");
			this.doc = Jsoup.parse(in, charset, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		etc.addTimeFrame("C");
		getArticleMetadata();
		print(mData.getAuthor() +  "- " + mData.getTitle() + " - " + mData.description );
		etc.addFrameAndPrint("D");
	}
	
	private void getArticleMetadata () {
		Elements meta = doc.head().getElementsByTag("meta");
        for (Element element : meta) {
            String elementName = element.attr("name");
            String elementproperty = element.attr("property");
            if (mData.author == null) {
                if (elementName.equals("author")){
                	mData.setAuthor(element.attr("content"));
                	continue;
                } else if (elementproperty.equals("article:author")) {
                	mData.setAuthor(element.attr("content"));
                	continue;
                }
            }
            if (mData.title == null) {
            	if (elementName.equals("title")) {
                	mData.setTitle(element.attr("content"));
                	continue;
                } else if (elementproperty.equals("og:title") ) {
                	mData.setTitle(element.attr("content"));
                	continue;
                } else if (elementproperty.equals("twitter:title")) {
                	mData.setTitle(element.attr("content"));
                	continue;
                }
            }
            if (mData.description == null) {
                if (elementName.equals("description")) {
                	mData.setDescription(element.attr("content"));
                	continue;
                } else if (elementproperty.equals("og:description")) {
                	mData.setDescription(element.attr("content"));
                	continue;
                } else if (elementproperty.equals("og:description")) {
                	mData.setDescription(element.attr("content"));
                	continue;
                }
            }
		}
        doc.head().remove();
	}
	
	private void prepareDocument () {
		
	}
}
