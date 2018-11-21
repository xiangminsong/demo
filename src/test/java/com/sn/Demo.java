package com.sn;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Demo {

    int threadCount = 10;
    ExecutorService pool = Executors.newFixedThreadPool(threadCount);

    @Test
    public void download_img() throws Exception {
        long start = System.currentTimeMillis();
        List<String> allImgs = Lists.newArrayList();
        for (int i=1;i<=73;i++){
            List<String> imgs = getImgSrcs(i);
            allImgs.addAll(imgs);
        }
        for (String src : allImgs){
            downloadImg(src);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
    
    
    @Test
    public void down_jd() throws IOException {
    	long start = System.currentTimeMillis();
    	int i = 600000;
    	List<String> imgs = getImgSrcs(i);
    	for (String src : imgs) {
			downloadImg(src);
		}
    	System.out.println(System.currentTimeMillis()-start);
    }
   

    @Test
    public void download_img_multi() throws Exception {
        long start = System.currentTimeMillis();
        
        List<String> imgSrcMulti = getImgSrcMulti();
        
        List<List<String>> group = GroupUtil.groupByCount(imgSrcMulti, threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        System.out.println("multi--------start-----");
        for(List<String> sub : group){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (String src : sub){
                            downloadImg(src);
                        }
                    }finally {
                        latch.countDown();
                    }
                }
            });
        }
        latch.await();
        System.out.println("multi--------done-----");
        System.out.println(System.currentTimeMillis() - start);
    }

    private List<String> getImgSrcMulti() throws IOException, InterruptedException, ExecutionException {
        List<Integer> page = Lists.newArrayList();
        for (int i=1;i<=73;i++){
            page.add(i);
        }

        List<String> allImgSrcs = Lists.newArrayList();

        List<List<Integer>> lists = GroupUtil.groupByCount(page, 20);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Future<List<String>>> futures = Lists.newArrayList();
        System.out.println("------all----start-----");
        for (List<Integer> sub : lists){

            Future<List<String>> future = pool.submit(new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> subImgSrcs = Lists.newArrayList();
                    try {
                        for (Integer p : sub) {
                            List<String> imgSrcs = getImgSrcs(p);
                            subImgSrcs.addAll(imgSrcs);
                        }
                    }finally {
                        System.out.println(Thread.currentThread()+"---done---");
                        latch.countDown();
                    }
                    return subImgSrcs;
                }
            });
            futures.add(future);
        }
        latch.await();
        System.out.println("------all----done-----");
        for (Future<List<String>> fs : futures){
            allImgSrcs.addAll(fs.get());
        }

        return allImgSrcs;
    }

    @Test
    public void down(){
//    	<a href="//item.jd.com/3130047.html" target="_blank"></a>
//        downloadImg("http://s2.static.kuman.com//001/00/54/76/17815/41275bbd94e18ebd8.jpeg!km");
//          downloadImg("http://s2.static.kuman.com//001/00/51/33/17196/20315bc045084ce60.jpeg!km2");
        downloadImg("https://img13.360buyimg.com/n1/jfs/t23419/293/2666470630/229077/201d2128/5b88fce0N406faa88.jpg");
    }

    
    private void downloadImg(String src){
    	String path = "D:/img/";
    	int pos  = src.lastIndexOf("/");
//    	String fileName = src.substring(pos+1, src.length())+".jpeg";
    	String fileName = src.substring(pos+1, src.length())+".jpg";
    	
    	
		try {
			URL	url = new URL(src);
			
			DataInputStream dis = new DataInputStream(url.openStream());
			
			byte[] buffer = new byte[1024];
			
			int length;
			FileOutputStream fos = new FileOutputStream(new File(path+fileName));
			
			while((length= dis.read(buffer))>0) {
				fos.write(buffer, 0, length);
			}
			
			fos.flush();
			fos.close();
			dis.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }

    
    private List<String> getImgSrcs(int page) throws IOException{
//                                         https://item.jd.com/600000.html
//    	Document doucment = Jsoup.connect("http://www.kuman.com/mh-1005476/"+page+"/").get();
//   	Document doucment = Jsoup.connect("http://www.kuman.com/mh-1005133/"+page+"/").get();
    	String url = "https://item.jd.com/8081722.html";
        trustEveryone();
		Connection conn = HttpConnection.connect(url);
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, br");
		conn.header("Accept-Language", "zh-CN,zh;q=0.9");
		conn.header("Cache-Control", "max-age=0");
		conn.header("Connection", "keep-alive");
		conn.header("Host", "blog.maxleap.cn");
		conn.header("Upgrade-Insecure-Requests", "1");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
		Document doucment = conn.get();

		//Document doucment = Jsoup.connect(url).get();
    	Elements imgs = doucment.select("img");
    	List<String> srcs = Lists.newArrayList();
    	for (Element img : imgs) {
			String alt = img.attr("alt");
			
			if(StringUtils.isNotBlank(alt)) {
				continue;
			}
			String src = img.attr("src");
			
			srcs.add(src);
		}
		return srcs;
    	
    }
    
    /**
	 * 信任任何站点，实现https页面的正常访问
	 * 
	 */
	
	private void trustEveryone() {
        try {  
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;  
                }
            });  
  
            SSLContext context = SSLContext.getInstance("TLS");  
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
  
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
  
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];  
                }
            } }, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();  
        }
    }  

}
