package api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class BaseReadableApi {
	
	public String url = "https://medium.com/age-of-awareness/ivory-towers-in-a-world-without-elephants-9fe2f6e8e135#.clna4bpm0";
    
	
	private String referrer = "https://github.com/karussell/snacktory";
    private String userAgent = "Mozilla/5.0 (compatible; Snacktory; +" + referrer + ")";
    private String cacheControl = "max-age=0";
    private String language = "en-us";
    private String accept = "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
    public String charset = "UTF-8";
  //  private SCache cache;
    private Proxy proxy = null;
    private AtomicInteger cacheCounter = new AtomicInteger(0);
    private int maxTextLength = -1;
   // private ArticleTextExtractor extractor = new ArticleTextExtractor();
    private Set<String> furtherResolveNecessary = new LinkedHashSet<String>() {
        {
            add("bit.ly");
            add("cli.gs");
            add("deck.ly");
            add("fb.me");
            add("feedproxy.google.com");
            add("flic.kr");
            add("fur.ly");
            add("goo.gl");
            add("is.gd");
            add("ink.co");
            add("j.mp");
            add("lnkd.in");
            add("on.fb.me");
            add("ow.ly");
            add("plurl.us");
            add("sns.mx");
            add("snurl.com");
            add("su.pr");
            add("t.co");
            add("tcrn.ch");
            add("tl.gd");
            add("tiny.cc");
            add("tinyurl.com");
            add("tmi.me");
            add("tr.im");
            add("twurl.nl");
        }
    };
    
    public final static String UTF8 = "UTF-8";
    public final static String ISO = "ISO-8859-1";
    public Converter createConverter(String url) {
        return new Converter(url);
    }
    public InputStream fetchAsString(String urlAsString, int timeout, boolean includeSomeGooseOptions)
            throws MalformedURLException, IOException {
        HttpURLConnection hConn = createUrlConnection(urlAsString, timeout, includeSomeGooseOptions);
        hConn.setInstanceFollowRedirects(true);
        String encoding = hConn.getContentEncoding();        
        InputStream is;
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            is = new GZIPInputStream(hConn.getInputStream());
        } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
            is = new InflaterInputStream(hConn.getInputStream(), new Inflater(true));
        } else {
            is = hConn.getInputStream();
        }
        charset = extractEncoding(hConn.getContentType());
       // String res = createConverter(urlAsString).streamToString(is, enc);
        return is;
    }
    public static String extractEncoding(String contentType) {
        String[] values;
        if (contentType != null)
            values = contentType.split(";");
        else
            values = new String[0];
        String charset = "";
        for (String value : values) {
            value = value.trim().toLowerCase();
            if (value.startsWith("charset="))
                charset = value.substring("charset=".length());
        }
        if (charset.length() == 0)
            charset = UTF8;
        return charset;
    }
    
    public Proxy getProxy() {
        return (proxy != null ? proxy : Proxy.NO_PROXY);
    }

    
    protected HttpURLConnection createUrlConnection(String urlAsStr, int timeout,
            boolean includeSomeGooseOptions) throws MalformedURLException, IOException {
        URL url = new URL(urlAsStr);
        //using proxy may increase latency
        //Proxy proxy = getProxy();
        HttpURLConnection hConn = (HttpURLConnection) url.openConnection();
        hConn.setRequestProperty("User-Agent", userAgent);
        hConn.setRequestProperty("Accept", accept);

        if (includeSomeGooseOptions) {
            hConn.setRequestProperty("Accept-Language", language);
            hConn.setRequestProperty("content-charset", charset);
            hConn.addRequestProperty("Referer", referrer);
            // avoid the cache for testing purposes only?
            hConn.setRequestProperty("Cache-Control", cacheControl);
        }

        // suggest respond to be gzipped or deflated (which is just another compression)
        // http://stackoverflow.com/q/3932117
        hConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        hConn.setConnectTimeout(timeout);
        hConn.setReadTimeout(timeout);
        return hConn;
    }
    
    public void print(String str) {
    	if (!str.isEmpty())
    	System.out.println(str);
    }

}
