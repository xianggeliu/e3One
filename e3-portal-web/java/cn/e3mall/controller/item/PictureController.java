package cn.e3mall.controller.item;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.pojo.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {
	@Value("${image.server.url}")
	private String imageServerUrl;

	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile) {
		try {
			// 1）接收上传的文件，取文件原始名称
			String filename = uploadFile.getOriginalFilename();
			// 2）从原始名称中截取文件扩展名
			String extName = filename.substring(filename.lastIndexOf("."));
			// 3）使用FastDFSClient工具类上传文件。
			FastDFSClient fds = new FastDFSClient("classpath:conf/client.conf");
			String url = fds.uploadFile(uploadFile.getBytes(), extName);
			// 4）需要把url拼接成完整的url
			url = imageServerUrl + url;
			// 5）创建一个map对象，设置返回内容
			Map map = new HashMap<>();
			map.put("error", 0);
			map.put("url", url);
			// 6）返回Map对象
			String json = JsonUtils.objectToJson(map);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			// 5）创建一个map对象，设置返回内容
			Map map = new HashMap<>();
			map.put("error", 1);
			map.put("message", e.getMessage());
			// 6）返回Map对象
			String json = JsonUtils.objectToJson(map);
			return json;
		}

	}

}
