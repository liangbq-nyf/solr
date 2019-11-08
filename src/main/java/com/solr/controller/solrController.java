package com.solr.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solr")
public class solrController {

    @RequestMapping("/qrySolrCity")
    public String qrySolrCity(){

        String solrResult = "";

        try {
            String urlString = "http://localhost:8983/solr/mysql";
            SolrClient solr = new HttpSolrClient.Builder(urlString).build();


            String keyword = "";

            //solr的相关配置
            SolrQuery query = new SolrQuery();
            query.setQuery("cityName:*广*"); //加上这个查询条件，才可以，若查全部则取 *:*
            //query.setQuery("*:*");
            query.setStart(1);
            query.setRows(100);
            //query.setStart(offset);
            //query.setHighlight(true);         //高亮
            //query.setHighlightSimplePre(hlPer);  //前缀
            //query.setHighlightSimplePost(hlPos);  //后缀
            //query.set("hl.fl", "cityId" + "," +"cityName");
            long startTime = System.currentTimeMillis();
            QueryResponse response = solr.query(query);
            SolrDocumentList documentList = response.getResults();
            long endTime = System.currentTimeMillis();
            for (SolrDocument entries : documentList) {
                String cityName = (String) entries.getFieldValue("cityName");
                String id = (String)entries.getFieldValue("id");
                solrResult += id+"-->"+cityName;
                System.out.println(id+"-->"+cityName);
            }

            solrResult += "</br>" + "耗时：" + ((endTime - startTime)/100 ) + "/ms";
        } catch (Exception e) {
            String rl = e.getMessage();
            System.out.println(rl);
        }
        return solrResult;
    }
}
