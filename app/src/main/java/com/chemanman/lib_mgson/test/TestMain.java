package com.chemanman.lib_mgson.test;

import com.chemanman.lib_mgson.MGson;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/7 9:05
 * package：com.paincker.utils
 * version：1.0
 * <p>description：  测试故意把格式搞错的json解析--->结果完美            </p>
 */
public class TestMain {
    public static void main(String[] args) {
        String jsonS  = "{\n" +
                "    \"resultData\": {\n" +
                "        \"result\": [],\n" +
                "        \"detail\": {\n" +
                "            \"area\": \"\",\n" +
                "            \"areaProvince\": \"\",\n" +
                "            \"beginTime\": \"\",\n" +
                "            \"brandImg\": \"https://static.youtoo365.com/app/im/car/feng_guang.png\",\n" +
                "            \"carName\": \"\",\n" +
                "            \"check\": \"\",\n" +
                "            \"cityId\": \"\",\n" +
                "            \"content\": 122,\n" +
                "            \"updateTime\": \"2019-05-05 08:58:56\",\n" +
                "            \"updaterId\": \"\",\n" +
                "            \"userImg\": \"https://static002.youtoo365.com//app/im/hd/1c37994c-59bd-482b-bc43-04673c9ed5f0.png\",\n" +
                "            \"userImgs\": null,\n" +
                "            \"userName\": \"0665\",\n" +
                "            \"versionId\": \"\",\n" +
                "            \"virtualPraisedCount\": \"\",\n" +
                "            \"virtualReplyCount\": \"\",\n" +
                "            \"weight\": false\n" +
                "        }\n" +
                "    },\n" +
                "    \"detail\": \"成功\",\n" +
                "    \"message\": \"成功\",\n" +
                "    \"isSuccess\": true\n" +
                "}";
        TestData testData = MGson.newGson().fromJson(jsonS,TestData.class);
        TestData.ResultDataBean.ResultBean result = testData.getResultData().getResult();
//        List<TestData.ResultDataBean.ResultBean.ContentBean> content = result.getContent();
        TestData.ResultDataBean.DetailBean detail = testData.getResultData().getDetail();
        int weight = detail.getWeight();
        System.out.println(detail.getContent()+" ____ "+" ____ "+weight);
    }
}
