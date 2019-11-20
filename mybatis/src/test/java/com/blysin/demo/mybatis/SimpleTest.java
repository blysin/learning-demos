package com.blysin.demo.mybatis;

import com.alibaba.fastjson.JSON;
import com.blysin.demo.mybatis.fee.FeeStaticDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

/**
 * @author daishaokun
 * @date 2019-10-08
 */
public class SimpleTest {
    @Test
    public void json() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String str = "{\n" + "        \"id\": null,\n" + "        \"feeName\": \"测试测试\",\n" + "        \"carStyle\": 1,\n" + "        \"cardType\": 2,\n" + "        \"timeFee\": 50,\n" + "        \"timeLong\": 2,\n" + "        \"capType\": 0,\n" + "        \"capCalFeeType\": 1,\n" + "        \"freeTime\": 10,\n" + "        \"ifFreeTime\": 1,\n" + "        \"useTicket\": 1,\n" + "        \"capsIfFree\": 1,\n" + "        \"belongAreas\": \"1\",\n" + "        \"leftOrRight\": 1,\n" + "        \"ifCross\": 1,\n" + "        \"crossTimes\": 11,\n" + "        \"ifMergeCalFee\": 1,\n" + "        \"calFeeMethod\": 1,\n" + "        \"description\": \"没有什么说明\",\n" + "        \"lotCode\": 226,\n" + "        \"feeAvailables\": [\n" + "            {\n" + "                \"id\": null,\n" + "                \"fromDate\": \"2019-09-30\",\n" + "                \"endDate\": \"2019-09-30\",\n" + "                \"feeType\": 1,\n" + "\"test\":123,\n" + "                \"weekData\": \"1|2|4\",\n" + "                \"ifFreeTime\": 1,\n" + "                \"freeTime\": 10,\n" + "                \"useTicket\": 1,\n" + "                \"feeMoney\": 10,\n" + "                \"feeByType\": 1,\n" + "                \"feeTypes\": [\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明0648075503\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明9138922584\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明0823413736\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    }\n" + "                ],\n" + "                \"stime\": \"00:15:40\",\n" + "                \"etime\": \"10:15:40\"\n" + "            },\n" + "            {\n" + "                \"id\": null,\n" + "                \"fromDate\": \"2019-09-30\",\n" + "                \"endDate\": \"2019-09-30\",\n" + "                \"feeType\": 1,\n" + "                \"weekData\": \"1|2|4\",\n" + "                \"ifFreeTime\": 1,\n" + "                \"freeTime\": 10,\n" + "                \"useTicket\": 1,\n" + "                \"feeMoney\": 10,\n" + "                \"feeByType\": 1,\n" + "                \"feeTypes\": [\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明1875591912\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明7915874930\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明5637208946\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    }\n" + "                ],\n" + "                \"stime\": \"10:15:41\",\n" + "                \"etime\": \"14:15:40\"\n" + "            },\n" + "            {\n" + "                \"id\": null,\n" + "                \"fromDate\": \"2019-09-30\",\n" + "                \"endDate\": \"2019-09-30\",\n" + "                \"feeType\": 1,\n" + "                \"weekData\": \"1|2|4\",\n" + "                \"ifFreeTime\": 1,\n" + "                \"freeTime\": 10,\n" + "                \"useTicket\": 1,\n" + "                \"feeMoney\": 10,\n" + "                \"feeByType\": 1,\n" + "                \"feeTypes\": [\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明2084864913\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明5216856759\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    },\n" + "                    {\n" + "                        \"id\": null,\n" + "                        \"feeType\": \"费用说明3636206921\",\n" + "                        \"timeLong\": 0,\n" + "                        \"timeFee\": 0\n" + "                    }\n" + "                ],\n" + "                \"stime\": \"14:15:41\",\n" + "                \"etime\": \"20:15:40\"\n" + "            }\n" + "        ]\n" + "    }";
        //FeeStaticDTO feeStaticDTO = mapper.readValue(str, FeeStaticDTO.class);
        //System.out.println(feeStaticDTO);

        FeeStaticDTO feeStaticDTO = JSON.parseObject(str, FeeStaticDTO.class);
        System.out.println(feeStaticDTO);
    }
}