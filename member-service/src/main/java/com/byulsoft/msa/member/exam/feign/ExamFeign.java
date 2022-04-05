package com.byulsoft.msa.member.exam.feign;

import com.byulsoft.msa.member.exam.dto.Member;
import com.byulsoft.msa.member.exam.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="exam-feign", url="${sms.exam.url}", path = "/api/sms/exam", fallbackFactory = ExamFeignClientFallbackFactory.class)
public interface ExamFeign {

    @GetMapping("/getMessage")
    ResponseDto getMessage(@RequestParam Member param);

    @PostMapping("/setMessage")
    ResponseDto setMessage(@RequestBody Member param);

}


@Slf4j
@Component
class ExamFeignClientFallbackFactory implements FallbackFactory<ExamFeign> {

    @Override
    public ExamFeign create(Throwable cause) {
        return new ExamFeign() {

            @Override
            public ResponseDto getMessage(Member param) {
                log.error(cause.getMessage());

                ResponseDto result = new ResponseDto();

                result.setMessage(cause.getMessage());

                return result;
            }

            @Override
            public ResponseDto setMessage(Member param) {
                log.error(cause.getMessage());

                ResponseDto result = new ResponseDto();

                result.setMessage(cause.getMessage());

                return result;
            }
        };
    }
}
