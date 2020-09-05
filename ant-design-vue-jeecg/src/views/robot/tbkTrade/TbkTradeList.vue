<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="淘宝子订单编号">
              <a-input placeholder="请输入淘宝子订单编号" v-model="queryParam.tradeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="淘宝订单编号">
              <a-input placeholder="请输入淘宝订单编号" v-model="queryParam.tradeParentId"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="点击时间">
              <a-input placeholder="请输入点击时间" v-model="queryParam.clickTime"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="创建时间">
              <a-input placeholder="请输入创建时间" v-model="queryParam.tkCreateTime"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="淘宝付款时间">
              <a-input placeholder="请输入淘宝付款时间" v-model="queryParam.tbPaidTime"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('淘宝客订单')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <tbkTrade-modal ref="modalForm" @ok="modalFormOk"></tbkTrade-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import TbkTradeModal from './modules/TbkTradeModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "TbkTradeList",
    mixins:[JeecgListMixin],
    components: {
      TbkTradeModal
    },
    data () {
      return {
        description: '淘宝客订单管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '淘宝子订单编号',
            align:"center",
            dataIndex: 'tradeId'
           },
		   {
            title: '淘宝订单编号',
            align:"center",
            dataIndex: 'tradeParentId'
           },
		   {
            title: '点击时间',
            align:"center",
            dataIndex: 'clickTime'
           },
		   {
            title: '创建时间',
            align:"center",
            dataIndex: 'tkCreateTime'
           },
		   {
            title: '淘宝付款时间',
            align:"center",
            dataIndex: 'tbPaidTime'
           },
		   {
            title: '付款时间',
            align:"center",
            dataIndex: 'tkPaidTime'
           },
		   {
            title: '订单状态',
            align:"center",
            dataIndex: 'tkStatusText'
           },
		   {
            title: '订单状态值',
            align:"center",
            dataIndex: 'tkStatus'
           },
		   {
            title: '商品ID',
            align:"center",
            dataIndex: 'itemId'
           },
		   {
            title: '商品图片',
            align:"center",
            dataIndex: 'itemImg'
           },
		   {
            title: '商品链接',
            align:"center",
            dataIndex: 'itemLink'
           },
		   {
            title: '商品标题',
            align:"center",
            dataIndex: 'itemTitle'
           },
		   {
            title: '商品数量',
            align:"center",
            dataIndex: 'itemNum'
           },
		   {
            title: '商品单价',
            align:"center",
            dataIndex: 'itemPrice'
           },
		   {
            title: '类目名称',
            align:"center",
            dataIndex: 'itemCategoryName'
           },
		   {
            title: '掌柜旺旺',
            align:"center",
            dataIndex: 'sellerNick'
           },
		   {
            title: '店铺名称',
            align:"center",
            dataIndex: 'sellerShopTitle'
           },
		   {
            title: 'Pid',
            align:"center",
            dataIndex: 'pid'
           },
		   {
            title: '产品ID',
            align:"center",
            dataIndex: 'pubId'
           },
		   {
            title: '媒体ID',
            align:"center",
            dataIndex: 'siteId'
           },
		   {
            title: '媒体名称',
            align:"center",
            dataIndex: 'siteName'
           },
		   {
            title: '推广位ID',
            align:"center",
            dataIndex: 'adzoneId'
           },
		   {
            title: '推广位名称',
            align:"center",
            dataIndex: 'adzoneName'
           },
		   {
            title: '订单类型',
            align:"center",
            dataIndex: 'orderType'
           },
		   {
            title: '付款金额',
            align:"center",
            dataIndex: 'alipayTotalPrice'
           },
		   {
            title: '佣金比率',
            align:"center",
            dataIndex: 'totalCommissionRate'
           },
		   {
            title: '佣金金额',
            align:"center",
            dataIndex: 'totalCommissionFee'
           },
		   {
            title: '补贴金额',
            align:"center",
            dataIndex: 'subsidyFee'
           },
		   {
            title: '补贴比率',
            align:"center",
            dataIndex: 'subsidyRate'
           },
		   {
            title: '补贴类型',
            align:"center",
            dataIndex: 'subsidyType'
           },
		   {
            title: '收入比率',
            align:"center",
            dataIndex: 'incomeRate'
           },
		   {
            title: '分成比率',
            align:"center",
            dataIndex: 'pubShareRate'
           },
		   {
            title: '提成百分比',
            align:"center",
            dataIndex: 'tkTotalRate'
           },
		   {
            title: '技术服务费率',
            align:"center",
            dataIndex: 'tkCommissionRateForMediaPlatform'
           },
		   {
            title: '技术服务费',
            align:"center",
            dataIndex: 'tkCommissionFeeForMediaPlatform'
           },
		   {
            title: '付款预估收入',
            align:"center",
            dataIndex: 'pubSharePreFee'
           },
		   {
            title: '结算预估收入',
            align:"center",
            dataIndex: 'tkCommissionPreFeeForMediaPlatform'
           },
		   {
            title: '推广者身份',
            align:"center",
            dataIndex: 'tkOrderRoleText'
           },
		   {
            title: '推广者身份值',
            align:"center",
            dataIndex: 'tkOrderRole'
           },
		   {
            title: '成交平台',
            align:"center",
            dataIndex: 'terminalType'
           },
		   {
            title: '分享费率',
            align:"center",
            dataIndex: 'pubShareFee'
           },
		   {
            title: '保证时间',
            align:"center",
            dataIndex: 'tkDepositTime'
           },
		   {
            title: '啊里妈妈费率',
            align:"center",
            dataIndex: 'alimamaRate'
           },
		   {
            title: '啊里妈妈费用',
            align:"center",
            dataIndex: 'alimamaShareFee'
           },
		   {
            title: '保证价格',
            align:"center",
            dataIndex: 'depositPrice'
           },
		   {
            title: '保证时间',
            align:"center",
            dataIndex: 'tbDepositTime'
           },
		   {
            title: '平台类型',
            align:"center",
            dataIndex: 'itemPlatformTypeText'
           },
		   {
            title: '退款标识',
            align:"center",
            dataIndex: 'refundTag'
           },
		   {
            title: ' 预售',
            align:"center",
            dataIndex: 'preSell'
           },
		   {
            title: '业务标识',
            align:"center",
            dataIndex: 'tkBizTag'
           },
		   {
            title: '来源',
            align:"center",
            dataIndex: 'flowSource'
           },
		   {
            title: '商品是否点击',
            align:"center",
            dataIndex: 'supportItemClick'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/robot/tbkTrade/list",
          delete: "/robot/tbkTrade/delete",
          deleteBatch: "/robot/tbkTrade/deleteBatch",
          exportXlsUrl: "robot/tbkTrade/exportXls",
          importExcelUrl: "robot/tbkTrade/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>