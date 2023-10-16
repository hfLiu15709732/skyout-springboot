package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/16 8:34
 */


@RestController
@RequestMapping("/admin/report")
@Api(tags = "B端统计报表相关接口")
@Slf4j
public class ReportController {


    @Autowired
    private ReportService reportService;




    /**
     * @description: 营业额数据统计
     * @author ${USER}
     * @date ${DATE} ${TIME}
     * @version 1.0
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计接口")
    public Result<TurnoverReportVO> turnoverReportVOResult(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {

        log.info("营业额数据统计-{}到{}",begin,end);

        TurnoverReportVO turnoverStatistics = reportService.getTurnoverStatistics(begin,end);

        return Result.success(turnoverStatistics);
    }




    /**
     * @description: 用户数量统计
     * @author ${USER}
     * @date ${DATE} ${TIME}
     * @version 1.0
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户数量统计接口")
    public Result<UserReportVO> userReportVOResult(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {

        log.info("用户数量数据统计-{}到{}",begin,end);

        UserReportVO userStatistics = reportService.getUserStatistics(begin, end);

        return Result.success(userStatistics);
    }



    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("订单数据统计：{},{}",begin,end);
        return Result.success(reportService.getOrderStatistics(begin,end));
    }

    /**
     * 销量排名top10
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    @ApiOperation("销量排名top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("销量排名top10：{},{}",begin,end);
        return Result.success(reportService.getSalesTop10(begin,end));
    }


    /**
     * @description: 下载运营数据表格
     * @author ${USER}
     * @date ${DATE} ${TIME}
     * @version 1.0
     */
    @GetMapping("/export")
    @ApiOperation("下载运营数据报表")
    public void export(HttpServletResponse response){
        //参数是为了获取response对象
        log.info("下载运营数据报表");
        reportService.exportBusinessData(response);
//        return Result.success(reportService.getSalesTop10(begin,end));
    }




}
