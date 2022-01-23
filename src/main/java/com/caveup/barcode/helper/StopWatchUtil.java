package com.caveup.barcode.helper;

import org.springframework.util.StopWatch;

import java.text.NumberFormat;

/**
 * @author xw80329
 * @Date 2020/9/28
 */
public class StopWatchUtil {

    /**
     * 重载打印StopWatch 的日志，因为Spring Core 5.2之后默认用的纳秒输出，不太方便
     *
     * @param sw
     * @return
     */
    public static String prettyPrint(StopWatch sw) {
        StringBuilder sb = new StringBuilder(sw.shortSummary());
        sb.append('\n');

        sb.append("---------------------------------------------\n");
        sb.append("ms         %     Task name\n");
        sb.append("---------------------------------------------\n");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(5);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
        for (StopWatch.TaskInfo task : sw.getTaskInfo()) {
            sb.append(nf.format(task.getTimeMillis())).append("  ");
            sb.append(pf.format((double) task.getTimeNanos() / sw.getTotalTimeNanos())).append("  ");
            sb.append(task.getTaskName()).append("\n");
        }
        return sb.toString();
    }
}
