package com.bootdo.app.controller;

import com.alibaba.fastjson.JSON;
import com.bootdo.app.dao.APPMapper;
import com.bootdo.app.service.impl.APPEventServiceImpl;
import com.bootdo.app.util.ImageUtil;
import com.bootdo.common.annotation.Log;
import com.bootdo.common.utils.CommonUtils;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.system.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

@RestController
@RequestMapping("/app/sys")
public class APPSysController {
    @Autowired
    APPEventServiceImpl aPPEventServiceImpl;
    @Autowired
    public APPMapper appMapper;
    public static LinkedBlockingDeque<Map<String,Object>> deque = new LinkedBlockingDeque<Map<String,Object>>();//当前的消息等待推送队列

    @Value("${bootdo.uploadPath}")
    private String uploadUrl;

    @RequestMapping(value = "/log")
    protected ResponseEntity log(HttpServletRequest request){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" app日志输出："+request.getParameter("msg"));
        return ResponseEntity.ok(R.ok().put("data",""));
    }
    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    ResponseEntity ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {

            List<SupportDeptPerson> l = aPPEventServiceImpl.getDeptPersonByUserName(username);
            if (l.size() > 0) {
                SupportDeptPerson p = aPPEventServiceImpl.getDeptPersonByUserName(username).get(0);
                subject.login(token);
                return ResponseEntity.ok(R.ok().put("userid",p.getId()).put("username",p.getName()));
            } else
                return ResponseEntity.ok(R.error("用户尚未绑定无法登陆"));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(R.error("用户或密码错误"));
        }
    }


    /**
     * 上传文件
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFile")
    protected synchronized void uploadFile(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        String pid = request.getParameter("pid");
        String from_tableanme = request.getParameter("from_tableanme");
        String file_type = request.getParameter("file_type");

        String userid = request.getParameter("userid");
        Map<String, String> map = new HashMap<String, String>();
       /* if (pid == null || pid.equals("")
                || userid == null || userid.equals("")
        ) {
            throw new Exception();
        }*/
        String path = uploadUrl+"app\\" + userid;
        File fileps = new File(path);
        if (!fileps.exists()) {
            fileps.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("filename");

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                // return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();
            // 处理文件名称
            // 获取文件数字（20位）
            String fileId = CommonUtils.getKey();
            String extNmae = getExtName(fileName);//文件类型
            String diskFileName = (extNmae == null || extNmae.isEmpty() || "null".equalsIgnoreCase(extNmae)) ? fileId : (fileId
                    + "." + extNmae);//文件名
            File dest = new File(path + "\\" + diskFileName);
            try {

                String fpath = "\\app\\" + userid + "\\" + diskFileName;

                String id = UUID.randomUUID().toString().replaceAll("-", "");
                appMapper.execInsertSql("insert into common_file(id,relation_id,file_type,file_url,file_physical_address,create_by,create_date,from_tableanme,file_name)" +
                        " values('" + id + "','" + pid + "'," + file_type + ",'" + fpath.replaceAll("\\\\", "\\\\\\\\") + "','" + dest.getPath().replaceAll("\\\\", "\\\\\\\\") + "','" + userid + "',now(),'" + from_tableanme + "','" + file.getOriginalFilename() + "')");
                file.transferTo(dest);
                map.put("result", file.getName() + "||==||" + id + "||==||" + file_type);
            } catch (IOException e) {

            }
        }
        System.out.println(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSON(map));
        response.getWriter().flush();
        response.getWriter().close();

    }

    /**
     * 附件记录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryFilelist")
    protected ResponseEntity queryFilelist(HttpServletRequest request,
                                                      HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        String id = request.getParameter("id");
        try{
        HashMap<String, Object> p = new HashMap<String, Object>();
        p.put("relation_id", id);
        List<Map<String, Object>> l = appMapper.queryFilelist(p);
        return ResponseEntity.ok(R.ok().put("data",l));
    } catch (Exception e) {
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }

    /**
     * 展示图片
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/showimg")
    public void showImg(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "image/jped");
        String issl = request.getParameter("issl");//是否显示缩略图
        List<Map<String, Object>> l = appMapper.execSelectSqlMap("select file_physical_address from common_file where id='" + request.getParameter("id") + "'");
        if (l.size() > 0) {
            String path = (String) l.get(0).get("file_physical_address");

            OutputStream toClient = response.getOutputStream();
            if (issl != null && !"".equals(issl)) {//显示缩略图
                ImageUtil.resize(path, toClient, 250, 0.5f);
                toClient.flush();
                toClient.close();
            } else {
                FileInputStream hFile = new FileInputStream(path);
                //得到文件大小
                int i = hFile.available();
                byte data[] = new byte[i];
                //读数据
                hFile.read(data);
                //输出数据
                toClient.write(data);
                toClient.flush();
                toClient.close();
                hFile.close();
            }
        }

    }

    /**
     * 显示视频流
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/showvideo")
    public void showvideo(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "video/mp4");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Accept-Ranges", "bytes");
        List<Map<String, Object>> l = appMapper.execSelectSqlMap("select file_physical_address from common_file where id='" + request.getParameter("id") + "'");
        if (l.size() > 0) {
            try {
                FileInputStream in = new FileInputStream((String) l.get(0).get("file_physical_address"));
                ServletOutputStream out = response.getOutputStream();
                int length;
                byte[] buffer = new byte[4 * 1024];
                // 向前台输出视频流
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (FileNotFoundException e) {
                System.out.println("文件读取失败, 文件不存在");
            }
        }

    }

    /**
     * 删除图片/视频
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delimg")
    protected ResponseEntity delImg(HttpServletRequest request,
                                         HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        String id = request.getParameter("id");
        try{
        if (id != null && !id.isEmpty()) {
            List<Map<String, Object>> file = appMapper.execSelectSqlMap("select file_physical_address from common_file where id='" + id + "'");
            if (file != null && file.size() > 0) {
                System.out.println(file.get(0));
                File filetp = new File((String) file.get(0).get("file_physical_address"));
                filetp.delete();
                appMapper.execDeleteSql("  delete from  common_file where id='"+id+"'");
                return ResponseEntity.ok(R.ok());
            }else {
                return ResponseEntity.ok(R.error("删除失败"));
            }
        } else {
            return ResponseEntity.ok(R.error("删除失败"));
        }
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    private String getExtName(String fileName) {
        String extName = fileName == null ? "" : fileName;
        int lastDotPos = fileName.lastIndexOf("."), len = fileName.length();
        if (lastDotPos == len - 1 || lastDotPos == -1) {
            return "";
        } else {
            return extName.substring(lastDotPos + 1);
        }
    }

    /**
     * ajax长链接
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/langLink")
    protected Map<String, Object> langLink(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        String userid=request.getParameter("userid");
        Map<String, Object> result =new HashMap<String,Object>();

        Long nt=new Date().getTime();
        //超时时间30秒，若马上有消息就里面返回
        while(new Date().getTime()<(nt+30000)){
            Thread.sleep(1000);
            //到同步队列里取消息
            if(APPSysController.deque.size()>0){
                //Map<String,Object> info= APPSysController.deque.take();

                break;
            }
        }
        return result;
    }
    @RequestMapping(value = "/test")
    protected ResponseEntity test(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        UserDO userDo= ShiroUtils.getUser();
        System.out.println(userDo);
        if(userDo==null)
        {
            return ResponseEntity.ok(R.error("请先登录！"));
        }
        Map<String, Object> info = new HashMap<>();
        return ResponseEntity.ok(R.ok().put("data",""));
    }

    public static void putmsglink(String msg,String type,String fsusername,String jsusername){
        Map<String, Object> info = new HashMap<>();
        info.put("msg",msg.substring(0,14)+"...");
        info.put("type",type);
        info.put("fsusername",fsusername);
        info.put("jsusername",jsusername);
        info.put("times",new Date());
        APPSysController.deque.push(info);
    }
}
