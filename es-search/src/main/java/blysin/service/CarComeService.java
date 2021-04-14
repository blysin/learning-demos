package blysin.service;

import blysin.domain.CarCome;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daishaokun
 * @date 2021/4/7
 */
@Service
@Slf4j
public class CarComeService {
    private final AtomicInteger counter = new AtomicInteger(1);

    private String[] province = "云,京,冀,吉,宁,川,新,晋,桂,武,沪,津,浙,渝,湘,琼,甘,皖,粤,苏,蒙,藏,豫,贵,赣,辽,鄂,闽,陕,青,鲁,黑,民,学,航,应,急,试,使,领,港,澳,国,警,军,边,战".split(",");
    private String[] word = "A,B,C,D,E,F,G".split(",");
    private String[] lotCodes = "2007,1001,8251".split(",");


    public CarCome getOne() {
        return new CarCome(counter.getAndIncrement(), RandomStringUtils.randomAlphanumeric(8), newCarNo(), new Date(), lotCodes[RandomUtils.nextInt(0, lotCodes.length)]);
    }

    public List<CarCome> list() {
        List<CarCome> list = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            list.add(new CarCome(counter.getAndIncrement(), RandomStringUtils.randomAlphanumeric(8), newCarNo(), new Date(), lotCodes[RandomUtils.nextInt(0, lotCodes.length)]));
        }

        return list;
    }

    private String newCarNo() {
        return province[RandomUtils.nextInt(0, province.length)] + word[RandomUtils.nextInt(0, word.length)] + RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    }
}
