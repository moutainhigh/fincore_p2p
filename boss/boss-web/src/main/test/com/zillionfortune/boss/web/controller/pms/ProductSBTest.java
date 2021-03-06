package com.zb.fincore.pms.web.test.p2p;

import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductRiskLevelEnum;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.CollectModeEnum;
import com.zb.fincore.pms.common.enums.PayChannelEnum;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
public class ProductSBTest {

    private final static String P2P_PRODUCT_REGISTER_URL="http://127.0.0.1:8080/pms/productForP2PService/registerProduct.json";

    /**
     * 货架系统 产品注册
     */
    @Test
    public void registerProductTest() {
        P2PProductRegisterRequestVo vo = new P2PProductRegisterRequestVo();
        
        //----------产品主信息表
        vo.setProductName("P2PN定期散标产品");
        vo.setProductDisplayName("P2PN定期散标产品");
        vo.setProductLineId(7L);					//必填项
        vo.setPatternCode(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode());//产品的类型代码，必填项
        vo.setSaleChannelCode(ChannelEnum.TXS.getCode());//必填项
        vo.setTotalAmount(new BigDecimal("1000"));//人工注册时必填项，自动注册不是必填项
        vo.setRiskLevel(ProductRiskLevelEnum.PRODUCT_RISK_LEVEL_1.getCode());//必填项
        vo.setLoanOrderNoSet("201808041006630");//借款订单编号集,以英文 “:”分割的
        vo.setCollectMode(CollectModeEnum.collect_package.getCode());//募集方式（01:募集包）

        vo.setPayChannel(PayChannelEnum.XL.getCode());//支付渠道（BF:宝付、XL:新浪）
        vo.setBuyWays("ZHYE,ZZB,YHK");//购买方式：ZHYE:账户余额、ZZB:至尊宝、YHK:银行卡
        vo.setNumberPeriod("101");//产品期数(产品名称后面的编号)
        
        //----------产品期限信息表
        vo.setSaleStartTime(DateUtils.parse("2018-6-15 00:00:00", "yyyy-MM-dd HH:mm:ss"));//募集起始时间
        vo.setSaleEndTime(DateUtils.parse("2018-6-20 23:00:00", "yyyy-MM-dd HH:mm:ss"));//募集截止时间
        vo.setValueTime(DateUtils.parse("2018-6-21 00:00:00", "yyyy-MM-dd HH:mm:ss"));//起息时间
        vo.setCeaseTime(DateUtils.parse("2018-6-25 00:00:00", "yyyy-MM-dd HH:mm:ss"));//止息时间
        vo.setExpectExpireTime(DateUtils.parse("2018-06-25 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期到期日期
        vo.setExpectEstablishTime(DateUtils.parse("2018-6-25 00:00:00", "yyyy-MM-dd HH:mm:ss")); //预订成立日
        vo.setExpectClearTime(DateUtils.parse("2018-06-26 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期结清时间=注册也中到期还款日
        vo.setInvestPeriod(5);//投资期限
        
        //----------产品投资收益信息表
        vo.setMinInvestAmount(new BigDecimal("100"));//起投金额，必填项
//        req.setSingleMaxInvestAmount(new BigDecimal("500"));//单笔投资限额,必填项
        vo.setMaxInvestAmount(new BigDecimal("1000"));//个人最大投资限额,非必填
        vo.setIncreaseAmount(new BigDecimal("100"));//递增金额(步长),必填项
        vo.setMinYieldRate(new BigDecimal("0.05"));//最低预期收益率,必填项
        vo.setMaxYieldRate(new BigDecimal("0.055"));//最高预期收益率,必填项

        String sendData = JsonUtils.object2Json(vo);

        System.out.println("定期产品注册请求对象：" + sendData);
        String respStr = HttpClientUtil.sendPostRequest(P2P_PRODUCT_REGISTER_URL, sendData);
        System.out.println("定期产品注册响应对象：" + JsonUtils.object2Json(response));
    }
    
    /**
     * 产品审核
     */
    @Test
    public void approveProduct() {
    	ApproveProductRequest req = new ApproveProductRequest();
    	req.setProductCode("0518050062");
        req.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        req.setApprovalSuggestion("aaa");
        req.setApprovalBy("kaiyun");
        req.setSign("A");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.approveProduct(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品上线
     */
    @Test
    public void productOnLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0518050062");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOnLine(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品下线
     */
    @Test
    public void productOffLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0218010009");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOffLine(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品列表【供运营台】
     */
    @Test
    public void queryProductListForBoss() {
    	QueryProductListRequest req = new QueryProductListRequest();
    	System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceFacade.queryProductListForBoss(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 待上架产品列表【供唐小僧】
     */
//    @Test
    public void queryProductList() {
        QueryProductListRequest req = new QueryProductListRequest();
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductList(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品列表【供交易】
     */
//    @Test
    public void queryProductListForTrade() {
        QueryProductListRequest req = new QueryProductListRequest();
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductListForTrade(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 货架系统 产品详情查询
     */
//    @Test
    public void queryProductInfo() {
        QueryProductInfoRequest req = new QueryProductInfoRequest();
        req.setProductCode("0518040007");
        req.setStatus("");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfo(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 货架系统 产品详情查询【供交易】
     */
    @Test
    public void queryProductInfoForTrade() {
    	QueryProductInfoForTradeRequest req = new QueryProductInfoForTradeRequest();
        req.setAssetPoolCode("");//资产池编号
        req.setInvestPeriod(0);//投资期限
        req.setValueTime(new Date());//产品起息时间
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfoForTrade(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(response));
    }

    
    /**
     * 供订单系统调用 库存售完通知产品
     */
    @Test
    public void noticeProductStockSellout() {
    	NoticeProductStockSelloutRequest req = new NoticeProductStockSelloutRequest();
        List<String> list = new ArrayList<>();
        list.add("0218060005");
        list.add("0518050074");
        req.setProductCodeList(list);
        System.out.println("供订单系统调用 库存售完通知产品请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.noticeProductStockSellout(req);
        System.out.println("供订单系统调用 库存售完通知产品响应对象：" + JsonUtils.object2Json(req));
    }

    /**
     * 供订单系统调用 产品作废
     */
    @Test
    public void putProductCancel() {
        CancelProductRequest req = new CancelProductRequest();
        List<String> list = new ArrayList<>();
        list.add("0218060005");
        list.add("0518050074");
        req.setProductCodeList(list);
        System.out.println("供订单系统调用 产品作废请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductCancel(req);
        System.out.println("供订单系统调用 产品作废响应对象：" + JsonUtils.object2Json(response));
    }
    
    

}
