package com.bootdo.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
		public  static String uploadFile(MultipartFile file,String path) throws IOException {
			String  name=file.getOriginalFilename();
			String suffixName=name.substring(name.lastIndexOf("."));
			String hash=Integer.toHexString((new Random().nextInt()));
			String fileName=hash+suffixName;
			File tempFile=new File(path,fileName);
			if(!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdir();
			}if(tempFile.exists()) {
				tempFile.delete();
			}
			tempFile.createNewFile();
			file.transferTo(tempFile);
			
			return tempFile.getName();
			
		}
		
		  public static  int delFile(String path) {
			  	int i=0;
			    try {
			      File file = new File(path);
			      if (file.exists()) {	    		
			    	  	file.delete();
			    	  	i=1;
			      } 
			    } catch (Exception e) {
			      e.printStackTrace();
			    }
				return i;
			    

			  }
		  

		  public static void  downFile(HttpServletResponse response,String filename,String path ){
			  R r=new R();
			  	if (filename != null) {
			  		FileInputStream is = null;
			  		BufferedInputStream bs = null;
			  		OutputStream os = null;
            try {

                File file = new File(path);

                if (file.exists()) {
                	response.reset();
                    //设置Headers
                    response.setHeader("Content-Type","application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setHeader("Content-Disposition","attachment;filename=" +  new String( filename.getBytes("gb2312"), "ISO8859-1" ));
                    is = new FileInputStream(file);
                    bs =new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = bs.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                    os.flush();
                    os.close();
                  	r.put("code", 0);
                	r.put("msg","操作成功");
                }else{
                	r.put("code", 1);
                	r.put("msg","文件不存在");

                }

            }catch(IOException ex){
                ex.printStackTrace();

            }finally {
                try{
                    if(is != null){
                        is.close();
                    }
                    if( bs != null ){
                        bs.close();
                    }
                    if( os != null){
                        os.flush();
                        os.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
               
            }
        }
	
    }
}
