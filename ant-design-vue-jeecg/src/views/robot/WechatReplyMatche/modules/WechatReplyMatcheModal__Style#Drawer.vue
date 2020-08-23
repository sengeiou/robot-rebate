<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      :closable="false"
      @close="close"
      :visible="visible"
  >

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="匹配内容">
          <a-input placeholder="请输入匹配内容" v-decorator="['content', validatorRules.content ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；">
          <a-input placeholder="请输入匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；" v-decorator="['type', validatorRules.type ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="回复配置ID">
          <a-input placeholder="请输入回复配置ID" v-decorator="['configId', {}]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "WechatReplyMatcheModal",
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
        content:{rules: [{ required: true, message: '请输入匹配内容!' }]},
        type:{rules: [{ required: true, message: '请输入匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；!' }]},
        },
        url: {
          add: "/robot/wechatReplyMatche/add",
          edit: "/robot/wechatReplyMatche/edit",
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
          this.form.setFieldsValue(pick(this.model,'content','type','configId'))
		  //时间格式化
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
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>