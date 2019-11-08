# solr + mysql
    仅供参考学习使用

#步骤
1、在solr-8.3.0\server\solr目录下，创建核心文件名称
为mysql（任意）的文件夹。
   
    （1）、solr-8.3.0\server\solr\configsets\sample_techproducts_configs
    目录下的所有文件拷贝到第一步创建的文件夹mysql内
    
2、mysql文件夹内，新建lib文件，将dist文件夹中的
solr-dataimporthandler和solr-dataimporthandler-extras包
拷贝到lib文件中，还有mysql的驱动包mysql-connector-java。

3、编辑mysql文件夹下conf中的solrconfig文件。
    
    在文件末尾添加以下内容
    <requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
        <lst name="defaults">
            <str name="config">data-config.xml</str>
        </lst>
     </requestHandler>

4、在conf目录下的data-config.xml进行如下编辑（没有则创建）：

    <?xml version="1.0" encoding="UTF-8" ?>
    <dataConfig>
        <dataSource type="JdbcDataSource"
                    driver="com.mysql.jdbc.Driver"
                    url="jdbc:mysql://localhost:3306/kjwl"
                    user="root"
                    password="root" />
        <document>
            <!-- document实体 -->
            <entity name="city" query="SELECT * FROM t_city">
                <!-- 数据库字段映射solr字段（前面为数据库字段值）
                    后面对应的名称为solr字段的信息
                 -->
                <field column="id" name="id"/>
                <field column="city_id" name="cityId"/>
                <field column="city_name" name="cityName"/>
                <field column="father_id" name="fatherId"/>
                <field column="post_code" name="postcode"/>
                <field column="keyword" name="keyword"/>
                <field column="i_framework" name="Iframework"/>
            </entity>
        </document>
    </dataConfig>
    
5、启动solr，solr start

6、新增名称为mysql的code，对应创建的核心文件夹名称。

7、新增managed-schema文件中加入相应的类型映射，可在solr首页，选择mysql的code进行添加。
    
8、添加完成。

#springboot + mysql + solr
1、IDEA创建springboot项目。

    （1）、File->new->project；
    （2）、选择“Spring Initializr”，点击next；（jdk1.8默认即可）
    （3）、完善项目信息，组名可不做修改，项目名可做修改。
    （4）、Web下勾选Spring Web Start，（网上创建springboot项目多是勾选Web选项，
        而较高版本的Springboot没有此选项，勾选Spring Web Start即可，
        2.1.8版本是Spring Web）；Template Englines勾选Thymeleaf；
        SQL勾选：MySQL Driver，JDBC API 和 MyBatis Framework三项；
        点击next；
     （5）、一直选择next，完成项目的建立。
     
 2、在启动类（application）的同级目录下，创建solrController。
    
    （1）、注解为@RestController,与@Controller的区别是，前者可以返回
    json数据信息打印在页面上，后者返回的是页面路径。
    （2）、solr web的调用方式，详见：https://lucene.apache.org/solr/guide/6_6/using-solrj.html
        String urlString = "http://localhost:8983/solr/techproducts";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();   
        zookeeper调用方式如下
        // Using a ZK Host String
        String zkHostString = "zkServerA:2181,zkServerB:2181,zkServerC:2181/solr";
        SolrClient solr = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
        
        // Using already running Solr nodes
        SolrClient solr = new CloudSolrClient.Builder().withSolrUrl("http://localhost:8983/solr").build();
        
     （3）、调用方式如下所示
        SolrQuery query = new SolrQuery();
        
        //加上这个查询条件，才可以，若查全部则取 *:*
        query.setQuery("cityName:*广*"); 
        query.setStart(1);
        query.setRows(100);  
        QueryResponse response = solr.query(query);
        SolrDocumentList documentList = response.getResults();
        for (SolrDocument entries : documentList) {
             String cityName = (String) entries.getFieldValue("cityName");
             String id = (String)entries.getFieldValue("id");
        }
 3、三者整合完成。       