<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="匹配内容">
              <a-input placeholder="请输入匹配内容" v-model="queryParam.content"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；">
              <a-input placeholder="请输入匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；" v-model="queryParam.type"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="回复配置ID">
              <a-input placeholder="请输入回复配置ID" v-model="queryParam.configId"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('回复匹配')">导出</a-button>
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
    <wechatReplyMatche-modal ref="modalForm" @ok="modalFormOk"></wechatReplyMatche-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import WechatReplyMatcheModal from './modules/WechatReplyMatcheModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "WechatReplyMatcheList",
    mixins:[JeecgListMixin],
    components: {
      WechatReplyMatcheModal
    },
    data () {
      return {
        description: '回复匹配管理页面',
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
            title: '匹配内容',
            align:"center",
            dataIndex: 'content'
           },
		   {
            title: '匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；',
            align:"center",
            dataIndex: 'type'
           },
		   {
            title: '回复配置ID',
            align:"center",
            dataIndex: 'configId'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/robot/wechatReplyMatche/list",
          delete: "/robot/wechatReplyMatche/delete",
          deleteBatch: "/robot/wechatReplyMatche/deleteBatch",
          exportXlsUrl: "robot/wechatReplyMatche/exportXls",
          importExcelUrl: "robot/wechatReplyMatche/importExcel",
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