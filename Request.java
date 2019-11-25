package Http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 封装请求协议
 * 
 * @author dell
 *
 */
public class Request {
	private String requestInfo;
	private String method;
	private String URL;
	private String queryInfo;
	private final String  CRLF = "\r\n";
	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}
	public Request(InputStream is) {
		byte[] datas=new byte[1024*1024];
		int len=0;
		try {
			len = is.read(datas);
			requestInfo=new String(datas,0,len);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parseRequestInfo(requestInfo);
	}
	private void parseRequestInfo(String requestInfo) {
		method=this.requestInfo.substring(0,this.requestInfo.indexOf("/")).trim();
		System.out.println("method:"+this.method);
		URL=this.requestInfo.substring(this.requestInfo.indexOf("/")+1,this.requestInfo.indexOf("HTTP/"));
		if(this.URL.indexOf("?")!=-1) {
			String[] datas=this.URL.split("\\?");
			this.URL=datas[0];
			this.queryInfo=datas[1].trim();
		}
		System.out.println("URL:"+this.URL);
		if(this.method.equals("POST")) {
			String str=this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			if(null==this.queryInfo) {
				this.queryInfo=str;
			}else {
				this.queryInfo+="&"+str;
			}
		}
		if(null==this.queryInfo) {
			this.queryInfo="";
		}
		System.out.println("queryInfo:"+this.queryInfo);
	}
	
}
