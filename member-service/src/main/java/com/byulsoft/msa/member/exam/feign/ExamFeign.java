package com.byulsoft.msa.member.exam.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@FeignClient(name="exam-feign", url="${sms.exam.url}", path = "/api/sms/exam", fallbackFactory = ExamFeignClientFallbackFactory.class)
public interface ExamFeign {

    @GetMapping("/getMessage")
    Map<String, Object> getTicket(@RequestParam Map<String, Object> param);

    @PostMapping("/setMessage")
    Map<String, Object> setTicket(@RequestBody Map<String, Object> param);

}


@Slf4j
@Component
class ExamFeignClientFallbackFactory implements FallbackFactory<ExamFeign> {

    @Override
    public ExamFeign create(Throwable cause) {
        return new ExamFeign() {

            @Override
            public Map<String, Object> getTicket(Map<String, Object> param) {
                log.error(cause.getMessage());

                Map<String, Object> result = new HashMap<String, Object>();

                result.put("result" , false);
                result.put("resultMessage" , cause.getMessage());

                return result;
            }

            @Override
            public Map<String, Object> setTicket(Map<String, Object> param) {
                log.error(cause.getMessage());

                Map<String, Object> result = new HashMap<String, Object>();

                result.put("result" , false);
                result.put("resultMessage" , cause.getMessage());

                return result;
            }
        };
    }
}
