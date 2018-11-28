package com.iexample.itoutaio.service;

import com.iexample.itoutaio.util.ToutiaoUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TecentService {


    // 1 初始化用户身份信息(secretId, secretKey)
    COSCredentials cred = new BasicCOSCredentials("AKID3ua1C72CMGuI5es5yc3FDvObtxlKtzUl", "A5mFTRAdqnY4KKFwObZIo6A1d1FI9qJX");
    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
    ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
    // 3 生成cos客户端
    COSClient cosClient = new COSClient(cred, clientConfig);
    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    String bucketName = "repository-1258167135";

    //private static String Tecent_IMAGE_DOMAIN = "https://repository-1258167135.cos.ap-shanghai.myqcloud.com/pics";
    private static String Tecent_IMAGE_DOMAIN = "https://repository-1258167135.cos.ap-shanghai.myqcloud.com";

    public  String uploadFile(MultipartFile file)throws IOException
    {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
// 大文件上传请参照 API 文档高级 API 上传
        //File localFile = new File("D:/photo/2202.jpg");
        //// 获取文件名
        //String fileName = file.getOriginalFilename();
        //// 获取文件后缀
        //String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        //final File excelFile = File.createTempFile(UUIDGenerator.getUUID(), prefix);
        // MultipartFile to File
        File localFile = File.createTempFile(UUID.randomUUID().toString(),fileExt);
        file.transferTo(localFile);
        String key = fileName;
       //File localFile = file.getBytes();
        // 指定要上传到 COS 上对象键
// 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        //https://repository-1258167135.cos.ap-shanghai.myqcloud.com/pics/2201.jpg
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(Tecent_IMAGE_DOMAIN+"/"+key);
        return Tecent_IMAGE_DOMAIN+"/"+key;
    }
    /*public  void downloadFile(String fileName)
    {
        // 指定要下载到的本地路径
        File downFile = new File("D:/download/1.jpg");
// 指定要下载的文件所在的 bucket 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
    }*/

}
