package cn.e3mall.fastDFS;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class DfsTest {
	
	@Test
	public void fastDfsTest() throws FileNotFoundException, IOException, MyException{
		// 1、创建一个配置文件，配置TrackerServer的ip地址及端口号。
		// 2、加载配置文件。
		ClientGlobal.init("F:/workspace/projectOne/e3-manager-web/src/main/resources/conf/client.conf");
		// 3、创建一个TrackerClient对象。直接new
		TrackerClient tc = new TrackerClient();
		// 4、使用TrackerClient获得一个TrackerServer对象。
		TrackerServer server = tc.getConnection();
		// 5、创建一个StorageClient对象。需要两个参数，一个是TrackerServer对象，一个是StorageServer对象（可以为null）
		StorageClient sc = new StorageClient(server, null);
		// 6、使用StorageClient对象上传文件。
		//参数一文件名 ,文件的全路径 参数2 文件的扩展名 参数三  元数据
		String[] file = sc.upload_appender_file("E:/temp/p2436379249.jpg","jpg", null);
		for (String string : file) {
			System.out.println(string);
		}
		
	}
	
	
	@Test
	public void fDfsUtilTest() throws Exception{
		FastDFSClient fdc = new FastDFSClient("F:/workspace/projectOne/e3-manager-web/src/main/resources/conf/client.conf");
		String file = fdc.uploadFile("E:/temp/p2436379249.jpg", "jsp");
		System.out.println(file);
	}
	
}
