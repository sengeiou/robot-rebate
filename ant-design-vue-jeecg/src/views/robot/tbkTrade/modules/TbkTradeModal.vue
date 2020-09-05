<template>
  <j-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="淘宝子订单编号">
          <a-input placeholder="请输入淘宝子订单编号" v-decorator="['tradeId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="淘宝订单编号">
          <a-input placeholder="请输入淘宝订单编号" v-decorator="['tradeParentId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="点击时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'clickTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="创建时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'tkCreateTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="淘宝付款时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'tbPaidTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="付款时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'tkPaidTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单状态">
          <a-input placeholder="请输入订单状态" v-decorator="['tkStatusText', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单状态值">
          <a-input-number v-decorator="[ 'tkStatus', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品ID">
          <a-input placeholder="请输入商品ID" v-decorator="['itemId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品图片">
          <a-input placeholder="请输入商品图片" v-decorator="['itemImg', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品链接">
          <a-input placeholder="请输入商品链接" v-decorator="['itemLink', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品标题">
          <a-input placeholder="请输入商品标题" v-decorator="['itemTitle', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品数量">
          <a-input-number v-decorator="[ 'itemNum', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品单价">
          <a-input-number v-decorator="[ 'itemPrice', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="类目名称">
          <a-input placeholder="请输入类目名称" v-decorator="['itemCategoryName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="掌柜旺旺">
          <a-input placeholder="请输入掌柜旺旺" v-decorator="['sellerNick', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="店铺名称">
          <a-input placeholder="请输入店铺名称" v-decorator="['sellerShopTitle', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="Pid">
          <a-input placeholder="请输入Pid" v-decorator="['pid', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品ID">
          <a-input placeholder="请输入产品ID" v-decorator="['pubId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="媒体ID">
          <a-input placeholder="请输入媒体ID" v-decorator="['siteId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="媒体名称">
          <a-input placeholder="请输入媒体名称" v-decorator="['siteName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="推广位ID">
          <a-input placeholder="请输入推广位ID" v-decorator="['adzoneId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="推广位名称">
          <a-input placeholder="请输入推广位名称" v-decorator="['adzoneName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单类型">
          <a-input placeholder="请输入订单类型" v-decorator="['orderType', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="付款金额">
          <a-input-number v-decorator="[ 'alipayTotalPrice', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="佣金比率">
          <a-input-number v-decorator="[ 'totalCommissionRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="佣金金额">
          <a-input-number v-decorator="[ 'totalCommissionFee', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="补贴金额">
          <a-input-number v-decorator="[ 'subsidyFee', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="补贴比率">
          <a-input-number v-decorator="[ 'subsidyRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="补贴类型">
          <a-input placeholder="请输入补贴类型" v-decorator="['subsidyType', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收入比率">
          <a-input-number v-decorator="[ 'incomeRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分成比率">
          <a-input-number v-decorator="[ 'pubShareRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="提成百分比">
          <a-input-number v-decorator="[ 'tkTotalRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="技术服务费率">
          <a-input-number v-decorator="[ 'tkCommissionRateForMediaPlatform', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="技术服务费">
          <a-input-number v-decorator="[ 'tkCommissionFeeForMediaPlatform', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="付款预估收入">
          <a-input-number v-decorator="[ 'pubSharePreFee', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="结算预估收入">
          <a-input-number v-decorator="[ 'tkCommissionPreFeeForMediaPlatform', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="推广者身份">
          <a-input placeholder="请输入推广者身份" v-decorator="['tkOrderRoleText', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="推广者身份值">
          <a-input-number v-decorator="[ 'tkOrderRole', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="成交平台">
          <a-input placeholder="请输入成交平台" v-decorator="['terminalType', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分享费率">
          <a-input-number v-decorator="[ 'pubShareFee', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="保证时间">
          <a-input placeholder="请输入保证时间" v-decorator="['tkDepositTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="啊里妈妈费率">
          <a-input-number v-decorator="[ 'alimamaRate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="啊里妈妈费用">
          <a-input-number v-decorator="[ 'alimamaShareFee', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="保证价格">
          <a-input-number v-decorator="[ 'depositPrice', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="保证时间">
          <a-input placeholder="请输入保证时间" v-decorator="['tbDepositTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="平台类型">
          <a-input placeholder="请输入平台类型" v-decorator="['itemPlatformTypeText', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="退款标识">
          <a-input-number v-decorator="[ 'refundTag', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label=" 预售">
          <a-input-number v-decorator="[ 'preSell', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="业务标识">
          <a-input-number v-decorator="[ 'tkBizTag', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="来源">
          <a-input placeholder="请输入来源" v-decorator="['flowSource', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品是否点击">
          <a-input placeholder="请输入商品是否点击" v-decorator="['supportItemClick', {}]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "TbkTradeModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/robot/tbkTrade/add",
          edit: "/robot/tbkTrade/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'tradeId','tradeParentId','tkStatusText','tkStatus','itemId','itemImg','itemLink','itemTitle','itemNum','itemPrice','itemCategoryName','sellerNick','sellerShopTitle','pid','pubId','siteId','siteName','adzoneId','adzoneName','orderType','alipayTotalPrice','totalCommissionRate','totalCommissionFee','subsidyFee','subsidyRate','subsidyType','incomeRate','pubShareRate','tkTotalRate','tkCommissionRateForMediaPlatform','tkCommissionFeeForMediaPlatform','pubSharePreFee','tkCommissionPreFeeForMediaPlatform','tkOrderRoleText','tkOrderRole','terminalType','pubShareFee','tkDepositTime','alimamaRate','alimamaShareFee','depositPrice','tbDepositTime','itemPlatformTypeText','refundTag','preSell','tkBizTag','flowSource','supportItemClick'))
		  //时间格式化
          this.form.setFieldsValue({clickTime:this.model.clickTime?moment(this.model.clickTime):null})
          this.form.setFieldsValue({tkCreateTime:this.model.tkCreateTime?moment(this.model.tkCreateTime):null})
          this.form.setFieldsValue({tbPaidTime:this.model.tbPaidTime?moment(this.model.tbPaidTime):null})
          this.form.setFieldsValue({tkPaidTime:this.model.tkPaidTime?moment(this.model.tkPaidTime):null})
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.clickTime = formData.clickTime?formData.clickTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.tkCreateTime = formData.tkCreateTime?formData.tkCreateTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.tbPaidTime = formData.tbPaidTime?formData.tbPaidTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.tkPaidTime = formData.tkPaidTime?formData.tkPaidTime.format('YYYY-MM-DD HH:mm:ss'):null;
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>