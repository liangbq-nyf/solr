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