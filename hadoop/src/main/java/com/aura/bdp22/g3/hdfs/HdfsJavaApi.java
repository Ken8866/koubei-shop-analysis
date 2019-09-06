package com.aura.bdp22.g3.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class HdfsJavaApi {

    public static final String DIR = "hdfs://hadoopnode:9000/tmp/tmptmp/test.txt" ;
    public static final String FILE_NAME = "hdfs://hadoopnode:9000/tmp/README.txt" ;

    public static void main(String[] args) throws Exception{
        readFile(FILE_NAME);
    }

    public static void createFile(String path) throws Exception{
        FileSystem fileSystem = null ;
        try {
            Configuration conf = new Configuration();
            Path hdfsPath = new Path(path);
            fileSystem = hdfsPath.getFileSystem(conf);
            fileSystem.mkdirs(hdfsPath);
            fileSystem.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileSystem!=null) fileSystem.close();
        }

    }

    public static void deleteFile(String path) throws Exception{
        FileSystem fileSystem = null ;
        try {
            Configuration conf = new Configuration();
            Path hdfsPath = new Path(path);
            fileSystem = hdfsPath.getFileSystem(conf);
            fileSystem.delete(hdfsPath,false);
            fileSystem.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileSystem!=null) fileSystem.close();
        }

    }

    public static void readFile(String path) throws Exception{
        FileSystem fileSystem = null ;
        FSDataInputStream fsDataInputStream = null ;
        try {
            Configuration conf = new Configuration();
            Path hdfsPath = new Path(path);
            fileSystem = hdfsPath.getFileSystem(conf);
            fsDataInputStream = fileSystem.open(hdfsPath);
            byte[] buf = new byte[1024];
            String str = null ;
            int len = 0 ;
            while((len = fsDataInputStream.read(buf))!=-1){
                str = new String(buf,0,len);
                System.out.println(str);
            }
            fsDataInputStream.close();
            fileSystem.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fsDataInputStream!=null) fsDataInputStream.close();
            if (fileSystem!=null) fileSystem.close();

        }

    }

    public static void writeFile(String path) throws Exception{
        FileSystem fileSystem = null ;
        FSDataOutputStream fsDataOutputStream = null ;
        try {
            Configuration conf = new Configuration();
            Path hdfsPath = new Path(path);
            fileSystem = hdfsPath.getFileSystem(conf);
            fsDataOutputStream = fileSystem.create(hdfsPath);
            fsDataOutputStream.writeUTF("Hello, Hadoop hdfs!!!");
            fsDataOutputStream.close();
            fileSystem.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fsDataOutputStream!=null) fsDataOutputStream.close();
            if (fileSystem!=null) fileSystem.close();

        }

    }


}
