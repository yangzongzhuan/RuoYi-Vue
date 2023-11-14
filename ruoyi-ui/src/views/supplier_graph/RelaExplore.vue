<template>
  <div style="margin: auto;background-color: #f9f9f9;padding-top: 15px;min-height: 700px">
    <div style="background-color: white;padding: 15px">
      <div class="c-title-div">
        关系探查
      </div>
      <div style="width: 800px;margin:auto">
        <div style="font-size: 14px;text-align: left">
          在一个基于公司股权 、高管任职、专利、招投标、涉诉等关系信息形成的企业复杂关系网络中，探查任意多个企业之
          间是否存在关联关系，并根据关联路径挖掘目标企业谱系中是存在异常关联。
        </div>
        <div style="margin-top: 10px">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入企业名称，输入多个换行输入"
            v-model="textarea2">
            <template slot="prepend">企业名称：</template>
          </el-input>
        </div>
        <div style="margin-top: 10px">
          <el-form :label-position="labelPosition"  label-width="80px">
            <el-form-item label="查询层级">
              <el-radio-group v-model="radio" placeholder="请选择">
                <el-radio label="1" value="1"></el-radio>
                <el-radio label="2" value="2"></el-radio>
                <el-radio label="3" value="3"></el-radio>
                <el-radio label="4" value="4"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="企业关系">
              <el-checkbox-group v-model="checkList"  placeholder="请选择">
                <el-checkbox label="企业股东" value="1"></el-checkbox>
                <el-checkbox label="企业对外投资" value="2"></el-checkbox>
                <el-checkbox label="历史企业股东" value="3"></el-checkbox>
                <el-checkbox label="历史企业对外投资" value="4"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="人员关系">
              <el-checkbox-group v-model="checkList" placeholder="请选择" style="width: 100%">
                <el-checkbox label="自然人股东" value="1"></el-checkbox>
                <el-checkbox label="自然人对外投资" value="2"></el-checkbox>
                <el-checkbox label="自然人对外投资" value="2"></el-checkbox>
                <el-checkbox label="公司管理人员" value="2"></el-checkbox>
                <el-checkbox label="管理人员其他公司任职" value="2"></el-checkbox>
                <el-checkbox label="历史自然人股东" value="2"></el-checkbox>
                <el-checkbox label="历史自然人对外投资" value="2"></el-checkbox>
                <el-checkbox label="历史公司管理人员" value="2"></el-checkbox>
                <el-checkbox label="历史管理人员其他公司任职" value="2"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="商业关系">
              <el-checkbox-group v-model="checkList" placeholder="请选择" style="width: 100%" >
                <el-checkbox label="共同投标"></el-checkbox>
                <el-checkbox label="共同专利"></el-checkbox>
                <el-checkbox label="诉讼关系"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="潜在关系">
              <el-checkbox-group v-model="checkList" placeholder="请选择" style="width: 100%">
                <el-checkbox label="共同联系方式"></el-checkbox>
                <el-checkbox label="共同注册地址"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </div>
        <div style="margin-top: 10px">
          <el-button type="primary" @click="handleSearch">查询</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default({
  name: "RelaExplore",
  data() {
    return {
      textarea2: '',
      checkList: [],
      radio: '1',
      labelPosition: 'left'
    }
  },
  methods:{
    handleSearch() {

    }
  }
})
</script>
<style scoped lang="scss">
.c-title-div{
  border-left: 3px solid #5297F7;
  padding-left: 10px;
  height: 30px;
  line-height: 30px;
  font-weight: bolder;
  font-size: 14px;
  color: #002060;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
