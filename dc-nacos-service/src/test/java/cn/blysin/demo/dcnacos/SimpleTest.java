package cn.blysin.demo.dcnacos;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daishaokun
 * @date 2020/11/24
 */
public class SimpleTest {
    @Test
    public void str() {
        String host = "https://zoskinhealth.com";
        //String urls = "/collections/shop-all/products/daily-sheer-broad-spectrum-spf-50,/collections/shop-all/products/broad-spectrum-sunscreen-spf-50,/collections/shop-all/products/firming-serum-accelerated-kit,/collections/shop-all/products/rozatrol-serum-accelerated-kit,/collections/shop-all/products/hydrafacial-rozatrol-booster,/collections/shop-all/products/hydrafacial-brightalive-booster,/collections/shop-all/products/zo-3-step-peel,/collections/shop-all/products/zo-controlled-depth-peel,/collections/shop-all/products/post-procedure-program,/collections/shop-all/products/recovery-creme,/collections/shop-all/products/hydrating-creme,/collections/shop-all/products/cellulite-control,/collections/shop-all/products/body-emulsion,/collections/shop-all/products/sunscreen-powder-broad-spectrum-light,/collections/shop-all/products/sunscreen-powder-broad-spectrum-medium,/collections/shop-all/products/sunscreen-powder-broad-spectrum-deep,/collections/shop-all/products/sunscreen-primer-spf-30,/collections/shop-all/products/smart-tone-broad-spectrum-spf-50";
        String urls = "/collections/shop-all/products/exfoliation-accelerator,/collections/shop-all/products/enzymatic-peel,/collections/shop-all/products/daily-skincare-program,/collections/shop-all/products/gentle-cleanser,/collections/shop-all/products/exfoliating-cleanser,/collections/shop-all/products/hydrating-cleanser,/collections/shop-all/products/exfoliating-polish,/collections/shop-all/products/dual-action-scrub,/collections/shop-all/products/complexion-renewal-pads,/collections/shop-all/products/oil-control-pads-acne-treatment,/collections/shop-all/products/calming-toner,/collections/shop-all/products/daily-power-defense";

        for (String url : urls.split(",")) {
            System.out.println("路径：" + url);
            String result = HttpUtil.get(host + url);
            for (String s : result.split("\n")) {
                if (s.contains("var meta")) {
                    System.out.println(s);
                }
            }
            System.out.println("--------------");
            System.out.println();
        }


        //System.out.println(result);
    }



    @Test
    public void temo() throws IOException {
        File logFile = new File("/home/blysin/Desktop/temp.json");
        File outFile = new File("/home/blysin/Desktop/out" + System.currentTimeMillis() + ".csv");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        writer.write("描述,规格,价格");
        writer.newLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            List<JSONObject> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isNotBlank(line) && line.contains("var meta")) {
                    line = line.substring(line.indexOf("{"), line.lastIndexOf("}")+1);
                    list.add(JSON.parseObject(line));
                }
            }
            String name;
            for (JSONObject object : list) {
                JSONObject product = object.getJSONObject("product");
                name = product.getString("vendor");
                JSONArray variants = product.getJSONArray("variants");
                for (Object variant : variants) {
                    JSONObject item = (JSONObject) variant;
                    name = item.getString("name");
                    name = name.substring(0, name.lastIndexOf("-"));
                    writer.write(name + "," + item.getString("public_title") + ",$" + (item.getIntValue("price") / 100));
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
