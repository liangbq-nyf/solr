# solr
本机搭建solr
基于spring 模式搭建

仅供参考学习使用

#下载solr
https://mirrors.cnnic.cn/apache/lucene/solr/

#安装
1、windows系统以zip结尾，linux系统以tgz结尾。

2、解压下载的solr-8.3.0.zip。

3、cmd 到solr-8.3.0\bin目录下，执行启动命令。
    
    启动命令：solr start 
    关闭命令：solr stop -all
    重启solr：solr restart –p p_num  

4、浏览器访问。

    http://localhost:8983/solr/
    
5、在solr-8.3.0\server\solr目录下，创建核心文件
    
    1）、创建任意名称的文件夹A，用于存储数据
    2）、将solr-8.3.0\server\solr\configsets\sample_techproducts_configs
    目录下的所有文件拷贝到第一步创建的文件夹A内
    3）、在solr的访问界面，选择 Core Admin 添加数据。添加存储
    数据的db，instanceDir取名与上述新建的文件名称一致，name名称
    任意。
    4）、在solr的访问界面，选择Schema，选择add filed，添加如下
    两个字段name(string),company(string)。  
    5）、选择项Document，类型选择cvs,并在数据填写域那里添加
    id,name,company
    11,张三，公司A
    12,李四，公司D
    选择最后提交选项，数据添加成功。
    6）可以实现查询信息
