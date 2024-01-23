package cn.iocoder.yudao.module.crm.controller.admin.operatelog;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.crm.controller.admin.operatelog.vo.CrmOperateLogPageReqVO;
import cn.iocoder.yudao.module.crm.enums.common.CrmBizTypeEnum;
import cn.iocoder.yudao.module.system.api.logger.OperateLogApi;
import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogV2RespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.iocoder.yudao.module.crm.enums.LogRecordConstants.*;

@Tag(name = "管理后台 - 操作日志")
@RestController
@RequestMapping("/crm/operate-log")
@Validated
public class CrmOperateLogController {

    @Resource
    private OperateLogApi operateLogApi;

    private static final Map<Integer, String> BIZ_TYPE_MAP = new HashMap<>();

    static {
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_LEADS.getType(), CRM_LEADS_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CUSTOMER.getType(), CRM_CUSTOMER_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CONTACT.getType(), CRM_CONTACT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_BUSINESS.getType(), CRM_BUSINESS_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CONTRACT.getType(), CRM_CONTRACT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_PRODUCT.getType(), CRM_PRODUCT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_RECEIVABLE.getType(), CRM_RECEIVABLE_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(), CRM_RECEIVABLE_PLAN_TYPE);
    }

    @GetMapping("/page")
    @Operation(summary = "获得操作日志")
    @Parameter(name = "id", description = "客户编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<OperateLogV2RespDTO>> getCustomerOperateLog(@Valid CrmOperateLogPageReqVO pageReqVO) {
        OperateLogV2PageReqDTO reqDTO = new OperateLogV2PageReqDTO();
        reqDTO.setPageSize(PAGE_SIZE_NONE); // 默认不分页，需要分页需注释
        reqDTO.setBizType(BIZ_TYPE_MAP.get(pageReqVO.getBizType()));
        reqDTO.setBizId(pageReqVO.getBizId());
        return success(operateLogApi.getOperateLogPage(reqDTO));
    }

}