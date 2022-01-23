package com.caveup.barcode.aspect;

import com.caveup.barcode.helper.StopWatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author xw80329
 * @Date 2020/9/28
 */
@Aspect
@Component
@Slf4j
public class ExecutionTimeAdvice {

    @Around("@annotation(com.caveup.barcode.aspect.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start(point.getSignature().getName());
        Object object = point.proceed();
        sw.stop();
        log.info("Class Name: " + point.getSignature().getDeclaringTypeName() + ". Method Name: " + point.getSignature().getName());
        log.info(StopWatchUtil.prettyPrint(sw));
        return object;
    }
}
