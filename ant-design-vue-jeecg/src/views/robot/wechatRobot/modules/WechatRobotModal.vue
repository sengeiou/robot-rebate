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
          label="微信机器人ID">
          <a-input placeholder="请输入微信机器人ID" v-decorator="['wxid', validatorRules.wxid ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="机器人昵称">
          <a-input placeholder="请输入机器人昵称" v-decorator="['nickname', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="头像">
          <a-input placeholder="请输入头像" v-decorator="['headUrl', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="背景">
          <a-input placeholder="请输入背景" v-decorator="['backgroundUrl', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="个性说明">
          <a-input placeholder="请输入个性说明" v-decorator="['signature', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="同步时间(和系统时间一致)">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'syncTime', {}]" />
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
    name: "WechatRobotModal",
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
        wxid:{rules: [{ required: true, message: '请输入微信机器人ID!' }]},
        },
        url: {
          add: "/robot/wechatRobot/add",
          edit: "/robot/wechatRobot/edit",
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
          this.form.setFieldsValue(pick(this.model,'wxid','nickname','headUrl','backgroundUrl','signature'))
		  //时间格式化
          this.form.setFieldsValue({syncTime:this.model.syncTime?moment(this.model.syncTime):null})
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
            formData.syncTime = formData.syncTime?formData.syncTime.format('YYYY-MM-DD HH:mm:ss'):null;
            
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