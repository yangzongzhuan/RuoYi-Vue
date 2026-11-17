package com.ruoyi.web.controller.store;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.store.mapper.StoreMemberMapper;
import com.ruoyi.store.mapper.StoreOrderMapper;
import com.ruoyi.store.mapper.StoreProductMapper;
import com.ruoyi.store.mapper.StoreStockRecordMapper;

/**
 * 数据统计 Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/statistics")
public class StoreStatisticsController extends BaseController
{
    @Autowired
    private StoreOrderMapper orderMapper;

    @Autowired
    private StoreProductMapper productMapper;

    @Autowired
    private StoreMemberMapper memberMapper;

    @Autowired
    private StoreStockRecordMapper stockRecordMapper;

    /**
     * 营业概况
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:overview')")
    @GetMapping("/overview")
    public AjaxResult overview()
    {
        Map<String, Object> result = new HashMap<>();

        // 今日订单数和金额
        Map<String, Object> today = orderMapper.selectTodayOrderStats();
        result.put("todayOrderCount", today != null ? today.get("orderCount") : 0);
        result.put("todayOrderAmount", today != null ? today.get("orderAmount") : BigDecimal.ZERO);

        // 本月订单数和金额
        Map<String, Object> month = orderMapper.selectMonthOrderStats();
        result.put("monthOrderCount", month != null ? month.get("orderCount") : 0);
        result.put("monthOrderAmount", month != null ? month.get("orderAmount") : BigDecimal.ZERO);

        // 会员总数
        Long memberCount = memberMapper.selectMemberCount();
        result.put("memberCount", memberCount);

        // 商品总数
        Long productCount = productMapper.selectProductCount();
        result.put("productCount", productCount);

        // 低库存商品数
        Long lowStockCount = productMapper.selectLowStockCount();
        result.put("lowStockCount", lowStockCount);

        return AjaxResult.success(result);
    }

    /**
     * 近7天营业额趋势
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:overview')")
    @GetMapping("/orderTrend")
    public AjaxResult orderTrend()
    {
        List<Map<String, Object>> list = orderMapper.selectLast7DaysOrderStats();
        return AjaxResult.success(list);
    }

    /**
     * 支付方式占比
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:overview')")
    @GetMapping("/payTypeStats")
    public AjaxResult payTypeStats()
    {
        List<Map<String, Object>> list = orderMapper.selectPayTypeStats();
        return AjaxResult.success(list);
    }

    /**
     * 商品销售排行 TOP10
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:product')")
    @GetMapping("/productRank")
    public AjaxResult productRank()
    {
        List<Map<String, Object>> list = orderMapper.selectProductSalesRank();
        return AjaxResult.success(list);
    }

    /**
     * 会员消费分析
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:member')")
    @GetMapping("/memberStats")
    public AjaxResult memberStats()
    {
        Map<String, Object> result = new HashMap<>();

        // 会员等级分布
        List<Map<String, Object>> levelDistribution = memberMapper.selectMemberLevelDistribution();
        result.put("levelDistribution", levelDistribution);

        // 近7天新增会员
        List<Map<String, Object>> newMemberTrend = memberMapper.selectLast7DaysNewMemberStats();
        result.put("newMemberTrend", newMemberTrend);

        // 会员消费排行 TOP10
        List<Map<String, Object>> memberConsumeRank = memberMapper.selectMemberConsumeRank();
        result.put("memberConsumeRank", memberConsumeRank);

        return AjaxResult.success(result);
    }

    /**
     * 库存统计
     */
    @PreAuthorize("@ss.hasPermi('store:statistics:stock')")
    @GetMapping("/stockStats")
    public AjaxResult stockStats()
    {
        Map<String, Object> result = new HashMap<>();

        // 库存总值
        BigDecimal stockValue = productMapper.selectTotalStockValue();
        result.put("stockValue", stockValue != null ? stockValue : BigDecimal.ZERO);

        // 库存预警商品列表
        List<Map<String, Object>> lowStockList = productMapper.selectLowStockList();
        result.put("lowStockList", lowStockList);

        // 近7天出入库统计
        List<Map<String, Object>> stockTrend = stockRecordMapper.selectLast7DaysStockStats();
        result.put("stockTrend", stockTrend);

        return AjaxResult.success(result);
    }
}
